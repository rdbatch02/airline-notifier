#!/bin/bash
parent_path=$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )
cd "$parent_path"

rm -rf ../build
mkdir -p ../build
../../gradlew -p ../../ shadowJar
cp ../../build/libs/*.jar ../build
mv ../build/*.jar ../build/atis.jar