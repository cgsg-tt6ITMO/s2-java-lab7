# JAVA, 2nd semester, Laba № 7
Started 14.05.2023

## HOW IT WORKS

<details><summary>Run this programme:</summary>


Connect to Helios -> run Server -> run Client -> register -> login -> enjoy commands!

</details>

<details><summary>How to connect to Helios:</summary>

```
 ssh -p2222 -L 5432:pg:5432 sXXXXXX@helios.cs.ifmo.ru
```
For example, for me it becomes:
```
ssh -p2222 -L 5432:pg:5432 s368924@helios.cs.ifmo.ru
```
Enter the password.

<hr/>

Enter:
``` 
cat .pgpass
```
You get:
```
*:*:*:sXXXXXX:[password]
```
The password is to be stored in *db.cfg* (for connection to server)

</details>

<details><summary>Execute script manual:</summary>

Firstly, register, then login. Then:

```
execute_script
C:\Z\ITMO\LabaN7\Client\src\files\inp.txt
```

</details>

## THE TASK, variant № 9844

![image](https://github.com/cgsg-tt6ITMO/s2-java-lab7/assets/76934492/2b20f055-35b0-4a6d-be93-8e40f2a4a0c7)

