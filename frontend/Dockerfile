FROM node:alpine

WORKDIR /app

COPY package.json package-lock.json* ./

RUN npm install

RUN npm rebuild esbuild --build-from-source

COPY . .

EXPOSE 5173

CMD ["npm", "run", "dev"]
