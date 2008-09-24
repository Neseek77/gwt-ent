@java -Xmx1024M -cp  "%~dp0\src;%~dp0\bin;%GWT_HOME%/gwt-user.jar;%GWT_HOME%/gwt-dev-windows.jar;%~dp0/lib/gwtent.jar;%~dp0/lib/gwtent-acegi.jar" com.google.gwt.dev.GWTCompiler -out "%~dp0\www" %* com.gwtent.sample.Application

echo %~dp0
echo %GWT_HOME%

pause