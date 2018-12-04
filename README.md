# voucher-storage-service
A microservice developed during "Kyma meets CCV2 Hackathon" at SAP Labs in Gliwice, Poland. It is responsible for providing and using promotional vouchers. This app is a small part of a bigger solution developed by the whole team during the hackathon.

## building and running the app

- building app: `./gradlew clean build`
- running app: `java -jar build/libs/app-1.0-all.jar`
- building docker container with app: `./dockerw.sh -b`
- running docker container with app: `./dockerw.sh -r`

## rest api

```
GET localhost:7000/voucher           # gets all available vouchers
GET localhost:7000/voucher/click10   # gets and deactivates voucher for 10% discount
GET localhost:7000/voucher/click15   # gets and deactivates voucher for 15% discount
GET localhost:7000/voucher/click20   # gets and deactivates voucher for 20% discount
```

## docker hub

docker image is available at https://hub.docker.com/r/pwittchen/voucher-storage-service/
