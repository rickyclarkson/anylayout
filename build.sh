#!/bin/bash

mkdir build
find src -name \*.java | xargs javac -classpath 'lib/fj.jar:build' -d build