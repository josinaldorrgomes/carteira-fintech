@REM ----------------------------------------------------------------------------
@REM Apache Maven Wrapper (Windows) - distributionType=only-script
@REM Baixa o Maven automaticamente na primeira execucao. Nao precisa de .jar.
@REM ----------------------------------------------------------------------------
@echo off
setlocal

set "SCRIPT_DIR=%~dp0"
set "PROPERTIES=%SCRIPT_DIR%.mvn\wrapper\maven-wrapper.properties"
if not exist "%PROPERTIES%" (echo mvnw: nao encontrei %PROPERTIES% & exit /b 1)

for /f "usebackq tokens=1* delims==" %%A in ("%PROPERTIES%") do (
  if "%%A"=="distributionUrl" set "distributionUrl=%%B"
)
if "%distributionUrl%"=="" (echo mvnw: distributionUrl ausente & exit /b 1)

for %%F in ("%distributionUrl%") do set "distributionUrlName=%%~nxF"
set "distributionName=%distributionUrlName:-bin.zip=%"

if "%MAVEN_USER_HOME%"=="" set "MAVEN_USER_HOME=%USERPROFILE%\.m2"
set "MAVEN_HOME=%MAVEN_USER_HOME%\wrapper\dists\%distributionName%"

if exist "%MAVEN_HOME%\bin\mvn.cmd" (
  "%MAVEN_HOME%\bin\mvn.cmd" %*
  exit /b %ERRORLEVEL%
)

echo Baixando o Apache Maven de: %distributionUrl%
set "TMP_ZIP=%TEMP%\maven-%RANDOM%.zip"
powershell -NoProfile -Command "Invoke-WebRequest -Uri '%distributionUrl%' -OutFile '%TMP_ZIP%'" || (echo mvnw: falha no download & exit /b 1)
powershell -NoProfile -Command "Expand-Archive -Force -Path '%TMP_ZIP%' -DestinationPath '%MAVEN_USER_HOME%\wrapper\dists'" || (echo mvnw: falha ao extrair & exit /b 1)
del "%TMP_ZIP%" >nul 2>&1

"%MAVEN_HOME%\bin\mvn.cmd" %*
exit /b %ERRORLEVEL%
