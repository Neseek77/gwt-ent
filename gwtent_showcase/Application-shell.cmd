@java -Xmx1024M -cp  "%~dp0\src;%~dp0\war\WEB-INF\classes;%~dp0/lib/gwtent.jar;%~dp0\lib\gwt-user.jar;%~dp0\lib\gwt-dev-windows.jar;%~dp0/lib/gwtent-acegi.jar;%~dp0/lib/aspectjlib.jar;%~dp0/lib/aspectjrt.jar;%~dp0/lib/aspectjtools.jar;%~dp0/lib/aspectjweaver.jar;%~dp0/lib/freemarker.jar" com.google.gwt.dev.HostedMode -startupUrl Gwtent_showcase.html com.gwtent.showcase.Gwtent_showcase

echo %~dp0
pauce