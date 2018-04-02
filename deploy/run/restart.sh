#!/bin/bash
DIR=/home/chenjx/yzj


#docker-compose stop
docker stop yzj_gateway_1 yzj_app_1 yzj_coredb_1 yzj_eureka_1 yzj_async_1
docker rm yzj_gateway_1 yzj_app_1 yzj_coredb_1 yzj_eureka_1 yzj_async_1
docker rmi gateway app coredb eureka async

docker build -t eureka $DIR/eureka
docker build -t gateway $DIR/gateway
#docker build -t admin $DIR/admin
docker build -t app $DIR/app
docker build -t coredb $DIR/coredb
docker build -t async $DIR/async

docker-compose up -d
