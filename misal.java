import org.cloudbus.cloudsim.allocationpolicies.VmAllocationPolicyFirstFit;
import org.cloudbus.cloudsim.brokers.DatacenterBroker;
import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple;
import org.cloudbus.cloudsim.cloudlets.Cloudlet;
import org.cloudbus.cloudsim.cloudlets.CloudletSimple;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.datacenters.Datacenter;
import org.cloudbus.cloudsim.datacenters.DatacenterSimple;
import org.cloudbus.cloudsim.hosts.Host;
import org.cloudbus.cloudsim.hosts.HostSimple;
import org.cloudbus.cloudsim.resources.Pe;
import org.cloudbus.cloudsim.resources.PeSimple;
import org.cloudbus.cloudsim.schedulers.cloudlet.CloudletSchedulerSpaceShared;
import org.cloudbus.cloudsim.utilizationmodels.UtilizationModelDynamic;
import org.cloudbus.cloudsim.utilizationmodels.UtilizationModelFull;
import org.cloudbus.cloudsim.vms.Vm;
import org.cloudbus.cloudsim.vms.VmSimple;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BatchRunner — uji coba skala dataset untuk 3 algoritma penjadwalan.
 *
 * Konfigurasi infrastruktur (datacenter, host, VM) diatur lewat
 * DC_SPECS[] dan VM_SPECS[] di bagian atas kelas.
 * Untuk mengubah jumlah/spek datacenter atau VM, cukup edit bagian
 * KONFIGURASI tanpa menyentuh logika simulasi di bawahnya.
 *
 * VM didistribusikan bergiliran ke seluruh datacenter; kebijakan First-Fit
 * menentukan host di dalam datacenter yang dituju.
 *
 * Uji coba: n = MIN_N..MAX_N (kelipatan STEP_N), tiap n diulang 3 kali
 * (seed dataset 1/2/3), hasil diekspor ke hasil/results.csv.
 */
public class BatchRunner {
    // =========================================================
    // [KONFIGURASI] DATACENTER & HOST
    // Tambah/hapus baris untuk mengubah jumlah datacenter.
    // Setiap baris = 1 datacenter dengan spek hostnya masing-masing.
    //   kolom: hostCount | hostPes | hostMips | hostRam | hostBw | hostStorage
    // =========================================================
    static final DatacenterSpec[] DC_SPECS = {
        new DatacenterSpec( 2,          8,        100_000,  32_000,  1_000_000,  1_000_000),
        new DatacenterSpec( 3,          4,         50_000,  16_000,  1_000_000,  1_000_000), // contoh DC ke-2
    };

    // =========================================================
    // [KONFIGURASI] VM
    // Tambah/hapus baris untuk mengubah jumlah & tipe VM.
    // Setiap baris = 1 VM.
    //   kolom: mips  | vCpu | ram   | bw     | size
    // Jumlah VM yang dibuat mengikuti jumlah baris di VM_SPECS.
    // =========================================================
    static final VmSpec[] VM_SPECS = {
        new VmSpec(     1_000,   2,    4_096,  10_000,  10_000),  // VM-0 Small
        new VmSpec(     2_500,   2,    4_096,  10_000,  10_000),  // VM-1 Medium
        new VmSpec(     5_000,   2,    4_096,  10_000,  10_000),  // VM-2 Large
        new VmSpec(     7_500,   4,    4_096,  10_000,  10_000),  // VM-3 XLarge
    };


     // =========================================================
    // [KONFIGURASI] BATAS PENGUJIAN JUMLAH TASK (n)
    // =========================================================
    static final int MIN_N  = 9_000;
    static final int MAX_N  = 10_000;
    static final int STEP_N = 100;


    // =========================================================
    // SPEC CLASSES — tidak perlu diubah
    // =========================================================

    /** Spesifikasi 1 datacenter beserta host-host di dalamnya. */
    static class DatacenterSpec {
        final int    hostCount, hostPes;
        final double hostMips;
        final long   hostRam, hostBw, hostStorage;

        DatacenterSpec(int hostCount, int hostPes, double hostMips,
                       long hostRam, long hostBw, long hostStorage) {
            this.hostCount   = hostCount;
            this.hostPes     = hostPes;
            this.hostMips    = hostMips;
            this.hostRam     = hostRam;
            this.hostBw      = hostBw;
            this.hostStorage = hostStorage;
        }
    }

