#!/bin/bash
#

baseDir=${ALLSPARK_HOME}
jarName="allspark-admin.jar"
#workDir="${baseDir}/lib"

JAVA="java"

prefixOps="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8092"

CMD="${JAVA} ${prefixOps} -jar ./lib/${jarName} >> /dev/null 2>&1 &"

start() {
    cd "${baseDir}" && eval "$CMD"
}

stop() {
    pgrep -P "(?<=-jar)\s+${jarName}" && kill $(pgrep -P "(?<=-jar)\s+${jarName}" | awk '{print $2}')
}

case $1 in
    start)
        start
        ;;
    stop)
        stop
        ;;
    restart)
        stop
        start
        ;;
esac