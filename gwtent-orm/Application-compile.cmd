@java -Xmx256M -cp "%~dp0\src;%~dp0\bin;C:/eclipse/gwt-windows-1.5.0/gwt-user.jar;C:/eclipse/gwt-windows-1.5.0/gwt-dev-windows.jar;C:\develop\cas_web2\ThirdParty\lib\gwt-gears.jar;C:\eclipse\gwt-windows-1.5.0\gwt-ent\gwtent\gwtent.jar" com.google.gwt.dev.GWTCompiler -out "%~dp0\www" %* com.gwtent.orm.Orm

pause