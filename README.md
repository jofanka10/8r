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
2. ```snprinf``` digunakan untuk menginput data tertentu seperti UI dan informasi player.
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
2. ```snprinf``` digunakan untuk menginput data tertentu seperti UI dan informasi persediaan player.
3. Server mengirimkan array tadi ke client menggunakan send.
4. ```memset``` diperlukan untuk menghindari input maupun output yang tidak diinginkan.
5. Setelah itu, jika user menginputkan ```ENTER```, maka setelah itu switch case akan berhenti.

**n) Shop**

Pada switch case shop digunakan untuk memunculkan item di shop yang dapat dibeli oleh player. Untuk kodenya seperti ini

    char msg[4096];
    snprintf(msg, sizeof(msg), "\033c\033[3J\033[38;2;105;210;82mWelcome to the shop!\e[0m\n\033[38;2;243;177;68mYour gold:\e[0m %d\n\n", player.gold);
    for (int i = 0; i < total_weapons; i++)
    {
        char weapon_msg[256];
        snprintf (weapon_msg, sizeof(weapon_msg), "%d. %s | \033[38;2;243;177;68mPrice: %d Gold\e[0m | \033[38;2;226;79;65mDamage: %d\e[0m | Passive: %s\n",
            i + 1, weapons[i].name, weapons[i].price, weapons[i].damage, weapons[i].passive);
        strncat(msg, weapon_msg, sizeof(msg) - strlen(msg) - 1);
    }
    strncat(msg, "\n\033[38;2;243;177;68mEnter the weapon you want to purchase (or 0 to cancel): \e[0m", sizeof(msg) - strlen(msg) - 1);

    send(client_socket, msg, strlen(msg), 0);

    memset(buf, 0, sizeof(buf));
    valread = read(client_socket, buf, sizeof(buf));
    if(valread <= 0) break; // client disconnect

    int choice = atoi(buf);
    if (choice == 0) break;

    Weapon selected = buy_weapon(choice);

    if (strlen(selected.name) == 0)
    {
        const char error[] = "\033[38;2;226;79;65mInvalid choice. Press enter to return...\e[0m ";
        send(client_socket, error, strlen(error), 0);
        memset(buf, 0, sizeof(buf));
        valread = read(client_socket, buf, sizeof(buf));
        break;
    }

    if (player.gold >= selected.price)
    {
        player.gold -= selected.price;
        player.inventory[player.inventory_count++] = selected;

        char success[256];
        snprintf(success, sizeof(success),
            "\033[38;2;243;177;68mSuccessfully purchased %s for %d gold!\nPress enter to return...\e[0m ",
            selected.name, selected.price);
        send(client_socket, success, strlen(success), 0);
    }
    else
    {
        const char failed[] = "\033[38;2;226;79;65mNot enough gold! Press enter to return...\e[0m ";
        send(client_socket, failed, strlen(failed), 0);
    }
    memset(buf, 0, sizeof(buf));
    valread = read(client_socket, buf, sizeof(buf)); // tunggu ENTER
    break;

dimana dcara kerja dari kodenya seperti ini.
1. Server mengirimkan tampilan shop kepada client dan meminta untuk client jika ingin membeli item.
2. Jika client memasukkan nomor item tertentu, maka server akan mengecek apakah gold yang dimiliki player sudah cukup.
3. Jika gold mencukupi, maka item dapat dibeli oleh player dan memasukkannnya ke dalam inventory.
4. Jika gold tidak mencukupi, maka server mengirim pesan kepada client bahwa gold tidak mencukupi.
5. Jika player ingin keluar dari shop, maka player dapat menginput ```0``` dan `ENTER` kepada server.  
