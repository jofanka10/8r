Osgiliath

/etc/network/interfaces
```
auto eth0
iface eth0 inet dhcp
#       hostname ervn-debi-1

# Static config for eth1
auto eth1
iface eth1 inet static
        address 10.78.1.229
        netmask 255.255.255.252

# Static config for eth2
auto eth2
iface eth2 inet static
        address 10.78.1.233
        netmask 255.255.255.252

# Static config for eth3
auto eth3
iface eth3 inet static
        address 10.78.1.237
        netmask 255.255.255.252
```

/root/.bashrc
```
# ip settings
ip addr add 192.168.122.1 dev eth0 
ip addr add 10.78.1.233/30 dev eth1
ip addr add 10.78.1.229/30 dev eth2
ip addr add 10.78.1.237/30 dev eth3
ip route add default via 192.168.122.1 dev eth0

# 1 ke Rivendell, Narya Vilya
ip route add 10.78.1.232/30 via 10.78.1.234 dev eth1
ip route add 10.78.1.200/29 via 10.78.1.234 dev eth1

# 2 ke Moria
ip route add 10.78.1.228/30 via 10.78.1.230 dev eth2

# ke IronHills
ip route add 10.78.1.208/30 via 10.78.1.230 dev eth2

# ke Wilderland
ip route add 10.78.1.212/30 via 10.78.1.230 dev eth2

# ke Durin
ip route add 10.78.1.128/26 via 10.78.1.230 dev eth2

# ke Khamul
ip route add 10.78.1.192/29 via 10.78.1.230 dev eth2

# 3 Minastir
ip route add 10.78.1.236/30 via 10.78.1.238 dev eth3

# ke Elendil Isildur
ip route add 10.78.0.0/24 via 10.78.1.238 dev eth3

# ke Pelargir, Palantir
ip route add 10.78.1.224/30 via 10.78.1.238 dev eth3
ip route add 10.78.1.216/30 via 10.78.1.238 dev eth3

# ke AnduinBanks, Gilgalad Cirdan
ip route add 10.78.1.220/30 via 10.78.1.238 dev eth3
ip route add 10.78.1.0/25 via 10.78.1.238 dev eth3

echo "nameserver 192.168.122.1" > /etc/resolv.conf
```

Rivendell

/etc/network/interfaces
```
auto eth0
iface eth0 inet static
        address 10.78.1.234
        netmask 255.255.255.252
#       gateway
#       up echo nameserver 192.168.0.1 > /etc/resolv.conf

auto eth1
iface eth1 inet static
        address 10.78.1.201
        netmask 255.255.255.248
#       gateway 10.78.1.201
#       up echo nameserver 192.168.0.1 > /etc/resolv.conf

```

/root/.bashrc
```
# IP Settings
ip addr add 10.78.1.234/30 dev eth0
ip addr add 10.78.1.201/29 dev eth1
ip route add default via 10.78.1.233 dev eth0

# 0 ke Osgiliath
ip route add 10.78.1.232/30 via 10.78.1.233

# 2 ke Moria
ip route add 10.78.1.228/30 via 10.78.1.233

# ke IronHills
ip route add 10.78.1.208/30 via 10.78.1.233

# ke Wilderland
ip route add 10.78.1.212/30 via 10.78.1.233

# ke Durin
ip route add 10.78.1.128/26 via 10.78.1.233

# ke Khamul
ip route add 10.78.1.192/29 via 10.78.1.233

# 3 Minastir
ip route add 10.78.1.236/30 via 10.78.1.233

# ke Elendil Isildur
ip route add 10.78.0.0/24 via 10.78.1.233

# ke Pelargir, Palantir
ip route add 10.78.1.224/30 via 10.78.1.233
ip route add 10.78.1.216/30 via 10.78.1.233

# ke AnduinBanks, Gilgalad Cirdan
ip route add 10.78.1.220/30 via 10.78.1.233
ip route add 10.78.1.0/25 via 10.78.1.233

echo "nameserver 192.168.122.1" > /etc/resolv.conf

```

Narya
/etc/network.interfaces
```
auto eth0
iface eth0 inet static
        address 10.78.1.202
        netmask 255.255.255.248
        gateway 10.78.1.201
#       up echo nameserver 192.168.0.1 > /etc/resolv.conf
```

