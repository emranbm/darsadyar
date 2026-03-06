#!/usr/bin/env bash

node ./.ci/build.js
https_proxy="" HTTPS_PROXY="" npx r1ec deploy darsadyar -f app.js
