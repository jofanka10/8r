## soal_3
Pada soal ini, praktikan diminta untuk membuat tiga file, yaitu ```dungeon.c``` sebagai server, ```shop.c```, dan ```player.c``` sebagai client.

### a. dungeon.c
**1) Library**

Untuk library yang digunakan yaitu library standar, ```<arpa/inet.h>```, ```<time.h>```, dan ```"shop.c"``` untuk mengambil data dari file ```shop.c```. Untuk kodenya seperti ini

    #include <stdio.h>
    #include <unistd.h>
    #include <string.h>
    #include <stdlib.h>
    #include <time.h>
    #include <arpa/inet.h>
    #include "shop.c"

**2) Struct**
Struct digunakan untuk memuat artibut data player dan musuh. Untuk kodenya seperti ini

**struct Player**

    typedef struct
    {
        int gold;
        Weapon inventory[20];
        int inventory_count;
        Weapon equipped;
        int base_damage;
        int enemies_defeated;
        int frozen;
    } Player;

**struct Enemy**

    typedef struct
    {
        char name[50];
        int health;
        int damage;
    } Enemy;
