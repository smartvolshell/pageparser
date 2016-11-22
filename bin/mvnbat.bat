@cd ..
call set MAVEN_OPTS=-Xms512m -Xms128m
call mvn clean
call mvn package -Dmanve.test.skip=true
@pause
