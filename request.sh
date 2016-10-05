#!/usr/bin/env bash

RESULT=$(curl -X POST http://localhost:8181/auth/realms/basic-auth/protocol/openid-connect/token -d grant_type=password -d username=admin -d password=password -d client_id=basic-auth-service)
TOKEN=$(echo -e "$RESULT" | awk -F"," '{print $1}' | awk -F":" '{print $2}' | sed s/\"//g | tr -d ' ')

echo ">>> TOKEN Received"
echo $TOKEN

echo ">>> Call Hello Service"
# curl http://localhost:8080/rest/hello -H "Authorization:Bearer $TOKEN"
http GET http://localhost:8080/basicauth/service/echo?value=hello "Authorization: Bearer $TOKEN"