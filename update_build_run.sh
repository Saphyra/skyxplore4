git pull
mvn clean package
nohup java $* -jar target/skyxplore4-1.0-SNAPSHOT.jar > /dev/null &