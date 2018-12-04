#!/usr/bin/env bash

function showHelp() {
    echo "
    ./dockerw.sh (docker wrapper)

                --help      shows help
                --build     builds container
                --run       runs container
                --push      uploads container
                --pull      downloads container
                --remove    removes container
    "
}

function buildDocker() {
    sudo docker build -t pwittchen/voucher-storage-service .
}

function runDocker() {
   sudo docker run -p 127.0.0.1:7000:7000 -t pwittchen/voucher-storage-service
}

function pushDocker() {
    sudo docker push pwittchen/voucher-storage-service
}

function pullDocker() {
    sudo docker pull pwittchen/voucher-storage-service
}

function removeDocker() {
   sudo docker rmi -f $(sudo docker images | grep "pwittchen/voucher-storage-service" | awk '{print $3}')
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
            printf 'WARN: Unknown option (ignored)ignored: %s\n' "$1" >&2
            ;;
        *)
            break
    esac

    shift
done
