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
iptables -t nat -A POSTROUTING -o eth0 -j SNAT --to-source 192.168.122.35
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
apt-get install isc-dhcp-server -y
```

Lalu, lakukan konfigurasi pada file berikut.
`nano /etc/default/isc-dhcp-server`
```
INTERFACESv4="eth0"
```

`/etc/dhcp/dhcpd.conf`
```
# Konfigurasi Global
default-lease-time 600;
max-lease-time 7200;

# PENTING: Arahkan DNS ke IP Narya
option domain-name-servers 10.78.1.202; 

# Subnet Vilya (A4) - Harus ada deklarasi ini agar service jalan
subnet 10.78.1.200 netmask 255.255.255.248 {
}

# Subnet A2 (Durin) - via Relay Wilderland
subnet 10.78.1.128 netmask 255.255.255.192 {
    range 10.78.1.131 10.78.1.190;
    option routers 10.78.1.129;
    option broadcast-address 10.78.1.191;
}

# Subnet A3 (Khamul) - via Relay Wilderland
subnet 10.78.1.192 netmask 255.255.255.248 {
    range 10.78.1.195 10.78.1.198;
    option routers 10.78.1.193;
    option broadcast-address 10.78.1.199;
}

# Subnet A5 (Elendil, Isildur) - via Relay Minastir
subnet 10.78.0.0 netmask 255.255.255.0 {
    range 10.78.0.10 10.78.0.254;
    option routers 10.78.0.1;
    option broadcast-address 10.78.0.255;
}

# Subnet A6 (Gilgalad, Cirdan) - via Relay AnduinBanks
subnet 10.78.1.0 netmask 255.255.255.128 {
    range 10.78.1.10 10.78.1.126;
    option routers 10.78.1.1;
    option broadcast-address 10.78.1.127;
}
```

Setelah itu, restart service
```
service isc-dhcp-server restart
```

### DHCP Relay - Rivendell, AnduinBanks, Minastir, Wilderland
Install package yang diperlukan.
```
apt-get update
apt-get install isc-dhcp-relay -y
```
Lalu, kita akan melakukan konfiguarasi DHCP relay

#### Rivendell, AnduinBanks, Minastir
`nano /etc/default/isc-dhcp-relay`
```
SERVERS="10.78.1.203"
INTERFACES="eth0 eth1"
OPTIONS=""
```

#### Wilderland
`nano /etc/default/isc-dhcp-relay`
```
SERVERS="10.78.1.203"
INTERFACES="eth0 eth1 eth2"
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


### DNS Server - Narya
Install package yang diperlukan.
```
apt-get update
apt-get install bind9
```
Lakukan konfigurasi file seperti ini.
`/etc/bind/named.conf.options`
```
options {
        directory "/var/cache/bind";
        allow-query { any; };
        auth-nxdomain no;    # conform to RFC1035
        listen-on-v6 { any; };
};
```

`/etc/bind/named.conf.local`
```
zone "middleearth.local" {
    type master;
    file "/etc/bind/db.middleearth";
};
```

`/etc/bind/db.middleearth`
```
;
; BIND data file for local loopback interface
;
$TTL    604800
@       IN      SOA     middleearth.local. root.middleearth.local. (
                              2         ; Serial
                         604800         ; Refresh
                          86400         ; Retry
                        2419200         ; Expire
                         604800 )       ; Negative Cache TTL
;
@       IN      NS      middleearth.local.
@       IN      A       10.78.1.202   ; IP Narya sendiri
; Record Web Server
ironhills    IN      A       10.78.1.210
palantir     IN      A       10.78.1.218
```

Setelah itu, kita restart bind9.
```
service named restart
```

### Web Server - IronHills, Palantir
Install `nginx`
```
apt-get update
apt-get install nginx
```

Buat file `index.html` (IronHills)
```
echo "Welcome to IronHills" > /var/www/html/index.html
```


Buat file `index.html` (Palantir)
```
echo "Welcome to Palantir" > /var/www/html/index.html
```

Jalankan `nginx`
```
service nginx start
```

### Uji Coba di Client
Jika Web Server berhasil diimplementasikan, maka akan muncul seperti ini.
`curl ironhills.middleearth.local`
<img width="621" height="71" alt="image" src="https://github.com/user-attachments/assets/370bc370-b9bb-4a48-8e1c-40fd79a21a00" />

