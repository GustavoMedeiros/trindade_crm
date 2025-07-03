@echo off
cd /d C:\Projetos\trindade_crm\backend\crm
echo Iniciando o CRM da Trindade Isenções...
call mvnw.cmd compile
call mvnw.cmd exec:java -Dexec.mainClass=com.trindadeisencoes.crm.CrmApplication
pause