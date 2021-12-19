#!/bin/bash

rm -rf ./build/env-config.js
touch ./build/env-config.js

echo "window.API_URL = ${API_URL}" >> ./public/env-config.js
