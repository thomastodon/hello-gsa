# Running Things Locally

get the `DOCKER_HOST` IP via `$ docker-machine env default`

run the `spring` container via `$ docker run -p 8080:8080 --name hello-gsa-spring -t hello-gsa-spring`

run the `mysql` container via

to ssh into the container: `$ docker exec -it hello-gsa-spring sh`