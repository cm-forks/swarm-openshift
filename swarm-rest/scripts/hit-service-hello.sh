#!/bin/bash
echo "Hit ctrl+c to stop"
while :
do
	http $(minishift service swarm-rest --url=true)/say/hello
	echo ""
    sleep 5
done