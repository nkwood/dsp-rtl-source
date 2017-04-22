#!/bin/bash

# "851162500" "851412500" "851562500"
FREQS=("851287500")

for FREQ in "${FREQS[@]}"
do
  :
  curl -H "Content-Type: application/json" -X POST -d '{"latitude":"10.20","longitude":"10.20","polarization":"0","frequency":"'$FREQ'","channelId":{"wacn":"781833","systemId":"318","rfSubsystemId":"1","siteId":"1"}}' http://localhost:8080/channels/control
  sleep 10
done
