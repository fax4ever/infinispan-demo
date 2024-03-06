# Tracing with Infinispan 15

## Helm Charts

```
helm repo add openshift https://charts.openshift.io/
```

## Kubernetes: Deploy

[./]

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

``` shell
kubectl get pods -w
```

``` shell
IP=$(kubectl get service infinispan -o jsonpath="{.status.loadBalancer.ingress[0].ip}")
PORT=$(kubectl get service infinispan -o jsonpath="{.spec.ports[0].port}")
echo $IP:$PORT
```

Use `$IP:$PORT` in place of `172.18.255.200:11222`
``` shell
http://172.18.255.200:11222
```
* Username: admin
* Password: password

### Jaeger Cluster

``` shell
kubectl apply -f jaeger.yaml
```

``` shell
kubectl get pods -w
```

``` shell
IP=$(kubectl get service jaeger-query -o jsonpath="{.status.loadBalancer.ingress[0].ip}")
PORT=$(kubectl get service jaeger-query -o jsonpath="{.spec.ports[0].port}")
echo $IP:$PORT
```

Use `$IP:$PORT` in place of `172.18.255.201:16686`
``` shell
http://172.18.255.201:16686
```

### Infinispan Quarkus Client

``` shell
kubectl apply -f kubernetes.yaml
```

``` shell
kubectl get all
```

``` shell
kubectl wait pod --all --for=condition=Ready --namespace=${ns}
```

``` shell
kubectl logs services/infinispan-client
```

``` shell
IP=$(kubectl get service infinispan-client -o jsonpath="{.status.loadBalancer.ingress[0].ip}")
PORT=$(kubectl get service infinispan-client -o jsonpath="{.spec.ports[0].port}")
echo $IP:$PORT
```

Use `$IP:$PORT` in place of `172.18.255.202:8080`:
``` web
http://172.18.255.202:8080/image/cacheimage/cache
```

```
kubectl rollout restart deployment infinispan-client
```

## Docker: Build and Publish

[./]

``` shell
docker-compose build
```

``` shell
docker-compose push
```

``` shell
docker run -i --rm -p 80:80 --name=test docker.io/fax4ever/test:1.0.0-SNAPSHOT
```

``` shell
docker exec -it test /bin/sh
```

``` shell
docker build -t docker.io/fax4ever/test:1.0.0-SNAPSHOT .
```

## Quarkus client: Develop and Package

[./gallery/]

``` shell
./mvnw compile quarkus:dev
```

``` shell
./mvnw package
```

## Install Bare Metal Kubernetes: Kind + MetalLB

``` shell
sudo systemctl start docker
```

> docker info

``` shell
kind create cluster --name=blablabla
```

> kind get clusters

> kubectl cluster-info --context kind-blablabla

> kind delete cluster --name=blablabla

(copied from https://raw.githubusercontent.com/metallb/metallb/v0.13.7/config/manifests/metallb-native.yaml)
``` shell
kubectl apply -f extra/metallb-native.yaml
```

``` shell
kubectl wait --namespace metallb-system \
--for=condition=ready pod \
--selector=app=metallb \
--timeout=90s
```

``` shell
docker network inspect -f '{{.IPAM.Config}}' kind
```

if ≠ 172.18.0.0/16
  modify with IP address [./extra/metallb-config.yaml]
```
kubectl apply -f extra/metallb-config.yaml
```
