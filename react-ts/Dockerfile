FROM node:12.2.0-alpine as build
WORKDIR /app

COPY package.json /app/package.json
RUN npm install --silent
RUN npm config set unsafe-perm true #https://stackoverflow.com/questions/52196518/could-not-get-uid-gid-when-building-node-docker
RUN npm install react-scripts@3.0.1 -g --silent
COPY . /app
RUN npm run build