# Tracing with Infinispan 15

## Helm Charts

```
helm repo add openshift https://charts.openshift.io/
```

## Kubernetes: Deploy

``` shell
kubectl config set-context --current --namespace=infinispan-15-tracing
```

``` shell
kubectl delete namespace infinispan-15-tracing
```

``` shell
kubectl create namespace infinispan-15-tracing
```

### Jaeger Service

``` shell
kubectl apply -n infinispan-15-tracing -f jaeger.yaml
```

### Infinispan Cluster

``` shell
helm install -n infinispan-15-tracing -f infinispan.yaml infinispan openshift/infinispan-infinispan --version 0.3.2
```

``` shell
kubectl get pods -w
```

### Infinispan Quarkus Client

``` shell
kubectl apply -n infinispan-15-tracing -f kubernetes.yaml
```

``` shell
kubectl wait pod --all --for=condition=Ready --namespace=${ns}
```

## Docker: Build & Push

### Quarkus client: Develop and Package

``` shell
mvn -f ./infinispan-client/pom.xml clean compile quarkus:dev
```

``` shell
mvn -f ./infinispan-client/pom.xml clean package
```

### Docker: Build and Publish

``` shell
docker-compose build
```

``` shell
docker-compose push
```

### Extra

Run Jaeger container

``` shell
docker run -i --rm --name jaeger \
  -e COLLECTOR_ZIPKIN_HOST_PORT=:9411 \
  -e COLLECTOR_OTLP_ENABLED=true \
  -p 5775:5775/udp \
  -p 6831:6831/udp \
  -p 6832:6832/udp \
  -p 5778:5778 \
  -p 16686:16686 \
  -p 14250:14250 \
  -p 14268:14268 \
  -p 14269:14269 \
  -p 9411:9411 \
  -p 4317:4317 \
  -p 4318:4318 \
  jaegertracing/all-in-one:1.53
```

## Demo

1. Add an image. Spans: client, container.

```
http POST http://172.18.255.202:8080/image <<<'{"user":"fabio", "file":"cat1.jpg", "caption":"a cat in the middle of the sky"}'
```

2. Add persistence tracing

```
http POST http://172.18.255.202:8080/tracing/persistence
```

3. Add a second image. Spans: client, container, persistence.

```
http POST http://172.18.255.202:8080/image <<<'{"user":"fabio", "file":"cat2.jpg", "caption":"a cat in the middle of the street"}'
```

4. Increase the cluster

``` shell
helm upgrade --reuse-values --set deploy.replicas=2 infinispan openshift/infinispan-infinispan --version 0.3.2
```

5. Enable cluster tracing 

```
http POST http://172.18.255.202:8080/tracing/cluster
```

6. Add a third image. Spans: client, container, cluster.

```
http POST http://172.18.255.202:8080/image <<<'{"user":"fabio", "file":"cat3.jpg", "caption":"a cat in the middle of the blue"}'
```

7. Disable tracing 

```
http DELETE http://172.18.255.202:8080/tracing
```

7. Add a fourth image. Spans: none

```
http POST http://172.18.255.202:8080/image <<<'{"user":"fabio", "file":"cat4.jpg", "caption":"a cat in the middle of the grass"}'
```