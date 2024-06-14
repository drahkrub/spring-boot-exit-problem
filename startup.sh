#!/bin/sh

start() {
    java -jar target/exit-0.0.1-SNAPSHOT.jar &
    APP_PID=$!
    wait $APP_PID
    EXIT_STATUS=$?
    echo "========================================"
    echo "Application exited with status: $EXIT_STATUS"
    if [ $EXIT_STATUS != 42 ]; then
        echo "===="
        echo "WHY?"
        echo "===="
    fi
    echo "========================================"
    start
}

start

