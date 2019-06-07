#!/bin/bash
TIME=$(date +%Y%d%m)    
VERSION=$(grep version pom.xml | grep SNAP | cut -c11-15)
docker build -t testingupmforge/tictactoe_$VERSION.nightly.$TIME:latest target/.