    /** Spesifikasi 1 VM. */
    static class VmSpec {
        final double mips;
        final int    vCpu;
        final long   ram, bw, size;

        VmSpec(double mips, int vCpu, long ram, long bw, long size) {
            this.mips = mips;
            this.vCpu = vCpu;
            this.ram  = ram;
            this.bw   = bw;
            this.size = size;
        }
    }

 

    public static void main(String[] args) throws IOException {
        validateInfrastructure();
        String dataDir = args.length > 0 ? args[0] : "Dataset-Sintetik";
        String outFile = args.length > 1 ? args[1] : "results.csv";

        // List<String> csvFiles = Arrays.asList(
        //                         new java.io.File(dataDir, "synthetic_seed1.csv").getPath(),
        //         new java.io.File(dataDir, "synthetic_seed2.csv").getPath(),
        //         new java.io.File(dataDir, "synthetic_seed3.csv").getPath());
        // List<int[]> runPlan = buildRunPlan(csvFiles.size());

       List<String> csvFiles = Arrays.asList(
                Paths.get(dataDir, "synthetic_seed1.csv").toString(),
                Paths.get(dataDir, "synthetic_seed2.csv").toString(),
                Paths.get(dataDir, "synthetic_seed3.csv").toString());
        List<int[]> runPlan = buildRunPlan(csvFiles.size());

        try (PrintWriter writer = new PrintWriter(new FileWriter(outFile))) {
            writer.println("algorithm,n_tasks,rep,dataset_file,makespan,degree_of_imbalance,utilization,throughput,cloudlet_success,total_vm_mips");
            for (int[] plan : runPlan) {
                for (String algo : new String[]{"MCT", "FCFS", "MINMIN"}) {
                    String row = runOnce(algo, plan[0], csvFiles.get(plan[1]));
                    if (row != null) writer.println(row);
                }
                writer.flush();
                System.out.printf("[PROGRES] selesai n=%d rep=%d%n", plan[0], plan[1] + 1);
            }
        }
        System.out.println("[SELESAI] Hasil lengkap tersimpan di " + outFile);
    }

    /** Pasangan (n, rep) untuk seluruh sweep sesuai batas konfigurasi, 3 repetisi. */
    static List<int[]> buildRunPlan(int numSeeds) {
        List<int[]> plan = new ArrayList<>();
        for (int rep = 0; rep < 3; rep++) {
            int seed = rep % numSeeds;
            for (int n = MIN_N; n <= MAX_N; n += STEP_N) {
                plan.add(new int[]{n, seed});
            }
        }
        return plan;
    }

    static void validateInfrastructure() {
        if (DC_SPECS.length == 0 || VM_SPECS.length == 0) {
            throw new IllegalStateException("Minimal harus ada satu datacenter dan satu VM.");
        }

        long[][][] free = new long[DC_SPECS.length][][];
        for (int d = 0; d < DC_SPECS.length; d++) {
            DatacenterSpec dc = DC_SPECS[d];
            if (dc.hostCount <= 0 || dc.hostPes <= 0 || dc.hostMips <= 0
                    || dc.hostRam <= 0 || dc.hostBw <= 0 || dc.hostStorage <= 0) {
                throw new IllegalStateException("Spesifikasi DC " + (d + 1) + " harus positif.");
            }
            free[d] = new long[dc.hostCount][4];
            for (int h = 0; h < dc.hostCount; h++) {
                free[d][h] = new long[]{dc.hostPes, dc.hostRam, dc.hostBw, dc.hostStorage};
            }
        }

        for (int i = 0; i < VM_SPECS.length; i++) {
            VmSpec vm = VM_SPECS[i];
            if (vm.mips <= 0 || vm.vCpu <= 0 || vm.ram <= 0 || vm.bw <= 0 || vm.size <= 0) {
                throw new IllegalStateException("Spesifikasi VM-" + i + " harus positif.");
            }

            boolean allocated = false;
            for (int attempt = 0; attempt < DC_SPECS.length && !allocated; attempt++) {
                int dcIndex = (i % DC_SPECS.length + attempt) % DC_SPECS.length;
                DatacenterSpec dc = DC_SPECS[dcIndex];
                if (dc.hostMips < vm.mips) {
                    continue;
                }

                for (long[] host : free[dcIndex]) {
                    if (host[0] >= vm.vCpu && host[1] >= vm.ram
                            && host[2] >= vm.bw && host[3] >= vm.size) {
                        host[0] -= vm.vCpu;
                        host[1] -= vm.ram;
                        host[2] -= vm.bw;
                        host[3] -= vm.size;
                        allocated = true;
                        break;
                    }
                }
            }

            if (!allocated) {
                throw new IllegalStateException(
                        "VM-" + i + " tidak muat di datacenter mana pun; periksa PE, MIPS per PE, RAM, BW, dan storage.");
            }
        }
    }


