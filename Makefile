.PHONY: all run clean-bin clean test

WEBSOCK_ADDRESS := $(shell jq -r '.ws_address' ./config.json)

BUILD_DIR := build
JAR_NAME := DeepPokemon.jar

OUT_JAR := $(BUILD_DIR)/$(JAR_NAME)

PROJECT_DIR := project
BUILD_SBT := build.sbt
PROJ_FILES := $(BUILD_SBT) $(wildcard $(PROJECT_DIR)/*)

SBT_SRC_DIR := src
SRC_DIR := $(SBT_SRC_DIR)/main
SRC_FILES := $(shell find $(SRC_DIR) -type f -name "*.scala")

$(OUT_JAR): $(PROJ_FILES) $(SRC_FILES)
	sbt assembly -Dbuild.location="$(OUT_JAR)"

all: $(OUT_JAR)

run: all
	scala $(OUT_JAR) $(WEBSOCK_ADDRESS)

test: all
	sbt test

clean-bin:
	rm -f $(OUT_JAR)

clean: clean-bin
	sbt clean clean-files