`curl palantir.middleearth.local`
<img width="625" height="66" alt="image" src="https://github.com/user-attachments/assets/326992cb-56c1-4458-b751-995d5eccafbe" />

### Error Handling
Jika saat DHCP Client menjalankan perintah `dhclient -v eth0`, lalu mumcul
```
bash: dhclient: command not found
```
Maka dapat menjalankan perintah seperti ini.
```
echo "nameserver 192.168.122.1" > /etc/resolv.conf  # untuk sementara.

apt-get update
apt-get install isc-dhcp-client -y

rm /etc/resolv.conf
dhclient -v eth0
```
## Misi 2 No. 2
Untuk melakukannya, kita hanya perlu satu iptables untuk konfigurasi ini.

### Vilya
```
iptables -A INPUT -p icmp --icmp-type echo-request -j DROP
```

Jika berhasil maka akan muncul seperti ini.
### IP Khamul (Dynamic)

<img width="604" height="105" alt="image" src="https://github.com/user-attachments/assets/8d55c686-3088-4e40-81b8-1a7f5c717ff6" />


### Vilya

<img width="620" height="193" alt="image" src="https://github.com/user-attachments/assets/84b26356-56e1-4a7e-8074-8976ef952a5c" />

### Khamul

<img width="648" height="140" alt="image" src="https://github.com/user-attachments/assets/987765c4-b207-4567-9a75-64e28c5e1b9c" />

Di mana Vilya dapat melakukan ping ke client, namun tidak sebaliknya.
## Misi 2 No. 3
Sebelum melakukan `iptables`, kita download netcat terlebih dahulu, di Vilya dan client lain.
```
apt-get update
apt-get install netcat-openbsd
```

Jika sudah, maka dapat melakukan iptables pada Narya.
```
iptables -A INPUT -p udp --dport 53 -s 10.78.1.203 -j ACCEPT
iptables -A INPUT -p udp --dport 53 -j REJECT
```
Lalu, kita dapat mengujinya dengan 
```
nc -z -v -u 10.78.1.202 53
```

Pada Vilya maupun client lainnya. Jika sudah berhasil maka akan muncul seperti ini.

### Vilya

<img width="614" height="37" alt="image" src="https://github.com/user-attachments/assets/b843dadf-6b37-4cdd-936c-63faf06b239b" />

### Khamul

<img width="615" height="42" alt="image" src="https://github.com/user-attachments/assets/2296dd07-f1e4-4339-8a42-2e7e1ec97599" />

Di mana hanya node Vilya yang dapat terhubung ke Narya.

## Misi 2 No. 4

Pada soal ini, kita akan melakukan `iptables` di IronHills. Untuk kofigurasinya seperti ini.

```
# 1. Izinkan Subnet Durin
iptables -A INPUT -p tcp --dport 80 -s 10.78.1.128/26 -m time --weekdays Sat,Sun -j ACCEPT

# 2. Izinkan Subnet Khamul
iptables -A INPUT -p tcp --dport 80 -s 10.78.1.192/29 -m time --weekdays Sat,Sun -j ACCEPT

# 3. Izinkan Subnet Elendil & Isildur
iptables -A INPUT -p tcp --dport 80 -s 10.78.0.0/24 -m time --weekdays Sat,Sun -j ACCEPT

# 4. Blokir sisanya
iptables -A INPUT -p tcp --dport 80 -j DROP
```
Menggunakan subnet diperlukan karena client menggunakan DHCP. Jika berhasil maka akan muncul seperti ini.
### hari Jum'at

<img width="1062" height="147" alt="image" src="https://github.com/user-attachments/assets/a530d9b2-488d-4355-be50-4a178d8c9ef3" />

### Hari Sabtu

<img width="1061" height="149" alt="image" src="https://github.com/user-attachments/assets/4c476cbd-c798-4ef0-a43a-21e4f8639557" />

