# Konfigurasi `/etc/network/interfaces` untuk Semua Router

## 1. Osgiliath

```bash
# /etc/network/interfaces

# DHCP config for eth0
auto eth0
iface eth0 inet dhcp

# Static config for eth1 (ke Rivendell)
auto eth1
iface eth1 inet static
        address 10.78.1.233
        netmask 255.255.255.252
        up ip route add 10.78.1.232/30 via 10.78.1.234
        up ip route add 10.78.1.200/29 via 10.78.1.234

# Static config for eth2 (ke Moria)
auto eth2
iface eth2 inet static
        address 10.78.1.229
        netmask 255.255.255.252
        up ip route add 10.78.1.228/30 via 10.78.1.230
        up ip route add 10.78.1.208/30 via 10.78.1.230
        up ip route add 10.78.1.212/30 via 10.78.1.230
        up ip route add 10.78.1.128/26 via 10.78.1.230
        up ip route add 10.78.1.192/29 via 10.78.1.230

# Static config for eth3 (ke Minastir)
auto eth3
iface eth3 inet static
        address 10.78.1.237
        netmask 255.255.255.252
        up ip route add 10.78.1.236/30 via 10.78.1.238
        up ip route add 10.78.0.0/24 via 10.78.1.238
        up ip route add 10.78.1.224/30 via 10.78.1.238
        up ip route add 10.78.1.216/30 via 10.78.1.238
        up ip route add 10.78.1.220/30 via 10.78.1.238
        up ip route add 10.78.1.0/25 via 10.78.1.238

up echo "nameserver 192.168.122.1" > /etc/resolv.conf
```

---

## 2. Moria

```bash
# /etc/network/interfaces

# Static config for eth0 (ke Osgiliath)
auto eth0
iface eth0 inet static
        address 10.78.1.230
        netmask 255.255.255.252
        up ip route add 10.78.1.228/30 via 10.78.1.229
        up ip route add 10.78.1.232/30 via 10.78.1.229
        up ip route add 10.78.1.200/29 via 10.78.1.229
        up ip route add 10.78.1.236/30 via 10.78.1.229
        up ip route add 10.78.0.0/24 via 10.78.1.229
        up ip route add 10.78.1.224/30 via 10.78.1.229
        up ip route add 10.78.1.216/30 via 10.78.1.229
        up ip route add 10.78.1.220/30 via 10.78.1.229
        up ip route add 10.78.1.0/25 via 10.78.1.229

# Static config for eth1 (ke IronHills)
auto eth1
iface eth1 inet static
        address 10.78.1.209
        netmask 255.255.255.252

# Static config for eth2 (ke Wilderland)
auto eth2
iface eth2 inet static
        address 10.78.1.213
        netmask 255.255.255.252
        up ip route add 10.78.1.212/30 via 10.78.1.214
        up ip route add 10.78.1.128/26 via 10.78.1.214
        up ip route add 10.78.1.192/29 via 10.78.1.214
up echo "nameserver 192.168.122.1" > /etc/resolv.conf
```

---

## 3. IronHills

```bash
# /etc/network/interfaces

auto eth0
iface eth0 inet static
        address 10.78.1.210
        netmask 255.255.255.252
        gateway 10.78.1.209
up echo "nameserver 192.168.122.1" > /etc/resolv.conf
```

---

## 4. Wilderland

```bash
# /etc/network/interfaces

# Static config for eth0 (ke Moria)
auto eth0
iface eth0 inet static
        address 10.78.1.214
        netmask 255.255.255.252
        up ip route add 10.78.1.212/30 via 10.78.1.213
        up ip route add 10.78.1.208/30 via 10.78.1.213
        up ip route add 10.78.1.228/30 via 10.78.1.213
        up ip route add 10.78.1.232/30 via 10.78.1.213
        up ip route add 10.78.1.200/29 via 10.78.1.213
        up ip route add 10.78.1.236/30 via 10.78.1.213
        up ip route add 10.78.0.0/24 via 10.78.1.213
        up ip route add 10.78.1.224/30 via 10.78.1.213
        up ip route add 10.78.1.216/30 via 10.78.1.213
        up ip route add 10.78.1.220/30 via 10.78.1.213
        up ip route add 10.78.1.0/25 via 10.78.1.213

# Static config for eth1 (ke Durin)
auto eth1
iface eth1 inet static
        address 10.78.1.129
        netmask 255.255.255.192

# Static config for eth2 (ke Khamul)
auto eth2
iface eth2 inet static
        address 10.78.1.193
        netmask 255.255.255.248
up echo "nameserver 192.168.122.1" > /etc/resolv.conf
```

