#!/bin/bash
echo "Hit ctrl+c to stop"
while :
do
	http $(minishift service swarm-rest --url=true)/say/echo?value=bye
	echo ""
    sleep 10
done