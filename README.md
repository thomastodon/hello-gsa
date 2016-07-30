# Running Things Locally

get the `DOCKER_HOST` IP via `$ docker-machine env default`

run the `spring` container via `$ docker run -p 8080:8080 --name spring -t springio/gs-spring-boot-docker`
run the `mysql` container via

access the web application in chrome at the `DOCKER_HOST` IP through port 8080, (e.g. `http://192.168.99.100:8080/`)
