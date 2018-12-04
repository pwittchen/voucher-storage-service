# voucher-storage-service [![Build Status](https://travis-ci.org/pwittchen/voucher-storage-service.svg?branch=master)](https://travis-ci.org/pwittchen/voucher-storage-service)
A microservice developed during "Kyma meets CCV2 Hackathon" at SAP Labs in Gliwice, Poland. It is responsible for providing and using promotional vouchers. This app is a small part of a bigger solution developed by the whole team during the hackathon.

**Tech Stack**
- application: JVM, Kotlin, Gradle, Dagger, Javalin, Docker
- tests: JUnit, JUnit Params, Truth, Mockito, REST Assured

## building and running the app

- building app: `./gradlew clean build`
- running app: `java -jar build/libs/app-1.0-all.jar`

## tests

- running unit tests: `./gradlew test`
- running integration (rest api) tests: `./gradlew test -Dtest.profile=integration`

## rest api

```
GET localhost:7000/voucher           # gets all available vouchers
GET localhost:7000/voucher/click10   # gets and deactivates voucher for 10% discount
GET localhost:7000/voucher/click15   # gets and deactivates voucher for 15% discount
GET localhost:7000/voucher/click20   # gets and deactivates voucher for 20% discount
GET localhost:7000/health            # gets information about health check
```

## docker

- building docker container: `./dockerw.sh --build`
- running docker container: `./dockerw.sh --run`
- pushing docker container: `./dockerw.sh --push`
- pulling docker container: `./dockerw.sh --pull`
- removing docker container: `./dockerw.sh --remove`
- displaying help for docker wrapper script: `./dockerw.sh --help`
- docker image is available at https://hub.docker.com/r/pwittchen/voucher-storage-service/