## Misi 2 No. 5
Untuk dapat melakukan shift di jam tertentu, kita akan mengkonfigurasi Palantir. 
```
# Shift Pagi di Subnet A6 (Gilgalad & Cirdan)
iptables -A INPUT -p tcp --dport 80 -s 10.78.1.0/25 -m time --timestart 07:00 --timestop 15:00 -j ACCEPT

# Shift Malam di Subnet A5 (Elendil & Isildur)
iptables -A INPUT -p tcp --dport 80 -s 10.78.0.0/24 -m time --timestart 17:00 --timestop 23:00 -j ACCEPT

# Blokir Sisanya
iptables -A INPUT -p tcp --dport 80 -j DROP
```
Setelah itu, jalankan `nginx` di Palantir.

```
service nginx start
```

Cek di Client menggunakan command ini.

```
curl 10.78.1.218
```

Jika berhasil, maka akan muncul seperti ini.

### Elendil
<img width="631" height="121" alt="image" src="https://github.com/user-attachments/assets/643d48a8-710a-42d2-bf8d-7ed2e47bb30f" />

### Cirdan
<img width="619" height="539" alt="image" src="https://github.com/user-attachments/assets/5740ae90-dcd1-451e-831a-a6b9848fd44b" />

Di mana pada pukul `12:51:33 UTC` Cirdan dapat terhubung dengan Palantir, sedangkan Elendil tidak dapat terhubung dengan Palantir. Ini menunjukkan bahwa konfigurasi sukses.

## Soal 2 No. 6
Kita akan mengkonfigurasi Palantir menggunakan iptables. Untuk kodenya seperti ini.

```
# Flush Iptables
iptables -F

# A. Jika IP ini sudah ditandai, blokir dan reset timer 20 detik.
iptables -A INPUT -m recent --name PORT_SCANNER --update -j DROP

# Jika koneksi SYN > 15 kali dalam 20 detik, maka akan dicatat.
iptables -A INPUT -p tcp --syn -m recent --name SCAN_COUNT --rcheck --seconds 20 --hitcount 16 -j LOG --log-prefix "PRT_SCAN_DETECTED: " --log-level 4

# Jika koneksi SYN > 15 kali dalam 20 detik, DROP.
iptables -A INPUT -p tcp --syn -m recent --name SCAN_COUNT --rcheck --seconds 20 --hitcount 16 -m recent --name PORT_SCANNER --set -j DROP

# Setiap koneksi baru dihitung.
iptables -A INPUT -p tcp --syn -m recent --name SCAN_COUNT --set


# Konfigurasi Berdasarkan Waktu

# Shift Pagi untuk Subnet A6 (Gilgalad & Cirdan)
iptables -A INPUT -p tcp --dport 80 -s 10.78.1.0/25 -m time --timestart 07:00 --timestop 15:00 -j ACCEPT

# Shift Malam untuk Subnet A5 (Elendil & Isildur)
iptables -A INPUT -p tcp --dport 80 -s 10.78.0.0/24 -m time --timestart 17:00 --timestop 23:00 -j ACCEPT

# Blokir Sisanya
iptables -A INPUT -p tcp --dport 80 -j DROP
```

Lalu, lakukan `nmap` pada Elendil. Untuk commandnya seperti ini. Kita dapat mengecek akses koneksinya menggunakan `ping`.
```
ping -c 3 10.78.1.218

nmap -T5 -p 1-100 10.78.1.218

ping -c 3 10.78.1.218
```

Jika berhasil, maka akan muncul seperti ini.

### Palantir
<img width="635" height="635" alt="image" src="https://github.com/user-attachments/assets/10fb74cd-f402-4251-9bdd-d04fbefce5f1" />


### Elendil
<img width="630" height="768" alt="image" src="https://github.com/user-attachments/assets/110810b6-cfa1-4351-814e-565143c63459" />




## Soal 2 No. 7
Kita akan membatasi akses ke IronHills maksimal 3 koneksi dalam waktu yang bersaman. Kita akan melakukan konfigurasi pada IronHills. 
```
iptables -F

# Limit akses hingga 3 koneksi dalam waktu yang bersamaan
iptables -A INPUT -p tcp --dport 80 -m connlimit --connlimit-above 3 --connlimit-mask 32 -j REJECT --reject-with tcp-reset

# Aturan Akses Waktu
# Subnet Durin (A2)
iptables -A INPUT -p tcp --dport 80 -s 10.78.1.128/26 -m time --weekdays Sat,Sun -j ACCEPT

# Subnet Khamul (A3)
iptables -A INPUT -p tcp --dport 80 -s 10.78.1.192/29 -m time --weekdays Sat,Sun -j ACCEPT

# Subnet Elendil & Isildur (A5)
iptables -A INPUT -p tcp --dport 80 -s 10.78.0.0/24 -m time --weekdays Sat,Sun -j ACCEPT

# Blokir Sisanya
iptables -A INPUT -p tcp --dport 80 -j DROP
```

