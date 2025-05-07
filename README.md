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



  
