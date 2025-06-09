# Laporran Resume Modul 5 Sistem Operasi

Pada modul ini, praktikan diminta untuk membuat sebuah Operating System sederhana yang bernama EorzeOS. Untuk struktur filenya seperti ini
```
|_ include\n
   |_ kernel.h
   |_ shell.h
   |_ std_lib.h
   |_ std_type.h
     
|_ src
   |_ bootloader.asm
   |_ kernel.asm
   |_ kernel.c
   |_ shell.c
   |_ std_lib.c
|_ bochsrc.txt
|_ makefile
```


Dimana, setiap file memiliki peran yang sangat penting dalam operating system ini.

## `kernel.h`


## `bootloader.asm`
Bootloader ini digunakan untuk menjalankan fungsi assembly ke dalam OS. Assembly menggunakan 16-bit, sehingga program tidak dapat menggaunakan / dan % untuk perhitungannya. Untuk kodenya seperti ini

```
; bootloader.asm
bits 16

KERNEL_SEGMENT equ 0x1000 ; kernel will be loaded at 0x1000:0x0000
KERNEL_SECTORS equ 15     ; kernel will be loaded in 15 sectors maximum
KERNEL_START   equ 1      ; kernel will be loaded in sector 1

; bootloader code
bootloader:
  ; load kernel to memory
  mov ax, KERNEL_SEGMENT    ; load address of kernel
  mov es, ax                ; buffer address are in ES:BX
  mov bx, 0x0000            ; set buffer address to KERNEL_SEGMENT:0x0000

  mov ah, 0x02              ; read disk sectors
  mov al, KERNEL_SECTORS    ; number of sectors to read

  mov ch, 0x00              ; cylinder number
  mov cl, KERNEL_START + 1  ; sector number
  mov dh, 0x00              ; head number
  mov dl, 0x00              ; read from drive A

  int 0x13                  ; call BIOS interrupts

  ; set up segment registers
  mov ax, KERNEL_SEGMENT
  mov ds, ax
  mov es, ax
  mov ss, ax

  ; set up stack pointer
  mov ax, 0xFFF0
  mov sp, ax
  mov bp, ax

  ; jump to kernel
  jmp KERNEL_SEGMENT:0x0000

  ; padding to make bootloader 512 bytes
  times 510-($-$$) db 0
  dw 0xAA55
```

## `kernel.asm`

Fungsi dari kernel.asm adalah sebagai modul assembly yang mengatur konfigurasi awal kernel, serta mengelola switching CPU ke protected mode dan menginisialisasi interrupt handlers sebelum menyerahkan kontrol ke kode kernel dalam bahasa C. Untuk kodenya seperti ini

```
; kernel.asm - VERSI FINAL STABIL

global _putInMemory
global _getBiosTick
global _bios_print_char
global _bios_read_char
global _bios_set_cursor

; void putInMemory(int segment, int address, char character)
_putInMemory:
	push bp
	mov bp,sp
	push ds
	mov ax,[bp+4]
	mov si,[bp+6]
	mov cl,[bp+8]
	mov ds,ax
	mov [si],cl
	pop ds
	pop bp
	ret

; unsigned int getBiosTick()
_getBiosTick:
    mov ah, 0x00
    int 0x1A
    mov ax, dx
    ret

; FUNGSI BARU: Mencetak satu karakter menggunakan BIOS teletype
; void bios_print_char(char c)
_bios_print_char:
    push bp
    mov bp, sp
    mov al, [bp+4]  ; Ambil karakter dari argumen
    mov ah, 0x0E      ; Fungsi teletype
    mov bh, 0         ; Halaman 0
    int 0x10
    pop bp
    ret

; FUNGSI BARU: Membaca satu karakter dari keyboard
; int bios_read_char() -> mengembalikan AX (Scan Code & ASCII)
_bios_read_char:
    mov ah, 0x00      ; Tunggu ketikan keyboard
    int 0x16
    ret

; FUNGSI BARU: Mengatur posisi kursor
; void bios_set_cursor(int row, int col)
_bios_set_cursor:
    push bp
    mov bp, sp
    mov dh, [bp+4]  ; Ambil baris (row)
    mov dl, [bp+6]  ; Ambil kolom (col)
    mov bh, 0         ; Halaman 0
    mov ah, 0x02      ; Fungsi set kursor
    int 0x10
    pop bp
    ret

```


## `kernel.c`
File ini digunakan untuk mengimplementasikan logika utama kernel yang meliputi manajemen hardware dasar (seperti input keyboard dan output layar), pengaturan sistem interrupt, manajemen memori sederhana, serta memulai shell sebagai interface interaktif pengguna. Untuk kodenya seperti ini

