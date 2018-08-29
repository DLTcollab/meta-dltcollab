#!/bin/bash

set -ue

LC_ALL=C
export LC_ALL

if [ -e "/etc/mender/artifact_info" ]; then
artifact_info=$(cat /etc/mender/artifact_info)
else
artifact_info="artifact_name=no-artifact-info"
fi
artifact_info=${artifact_info#*artifact_name=}

if [ -e "/data/mender/device_type" ]; then
device_type=$(cat /data/mender/device_type)
else
device_type="device_type=no-device-type"
fi
device_type=${device_type#*device_type=}

uuid=$(tangleid UUID)
backend=$(tangleid backend)

msg='{'\
' "artifact_info" : "'$artifact_info'" ,'\
' "device_type" : "'$device_type'" ,'\
' "UUID" : "'$uuid'" ,'\
' "backend" : "'$backend'" '\
'}'

echo "claim msg : $msg" >& 2

res=$(tangleid new_claim "$msg")

echo "$res" >& 2