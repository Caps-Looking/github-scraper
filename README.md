# Github Scraper

API that returns the file count, the total number of lines, and the total number of bytes grouped by file extension, of a given public Github repository.

## Running

To run locally, you need to have jdk 11, docker and docker compose installed.

```bash
./mvnw clean install package

docker-compose build
docker-compose up
```

To run only automated tests:
```bash
./mvnw clean verify
```

To access the docs locally, go to [http://localhost:8080/](http://localhost:8080/).

## Heroku instance

Go to [philipe-github-scraper](https://philipe-github-scraper.herokuapp.com/) to open swagger documentation.

## How does it work

To allow multiple requests (with the same repo as payload) without performance loss, the API has a 10 minutes cache, so changing repo files will take a while to reflect.

For changing this behavior, simple disable the cache by changing on `aplication.properties`:
```properties
spring.cache.type=none
```

Or changing the cache timeout in milliseconds:
```properties
spring.cache.redis.time-to-live=600000
```