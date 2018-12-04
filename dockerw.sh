#!/usr/bin/env bash

function showHelp() {
    echo "
    ./dockerw.sh (docker wrapper)

                -h      shows help
                -b      builds container
                -r      runs container
                -u      uploads container
                -d      downloads container
    "
}

function buildDocker() {
    sudo docker build -t pwittchen/voucher-storage-service .
}

function runDocker() {
   sudo docker run -p 127.0.0.1:7000:7000 -t pwittchen/voucher-storage-service
}

function uploadDocker() {
    sudo docker push pwittchen/voucher-storage-service
}

function downloadDocker() {
    sudo doker pull pwittchen/voucher-storage-service
}

OPTIND=1 # Reset in case getopts has been used previously in the shell.

while getopts "hbrud" opt; do
    case "$opt" in
    h)
        showHelp
        exit 0
        ;;
    b)  buildDocker
        ;;
    r)  runDocker
        ;;
    u)  uploadDocker
        ;;
    d)  downloadDocker
        ;;
        esac
done

shift $((OPTIND-1))

[ "$1" = "--" ] && shift
