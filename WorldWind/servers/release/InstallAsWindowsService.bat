@echo on
SET SERVICE_NAME=wmsd
SET SERVICE_PATH=C:\opt\wms
SET SERVICE_DESCRIPTION="NASA World Wind WMS (Web Map Service)"
SET SERVICE_STARTUP_CLASS="gov.nasa.worldwind.servers.wms.WMSServer"
SET JDK_SERVER_JVM_PATH="C:\Java\jdk1.6.0_20-x64\jre\bin\server\jvm.dll"
REM SET JAVA_CLASSPATH="C:\Java\jdk1.6.0_20-x64\jre\lib\alt-rt.jar;C:\Java\jdk1.6.0_20-x64\jre\lib\charsets.jar;C:\Java\jdk1.6.0_20-x64\jre\lib\deploy.jar;C:\Java\jdk1.6.0_20-x64\jre\lib\javaws.jar;C:\Java\jdk1.6.0_20-x64\jre\lib\jce.jar;C:\Java\jdk1.6.0_20-x64\jre\lib\jsse.jar;C:\Java\jdk1.6.0_20-x64\jre\lib\management-agent.jar;C:\Java\jdk1.6.0_20-x64\jre\lib\plugin.jar;C:\Java\jdk1.6.0_20-x64\jre\lib\resources.jar;C:\Java\jdk1.6.0_20-x64\jre\lib\rt.jar;C:\Java\jdk1.6.0_20-x64\jre\lib\ext\clibwrapper_jiio.jar;C:\Java\jdk1.6.0_20-x64\jre\lib\ext\dnsns.jar;C:\Java\jdk1.6.0_20-x64\jre\lib\ext\jai_codec.jar;C:\Java\jdk1.6.0_20-x64\jre\lib\ext\jai_core.jar;C:\Java\jdk1.6.0_20-x64\jre\lib\ext\jai_imageio.jar;C:\Java\jdk1.6.0_20-x64\jre\lib\ext\localedata.jar;C:\Java\jdk1.6.0_20-x64\jre\lib\ext\mlibwrapper_jai.jar;C:\Java\jdk1.6.0_20-x64\jre\lib\ext\sunjce_provider.jar;%CLASSPATH%"

%SERVICE_NAME%.exe //IS//%SERVICE_NAME%  --Description=%SERVICE_DESCRIPTION% --Startup=auto --DisplayName=%SERVICE_DESCRIPTION% --Install="%SERVICE_PATH%\%SERVICE_NAME%.exe"  --LogPath=%SERVICE_PATH%  --LogPrefix=%SERVICE_NAME% --LogLevel=DEBUG --StdOutput="%SERVICE_PATH%\%SERVICE_NAME%-stdout.txt" --Jvm=%JDK_SERVER_JVM_PATH% --Classpath="%SERVICE_PATH%;%SERVICE_PATH%\lib;%SERVICE_PATH%\lib\WMS.jar;%SERVICE_PATH%\lib\gdal.jar;%SERVICE_PATH%\lib\worldwind.jar;%SERVICE_PATH%\lib\jogl.jar;%SERVICE_PATH%\lib\gluegen-rt.jar"  --StartMode=jvm --StartClass=%SERVICE_STARTUP_CLASS% --StartMethod=main --StartParams=start --StartPath=%SERVICE_PATH% --StopMode=jvm --StopClass=%SERVICE_STARTUP_CLASS%  --StopMethod=main --StopParams=stop --StopTimeout=10 --StopPath=%SERVICE_PATH% ++JvmOptions="-Djava.awt.headless=true;-Dsun.java2d.noddraw=true;-Djava.util.logging.config.file=%SERVICE_PATH%\wms.logging.properties;-Djava.library.path=%SERVICE_PATH%\lib;"

