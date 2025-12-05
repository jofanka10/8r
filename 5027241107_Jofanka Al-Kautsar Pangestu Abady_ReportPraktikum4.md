
<h1>Laporan Praktikum Ethical Hacking</h1>
<p><strong>Nama Praktikan:</strong> [Nama Kamu]</p>
<p><strong>Tanggal:</strong> [Tanggal Hari Ini]</p>


<!-- ------------------------------- 1. Omongan Wong Semarang ------------------------------- -->
<h2>1. Omongan Wong Semarang</h2>


<!-- ----------- 1a. Nama Kerentanan ----------- -->
<h3>
  Nama Kerentanan
</h3>

<p>
  <strong>Local File Inclusion (LFI)</strong> - Source Code Disclosure
</p>

<!-- ----------- 1b. Deskripsi Kerentanan ----------- -->
<h3>Deskripsi Kerentanan</h3>

<p>
  Aplikasi memiliki parameter <code>page</code> pada URL yang menerima input berupa string Base64. Input ini di-decode oleh server dan langsung digunakan dalam fungsi <code>include()</code> PHP dengan penambahan ekstensi <code>.<span class="selected">php</span></code> secara otomatis. Tidak adanya validasi input memungkinkan penyerang untuk memanipulasi jalur file (path traversal) dan membaca file sensitif di server atau membaca source code aplikasi menggunakan PHP Wrapper.
</p>

<!-- ----------- 1c. CVSS Score ----------- -->
<h3>
  CVSS Score
</h3>

<p>
  <strong>Vector String:</strong> <code>AV:N/AC:L/PR:N/UI:N/S:U/C:H/I:N/A:N</code>
<em>Penjelasan: Serangan via Network (N), Kompleksitas Rendah (L), Tanpa Privilege (N), Tanpa User Interaction (N), Scope Unchanged (U), Confidentiality High (H) karena bisa baca config/source code, Integrity &amp; Availability None.</em>
</p>
<p>
  <strong>
    Hasil Kalkulasi Terminal:
  </strong>

</p>
<blockquote>
  <p>
    <em>
      [MASUKKAN SCREENSHOT TERMINAL HASIL RUNNING cvss_calc.py DISINI]
     </em>
  
<strong>
Score: 7.5 (High)
</strong>
</p> 
</blockquote>

<!-- ----------- 1d. Langkah Pengerjaan (Proof of Concept) ----------- -->
<h3>Langkah Pengerjaan (Proof of Concept)</h3>


<ol>
  <li>
    <p>
      <strong>Analisis Request:</strong> Ditemukan parameter <code>?page=</code> yang berisi string acak. Setelah di-decode menggunakan Base64, string tersebut berisi nama file (misal <code>pages/home</code>).</p><blockquote><p><em>[MASUKKAN SCREENSHOT BURP SUITE SAAT ANALISIS PAGE PARAMETER]</em></p>
      </blockquote>
  </li>
  
  <li>
    <p>
      <strong>Percobaan Eksploitasi (Thought Process):</strong>
Saya mencoba melakukan Path Traversal <code>../../../../etc/passwd</code>. Namun server memberikan error <code>failed to open stream: .../passwd.php</code>. Ini menunjukkan server memaksa ekstensi <code>.php</code>.
    </p>
    <blockquote>
      <p>
        <em>
          [MASUKKAN SCREENSHOT ERROR MESSAGE YANG MENAMPILKAN .PHP]
        </em>
    </p>
</blockquote>
  </li>
  
  <li>
    <p>
      <strong>Bypass Menggunakan PHP Wrapper:</strong> Karena ekstensi <code>.php</code> dipaksa, saya menggunakan wrapper <code>php://filter</code> untuk membaca source code file <code>config.php</code> dalam bentuk Base64 agar tidak dieksekusi server.
    </p>
    <p>
      <strong>Payload:</strong> <code>php://filter/convert.base64-encode/resource=config</code>
      
<strong>Base64 Encoded Payload (untuk URL):</strong> 

<code>cGhwOi8vZmlsdGVyL2NvbnZlcnQuYmFzZTY0LWVuY29kZS9yZXNvdXJjZT1jb25maWc=</code>
</p>
<blockquote>
  <p>
    <em>
      [MASUKKAN SCREENSHOT REPEATER DENGAN PAYLOAD DI ATAS]
    </em>
  </p>
