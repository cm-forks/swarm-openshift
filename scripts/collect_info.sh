#!/bin/bash

#export PODNAMES=""
#
#for var in "$@"
#do
#  PODNAMES="$PODNAMES $(oc get pods | grep $var | cut -f 1 -d ' ')"
#done
#
#echo $PODNAMES
#
#export POD=$(echo $PODNAMES | cut -f 1 -d ' ')
#echo "viewing logs for pod: $POD"
# oc logs -f $POD

# PODS=$(oc get pods | cut -f 1 -d ' ')
# echo $PODS
# for i in "${PODS[@]}"
# do
#    oc describe pod/$i
# done

for var in "$@"
do
  oc get pods | grep $var
done