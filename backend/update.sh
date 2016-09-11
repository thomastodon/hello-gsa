./gradlew build
docker cp build/libs/app-gsa-spring-0.1.0.jar webapp:/app.jar
docker restart db
docker restart webapp