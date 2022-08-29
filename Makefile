help:
	@./mvnw compile exec:java --quiet

export-properties:
	@./mvnw compile exec:java --quiet -Dexec.args="$(FILE)"