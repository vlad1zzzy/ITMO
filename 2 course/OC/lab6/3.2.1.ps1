#Get-Service -Name Dnscache | Stop-Service
#REG add "HKLM\SYSTEM\CurrentControlSet\services\Dnscache" /v Stop /t REG_DWORD /d 4 /f