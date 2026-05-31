# Inicia backend y frontend con un solo comando (sin instalar dependencias extra).
$Root = $PSScriptRoot

Write-Host "Iniciando backend (Spring Boot)..." -ForegroundColor Cyan
Start-Process powershell -ArgumentList @(
  '-NoExit',
  '-Command',
  "Set-Location '$Root\reservation-backend'; .\mvnw.cmd spring-boot:run"
)

Start-Sleep -Seconds 2

Write-Host "Iniciando frontend (Angular)..." -ForegroundColor Magenta
Set-Location "$Root\reservation-frontend"
npm start
