@REM ------------------ R. Scheurer, 10/2016 --------------------------
@echo off

REM TODO: Please adjust the following two paths if needed!
REM set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_144
set GCCPath=C:\TDM-GCC-64\bin

:parms
IF NOT %1/==/ goto parm2
echo Parameter(s) missing!
goto usage

:parm2
IF NOT %2/==/ goto checkGCCPath
echo Second parameter is missing!
goto usage

:checkGCCPath
REM check if path to GCC has already been added to environment variable PATH
WHERE gcc > NUL 2>&1 && goto checkJDKPath
set PATH=%PATH%;%GCCPath%
WHERE gcc > NUL 2>&1 && goto checkJDKPath
echo ERROR: could not find gcc installation!
echo You need to edit this batch file ...
goto end

:checkJDKPath
if exist "%JAVA_HOME%\bin\javac.exe" goto comp
echo ERROR: path to Java JDK (variable JAVA_HOME) is not valid!
goto end

:comp
echo on
gcc -s -D_JNI_IMPLEMENTATION_ -I"%JAVA_HOME%\include" -I"%JAVA_HOME%\include\win32" -shared %1 -o %2
@goto end

:usage
echo.
echo USAGE: gcc4jni "c-file" "dll-name"
echo.

:end