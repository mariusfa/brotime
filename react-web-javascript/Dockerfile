# Slim for smaller image size
FROM node:10-slim

# Just for developer or orchestrator to know which port the app is running on
EXPOSE 3000

# Creating app directory and setting it up for node user
RUN mkdir /opt/app && chown node:node /opt/app
WORKDIR /opt/app

# Setting node user to install and run application. 
# Node user is less privileged than root user in case os security issues.
USER node
COPY package.json package-lock.json* ./
RUN npm install --no-optional && npm cache clean --force

# Health check for application.
# In case app is broken, but docker sees it as running
HEALTHCHECK --interval=30s CMD node healthcheck.js

COPY . .

CMD ["npm", "start"]