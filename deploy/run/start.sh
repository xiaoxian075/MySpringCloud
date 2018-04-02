#!/bin/bash
DIR=/home/chenjx/yzj
docker build -t eureka $DIR/eureka
docker build -t gateway $DIR/gateway
#docker build -t admin $DIR/admin
docker build -t app $DIR/app
docker build -t coredb $DIR/coredb
docker build -t async $DIR/async

docker-compose up -d