Lalu, kita coba test di Client yang bersangkutan. Kita dapa tmembuat file script dengan isi seperti ini.

`curl_test.sh`

```bash
echo "--- TES BUKTI LIMIT 3 IP ---"
# Kita jalankan 10 request SEKALIGUS (background)
for i in {1..10}; do
  curl -s -o /dev/null -w "Request $i: %{http_code}\n" --connect-timeout 2 http://10.78.1.210/ &
done
wait
```

Lalu jalankan dengan command ini.
```
chmod +x curl_test.sh
./curl_test.sh
```

Jika berhasil maka akan muncul seperti ini.

<img width="398" height="313" alt="image" src="https://github.com/user-attachments/assets/db9fe9bf-cb5a-466e-a0ef-764179ffe929" />



## Misi 2 No. 8
Agar pesannya dapat dibelokkan ke IronHills, maka kita perlu mengkonfigurasi Moria. Untuk konfigurasinya seperti ini.

```
iptables -t nat -A PREROUTING -s 10.78.1.203 -d 10.78.1.196 -p tcp --dport 5555 -j DNAT --to-destination 10.78.1.210:5555
```

Setelah itu, jalankan kode ini pada Vilya, IronHills, dan Khamul.
```
nc -l -p 5555
```

Lalu, Kita dapat mencoba mengirim pesan dari VIlya dengan mengetikkan `Hai Khamul`. Jika berhasil maka akan muncul seperti ini.

### Vilya
<img width="348" height="56" alt="image" src="https://github.com/user-attachments/assets/51783bc0-130c-42e9-9133-fa850a5a97c5" />

### IronHills
<img width="376" height="65" alt="image" src="https://github.com/user-attachments/assets/9a336e4c-ebcf-44d0-b615-2104f591ac60" />

### Khamul
<img width="316" height="47" alt="image" src="https://github.com/user-attachments/assets/8c193c64-a9c2-47e4-a44c-de9103edab89" />

Di mana pesannya muncul di IronHills, bukan Khamul. Ini menandakan bahwa konfigurasi berhasil.

## Misi 3 No. 1
Pada soal terakhir, kita akan mencoba konfigurasi pada Wilderland. Untuk konfigurasinya seperti ini.
```
iptables -F FORWARD
iptables -I FORWARD -i eth2 -m mac --mac-source 02:42:79:29:0c:00 -j DROP
```
Di mana `02:42:79:29:0c:00` adalah mac milik Khamul (dapat dicek pada `ip a show eth0` pada Khamul). Mengapa menggunakan mac? Karena Khamul merupakan DHCP Client, apabila menggunakan IP maka kemungkinan dapat terjadi salah konfigurasi.

Untuk pengujiannya dapat menggunakan command seperti ini.

### `ping`
```
ping <IP Khamul/Client lain>
```

### `nc`
```
# Sender
nc -v <IP Tujuan> 8888

# Listener
nc -l -p 8888 
```
Jika berhasil maka akan muncul seperti ini.

### IronHills ke Khamul

<img width="646" height="217" alt="image" src="https://github.com/user-attachments/assets/684b4234-b700-4f0c-bbc2-bfdb68bd5953" />

<img width="615" height="48" alt="image" src="https://github.com/user-attachments/assets/88f251a5-a4b2-47e4-82bf-1118d3e17147" />

### Khamul ke IronHills

<img width="384" height="47" alt="image" src="https://github.com/user-attachments/assets/fd984f63-7360-4f4b-86a9-00a0991342cf" />

<img width="632" height="217" alt="image" src="https://github.com/user-attachments/assets/87f77c6a-4ae7-42ff-9802-a913f0622c03" />


