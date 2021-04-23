#!/bin/bash

rm -rf ./public/env-config.js
touch ./public/env-config.js

echo "window.API_URL = ${API_URL}" >> ./public/env-config.js
