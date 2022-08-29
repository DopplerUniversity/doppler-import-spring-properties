# Doppler Import Spring Properties Files

This is a rudimentary Java CLI script to test importing Spring Property Files before baking into our server application.

It works by exporting the entire contents of a single Java properties file to JSON which can then be imported by the Doppler CLI or dashboard.

## Prerequisites

- Java 18
- Gradle or Maven
- Make (optional)
- [Doppler CLI](https://docs.doppler.com/docs/install-cli)

## Setup

Clone this repository to your local machine.

Authenticate the Doppler CLI if you haven't already:

```sh
doppler login
```

Create a Doppler project for your Spring application via the Dashboard, or use the Doppler CLI:

```sh
doppler projects create spring-boot-app
```

Then configure the Doppler CLI that Project and Config:

```sh
doppler setup
```

## Usage

Because the Doppler server does not yet support importing properties files, we'll convert from a properties file to JSON. 

Simply execute the project and supply the path to the properties file, saving the output to a file in one of three ways: 

1. Makefile (uses Maven):

```sh
make export-properties FILE="./sample-application.properties" > doppler-secrets.json
```

2. Maven:

```sh
./mvnw compile exec:java --quiet -Dexec.args="./sample-application.properties" > doppler-secrets.json
```

3. Gradle: 

```sh
./gradlew run --quiet --args="./sample-application.properties" > doppler-secrets.json
```

Then upload the secrets using the Doppler CLI and delete the `doppler-secrets.json` file:

```sh
doppler secrets upload doppler-secrets.json
rm doppler-secrets.json
```

If using a bash shell, only a single command is needed thanks to process substitution:

```sh
 doppler secrets upload <(make export-properties FILE="./sample-application.properties")
```