/root/.bashrc
```
ip addr add 10.78.1.202/29 dev eth0
ip route add default via 10.78.1.201 dev eth0
echo "nameserver 192.168.122.1" > /etc/resolv.conf
```

Vilya
/etc/network/interfaces
```
auto eth0
iface eth0 inet static
        address 10.78.1.203
        netmask 255.255.255.248
        gateway 10.78.1.201
#       up echo nameserver 192.168.0.1 > /etc/resolv.conf
```

/root/.bashrc
```
ip addr add 10.78.1.203/29 dev eth0
ip route add default via 10.78.1.201 dev eth0
echo "nameserver 192.168.122.1" > /etc/resolv.conf
```

Moria
/etc/network/interfaces
```
auto eth0
iface eth0 inet static
        address 10.78.1.230
        netmask 255.255.255.252
#       gateway 10.78.1.201
#       up echo nameserver 192.168.0.1 > /etc/resolv.conf

auto eth1
iface eth1 inet static
        address 10.78.1.209
        netmask 255.255.255.252
#       gateway 10.78.1.209
#       up echo nameserver 192.168.0.1 > /etc/resolv.conf

auto eth2
iface eth2 inet static
        address 10.78.1.213
        netmask 255.255.255.252
#       gateway 10.78.1.201
#       up echo nameserver 192.168.0.1 > /etc/resolv.conf
```

/root/.bashrc
```
# IP Settings
ip addr add 10.78.1.230/30 dev eth0
ip addr add 10.78.1.209/30 dev eth1
ip addr add 10.78.1.213/30 dev eth2
ip route add default via 10.78.1.229 dev eth0

# ke Osgiliath
ip route add 10.78.1.228/30 via 10.78.1.229

# ke Wilderland
ip route add 10.78.1.212/30 via 10.78.1.214

# ke Durin
ip route add 10.78.1.128/26 via 10.78.1.214

# ke Khamul
ip route add 10.78.1.192/29 via 10.78.1.214

# ke Rivendel, Narya, Vilya
ip route add 10.78.1.232/30 via 10.78.1.229
ip route add 10.78.1.200/29 via 10.78.1.229

# ke Minastir, Elendil, Isildur
ip route add 10.78.1.236/30 via 10.78.1.229
ip route add 10.78.0.0/24 via 10.78.1.229

# ke Pelargir, Palantir
ip route add 10.78.1.224/30 via 10.78.1.229
ip route add 10.78.1.216/30 via 10.78.1.229

# ke AndiunBanks, Cirdan, Gilgalad
ip route add 10.78.1.220/30 via 10.78.1.229
ip route add 10.78.1.0/25 via 10.78.1.229

echo "nameserver 192.168.122.1" > /etc/resolv.conf 
```

IronHills
/etc/network/interfaces
```
auto eth0
iface eth0 inet static
        address 10.78.1.210
        netmask 255.255.255.252
        gateway 10.78.1.209
#       up echo nameserver 192.168.0.1 > /etc/resolv.conf
```

/root/.bashrc
```
ip addr add 10.78.1.210/30 dev eth0
ip route add default via 10.78.1.209 dev eth0
echo "nameserver 192.168.122.1" > /etc/resolv.conf 
```

Wilderland
/etc/network/interfaces
```
auto eth0
iface eth0 inet static
        address 10.78.1.214
        netmask 255.255.255.252
#       gateway 10.78.1.213
#       up echo nameserver 192.168.0.1 > /etc/resolv.conf

auto eth1
iface eth1 inet static
        address 10.78.1.129
        netmask 255.255.255.192
#       gateway 10.78.1.129
#       up echo nameserver 192.168.0.1 > /etc/resolv.conf

auto eth2
iface eth2 inet static
        address 10.78.1.193
        netmask 255.255.255.248
#       gateway 10.78.1.193
#       up echo nameserver 192.168.0.1 > /etc/resolv.conf
```