</blockquote>
</li>

<li> 
  <p>
    <strong>Decoding Hasil:</strong>Respon dari server berupa string Base64 panjang. Setelah di-decode, ditemukan kredensial database dan Flag.
  </p>
<blockquote>
  <p>
    <em>
      [MASUKKAN SCREENSHOT HASIL DECODE CONFIG.PHP YANG ADA FLAGNYA]
    </em>
  </p>
</blockquote>
</li>
</ol>

<!-- ----------- 1e. Dampak ----------- -->
<h3>Dampak</h3>

<p>Penyerang dapat membaca source code aplikasi (Intellectual Property) dan file konfigurasi sensitif yang berisi kredensial database atau API Key, yang dapat mengarah pada pengambilalihan sistem secara penuh.</p>

<!-- ----------- 1f. Mitigasi ----------- -->
<h3>Mitigasi</h3>
<ol>
  <li>
    <p>Gunakan <strong>Whitelist</strong> (daftar putih) untuk file yang boleh diakses. Hindari memasukkan input user langsung ke fungsi <code>include()</code>.</p>
  </li>
  <li>
    <p>Matikan opsi <code>allow_url_include</code> dan <code>allow_url_fopen</code> di <code>php.ini</code>.</p>
  </li>
  <li>
    <p>Validasi input user secara ketat (hanya alphanumeric).</p>
  </li>
</ol>

<!-- ------------------------------- 2. URL Surfer ------------------------------- -->
<h2>2. URL Surfer</h2>

<!-- ----------- 2a. Nama Kerentanan ----------- -->
<h3>Nama Kerentanan</h3>
<p><strong>Server-Side Request Forgery (SSRF)</strong></p>

<!-- ----------- 2b. Deskripsi Kerentanan ----------- -->
<h3>Deskripsi Kerentanan</h3>
<p>Aplikasi memiliki fitur untuk mengambil konten dari URL yang diberikan pengguna (<code>/fetch?url=...</code>). Logika filter domain hanya mengecek keberadaan string <code>google.com/url?q=</code> tanpa memvalidasi struktur URL secara keseluruhan. Penyerang dapat memanipulasi input untuk memaksa server mengakses layanan internal (localhost) yang seharusnya tidak dapat diakses dari luar.</p>

<!-- ----------- 2c. CVSS Score ----------- -->
<h3>CVSS Score</h3>
<p><strong>Vector String:</strong> <code>AV:N/AC:L/PR:N/UI:N/S:C/C:H/I:N/A:N</code>
<em>Penjelasan: Scope Changed (C) karena serangan berpindah dari aplikasi web ke layanan internal (port 1337).</em></p><p><strong>Hasil Kalkulasi Terminal:</strong></p><blockquote><p><em>[MASUKKAN SCREENSHOT TERMINAL HASIL RUNNING cvss_calc.py DISINI]</em>
  
<strong>Score: 8.6 (High)</strong></p></blockquote>

<!-- ----------- 2d. Langkah Pengerjaan (Proof of Concept) ----------- -->
<h3>Langkah Pengerjaan (Proof of Concept)</h3><ol><li><p><strong>Analisis Source Code:</strong>
Ditemukan celah pada logika Python: <code>url.split("google.com/url?q=")[1]</code>. Kode hanya mengambil bagian belakang string tanpa memvalidasi bagian depan.</p></li><li><p><strong>Crafting Payload:</strong>
Saya menyusun URL dengan format: <code>[Bypass Filter] + [Splitter] + [Internal Target]</code>.
Target internal adalah <code>http://127.0.0.1:1337/flag</code>.</p><p><strong>Payload:</strong> <code>http://google.com/url?q=http://127.0.0.1:1337/flag</code></p></li><li><p><strong>Eksploitasi:</strong>
Mengirimkan request ke endpoint <code>/fetch</code> dengan payload tersebut.</p><blockquote><p><em>[MASUKKAN SCREENSHOT BROWSER/BURP SUITE SAAT REQUEST KE /FETCH]</em></p></blockquote></li><li><p><strong>Hasil:</strong>
Server merespons dengan JSON yang berisi Flag dari layanan internal.</p><blockquote><p><em>[MASUKKAN SCREENSHOT RESPON BERISI FLAG]</em></p></blockquote></li></ol>

