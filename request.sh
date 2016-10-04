#!/usr/bin/env bash

RESULT=$(curl -X POST http://localhost:8080/auth/realms/master/protocol/openid-connect/token -d grant_type=password -d username=admin -d password=admin -d grant_type=password -d client_id=securehello)
TOKEN=$(echo -e "$RESULT" | awk -F"," '{print $1}' | awk -F":" '{print $2}' | sed s/\"//g | tr -d ' ')

echo ">>> TOKEN Received"
echo $TOKEN

echo ">>> Call Hello Service"
# curl http://localhost:8080/rest/hello -H "Authorization:Bearer $TOKEN"
http GET http://localhost:8080/rest/hello "Authorization: Bearer $TOKEN"