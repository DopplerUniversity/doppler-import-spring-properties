# Doppler Import Spring Properties Files

This is a temporary Java CLI script to test our import strategy for Spring Property Files before baking into our server application.

## Prerequisites

- Java 18
- Gradle
- [Doppler CLI](https://docs.doppler.com/docs/install-cli)

## Setup

Create a Doppler project for your Spring application (e.g. `spring-boot-app` ), then configure the Doppler CLI that Project and Config:

```sh
doppler setup
```

## Usage

Because the Doppler server does not yet support importing properties files, we'll convert from a properties file to JSON, then import the JSON using the CLI or optionally drag and drop into the **Import Secrets** text field in the dashboard.

To convert to JSON, supply an absolute path to the properties file you wish to convert:

```sh
./gradlew :doppler:run --args="$(pwd)/sample-application.properties" --quiet > doppler-secrets.json
```

Then upload (and delete) the `doppler-secrets.json` file:

```sh
doppler secrets upload doppler-secrets.json

rm doppler-secrets.json
```
