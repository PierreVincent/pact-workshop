#!/usr/bin/env bash

if [[ "$1" == "" ]]; then
    echo "Missing token";
    exit 1;
fi

curl -v -XPUT -H "Content-Type: application/json" \
    -H "Authorization: Bearer $1" \
    -d@build/pacts/game-service-leaderboard-service.json \
    https://pvincent.pact.dius.com.au/pacts/provider/leaderboard-service/consumer/game-service/version/1.0.0