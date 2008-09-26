@java -Xmx1024M -cp  "%~dp0\src;%~dp0\bin;C:/eclipse/gwt-windows-1.5.0/gwt-user.jar;C:/eclipse/gwt-windows-1.5.0/gwt-dev-windows.jar;%~dp0/WebContent/lib/gwtent.jar;%~dp0/WebContent/lib/gwtent-acegi.jar" com.google.gwt.dev.GWTCompiler -out "%~dp0\www" %* com.gwtent.acegi.sample.Application

pause