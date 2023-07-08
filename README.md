# JAVA, 2nd semester, Laba № 7
Started 14.05.2023

## HOW IT WORKS

Connect to Helios:
```
 ssh -p2222 -L 5432:pg:5432 sXXXXXX@helios.cs.ifmo.ru
```
For example, for me it becomes:
```
ssh -p2222 -L 5432:pg:5432 s368924@helios.cs.ifmo.ru
```
Enter the password.

Enter:
``` 
cat .pgpass
```
You get:
```
*:*:*:sXXXXXX:[password]
```
Tthe password is to be stored in *db.cfg* (for connection to server)

## THE TASK, variant № 9844

![image](https://github.com/cgsg-tt6ITMO/s2-java-lab7/assets/76934492/2b20f055-35b0-4a6d-be93-8e40f2a4a0c7)

