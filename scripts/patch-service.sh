#!/usr/bin/env bash

service=$1

oc patch service/$service -p '{"spec": {"type": "NodePort"}}' --type=merge