```
// =======================================================
// DEKLARASI YANG DIBUTUHKAN (PENGGANTI .h)
// =======================================================
// Dari std_type.h
typedef unsigned char byte;
typedef char bool;
#define true 1
#define false 0

// Deklarasi dari kernel.asm (FUNGSI BARU yang stabil)
extern void putInMemory(int segment, int address, char character);
extern unsigned int getBiosTick();
extern void bios_print_char(char c);
extern int bios_read_char();
extern void bios_set_cursor(int row, int col);

// Deklarasi fungsi dari file lain
void shell();

// =======================================================
// KODE UTAMA
// =======================================================

// Variabel global yang dibutuhkan oleh shell.c
int current_color = 0x07;

void printString(char *str) {
    while (*str != '\0') {
        // BIOS teletype secara otomatis menangani newline dan scrolling
        bios_print_char(*str);
        str++;
    }
}

void readString(char *buf) {
    int i = 0;
    char ch;

    while (1) {
        // Panggil fungsi assembly yang baru dan bersih
        int key = bios_read_char();
        ch = key & 0xFF; // Ambil hanya kode ASCII dari AL

        if (ch == 0x0D) { // Tombol Enter
            buf[i] = '\0';
            printString("\r\n"); // Cetak baris baru
            break;
        } else if (ch == 0x08) { // Tombol Backspace
            if (i > 0) {
                i--;
                printString("\b \b"); // Kirim sekuens backspace, spasi, backspace
            }
        } else {
            if (i < 127) {
                buf[i++] = ch;
                bios_print_char(ch); // Tampilkan karakter yang diketik (echo)
            }
        }
    }
}

void clearScreen(int color) {
    int i;
    // Set warna akan mempengaruhi seluruh layar saat di-clear
    // dan juga akan menjadi warna default untuk `bios_print_char`
    current_color = color; 
    for (i = 0; i < 25 * 80; i++) {
        putInMemory(0xB800, i * 2, ' ');
        putInMemory(0xB800, i * 2 + 1, color); 
    }
    bios_set_cursor(0, 0); // Pindahkan kursor ke pojok kiri atas
}

int main() {
  clearScreen(0x07);
  printString("hallo user, silahkan masukan perintah\r\n");
  shell();
}
```
Dimana cara kerjanya seperti ini.
- Kode ini mendefinisikan tipe data dasar seperti byte dan bool serta konstanta true dan false.
- Deklarasi eksternal menyediakan akses ke fungsi tingkat rendah dari kernel.asm dan bios untuk interaksi langsung dengan perangkat keras.
- Fungsi shell() dideklarasikan sebagai fungsi dari file lain yang akan dipanggil di main.
- Variabel global current_color menyimpan warna latar belakang dan teks saat ini.
- Fungsi printString menampilkan string karakter ke layar menggunakan bios_print_char hingga karakter null ditemui.
- Fungsi readString membaca input karakter demi karakter dari keyboard menggunakan bios_read_char dan menyimpannya ke dalam buffer yang disediakan.
- readString secara khusus menangani tombol Enter untuk mengakhiri input dan tombol Backspace untuk menghapus karakter sebelumnya.
- Fungsi clearScreen mengisi seluruh memori video dengan spasi dan warna yang ditentukan, lalu mengatur ulang kursor ke posisi awal.
- Fungsi main adalah titik masuk program yang pertama kali membersihkan layar, mencetak pesan sambutan, dan kemudian memanggil fungsi shell().

## `shell.c`
Fungsi dari kode ini adalah untuk menyediakan command line interface berbasis teks yang memungkinkan pengguna untuk memasukkan perintah, memproses input tersebut, menjalankan perintah built-in seperti kalkulator, clear screen, dan pengaturan warna terminal, serta memberikan umpan balik interaktif sesuai dengan state shell dan pengguna. Untuk kodenya seperti ini

