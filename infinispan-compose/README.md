# Infinispan Docker Compose Demo

## Use Docker compose

1. Compile the client project

```bash
mvn -f ./spring-client/pom.xml clean package spring-boot:repackage
```

1. Build the client image

```bash
docker compose build
```

2. Run the client and Infinispan

```bash
docker compose up
```

3. Optional. Push the image to the Docker hub

```bash
docker compose push
```

4. Invoke the rest service

```
http://localhost:8080/ciao
```

5. Open the Infinispan console you should see the `play` cache

```bash
http://localhost:11222/console/
```

## Try without containers

You need to the change the Infinispan address in the [application.properties](spring-client%2Fsrc%2Fmain%2Fresources%2Fapplication.properties)

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



