docker stop hello-gsa-spring
docker rm hello-gsa-spring
docker rmi hello-gsa-spring
./gradlew buildDocker
docker run -p 8080:8080 --name hello-gsa-spring -t hello-gsa-spring