/root/.bashrc
```
# IP Settings
ip addr add 10.78.1.214/30 dev eth0
ip addr add 10.78.1.129/26 dev eth1
ip addr add 10.78.1.193/29 dev eth2
ip route add default via 10.78.1.213 dev eth0

# ke Moria
ip route add 10.78.1.212/30 via 10.78.1.213

# ke IronHills
ip route add 10.78.1.208/30 via 10.78.1.213

# ke Osgiliath
ip route add 10.78.1.228/30 via 10.78.1.213

# ke Durin
# ip route add 10.78.1.128/26 via 10.78.1.214

# ke Khamul
# ip route add 10.78.1.129/29 via 10.78.1.130

# ke Rivendel, Narya, Vilya
ip route add 10.78.1.232/30 via 10.78.1.213
ip route add 10.78.1.200/29 via 10.78.1.213

# ke Minastir, Elendil Isildur
ip route add 10.78.1.236/30 via 10.78.1.213
ip route add 10.78.0.0/24 via 10.78.1.213

# ke Pelargir, Palantir
ip route add 10.78.1.224/30 via 10.78.1.213
ip route add 10.78.1.216/30 via 10.78.1.213

# ke AndinBanks, Cirdan, Gilgalad
ip route add 10.78.1.220/30 via 10.78.1.213
ip route add 10.78.1.0/25 via 10.78.1.213

echo "nameserver 192.168.122.1" > /etc/resolv.conf 
```

Durin
/etc/network/interfaces
```
auto eth0
iface eth0 inet static
        address 10.78.1.130
        netmask 255.255.255.192
        gateway 10.78.1.129
#       up echo nameserver 192.168.0.1 > /etc/resolv.conf 
```

/root/.bashrc
```
ip addr add 10.78.1.130/26 dev eth0
ip route add default via 10.78.1.129 dev eth0
echo "nameserver 192.168.122.1" > /etc/resolv.conf 
```


Khamul
/etc/network/interfaces
```
auto eth0
iface eth0 inet static
        address 10.78.1.194
        netmask 255.255.255.248
        gateway 10.78.1.193
#       up echo nameserver 192.168.0.1 > /etc/resolv.conf 
```

/root/.bashrc
```
ip addr add 10.78.1.194/29 dev eth0
ip route add default via 10.78.1.193 dev eth0
echo "nameserver 192.168.122.1" > /etc/resolv.conf 
```

Minastir
/etc/network/interfaces
```
auto eth0
iface eth0 inet static
        address 10.78.1.238
        netmask 255.255.255.252
#       gateway 
#       up echo nameserver 192.168.0.1 > /etc/resolv.conf

auto eth1
iface eth1 inet static
        address 10.78.0.1
        netmask 255.255.255.0
#       gateway 
#       up echo nameserver 192.168.0.1 > /etc/resolv.conf

auto eth2
iface eth2 inet static
        address 10.78.0.225
        netmask 255.255.255.252
#       gateway 
#       up echo nameserver 192.168.0.1 > /etc/resolv.conf
```

/root/.bashrc
```
ip addr add 10.78.1.238/30 dev eth0
ip addr add 10.78.0.1/24 dev eth1
ip addr add 10.78.1.225/30 dev eth2
ip route add default via 10.78.1.237 dev eth0

# 0 ke Osgiliath
ip route add 10.78.1.236/30 via 10.78.1.237

# 1 ke Rivendell, Narya Vilya
ip route add 10.78.1.232/30 via 10.78.1.237
ip route add 10.78.1.200/29 via 10.78.1.237

# 2 ke Moria
ip route add 10.78.1.228/30 via 10.78.1.237

# ke IronHills
ip route add 10.78.1.208/30 via 10.78.1.237

# ke Wilderland
ip route add 10.78.1.212/30 via 10.78.1.237

# ke Durin
ip route add 10.78.1.128/26 via 10.78.1.237

# ke Khamul
ip route add 10.78.1.192/29 via 10.78.1.237

# 3 Elendil Isildur
# ip route add 10.78.0.0/24 via 10.78.1.238

# ke Pelargir, Palantir
ip route add 10.78.1.224/30 via 10.78.1.226
ip route add 10.78.1.216/30 via 10.78.1.226

# ke AnduinBanks, Gilgalad Cirdan
ip route add 10.78.1.220/30 via 10.78.1.226
ip route add 10.78.1.0/25 via 10.78.1.226

echo "nameserver 192.168.122.1" > /etc/resolv.conf
```

Elendil
/etc/network/interfaces
```
auto eth0
iface eth0 inet static
        address 10.78.0.2
        netmask 255.255.255.0
        gateway 10.78.0.1
#       up echo nameserver 192.168.0.1 > /etc/resolv.conf
```

/root/.bashrc
```
ip addr add 10.78.0.2/24 dev eth0
ip route add default via 10.78.0.1 dev eth0
echo "nameserver 192.168.122.1" > /etc/resolv.conf
```

