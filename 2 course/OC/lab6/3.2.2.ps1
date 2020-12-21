#tyt ostanovka DNS-Client, kotoraya ne rabotaet

$time = (Get-Date).AddMinutes(1).ToString("HH:mm")
SchTasks.exe /F /Create /SC ONCE /ST $time  /TN "GetService" /TR "powershell -command &{Get-Service | findstr Running > C:\LAB6\service.upd.txt}"

#Start-Sleep -Seconds 5
#Get-Service | findstr Running > service.upd.txt