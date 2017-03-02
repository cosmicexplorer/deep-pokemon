.PHONY: all run clean-bin clean test

CONFIG_FILE := config.json

BUILD_DIR := build
BAD_ONEJAR_NAME := deep-pokemon.jar--one-jar.jar
JAR_NAME := deep-pokemon.jar

BAD_JAR := $(BUILD_DIR)/$(BAD_ONEJAR_NAME)
OUT_JAR := $(BUILD_DIR)/$(JAR_NAME)

PROJECT_DIR := project
BUILD_SBT := build.sbt
PROJ_FILES := $(BUILD_SBT) $(wildcard $(PROJECT_DIR)/*)

SBT_SRC_DIR := src
SRC_DIR := $(SBT_SRC_DIR)/main
SRC_FILES := $(shell find $(SRC_DIR) -type f -name "*.scala")

$(BAD_JAR): $(PROJ_FILES) $(SRC_FILES)
	sbt one-jar -D"build.jar.name=$(JAR_NAME)" \
		-D"build.location=$(BUILD_DIR)"

$(OUT_JAR): $(BAD_JAR)
	mv $(BAD_JAR) $(OUT_JAR)

all: $(OUT_JAR)

run: all
	java -jar $(OUT_JAR) $(CONFIG_FILE)

test: all
	sbt test

clean-bin:
	rm -f $(OUT_JAR) $(JAR_COMPLETE_FILE)

clean: clean-bin
	sbt clean clean-files
