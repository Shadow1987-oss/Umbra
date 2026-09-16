@echo off
setlocal EnableDelayedExpansion

rem --- locate the bundle (root of the project or next to this .bat) ---
if exist "%~dp0build\injection" goto root
goto bundle
:root
cd /d "%~dp0build\injection"
goto run
:bundle
cd /d "%~dp0"
:run

echo ================================================
echo   Umbra injection helper
echo ================================================
echo.
echo [i] Bundle: %CD%
echo.

if not exist "UmbraNative.dll" goto nodll

echo Select the Minecraft version (Java process) from the list below.
echo Use Up/Down arrows, press Enter to inject, Esc to quit.
echo.
UmbraInjector.exe
echo.
echo [i] Resultado: umbra-native.log
pause
exit /b 0

:nodll
echo [!] UmbraNative.dll not found in: %CD%
echo     Make sure UmbraNative.dll is in the same folder as this script.
echo.
pause
exit /b 1
