# Infinispan Docker Compose Demo

## Try without containers

1. Create Admin user (if you don't already have it)

```bash
./bin/cli.sh user create --groups=admin --password=yyyyyy xxxxxx
```

2. Run the server *infinispan-server-14.0.29.Final*

```bash
./bin/server.sh
```

3. Run tests for SpringBoot app

from `spring-client`
```bash
mvn clean package spring-boot:repackage
```

4. Run tests for SpringBoot app

from `spring-client`
```bash
mvn springboot:run
```

or 

from `spring-client`
```bash
java -jar target/spring-client-1.0-SNAPSHOT.jar
```

5. Invoke the rest service

```
http://localhost:8080/ciao
```

6. Open the Infinispan console you should see the `play` cache

```bash
http://localhost:11222/console/
```

## Use Docker compose

1. To build the client image

```bash
docker compose build
```

2. To run the client image

```bash
docker compose up
```

3. To push the image to the Docker hub

```bash
docker compose push
```

