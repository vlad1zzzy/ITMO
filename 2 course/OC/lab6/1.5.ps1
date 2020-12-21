$content = Get-Content -Path .\1.*.ps1
Set-Content -Path .\commands.txt -Value $content