#!/usr/bin/env bash

set -o errexit
set -o pipefail
set -o nounset

readonly BASE_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

(docker-compose --file ${BASE_DIR}/docker-compose.yml down)


GET_LATEST="1"
UP_ARGS="-d"

for i in "$@"
do
case $i in
    --no-pull)
    GET_LATEST="0"
    ;;
    --console)
    UP_ARGS=""
    ;;
esac
done

if [ "${GET_LATEST}" == "1" ]; then
    (docker-compose --file ${BASE_DIR}/docker-compose.yml pull)
fi


# provide init scripts to the postgres instances without relying on files on the docker host
# delete the volumes if they exists
docker volume rm pg-shopping-cart-init-scripts || true
echo -e "removed volume"
# create fresh volumes
docker volume create pg-shopping-cart-init-scripts
echo -e "cretaed volume"
# create arbitrary containers to allow us to copy files into the volume (no need to start the container)
docker container create --name file-transfer-pg-shopping-cart -v pg-shopping-cart-init-scripts:/file-transfer-mount hello-world
echo -e "cretaed container"
# copy the files over
docker cp ${BASE_DIR}/postgresql-init-scripts/init.sql file-transfer-pg-shopping-cart:/file-transfer-mount/init.sql
echo -e "cpoied file"
# get rid of the containers
docker rm file-transfer-pg-shopping-cart

(docker-compose --file ${BASE_DIR}/docker-compose.yml up ${UP_ARGS})
sleep 3

echo -e "Everything's started!"