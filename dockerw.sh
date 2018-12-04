#!/usr/bin/env bash

IMAGE_NAME="pwittchen/voucher-storage-service"

function showHelp() {
    echo "
    ./dockerw.sh (docker wrapper)

                --help      shows help
                --build     builds container
                --run       runs container
                --push      pushes container to docker hub
                --pull      pulls container from docker hub
                --remove    removes container
    "
}

function buildDocker() {
    sudo docker build -t $IMAGE_NAME .
}

function runDocker() {
   sudo docker run -p 127.0.0.1:7000:7000 -t $IMAGE_NAME
}

function pushDocker() {
    sudo docker push $IMAGE_NAME
}

function pullDocker() {
    sudo docker pull $IMAGE_NAME
}

function removeDocker() {
   sudo docker rmi -f $(sudo docker images | grep $IMAGE_NAME | awk '{print $3}')
}

while :; do
    case $1 in
        --help)
            showHelp
            exit
            ;;
        --build)
            buildDocker
            ;;
        --run)
            runDocker
            ;;
        --push)
            pushDocker
            ;;
        --pull)
            pullDocker
            ;;
        --remove)
            removeDocker
            ;;
        --)
            shift
            break
            ;;
        -?*)
            printf 'WARN: Unknown option (ignored): %s\n' "$1" >&2
            ;;
        *)
            break
    esac

    shift
done
