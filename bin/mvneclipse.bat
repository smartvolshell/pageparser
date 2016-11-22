@cd ..
call set MAVEN_OPTS=-Xmx768m -Xms256m
call mvn eclipse:clean eclipse:eclipse -DdownloadSources=true -e -U
@pause
