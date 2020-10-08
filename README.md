# PACT Workshop: Consumer-driven contracts in practice

This repository contains very simple microservices used to illustrate consumer-contract testing using the Pact framework.

## The example

The example services represent different parts of an online game. The `game-service` is used directly by players to play games. Whenever a player wins or loses a game, the result of the game is reported to the `leaderboard-service` which keeps track of the win rate for every player and game.

## The goal

This exercise focuses on one API interaction: the `game-service` (API consumer) submitting a game result to the `leaderboard-service` (API provider).

The goal of this exercise is to:

- Agree on the specification of this API interaction
- Implement the API endpoint for the provider
- Implement the API client for the consumer
- Write PACT consumer unit tests to define the contract for this interaction
- Share this contract with the provider
- Verify that the provider implementation fulfills the contract

## Running the example

### Game service

The `game-service` runs on port `8080` by default. This can be configured under `application.properties`.

Run tests:

```
# in game-service/ directory
./gradlew test
```

Pacts are being generated under `build/pacts`.

Start service:

```
./gradlew run
```

### Leaderboard service

The `leaderboard-service` runs on port `8081` by default. This can be configured under `application.properties`. Note that if you are changing this port, you will need to update `leaderboardService.baseUrl` in the `game-service` configuration accordingly.

Run pact test verification:

```
# in leaderboard-service/ directory
./gradlew pactVerify
```

Start service:

```
./gradlew run
```

### Sending requests to the application

Once you have both application started, you should be able to send requests to them using `curl`.

Example:

```
curl -X POST -H "Content-Type: application/json" --data '{"username": "pierre", game":"headsortails", "choice": "tails"}' http://localhost:8080/play

# Output: {"won":false,"message":"Heads: you lost!","totalPlays":4,"totalWins":3,"winRate":75}
```