    // =========================================================
    // HELPER: Pembuatan infrastruktur
    // =========================================================

    /**
     * Membuat daftar Host untuk satu datacenter sesuai spesifikasinya.
     * Setiap host memiliki hostPes buah core CPU masing-masing hostMips MIPS.
     */
    static List<Host> buatHostList(DatacenterSpec spec) {
        List<Host> hostList = new ArrayList<>();
        for (int h = 0; h < spec.hostCount; h++) {
            List<Pe> peList = new ArrayList<>();
            for (int j = 0; j < spec.hostPes; j++)
                peList.add(new PeSimple(spec.hostMips));
            hostList.add(new HostSimple(spec.hostRam, spec.hostBw, spec.hostStorage, peList));
        }
        return hostList;
    }

    /**
     * Membuat daftar VM dari VM_SPECS.
     * Setiap VM menggunakan CloudletSchedulerSpaceShared (maksimal satu task per PE).
     */
    static List<Vm> buatVmList() {
        List<Vm> vmList = new ArrayList<>();
        for (VmSpec spec : VM_SPECS) {
            Vm vm = new VmSimple(spec.mips, spec.vCpu);
            vm.setRam(spec.ram).setBw(spec.bw).setSize(spec.size);
            vm.setCloudletScheduler(new CloudletSchedulerSpaceShared());
            vmList.add(vm);
        }
        return vmList;
    }


     // =========================================================
    // SIMULASI UTAMA
    // =========================================================