<!-- ----------- 2e. Dampak ----------- -->
<h3>Dampak</h3><p>Penyerang dapat memindai port internal (Port Scanning), mengakses layanan internal yang tidak terproteksi (seperti database, admin panel, atau microservices), dan membaca data sensitif.</p>

<!-- ----------- 2f. Mitigasi ----------- -->
<h3>Mitigasi</h3><ol><li><p>Validasi input URL menggunakan library parsing yang aman, pastikan hostname yang dituju benar-benar valid.</p></li><li><p>Implementasikan <strong>Blocklist</strong> untuk IP Private/Local (seperti 127.0.0.1, 10.x.x.x, 192.168.x.x).</p></li><li><p>Nonaktifkan fitur redirect pada library HTTP client (jika tidak diperlukan).</p></li></ol>


<!-- ------------------------------- 3. XML Idol Processor ------------------------------- -->
<h2>3. XML Idol Processor</h2>

<!-- ----------- 3a. Nama Kerentanan ----------- -->
<h3>Nama Kerentanan</h3>

<p>
  <strong>
    XML External Entity (XXE) Injection
  </strong>
</p>

<!-- ----------- 3b. Deskripsi Kerentanan ----------- -->
<h3>Deskripsi Kerentanan</h3>

<p>
  Aplikasi memproses input XML dari pengguna (melalui URL eksternal) dengan konfigurasi parser yang tidak aman (<code>LIBXML_NOENT</code> dan <code>substituteEntities = true</code>). Hal ini memungkinkan penyerang mendefinisikan <em>external entities</em> untuk membaca file lokal di server.
</p>

<!-- ----------- 3c. CVSS Score ----------- -->
<h3>CVSS Score</h3>

<p>
  <strong>Vector String:</strong> <code>AV:N/AC:L/PR:N/UI:N/S:U/C:H/I:N/A:N</code>

  <em>Penjelasan: Confidentiality High karena bisa membaca file sistem (/etc/passwd).</em></p><p><strong>Hasil Kalkulasi Terminal:</strong>
</p>

<blockquote>
  <p>
  <em>
    [MASUKKAN SCREENSHOT TERMINAL HASIL RUNNING cvss_calc.py DISINI]
  </em>
<strong>
  Score: 7.5 (High)
</strong>
</p>
  
</blockquote>

<!-- ----------- 3d. Langkah Pengerjaan (Proof of Concept) ----------- -->
<h3>Langkah Pengerjaan (Proof of Concept)</h3>
<ol>
  <li>
    <p>
      <strong>Identifikasi Celah:</strong>
Halaman depan menyebutkan "supports DTD processing and external entity resolution". Ini adalah indikator utama XXE.
    </p>
  </li>
  
  <li>
    <p>
      <strong>
        Pembuatan Payload (GitHub Gist):
      </strong>
Karena ada filter HTML, saya membuat file XML murni (Raw) di GitHub Gist yang berisi definisi entity jahat untuk membaca <code>/etc/passwd</code>.</p><p><strong>XML Payload:</strong></p><pre><code>&lt;!DOCTYPE foo [ &lt;!ENTITY xxe SYSTEM "file:///etc/passwd" &gt; ]&gt;
&lt;data&gt;&amp;xxe;&lt;/data&gt;
<br class="ProseMirror-trailingBreak"></code></pre>
    <blockquote>
      <p>
        <em>
          [MASUKKAN SCREENSHOT GIST RAW]
        </em>
      </p>
    </blockquote>
    
  </li>
  
  <li>
    <p>
      <strong>
        Eksploitasi:
      </strong>
Memasukkan URL Raw Gist tersebut ke parameter <code>?idol=</code>.
    </p>
    <blockquote>
      <p>
        <em>
          [MASUKKAN SCREENSHOT URL BAR BROWSER]
        </em>
      </p>
    </blockquote>
  </li>
  
  <li>
    <p>
      <strong>
        Hasil:
      </strong>
Aplikasi menampilkan isi file <code>/etc/passwd</code>.
    </p>
          <blockquote>
            <p>
  <em>
    [MASUKKAN SCREENSHOT ISI FILE PASSWD DI WEBSITE]
  </em>
            </p>
          </blockquote>
  </li>
</ol>

<!-- ----------- 3e. Dampak ----------- -->
<h3>
  Dampak