Isildur
/etc/network/interfaces
```
auto eth0
iface eth0 inet static
        address 10.78.0.3
        netmask 255.255.255.0
        gateway 10.78.0.1
#       up echo nameserver 192.168.0.1 > /etc/resolv.conf
```

/root/.bashrc
```
ip addr add 10.78.0.3/24 dev eth0
ip route add default via 10.78.0.1 dev eth0
echo "nameserver 192.168.122.1" > /etc/resolv.conf
```

Pelargir
/etc/network/interfaces
```
auto eth0
iface eth0 inet static
        address 10.78.1.226
        netmask 255.255.255.252
#       gateway 
#       up echo nameserver 192.168.0.1 > /etc/resolv.conf

auto eth1
iface eth1 inet static
        address 10.78.1.217
        netmask 255.255.255.252
#       gateway 
#       up echo nameserver 192.168.0.1 > /etc/resolv.conf

auto eth2
iface eth2 inet static
        address 10.78.1.221
        netmask 255.255.255.252
#       gateway 
#       up echo nameserver 192.168.0.1 > /etc/resolv.conf
```

/root/.bashrc
```
# IP Settings
ip addr add 10.78.1.226/30 dev eth0
ip addr add 10.78.1.217/30 dev eth1
ip addr add 10.78.1.221/30 dev eth2
ip route add default via 10.78.1.225 dev eth0

# 0 ke Osgiliath
ip route add 10.78.1.236/30 via 10.78.1.225

# 1 ke Rivendell, Narya Vilya
ip route add 10.78.1.232/30 via 10.78.1.225
ip route add 10.78.1.200/29 via 10.78.1.225

# 2 ke Moria
ip route add 10.78.1.228/30 via 10.78.1.225

# ke IronHills
ip route add 10.78.1.208/30 via 10.78.1.225

# ke Wilderland
ip route add 10.78.1.212/30 via 10.78.1.225

# ke Durin
ip route add 10.78.1.128/26 via 10.78.1.225

# ke Khamul
ip route add 10.78.1.192/29 via 10.78.1.225

# 3 Minastir
ip route add 10.78.1.224/30 via 10.78.1.225

# ke Elendil Isildur
ip route add 10.78.0.0/24 via 10.78.1.225

# ke AnduinBanks, Gilgalad Cirdan
ip route add 10.78.1.220/30 via 10.78.1.222
ip route add 10.78.1.0/25 via 10.78.1.222

echo "nameserver 192.168.122.1" > /etc/resolv.conf
```

Palantir
/etc/network/interfaces
```
auto eth0
iface eth0 inet static
        address 10.78.1.218
        netmask 255.255.255.252
        gateway 10.78.1.217
#       up echo nameserver 192.168.0.1 > /etc/resolv.conf
```

/root/.bashrc
```
ip addr add 10.78.1.218/30 dev eth0
ip route add default via 10.78.1.217 dev eth0
echo "nameserver 192.168.122.1" > /etc/resolv.conf
```

AnduinBanks
/etc/network/interfaces
```
auto eth0
iface eth0 inet static
        address 10.78.1.222
        netmask 255.255.255.252
#       gateway 
#       up echo nameserver 192.168.0.1 > /etc/resolv.conf

auto eth1
iface eth1 inet static
        address 10.78.1.1
        netmask 255.255.255.128
#       gateway 
#       up echo nameserver 192.168.0.1 > /etc/resolv.conf
```

/root/.bashrc
```
ip addr add 10.78.1.222/30 dev eth0
ip addr add 10.78.1.1/25 dev eth1
ip route add default via 10.78.1.221 dev eth0

# 0 ke Osgiliath
ip route add 10.78.1.236/30 via 10.78.1.221

# 1 ke Rivendell, Narya Vilya
ip route add 10.78.1.232/30 via 10.78.1.221
ip route add 10.78.1.200/29 via 10.78.1.221

# 2 ke Moria
ip route add 10.78.1.228/30 via 10.78.1.221

# ke IronHills
ip route add 10.78.1.208/30 via 10.78.1.221

# ke Wilderland
ip route add 10.78.1.212/30 via 10.78.1.221

# ke Durin
ip route add 10.78.1.128/26 via 10.78.1.221

# ke Khamul
ip route add 10.78.1.192/29 via 10.78.1.221

# 3 Minastir
ip route add 10.78.1.224/30 via 10.78.1.221

# ke Elendil Isildur
ip route add 10.78.0.0/24 via 10.78.1.221

# ke Pelargir, Palantir
ip route add 10.78.1.224/30 via 10.78.1.221
ip route add 10.78.1.216/30 via 10.78.1.221

echo "nameserver 192.168.122.1" > /etc/resolv.conf
```