    static String runOnce(String algo, int n, String csvFile) {
        List<Long> lengths = readCsv(csvFile, n);
        if (lengths.isEmpty()) {
            System.out.println("[ERROR] CSV kosong/gagal dibaca: " + csvFile);
            return null;
        }

        CloudSim simulation = new CloudSim();

        // --- Buat semua datacenter dari DC_SPECS ---
        List<Datacenter> datacenters = new ArrayList<>();
        for (DatacenterSpec spec : DC_SPECS) {
            datacenters.add(new DatacenterSimple(
                simulation,
                buatHostList(spec),
                new VmAllocationPolicyFirstFit()));
        }

        DatacenterBroker broker = new DatacenterBrokerSimple(simulation);

        List<Vm> vmList = buatVmList();
        Map<Long, Integer> dcAttempts = new HashMap<>();
        Map<Long, Integer> vmIndexes = new HashMap<>();
        broker.setDatacenterMapper((lastDatacenter, vm) -> {
            int attempt = dcAttempts.getOrDefault(vm.getId(), 0);
            if (attempt >= datacenters.size()) {
                return Datacenter.NULL;
            }

            Integer vmIndex = vmIndexes.get(vm.getId());
            if (vmIndex == null) {
                throw new IllegalStateException("Indeks VM tidak ditemukan untuk VM " + vm.getId());
            }
            int preferredIndex = vmIndex % datacenters.size();
            int targetIndex = (preferredIndex + attempt) % datacenters.size();
            Datacenter target = datacenters.get(targetIndex);
            if (attempt > 0) {
                int previousIndex = (preferredIndex + attempt - 1) % datacenters.size();
                System.out.printf(
                        "[FALLBACK] VM %d tidak muat di DC %d; mencoba DC %d%n",
                        vm.getId(),
                        datacenters.get(previousIndex).getId(),
                        target.getId());
            }
            dcAttempts.put(vm.getId(), attempt + 1);
            return target;
        });

        final boolean[] vmAllocated = new boolean[vmList.size()];
        boolean[] datacenterUsed = new boolean[datacenters.size()];
        for (int i = 0; i < vmList.size(); i++) {
            final int vmIndex = i;
            vmList.get(i).addOnHostAllocationListener(
                    info -> {
                        vmAllocated[vmIndex] = true;
                        int dcIndex = datacenters.indexOf(info.getHost().getDatacenter());
                        if (dcIndex >= 0) {
                            datacenterUsed[dcIndex] = true;
                        }
                    });
        }
        broker.submitVmList(vmList);
        for (int i = 0; i < vmList.size(); i++) {
            vmIndexes.put(vmList.get(i).getId(), i);
        }

        List<Cloudlet> cloudletList = new ArrayList<>();
        for (long len : lengths) {
            cloudletList.add(new CloudletSimple(len, 1)
                    .setUtilizationModelCpu(new UtilizationModelFull()));
        }

        // ==== Penjadwalan: hitung mapping task->VM/PE di luar CloudSim ====
        double[][] readyByPe = new double[vmList.size()][];
        double[] vmBusy = new double[vmList.size()];
        for (int i = 0; i < vmList.size(); i++) {
            readyByPe[i] = new double[(int) vmList.get(i).getNumberOfPes()];
        }
        switch (algo) {
            case "FCFS" -> fcfsAssign(cloudletList, vmList, readyByPe, vmBusy);
            case "MCT" -> mctAssign(cloudletList, vmList, readyByPe, vmBusy);
            case "MINMIN" -> minMinAssign(cloudletList, vmList, readyByPe, vmBusy);
            default -> throw new IllegalArgumentException("Algoritma tidak dikenal: " + algo);
        }

        for (Cloudlet cloudlet : cloudletList) {
            double resourceShare = 0.99 / cloudlet.getVm().getNumberOfPes();
            cloudlet.setUtilizationModelRam(new UtilizationModelDynamic(resourceShare));
            cloudlet.setUtilizationModelBw(new UtilizationModelDynamic(resourceShare));
        }

        broker.submitCloudletList(cloudletList);
        simulation.start();

        // Pastikan setiap VM berhasil dialokasikan pada salah satu datacenter.
        for (boolean allocated : vmAllocated) {
            if (!allocated) {
                System.out.printf("[PERINGATAN] VM tidak berhasil dialokasikan (algo=%s, n=%d)%n", algo, n);
                break;
            }
        }

        for (int i = 0; i < datacenterUsed.length; i++) {
            if (!datacenterUsed[i]) {
                System.out.printf(
                        "[PERINGATAN] Datacenter %d tidak menerima VM (algo=%s, n=%d)%n",
                        datacenters.get(i).getId(), algo, n);
            }
        }

        List<Cloudlet> finished = broker.getCloudletFinishedList();
        if (finished.size() != cloudletList.size()) {
            throw new IllegalStateException(String.format(
                    "Simulasi %s n=%d: hanya %d dari %d cloudlet yang selesai.",
                    algo, n, finished.size(), cloudletList.size()));
        }

        double makespan = 0;
        double busySum = 0;
        for (Cloudlet c : finished) {
            makespan = Math.max(makespan, c.getFinishTime());
            busySum += c.getActualCpuTime();
        }

        // Degree of Imbalance = (max_j T_j - min_j T_j) / mean_j T_j,
        // dengan T_j = total waktu CPU aktif per VM, dijumlahkan dari seluruh PE.
        double tMax = vmBusy[0], tMin = vmBusy[0], tSum = 0;
        long totalPes = 0;
        for (int i = 0; i < vmBusy.length; i++) {
            double t = vmBusy[i];
            tMax = Math.max(tMax, t);
            tMin = Math.min(tMin, t);
            tSum += t;
            totalPes += vmList.get(i).getNumberOfPes();
        }
        double tAvg = tSum / vmBusy.length;
        double di = tAvg > 0 ? (tMax - tMin) / tAvg : 0;
        double utilization = makespan > 0 ? busySum / (totalPes * makespan) : 0;
        double throughput = makespan > 0 ? finished.size() / makespan : 0;
        double totalVmMips = vmList.stream().mapToDouble(Vm::getTotalMipsCapacity).sum();

        return String.format(java.util.Locale.US,
                "%s,%d,%d,%s,%.2f,%.4f,%.4f,%.6f,%d,%.2f",
                algo, n, planRep(csvFile), new java.io.File(csvFile).getName(),
                makespan, di, utilization, throughput, finished.size(), totalVmMips);
    }

