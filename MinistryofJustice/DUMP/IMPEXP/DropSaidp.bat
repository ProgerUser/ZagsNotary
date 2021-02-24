@ECHO off
set curdir=%CD%
cd C:\oraclexe\app\oracle\product\11.2.0\server\bin
sqlplus.exe / as sysdba @%curdir%\drop.sql