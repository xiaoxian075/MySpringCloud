#!/bin/bash
docker stop yzj_gateway_1 yzj_app_1 yzj_coredb_1 yzj_eureka_1 yzj_async_1
docker rm yzj_gateway_1 yzj_app_1 yzj_coredb_1 yzj_eureka_1 yzj_async_1
docker rmi gateway app coredb eureka async
