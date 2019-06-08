#!/bin/bash
TIME=$(date +%Y%d%m)    
VERSION=$(grep version pom.xml | grep SNAP | cut -c11-15)
docker build -t testingupmforge/tictactoe:latest ./target/.
docker tag testingupmforge/tictactoe:latest testingupmforge/tictactoe:$VERSION.nightly.$TIME
docker login -u="testingupmforge" -p="carl*sys3rgi*123"
docker push testingupmforge/tictactoe:$VERSION.nightly.$TIME
