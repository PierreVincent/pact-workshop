# Workbook

## Thinking about API design

* Design the HTTP API call made by the `game-service` to the `leaderboard-service` to record the outcome of a played game.
	- Request specifies: the game played, the user playing, the outcome of the game (won or lost)
	- Responds contains: number of games played, number of games won, win/loss ratio

## API implementation

Implement the API designed previously in both services.

### leaderboard-service

* In `ScoreController.java`:
	- Specify the API endpoint (`@PostMapping`)
	- Specify the Request Body (`RecordScoreRequestBody`)
	- Specify the Response Body (`RecordScoreResponseBody`)

### game-service

* In `LeaderboardClient.java`:
	- Specify API endpoint to call (`recordScoreUrl`)
	- Specify Request Body (`RecordScoreRequestBody`)
	- Specify Response Body (`RecordScoreResponseBody`)

## API testing the hard way (without tests)

* Start both services
* Play a game by calling the `/play` endpoint on the `game-service`

*Question:* What are the limitations of this approach?

## API testing with mocks

Implement API unit test in `LeaderboardClientTest.java`:
* Specify the expected request/response from a mock provider
* Use the LeaderboardClient to trigger an HTTP call to the mock

*Question:* What are the limitations of this approach?

## API testing with contracts

Implement Pact unit test in `LeaderboardClientPactTest.java`:
* Complete the Pact definition representing the contract (`RequestResponsePact`)
* Update the expected response (`expectedResponse`)
* Update the expected request (`client.recordScore`)

### Generating the contract (consumer)

After running the tests of `LeaderboardClientPactTest`, a pact file should be generated under `build/pacts/game-service-leaderboard-service.json`.

*Questions:*
* Does the contract match the expected API interaction?
* Does it contain things you didn't specify explicitly?

### Verifying the contract (provider)

* Copy the previously generated Pact file from `game-service/build/pacts/game-service-leaderboard-service.json` to the `leaderboard-service/pacts/` directory.
* Run the pact verification test from `leaderboard-service` directory:

```
./gradlew pactVerify
```

*Questions:*
* Can you tell what is happening during this verification?

## Introducing API breaking changes

Implement a _breaking change_ to the `leaderboard-service` endpoint used to record scores.

### Effect on Mock tests

Run the `LeaderboardClientTest` in the `game-service`.

*Question:* Why is it still passing?

### Effect on Contract tests

Run the `LeaderboardCLientPactTest` in the `game-service`.

*Question:* What happened to the contract?

Run the pact verification from `leaderboard-service`.

*Question:* What can you tell from the output?

## Adding more interactions to the contract

### Contracting unhappy path

Repeat the previous steps to define a test to record an interaction where the `game-service` makes a call to the `leaderboard-service` without specifying a player.

### Bonus: Contracting another endpoint

After playing a game, the `game-service` now returns the player who has the most wins. The `leaderboard-service` already provides this endpoint, under `/games/{game}/leader`.

* Update the `game-service` to call this endpoint after recording the score
* Update the `game-service` Pact test to include this interaction
* Generate the updated contract and check that the new interaction is recorded in it
* Verify the updated contract with the provider

## Contract sharing and verifications with Pactflow

### Getting your Pactflow credentials

If you haven't yet, navigate to [pactflow.io](https://pactflow.io/) to create a free account.

In the settings page (cog icon in top right corner), copy your _Read/write token (CI)_, which will be used to configure your gradle to interact with Pactflow.

### Publishing the contract (consumer side)

Update `gradle.build` in the `game-service`, by uncommenting the `pact { ... }` code block and specify the values for your Pactflow account:

```
pact {
    publish {
        providerVersion = { gameServiceVersion }
        pactBrokerUrl = 'https://YOUR_ACCOUNT.pactflow.io'
        pactBrokerToken = 'YOUR_TOKEN'
    }
}
```

Run the following command to publish the generated contract to Pactflow:

```
./gradlew pactPublish
```

Navigate to the Pactflow overview and confirm that a Pact has been created between `game-service` and `leaderboard-service`

### Retrieving the contracts for verification (provider side)

Update `gradle.build` in the `leaderboard-service`:
* Uncomment the `broker { ... }` block and specify the values for your Pactflow account
* Comment the existing `hasPactWith('gameService') { ... }` block
* Uncomment the `fromPactBroker { ... }` block

Finally, run the contract verification in the `leaderboard-service`:

```
./gradlew pactVerify
```

### Publishing the verification status

By default, `pactVerify` does not push the verification results to the Pact Broker - this is because the tests can be run locally without publishing the results (potentially misleading the consumer that their service cannot be deployed).

In order to publish the results, the option must be passed explicitly (typically something you would have in your CI pipeline):

```
./gradlew pactVerify -Ppact.verifier.publishResults=true 
```

The contract should now be marked as "Successfully verified" or "Verification failed" in Pactflow.

### Closing the loop by checking the verification status

Use the Pactflow API to check the verification result ("can-i-deploy").

*Question:* How is this useful and at what stage should this be checked?
