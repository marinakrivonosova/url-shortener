# URL shortener

The goal is to implement a simple URL shortener.


## Requirements

- Java 17
- Kotlin 2.0
- Docker
- Docker Compose

### Stack can be run with

```shell
./gradlew bootJar
docker compose up -d --build
```



Web app will be available at http://localhost:8087/

By running compose with `debug` profile Mongo Express will be available at http://localhost:8081 for db exploration.

```shell
docker compose --profile debug up -d --build

```


Grafana will be available at http://localhost:3000/
