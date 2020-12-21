$name = HOSTNAME
mkdir "C:\$name\temp"
New-SmbShare -Name "TmpShare" -Path "C:\$name\temp" -ChangeAccess "Users" -FullAccess "Administrators"