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

### Infinispan Cluster

``` shell
helm install -n infinispan-15-tracing -f infinispan.yaml infinispan openshift/infinispan-infinispan --version 0.3.2
```

### Jaeger Service

``` shell
kubectl apply -n infinispan-15-tracing -f jaeger.yaml
```

### Infinispan Quarkus Client

``` shell
kubectl apply -n infinispan-15-tracing -f kubernetes.yaml
```

``` shell
kubectl wait pod --all --for=condition=Ready --namespace=${ns}
```

## Docker: Build & Push

## Quarkus client: Develop and Package

``` shell
mvn -f ./infinispan-client/pom.xml clean compile quarkus:dev
```

``` shell
mvn -f ./infinispan-client/pom.xml clean package
```

## Docker: Build and Publish

``` shell
docker-compose build
```

``` shell
docker-compose push
```