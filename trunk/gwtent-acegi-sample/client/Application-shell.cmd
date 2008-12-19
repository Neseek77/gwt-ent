@java -Xmx512M -cp "%~dp0\src;%~dp0\bin;%GWT_HOME%/gwt-user.jar;%GWT_HOME%/gwt-dev-windows.jar;%~dp0/lib/gwtent.jar;%~dp0/lib/gwtent-acegi.jar;" com.google.gwt.dev.GWTShell -noserver -port 8080 -out "%~dp0/www" %* com.gwtent.acegi.sample.Application/Application.html

echo %~dp0