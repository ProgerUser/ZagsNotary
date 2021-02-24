set curdir=%CD%
cd C:\oraclexe\app\oracle\product\11.2.0\server\bin
impdp.exe system/123@XE schemas=saidp directory=TEST_DIR dumpfile=dmp.dmp logfile=dmp.log;

sqlplus.exe / as sysdba @%curdir%\grantandrecomp.sql