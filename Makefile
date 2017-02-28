.PHONY: all run clean test

WEBSOCK_ADDRESS := $(shell jq -r '.ws_address' ./config.json)

BUILD_DIR := build
JAR_NAME := DeepPokemon.jar

OUT_JAR := $(BUILD_DIR)/$(JAR_NAME)

$(OUT_JAR):
	sbt assembly -Dbuild.location="$(OUT_JAR)"

all: $(OUT_JAR)

run: all
	@scala $(OUT_JAR) $(WEBSOCK_ADDRESS)

test:
	sbt test

clean:
	sbt clean clean-files
	rm $(OUT_JAR)
