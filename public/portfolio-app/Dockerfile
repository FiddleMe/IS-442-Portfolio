FROM node:18-alpine

WORKDIR /usr/src/reactApp

COPY public/ /usr/src/reactApp/public

COPY src/ /usr/src/reactApp/src

COPY package.json /usr/src/reactApp/

RUN npm install --force

CMD ["npm", "start"]