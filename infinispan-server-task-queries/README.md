# Infinispan Server Task with Queries

1. Compile the project

```
mvn clean install -DskipTests
```

2. Download and unzip the [Infinispan Server](https://downloads.jboss.org/infinispan/15.0.3.Final/infinispan-server-15.0.3.Final.zip)

```
cd infinispan-server-15.0.3.Final
```

3. Create admin user

```
./bin/cli.sh user create --groups=admin --password=pass admin
```

4. Copy the jars to the server libs

```
cp /home/fax/code/infinispan-demo/infinispan-server-task-queries/server-task/target/server-task-1.0-SNAPSHOT.jar server/lib/
```

```
cp /home/fax/code/infinispan-demo/infinispan-server-task-queries/model-task/target/model-task-1.0-SNAPSHOT.jar server/lib/
```

5. Run the server

```
./bin/server.sh
```

you should see the log:

```
2024-05-11 21:34:03,964 INFO  [o.i.SERVER] ISPN080027: Loaded extension 'fax.play.task.PlayTask'
```

6. Execute the test

On project directory run:

```
mvn clean verify -pl client-task
```

