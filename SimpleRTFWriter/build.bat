@echo off

REM BUILD Verzeichnis löschen und neu anlegen
rd .\build /s /q
md .\build

REM Beispiele kopieren
xcopy ..\SimpleRTFWriter_Examples\*.* .\build\examples\ /s
rd .\build\examples\bin\ /s /q
del /q .\build\examples\out_*
del /q .\build\examples\.* 

REM Readme.txt und LIcense.txt kopieren
xcopy .\infos\license.txt .\build\
xcopy .\infos\readme.txt .\build\
xcopy .\infos\changelog.txt .\build\

REM jar erstellen
pushd .\bin
%JAVA_HOME%\bin\jar cvf ..\build\srw.jar *
popd

REM JavaDoc erstellen
%JAVA_HOME%\bin\javadoc -locale de_DE -public -sourcepath .\src\ -d .\build\docs -use com.itseasy.rtf com.itseasy.rtf.text com.itseasy.rtf.exception

REM evtl. -version -author -windowtitle yyy -doctitle <html> -header <html> -footer <html> -bottom <html> 

pause
