#!/bin/bash

mkdir build &&
find src -name \*.scala | xargs scalac -deprecation -classpath 'lib/fj.jar:build' -d build