---

## 5. Durin

```bash
# /etc/network/interfaces

auto eth0
iface eth0 inet static
        address 10.78.1.130
        netmask 255.255.255.192
        gateway 10.78.1.129
up echo "nameserver 192.168.122.1" > /etc/resolv.conf
```

---

## 6. Khamul

```bash
# /etc/network/interfaces

auto eth0
iface eth0 inet static
        address 10.78.1.194
        netmask 255.255.255.248
        gateway 10.78.1.193
up echo "nameserver 192.168.122.1" > /etc/resolv.conf
```

---

## 7. Rivendell

```bash
# /etc/network/interfaces

# Static config for eth0 (ke Osgiliath)
auto eth0
iface eth0 inet static
        address 10.78.1.234
        netmask 255.255.255.252
        up ip route add 10.78.1.232/30 via 10.78.1.233
        up ip route add 10.78.1.228/30 via 10.78.1.233
        up ip route add 10.78.1.208/30 via 10.78.1.233
        up ip route add 10.78.1.212/30 via 10.78.1.233
        up ip route add 10.78.1.128/26 via 10.78.1.233
        up ip route add 10.78.1.192/29 via 10.78.1.233
        up ip route add 10.78.1.236/30 via 10.78.1.233
        up ip route add 10.78.0.0/24 via 10.78.1.233
        up ip route add 10.78.1.224/30 via 10.78.1.233
        up ip route add 10.78.1.216/30 via 10.78.1.233
        up ip route add 10.78.1.220/30 via 10.78.1.233
        up ip route add 10.78.1.0/25 via 10.78.1.233

# Static config for eth1 (ke Switch dengan Vilya & Narya)
auto eth1
iface eth1 inet static
        address 10.78.1.201
        netmask 255.255.255.248
up echo "nameserver 192.168.122.1" > /etc/resolv.conf
```

---

## 8. Vilya

```bash
# /etc/network/interfaces

auto eth0
iface eth0 inet static
        address 10.78.1.203
        netmask 255.255.255.248
        gateway 10.78.1.201
up echo "nameserver 192.168.122.1" > /etc/resolv.conf
```

---

## 9. Narya

```bash
# /etc/network/interfaces

auto eth0
iface eth0 inet static
        address 10.78.1.202
        netmask 255.255.255.248
        gateway 10.78.1.201
up echo "nameserver 192.168.122.1" > /etc/resolv.conf
```

---

## 10. Minastir

```bash
# /etc/network/interfaces

# Static config for eth0 (ke Osgiliath)
auto eth0
iface eth0 inet static
        address 10.78.1.238
        netmask 255.255.255.252
        up ip route add 10.78.1.236/30 via 10.78.1.237
        up ip route add 10.78.1.232/30 via 10.78.1.237
        up ip route add 10.78.1.200/29 via 10.78.1.237
        up ip route add 10.78.1.228/30 via 10.78.1.237
        up ip route add 10.78.1.208/30 via 10.78.1.237
        up ip route add 10.78.1.212/30 via 10.78.1.237
        up ip route add 10.78.1.128/26 via 10.78.1.237
        up ip route add 10.78.1.192/29 via 10.78.1.237

# Static config for eth1 (ke Switch dengan Elendil & Isildur)
auto eth1
iface eth1 inet static
        address 10.78.0.1
        netmask 255.255.255.0

# Static config for eth2 (ke Pelargir)
auto eth2
iface eth2 inet static
        address 10.78.1.225
        netmask 255.255.255.252
        up ip route add 10.78.1.224/30 via 10.78.1.226
        up ip route add 10.78.1.216/30 via 10.78.1.226
        up ip route add 10.78.1.220/30 via 10.78.1.226
        up ip route add 10.78.1.0/25 via 10.78.1.226
up echo "nameserver 192.168.122.1" > /etc/resolv.conf
```

---

## 11. Elendil

```bash
# /etc/network/interfaces

auto eth0
iface eth0 inet static
        address 10.78.0.2
        netmask 255.255.255.0
        gateway 10.78.0.1
up echo "nameserver 192.168.122.1" > /etc/resolv.conf
```

---

## 12. Isildur

```bash
# /etc/network/interfaces

auto eth0
iface eth0 inet static
        address 10.78.0.3
        netmask 255.255.255.0
        gateway 10.78.0.1
up echo "nameserver 192.168.122.1" > /etc/resolv.conf
```

---

## 13. Pelargir

