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

5. Allow server side deserialization for the model

Edit the configuration

```
subl ./server/conf/infinispan.xml
```

Put the attribute:

``` xml
<serialization>
  <allow-list>
    <class>fax.play.model.Play</class>
  </allow-list>
</serialization>
```

inside the `cache-container` element.
For instance:

``` xml
<cache-container name="default" statistics="true">
  <transport cluster="${infinispan.cluster.name:cluster}" stack="${infinispan.cluster.stack:tcp}" node-name="${infinispan.node.name:}"/>
  <security>
     <authorization/>
  </security>
  <serialization>
     <allow-list>
       <class>fax.play.model.Play</class>
     </allow-list>
  </serialization>
</cache-container>
```

6. Run the server

[OPTIONALLY] If you need to run it with the remote debugging enabled, add this line to the `server.sh` at the beginning of the file
> JAVA_OPTS="$JAVA_OPTS -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=*:8000"

```
./bin/server.sh
```

you should see the log:

```
2024-05-11 21:34:03,964 INFO  [o.i.SERVER] ISPN080027: Loaded extension 'fax.play.task.PlayTask'
```

7. Execute the test

On project directory run:

```
mvn clean verify -pl client-task
```

## Use index embedded with a protostream encoded cache

Working on the Infinispan server compiled from [the PR](https://github.com/infinispan/infinispan/pull/12377), 
it is possible to switch to the following cache configuration: 

```xml
<?xml version="1.0"?>
<distributed-cache name="play" owners="2" mode="SYNC" statistics="true">
    <encoding media-type="application/x-protostream"/>
    <indexing enabled="true" storage="local-heap" use-java-embedded-entities="true">
        <indexed-entities>
            <indexed-entity>fax.play.model.Play</indexed-entity>
        </indexed-entities>
    </indexing>
</distributed-cache>
```

I tested it using this demo, only changing the `[play.xml](client-task%2Fsrc%2Fmain%2Fresources%2Fplay.xml)`,
using an Infinispan server that contains the patch `ISPN-16069 Allow the indexes to start with embedded setup`.
