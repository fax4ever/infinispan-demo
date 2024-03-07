# Tracing with Infinispan 15

## Helm Charts

```
helm repo add openshift https://charts.openshift.io/
```

## Kubernetes: Deploy

``` shell
kubectl config set-context --current --namespace=infinspan-15-tracing
```

``` shell
kubectl delete namespace infinspan-15-tracing
```

``` shell
kubectl create namespace infinspan-15-tracing
```

### Infinispan Cluster

``` shell
helm install -f infinispan.yaml -n infinspan-15-tracing infinispan openshift/infinispan-infinispan --version 0.3.2
```

### Jaeger Service

``` shell
kubectl apply -f jaeger.yaml
```

### Infinispan Quarkus Client

``` shell
kubectl apply -f kubernetes.yaml
```

## Docker: Build and Publish

``` shell
docker-compose build
```

``` shell
docker-compose push
```

## Quarkus client: Develop and Package

``` shell
mvn -f ./infinispan-client/pom.xml clean compile quarkus:dev
```

``` shell
mvn -f ./infinispan-client/pom.xml clean package
```