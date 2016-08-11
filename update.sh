./gradlew build
docker cp build/libs/hello-gsa-spring-0.1.0.jar webapp:/app.jar
docker restart db
docker restart webapp