```bash
# /etc/network/interfaces

# Static config for eth0 (ke Minastir)
auto eth0
iface eth0 inet static
        address 10.78.1.226
        netmask 255.255.255.252
        up ip route add 10.78.1.236/30 via 10.78.1.225
        up ip route add 10.78.1.232/30 via 10.78.1.225
        up ip route add 10.78.1.200/29 via 10.78.1.225
        up ip route add 10.78.1.228/30 via 10.78.1.225
        up ip route add 10.78.1.208/30 via 10.78.1.225
        up ip route add 10.78.1.212/30 via 10.78.1.225
        up ip route add 10.78.1.128/26 via 10.78.1.225
        up ip route add 10.78.1.192/29 via 10.78.1.225
        up ip route add 10.78.1.224/30 via 10.78.1.225
        up ip route add 10.78.0.0/24 via 10.78.1.225

# Static config for eth1 (ke Palantir)
auto eth1
iface eth1 inet static
        address 10.78.1.217
        netmask 255.255.255.252

# Static config for eth2 (ke AnduinBanks)
auto eth2
iface eth2 inet static
        address 10.78.1.221
        netmask 255.255.255.252
        up ip route add 10.78.1.220/30 via 10.78.1.222
        up ip route add 10.78.1.0/25 via 10.78.1.222
up echo "nameserver 192.168.122.1" > /etc/resolv.conf
```

---

## 14. Palantir

```bash
# /etc/network/interfaces

auto eth0
iface eth0 inet static
        address 10.78.1.218
        netmask 255.255.255.252
        gateway 10.78.1.217
up echo "nameserver 192.168.122.1" > /etc/resolv.conf
```

---

## 15. AnduinBanks

```bash
# /etc/network/interfaces

# Static config for eth0 (ke Pelargir)
auto eth0
iface eth0 inet static
        address 10.78.1.222
        netmask 255.255.255.252
        up ip route add 10.78.1.236/30 via 10.78.1.221
        up ip route add 10.78.1.232/30 via 10.78.1.221
        up ip route add 10.78.1.200/29 via 10.78.1.221
        up ip route add 10.78.1.228/30 via 10.78.1.221
        up ip route add 10.78.1.208/30 via 10.78.1.221
        up ip route add 10.78.1.212/30 via 10.78.1.221
        up ip route add 10.78.1.128/26 via 10.78.1.221
        up ip route add 10.78.1.192/29 via 10.78.1.221
        up ip route add 10.78.1.224/30 via 10.78.1.221
        up ip route add 10.78.0.0/24 via 10.78.1.221
        up ip route add 10.78.1.216/30 via 10.78.1.221

# Static config for eth1 (ke Switch dengan Cirdan & Gilgalad)
auto eth1
iface eth1 inet static
        address 10.78.1.1
        netmask 255.255.255.128
up echo "nameserver 192.168.122.1" > /etc/resolv.conf
```

---

## 16. Cirdan

```bash
# /etc/network/interfaces

auto eth0
iface eth0 inet static
        address 10.78.1.2
        netmask 255.255.255.128
        gateway 10.78.1.1
up echo "nameserver 192.168.122.1" > /etc/resolv.conf
```

---

## 17. Gilgalad

```bash
# /etc/network/interfaces

auto eth0
iface eth0 inet static
        address 10.78.1.3
        netmask 255.255.255.128
        gateway 10.78.1.1
up echo "nameserver 192.168.122.1" > /etc/resolv.conf
```

---

## Cara Apply Konfigurasi:

### 1. Edit file di setiap router:
```bash
nano /etc/network/interfaces
```

### 2. Enable IP Forwarding (untuk semua router):
```bash
echo "net.ipv4.ip_forward=1" >> /etc/sysctl.conf
sysctl -p
```

### 3. Restart networking atau reboot:
```bash
# Opsi 1: Restart network service
systemctl restart networking

# Opsi 2: Reboot (lebih aman)
reboot
```

### 4. Verifikasi setelah restart:
```bash
ip a                    # Cek IP address
ip route show           # Cek routing table
ping [IP_tujuan]        # Test koneksi
```

---

## Catatan Penting:

- Semua routing sudah menggunakan `up ip route add` agar persistent setelah restart
- IP Forwarding sudah otomatis diaktifkan lewat `/etc/sysctl.conf`
- Client nodes (IronHills, Durin, Khamul, Vilya, Narya, Elendil, Isildur, Palantir, Cirdan, Gilgalad) hanya butuh gateway, tidak perlu routing manual
- Router nodes perlu routing lengkap agar bisa forward traffic ke semua subnet
