#!/bin/bash
#
#   command line for tangleid
#
#   2018/08/21 csielee

function byteToTryte {

    if [ "$1" == "" ]; then
        echo "you need to give some bytes" >&2
        return 1
    fi

    bytes=$1

    TRYTE_VALUES="9ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    trytes=""

    for ((i=0;i<${#bytes};i=i+2))
    do
        ((byte=16#${bytes:$i:2}))
        if [ "$byte" == "" ]; then
            echo "error byte code" >&2
            return 1
        fi
        first=$(($byte%27))
        second=$((($byte-$first)/27))
        trytes=$trytes${TRYTE_VALUES:$first:1}${TRYTE_VALUES:$second:1}
    done

    echo $trytes

    return 0
}

function getMac {
    set -ue

    SCN=/sys/class/net
    min=65535
    arphrd_ether=1
    ifdev=

    # find iface with lowest ifindex, skip non ARPHRD_ETHER types (lo, sit ...)
    for dev in $SCN/*; do
        if [ ! -f "$dev/type" ]; then
            continue
        fi

    iftype=$(cat $dev/type)
    if [ $iftype -ne $arphrd_ether ]; then
        continue
    fi

    idx=$(cat $dev/ifindex)
    if [ $idx -lt $min ]; then
        min=$idx
        ifdev=$dev
    fi
    done

    if [ -z "$ifdev" ]; then
        echo "no suitable interfaces found" >&2
        return 1
    else
        # echo "using interface $ifdev" >&2
        # get MAC address without ':'
        mac=$(cat $ifdev/address | tr -d :)
    fi

    echo $mac
}

function UUID {
    set -ue

    # get UUID from etc
    if [ -e /etc/tangleid/UUID ]; then
        echo $(cat /etc/tangleid/UUID)
        return 0
    fi

    mac=$(getMac)
    trytes=$(byteToTryte ${mac})
    if [ -d /etc/tangleid ]; then
        echo $trytes > /etc/tangleid/UUID
    fi
    
    echo $trytes
}

function POST {
    # get backend
    if [ -e /etc/tangleid/backend ]; then
        TangleID_backend=$(cat /etc/tangleid/backend)
    else
        # default value
        # echo "use default backend" >& 2
        TangleID_backend="http://node2.puyuma.org:8000"
    fi

    RES=$(curl -s $TangleID_backend \
        -X POST \
        -H 'Content-Type: application/json' \
        -d ''"$*"'')

    if [ $? -ne 0 ]; then
        echo 'Assert failed: '$* >&2
        echo 'Response : '$RES >&2
        return 1
    else
        echo $RES
        return 0
    fi
}

function help {
    echo ""
    echo "Usage : $(basename $0) [command] [args]"
    echo ""
    echo "Commands : "
    echo ""
    echo "UUID : get UUID"
    echo "new_claim : send claim"
    echo "  Args:"
    echo "    message : claim message"
}

case $1 in
    "UUID")
        output=$(UUID)
        ;;
    "ToTryte")
        output=$(byteToTryte $2)
        ;;
    "new_claim")
        uuid=$(UUID)
        body='{"extension":"tangleid","command":"new_claim","uuid":"'$uuid'",'\
'"part_a":"'$uuid'","part_b":"'$uuid'",'\
'"exp_date":"20190101",'\
'"claim_pic":"",'\
'"msg":'"$2"'}'
        output=$(POST $body)
        ;;
    "help")
        help
        exit 0
        ;;
    *)
        help
        exit 0
        ;;
esac

tmp=$?
if [ "$tmp" != "0" ]; then
    exit $tmp
fi

echo $output