    /** FCFS: task berikutnya diberikan ke PE yang paling cepat tersedia. */
    static void fcfsAssign(List<Cloudlet> cloudlets, List<Vm> vms,
                           double[][] readyByPe, double[] vmBusy) {
        for (Cloudlet c : cloudlets) {
            int bestVm = 0, bestPe = 0;
            double earliest = Double.MAX_VALUE;
            for (int j = 0; j < vms.size(); j++) {
                for (int p = 0; p < readyByPe[j].length; p++) {
                    if (readyByPe[j][p] < earliest) {
                        earliest = readyByPe[j][p];
                        bestVm = j;
                        bestPe = p;
                    }
                }
            }
            double duration = c.getLength() / vms.get(bestVm).getMips();
            c.setVm(vms.get(bestVm));
            readyByPe[bestVm][bestPe] += duration;
            vmBusy[bestVm] += duration;
        }
    }

    /** MCT: tiap task diassign ke PE dengan completion time terkecil. */
    static void mctAssign(List<Cloudlet> cloudlets, List<Vm> vms,
                          double[][] readyByPe, double[] vmBusy) {
        for (Cloudlet c : cloudlets) {
            int bestVm = 0, bestPe = 0;
            double minCt = Double.MAX_VALUE;
            for (int j = 0; j < vms.size(); j++) {
                double duration = c.getLength() / vms.get(j).getMips();
                for (int p = 0; p < readyByPe[j].length; p++) {
                    double ct = readyByPe[j][p] + duration;
                    if (ct < minCt) {
                        minCt = ct;
                        bestVm = j;
                        bestPe = p;
                    }
                }
            }
            double duration = c.getLength() / vms.get(bestVm).getMips();
            c.setVm(vms.get(bestVm));
            readyByPe[bestVm][bestPe] = minCt;
            vmBusy[bestVm] += duration;
        }
    }

    /** Min-Min: dari semua task tersisa, pasangan (task, VM) dgn CT terkecil dieksekusi duluan.
     *  Persamaan logika dengan versi O(n^2*m) README, tapi pakai array boolean supaya
     *  tetap praktis untuk n = 10.000 (ArrayList.remove O(n) dihapus). */
    static void minMinAssign(List<Cloudlet> cloudlets, List<Vm> vms,
                             double[][] readyByPe, double[] vmBusy) {
        int n = cloudlets.size();
        int m = vms.size();
        boolean[] done = new boolean[n];
        List<Cloudlet> assignmentOrder = new ArrayList<>(n);

        for (int assigned = 0; assigned < n; assigned++) {
            int winC = -1, winV = -1, winPe = -1;
            double globalMin = Double.MAX_VALUE;
            double[] earliestReady = new double[m];
            int[] earliestPe = new int[m];
            for (int j = 0; j < m; j++) {
                earliestReady[j] = Double.MAX_VALUE;
                for (int p = 0; p < readyByPe[j].length; p++) {
                    if (readyByPe[j][p] < earliestReady[j]) {
                        earliestReady[j] = readyByPe[j][p];
                        earliestPe[j] = p;
                    }
                }
            }

            for (int i = 0; i < n; i++) {
                if (done[i]) continue;
                Cloudlet c = cloudlets.get(i);
                for (int j = 0; j < m; j++) {
                    double duration = c.getLength() / vms.get(j).getMips();
                    double ct = earliestReady[j] + duration;
                    if (ct < globalMin) {
                        globalMin = ct;
                        winC = i;
                        winV = j;
                        winPe = earliestPe[j];
                    }
                }
            }
            Cloudlet winner = cloudlets.get(winC);
            winner.setVm(vms.get(winV));
            assignmentOrder.add(winner);
            readyByPe[winV][winPe] = globalMin;
            vmBusy[winV] += winner.getLength() / vms.get(winV).getMips();
            done[winC] = true;
        }
        cloudlets.clear();
        cloudlets.addAll(assignmentOrder);
    }

    static List<Long> readCsv(String path, int limit) {
        List<Long> lengths = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine(); // header
            while ((line = br.readLine()) != null && lengths.size() < limit) {
                lengths.add(Long.parseLong(line.split(",")[1].trim()));
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("[ERROR] Gagal membaca " + path + ": " + e.getMessage());
        }
        return lengths;
    }

    static int planRep(String csvFile) {
        if (csvFile.contains("seed1")) return 1;
        if (csvFile.contains("seed2")) return 2;
        return 3;
    }
}