```
// =======================================================
// DEKLARASI YANG DIBUTUHKAN (PENGGANTI .h)
// =======================================================
// Dari std_type.h
typedef unsigned char byte;
typedef char bool;
#define true 1
#define false 0

// Deklarasi dari kernel.c
void printString(char* str);
void readString(char* buf);
void clearScreen(int color);
unsigned int getBiosTick();

// Deklarasi dari std_lib.c
int div(int a, int b);
int mod(int a, int b);
bool strcmp(char *str1, char *str2);
void strcpy(char *dst, char *src);
void clear(byte *buf, unsigned int size);
void atoi(char *str, int *num);
void itoa(int num, char *str);

// =======================================================
// KODE UTAMA
// =======================================================

char user[64] = "user"; 
char host[64] = ""; 
// Variabel warna sekarang dikelola oleh kernel.c
extern int current_color;

void setTextColor(int color) {
    current_color = color;
}

void prompt(char *word) {
  printString(word);
  if (host[0] != '\0') {
    printString(host);  
  }
  printString("> ");
}

void parseCommand(char *buf, char *cmd, char arg[2][64]) {
    int i = 0, j = 0;
    int argIndex = 0;
    while (buf[i] != '\0' && (buf[i] == '\r' || buf[i] == '\n')) i++;
    while (buf[i] != '\0' && buf[i] != ' ' && buf[i] != '\r' && buf[i] != '\n') {
        cmd[j++] = buf[i++];
    }
    cmd[j] = '\0';   
    while (buf[i] == ' ') i++;   
    for (argIndex = 0; argIndex < 2; argIndex++) {
        int k = 0;
        while (buf[i] != '\0' && buf[i] != ' ' && buf[i] != '\r' && buf[i] != '\n') {
            arg[argIndex][k++] = buf[i++];
        }
        arg[argIndex][k] = '\0';
        while (buf[i] == ' ') i++;
    }
}

void randomAnswer() {
    int r = mod(getBiosTick(),3);
    if (r == 0) {
        printString("ts unami gng </3\r\n");
    } else if (r == 1) {
        printString("yo\r\n");
    } else {
        printString("sygau\r\n");
    }    
}

void eksekusi(char *cmd, char *buf, char arg[2][64]) {
    int a, b, result;
    char result_str[32];
    if (strcmp(cmd, "yo")) {
        printString("gurt\r\n");
    } else if (strcmp(cmd, "gurt")) {
        printString("yo\r\n");
    } else if (strcmp(cmd, "user")) {
        if (arg[0][0] == '\0') {
            strcpy(user, "user");
        } else {
            strcpy(user, arg[0]);
        }
        printString("Username changed to ");
        printString(user);
        printString("\r\n");
    } else if (strcmp(cmd, "grandcompany")) {        
        if (strcmp(arg[0], "maelstrom")) {  
          setTextColor(0x0C); // Merah Terang
          clearScreen(current_color);                       
          strcpy(host, "@Storm");            
        } else if (strcmp(arg[0], "twinadder")) {   
          setTextColor(0x0E); // Kuning
          clearScreen(current_color);             
          strcpy(host, "@Serpent");            
        } else if (strcmp(arg[0], "immortalflames")) {       
          setTextColor(0x09); // Biru Terang
          clearScreen(current_color);             
          strcpy(host, "@Flame");                   
        } else {
            printString("Unknown company\r\n");
        }
    } else if (strcmp(cmd, "clear")) {
      strcpy(host, "");
      setTextColor(0x07);
      clearScreen(current_color);      
    } else if (strcmp(cmd, "add")){
        atoi(arg[0], &a); atoi(arg[1], &b);
        result = a + b;
        itoa(result, result_str);        
        printString(result_str); printString("\r\n");
    } else if (strcmp(cmd, "sub")) {
        atoi(arg[0], &a); atoi(arg[1], &b);
        result = a - b;
        itoa(result, result_str);        
        printString(result_str); printString("\r\n");
    } else if (strcmp(cmd, "mul")){  
      atoi(arg[0], &a); atoi(arg[1], &b);
      result = a * b;
      itoa(result, result_str);      
      printString(result_str); printString("\r\n");
    } else if (strcmp(cmd, "div")){
      atoi(arg[0], &a); atoi(arg[1], &b);
      if (b == 0) {
          printString("Error: Division by zero\r\n");
          return;
      }
      result = div(a, b);
      itoa(result, result_str);      
      printString(result_str); printString("\r\n");
    } else if (strcmp(cmd, "yogurt")){
      prompt("gurt");
      randomAnswer();    
    } else if (cmd[0] != '\0') {
        printString("Command not found: ");
        printString(cmd);
        printString("\r\n");
    }
}

void shell(){
  char buf[128];
  char cmd[64];
  char arg[2][64];
  
  while (true) {
    prompt(user);
    readString(buf);
    parseCommand(buf, cmd, arg);
    eksekusi(cmd, buf, arg);    
  }
}

```

Dimana cara kerjanya seperti ini
- Shell mendeklarasikan tipe data dasar dan mengimpor fungsi dari modul lain untuk operasi sistem dan utilitas.
- Variabel global user dan host disiapkan untuk informasi prompt.
- Fungsi setTextColor mengatur warna output teks dengan mengubah variabel current_color.
- Fungsi prompt menampilkan baris perintah dengan nama pengguna dan host, menunggu masukan.
- parseCommand membagi masukan pengguna menjadi perintah utama dan argumen yang mungkin ada.
- randomAnswer menghasilkan respons acak berdasarkan waktu sistem, menambah elemen interaktif.
- Fungsi eksekusi menafsirkan perintah dan argumen, menjalankan fungsi yang sesuai.
- Perintah meliputi operasi seperti mengubah nama pengguna, mengganti tema layar, dan melakukan perhitungan matematika.
- Shell beroperasi dalam loop tak terbatas, terus-menerus membaca, mengurai, dan mengeksekusi perintah.

