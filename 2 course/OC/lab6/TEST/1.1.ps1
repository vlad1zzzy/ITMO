systeminfo | findstr /b /c:"OS Version" > 1.1.systeminfo.txt
systeminfo | findstr /c:"Memory" >> 1.1.systeminfo.txt
Get-WmiObject -Class Win32_LogicalDisk > 1.1.wmic.txt