Cirdan
/etc/network/interfaces
```
auto eth0
iface eth inet static
        address 10.78.1.2
        netmask 255.255.255.128
        gateway 10.78.1.1
#       up echo nameserver 192.168.0.1 > /etc/resolv.conf
```

/root/.bashrc
```
ip addr add 10.78.1.2/25 dev eth0
ip route add default via 10.78.1.1 dev eth0
echo "nameserver 192.168.122.1" > /etc/resolv.conf
```

Gilgalad
/etc/network/interfaces
```
auto eth0
iface eth inet static
        address 10.78.1.3
        netmask 255.255.255.128
        gateway 10.78.1.1
#       up echo nameserver 192.168.0.1 > /etc/resolv.conf
```

/root/.bashrc
```
ip addr add 10.78.1.3/25 dev eth0
ip route add default via 10.78.1.1 dev eth0
echo "nameserver 192.168.122.1" > /etc/resolv.conf
```


## Misi 2 No. 1 - Misi 1 No. 4
Pertama-tama, kita akan konfigurasi DHCP Server

### DHCP Server - Vilya
Download package yang dibutuhkan.

```
apt update
apt-get install isc-dhcp-relay -y
```

Lalu, lakukan konfigurasi pada file berikut.
`nano /etc/default/isc-dhcp-relay`
```
SERVERS="10.78.1.203"
INTERFACES="eth0 eth1"
OPTIONS=""
```
Lalu, aktifkan IP forwarding dengan kode ini
```
echo 1 > /proc/sys/net/ipv4/ip_forward
```

Setelah itu, restart service
```
service isc-dhcp-relay restart
```

### DHCP Relay - Rivendell, AnduinBanks, Minastir
Install package yang diperlukan.
```
apt-get update
apt-get install isc-dhcp-relay -y
```
Lalu, kita akan melakukan konfiguarasi DHCP relay
`nano /etc/default/isc-dhcp-relay`
```
SERVERS="10.78.1.203"
INTERFACES="eth0 eth1"
OPTIONS=""
```
Dimana `10.78.1.203` adalah IP Vilya (DHCP Server).
Selanjutnya, lakuakn IP forwarding dan restart service.
```
echo 1 > /proc/sys/net/ipv4/ip_forward
service isc-dhcp-relay restart
```
### DHCP Client
Sebelum menghapus IP static yang sudah diperlukan, install package terlebih dahulu.
#### Pada Khamul, Cirdan, Isildur, Durin, Gilgalad, dan Elendil
(Contoh pada Cirdan)
```
apt-get update
apt-get install isc-dhcp-client -y
```

Jika sudah, check dengan `dhclient -V`. Jika berhasil, maka akan muncul seperti ini.

<img width="758" height="498" alt="image" src="https://github.com/user-attachments/assets/551a7f91-1efb-47a4-9cca-006f061a93ae" />

Selanjutnya, kita akan menghapus konfigurasi IP static pada client. Ubah menjadi seperti ini.
`/etc/network/interfaces`
```
auto eth0
iface eth0 inet dhcp
```
`/root/.bashrc`

```
#ip addr add 10.78.1.2/25 dev eth0
#ip route add default via 10.78.1.1 dev eth0
#echo "nameserver 192.168.122.1" > /etc/resolv.conf
```
Setelah itu, restart node DHCP Client. Jika dicek menggunakan `ip a show eth0`, maka tidak ada IP pada interface tersebut.

<img width="737" height="110" alt="image" src="https://github.com/user-attachments/assets/90b8bfc5-0752-42b8-a1a3-ee961277b882" />

Lalu, kita bisa minta IP dengan perintah ini.
```dhclient -v eth0```

Jika berhasil, maka akan muncul seperti ini.

<img width="682" height="241" alt="image" src="https://github.com/user-attachments/assets/8522ad8b-ff78-409b-9036-bb2a2b723bfd" />

`ip a show eth0`

<img width="920" height="125" alt="image" src="https://github.com/user-attachments/assets/2d6865af-a992-4f8a-9dd0-0e82ce394d77" />



