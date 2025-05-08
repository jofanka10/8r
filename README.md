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

**3) Fungsi ```trim_trailing_whitespace```**

Fungsi ini digunakan untuk menghapus karakter tertentu, seperti ```"\n"```, ```"\r"```, ```" "```, dan ```"\t"``` yang akan digunakan untuk case 5. Untuk kodenya seperti ini

    void trim_trailing_whitespace(char *str)
    {
        int len = strlen(str);
        while(len > 0 && (str[len-1] == '\n' || str[len-1] == '\r' || str[len-1] == ' ' || str[len-1] == '\t'))
        {
            str[len-1] = '\0';
            len--;
        }
    }

**n) Fungsi ```handle_client```
Fungsi ini digunakan untuk menjalankan keseluruhan fungsi UI dan cara program bekerja. Untuk kodenya seperti ini

    void handle_client(int client_socket)
    {
        Player player;
        player.gold = 100;
        player.inventory_count = 0;
        player.inventory[player.inventory_count++] = fists;
        player.equipped = fists;
        player.base_damage = player.equipped.damage;
        player.enemies_defeated = 0;
        player.frozen = 0;
    
        char buffer[4096];
        while (1)
        {
            display_menu(client_socket);
    
            char buf[100] = {0};
            memset(buf, 0, sizeof(buf));
            int valread = read(client_socket, buf, sizeof(buf));
            if(valread <= 0) {
                // client disconnect
                break;
            }
            trim_trailing_whitespace(buf);
    
            int option = buf[0];
    
            switch (option)
            {
                case '1':
                {
                    ...
                }
                case '2':
                {
                    ...
                }
                case '3':
                {
                    ...
                }
                case '4':
                {
                    ...
                }
                case '5':
                {
                    ...
                }
                case '6':
                {
                    ...
                }
                case 'x':
                {
                    ...
                }
                default:
                {
                    ...
                }
            }
        }
    }

dimana cara kerjanya sebagai berikut.
1. Player akan mendapatkan atribut default.
2. Program akan memunculkan UI kepada client.
3. Setelah server menerima input dari client, maka input dari client akan diproses menggunakan switch case.
4. Jika player salah input ke server, maka switch case default dijalankan.

**n) Show Player Stats**
Show player stats adalah informasi untuk melihat atribut yang digunakan oleh player. Untuk kodenya seperti ini

    char msg[4096];
    snprintf(msg, sizeof(msg),
        "\033c\033[3J"
        "\033[38;2;105;210;82m=== PLAYER STATS ===\e[0m\n\n"
        "\033[38;2;243;177;68mGold: %d\e[0m\n"
        "Equipped: %s\n"
        "\033[38;2;226;79;65mBase damage: %d\e[0m\n"
        "\033[38;2;127;217;241mEnemies defeated: %d\e[0m\n"
        "Passive: %s\n\n"
        "\033[38;2;243;177;68mPress enter to return...\e[0m ",
        player.gold, player.equipped.name,
        player.base_damage, player.enemies_defeated,
        player.equipped.passive);

    send(client_socket, msg, strlen(msg), 0);
    memset(buf, 0, sizeof(buf));
    valread = read(client_socket, buf, sizeof(buf)); // tunggu ENTER
    break;

dimana cara kerjanya sebagai berikut.
1. Program membuat array.
2. ```Snprinf``` digunakan untuk menginput data tertentu seperti UI dan informasi player.
3. Server mengirimkan array tadi ke client menggunakan send.
4. ```memset``` diperlukan untuk menghindari input maupun output yang tidak diinginkan.
5. Setelah itu, jika user menginputkan ```ENTER```, maka setelah itu switch case akan berhenti.

**n) Inventory**
Sama seperti shoe player stats, inventory digunakan untuk menampilkan persediaan yang dimiliki player. Untuk kodenya seperti ini

    char msg[4096];
    msg[0] = '\0'; // inisialisasi string kosong

    snprintf(msg, sizeof(msg), "\033c\033[3J\033[38;2;105;210;82m=== INVENTORY ===\e[0m\n\n");
    for (int i = 0; i < player.inventory_count; i++)
    {
        char temp[100];
        snprintf(temp, sizeof(temp), "%d. %s - \033[38;2;226;79;65mDamage: %d\e[0m - Passive: %s\n",
            i+1, player.inventory[i].name, player.inventory[i].damage,
            player.inventory[i].passive);
        strncat(msg, temp, sizeof(msg) - strlen(msg) - 1);
    }
    strncat(msg, "\n\033[38;2;243;177;68mPress enter to return..\e[0m ", sizeof(msg) - strlen(msg) - 1);

    send(client_socket, msg, strlen(msg), 0);
    memset(buf, 0, sizeof(buf));
    valread = read(client_socket, buf, sizeof(buf)); // tunggu ENTER
    break;

dimana cara kerjanya sebagai berikut.
1. Program membuat array.
2. ```Snprinf``` digunakan untuk menginput data tertentu seperti UI dan informasi persediaan player.
3. Server mengirimkan array tadi ke client menggunakan send.
4. ```memset``` diperlukan untuk menghindari input maupun output yang tidak diinginkan.
5. Setelah itu, jika user menginputkan ```ENTER```, maka setelah itu switch case akan berhenti.
