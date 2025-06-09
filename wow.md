# Laporran Resume Modul 5 Sistem Operasi

Pada modul ini, praktikan diminta untuk membuat sebuah Operating System sederhana yang bernama EorzeOS. Untuk struktur filenya seperti ini

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
