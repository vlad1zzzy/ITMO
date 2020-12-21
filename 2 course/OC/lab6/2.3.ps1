$name = HOSTNAME
$time = (Get-Date).AddMinutes(1).ToString("HH:mm")
SchTasks.exe /F /Create /SC ONCE /ST $time  /TN "RunPrev" /TR "powershell -file C:\LAB6\2.2.ps1"
#SchTasks.exe /F /Create /SC ONCE /ST $time  /TN "RunPrev" /TR "robocopy C:\Windows C:\$name\temp /min:2097152 /Z -WindowStyle hidden"
#Start-Sleep -Seconds 5
#& ".\2.2.ps1"