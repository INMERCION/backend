@echo off
REM Script para compilar e iniciar el backend de Mr. Pastel
REM Este script usa Maven para compilar y ejecutar la aplicación Spring Boot

setlocal enabledelayedexpansion

echo.
echo =====================================
echo Mr. Pastel Backend - Build ^& Run
echo =====================================
echo.

REM Verificar si mvnw.cmd existe
if exist "mvnw.cmd" (
    echo. ^> Maven Wrapper encontrado
    set MAVEN_CMD=mvnw.cmd
) else (
    echo. ^> Intentando con 'mvn'
    set MAVEN_CMD=mvn
)

REM Compilar el proyecto
echo.
echo Compilando el proyecto...
call %MAVEN_CMD% clean compile -DskipTests
if errorlevel 1 (
    echo.
    echo ❌ Error en la compilacion
    exit /b 1
)

echo.
echo ✓ Compilacion exitosa
echo.
echo Iniciando la aplicacion...
echo.
echo La aplicacion estara disponible en: http://localhost:8080
echo Swagger UI disponible en: http://localhost:8080/swagger-ui.html
echo.

REM Ejecutar la aplicación
call %MAVEN_CMD% spring-boot:run

endlocal
