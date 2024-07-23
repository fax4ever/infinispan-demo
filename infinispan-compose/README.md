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

```bash
[spring-client] mvn clean install
```

4. Run tests for SpringBoot app

```bash
[spring-client] mvn springboot:run
```

5. Invoke the rest service

```
http://localhost:8080/ciao
```

5. Open the Infinispan console you should see the `play` cache

```bash
http://localhost:11222/console/
```

