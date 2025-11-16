#!/usr/bin/env powershell

# Script para compilar e iniciar el backend de Mr. Pastel
# Este script usa Maven para compilar y ejecutar la aplicación Spring Boot

param(
    [switch]$Clean = $false,
    [switch]$SkipTests = $true
)

$backendDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$mvnwCmd = Join-Path $backendDir "mvnw.cmd"

Write-Host "=====================================" -ForegroundColor Cyan
Write-Host "Mr. Pastel Backend - Build & Run" -ForegroundColor Cyan
Write-Host "=====================================" -ForegroundColor Cyan
Write-Host ""

# Verificar si mvnw.cmd existe
if (Test-Path $mvnwCmd) {
    Write-Host "✓ Maven Wrapper encontrado" -ForegroundColor Green
    $mvnCmd = "cmd /c `"$mvnwCmd`""
} else {
    Write-Host "✗ Maven Wrapper NO encontrado, intentando con 'mvn'" -ForegroundColor Yellow
    $mvnCmd = "mvn"
}

# Compilar el proyecto
Write-Host ""
Write-Host "Compilando el proyecto..." -ForegroundColor Yellow
$buildArgs = @("clean", "compile")
if ($SkipTests) {
    $buildArgs += "-DskipTests"
}

Invoke-Expression "$mvnCmd $($buildArgs -join ' ')"
if ($LASTEXITCODE -ne 0) {
    Write-Host "❌ Error en la compilación" -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "✓ Compilación exitosa" -ForegroundColor Green
Write-Host ""
Write-Host "Iniciando la aplicación..." -ForegroundColor Yellow
Write-Host ""
Write-Host "La aplicación estará disponible en: http://localhost:8080" -ForegroundColor Cyan
Write-Host "Swagger UI disponible en: http://localhost:8080/swagger-ui.html" -ForegroundColor Cyan
Write-Host ""

# Ejecutar la aplicación
Invoke-Expression "$mvnCmd spring-boot:run"