</h3>
<p>
  Penyerang dapat membaca file sistem (Sensitive Data Exposure), melakukan Server-Side Request Forgery (SSRF), atau Denial of Service (DoS) melalui serangan Billion Laughs.
</p>

<!-- ----------- 3f. Mitigasi ----------- -->
<h3>
  Mitigasi
</h3>
<ol>
  <li>
    <p>
      Nonaktifkan pemrosesan DTD dan External Entities pada konfigurasi parser XML (<code>libxml_disable_entity_loader(true)</code> pada PHP lama atau setel opsi <code>LIBXML_NOENT</code> ke false).
    </p>
  </li>
  
  <li>
    <p>
      Gunakan format data yang lebih aman seperti JSON jika memungkinkan.
    </p>
    </li>
</ol>


<!-- ------------------------------- 4. Eterion Storage ------------------------------- -->
<!-- ----------- 4a. Nama Kerentanan ----------- -->
<!-- ----------- 4b. Deskripsi Kerentanan ----------- -->
<!-- ----------- 4c. CVSS Score ----------- -->
<!-- ----------- 4d. Langkah Pengerjaan (Proof of Concept) ----------- -->
<!-- ----------- 4e. Dampak ----------- -->
<!-- ----------- 4f. Mitigasi ----------- -->
<h2>4. Eterion Storage</h2><h3>Nama Kerentanan</h3><p><strong>Arbitrary File Read via Archive Symlink (Zip Slip Variation)</strong></p><h3>Deskripsi Kerentanan</h3><p>Fitur "Auto extract" pada aplikasi gagal memvalidasi isi file arsip (ZIP). Aplikasi mengekstrak <em>symbolic link</em> (tautan simbolik) apa adanya. Penyerang dapat mengunggah arsip berisi symlink yang mengarah ke file di luar direktori upload (misalnya <code>/flag.txt</code>), lalu mengakses file tersebut melalui antarmuka web.</p><h3>CVSS Score</h3><p><strong>Vector String:</strong> <code>AV:N/AC:L/PR:N/UI:N/S:U/C:H/I:N/A:N</code></p><p><strong>Hasil Kalkulasi Terminal:</strong></p><blockquote><p><em>[MASUKKAN SCREENSHOT TERMINAL HASIL RUNNING cvss_calc.py DISINI]</em>
<strong>Score: 7.5 (High)</strong></p></blockquote><h3>Langkah Pengerjaan (Proof of Concept)</h3><ol><li><p><strong>Pembuatan Payload (Terminal):</strong>
Menggunakan terminal Linux/Mac untuk membuat symlink yang mengarah ke flag, lalu membungkusnya dengan opsi <code>--symlinks</code>.</p><pre><code>ln -s /flag.txt flag_link
zip --symlinks exploit.zip flag_link
<br class="ProseMirror-trailingBreak"></code></pre><blockquote><p><em>[MASUKKAN SCREENSHOT TERMINAL SAAT MEMBUAT ZIP]</em></p></blockquote></li><li><p><strong>Upload:</strong>
Mengunggah file <code>exploit.zip</code> ke aplikasi Eterion Storage.</p></li><li><p><strong>Akses File:</strong>
Mengklik file hasil ekstraksi (<code>flag_link</code>) pada File Manager web.</p><blockquote><p><em>[MASUKKAN SCREENSHOT FILE MANAGER DI WEBSITE]</em></p></blockquote></li><li><p><strong>Hasil:</strong>
Browser menampilkan isi dari <code>/flag.txt</code>.</p><blockquote><p><em>[MASUKKAN SCREENSHOT FLAG YANG MUNCUL]</em></p></blockquote></li></ol>


<h3>Dampak</h3><p>Penyerang dapat membaca file apa pun di sistem server yang memiliki izin baca oleh user web server, termasuk konfigurasi server, source code, dan data sensitif lainnya.</p>


<h3>Mitigasi</h3><ol><li><p>Saat mengekstrak file ZIP, validasi setiap entry. Jangan izinkan ekstraksi file tipe <em>Symbolic Link</em>.</p></li><li><p>Pastikan jalur ekstraksi file (<code>canonical path</code>) tetap berada di dalam direktori tujuan yang diizinkan (mencegah Path Traversal).</p></li></ol></div>
