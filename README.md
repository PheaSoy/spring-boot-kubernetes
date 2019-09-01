# spring-boot-kubernetes
## Create Dockerfile
```
FROM openjdk:8-jdk-alpine
MAINTAINER Phea Soy
VOLUME [ "/tmp" ]
COPY  target/spring-boot-kubernetes-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9977
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
```
## Build image
```bash
$ docker build -t spring-boot-k8s:v1 .
```

## Install minikube for single cluster

* [This is the official document](https://kubernetes.io/docs/tasks/tools/install-minikube/) - minikube-install

## Start minikube
```base
$ minikube start
```
## Set docker to use minikube daemon
```bash
$ eval $(minikube docker-env)

```
## Create Deployment
```base
$ kubectl run spring-boot-k8s --image=spring-boot-k8s:v1 --port=9977
```

## Get POD
```base
$ kubectl get pod
```

## Watch pod status
```base
$ kubectl get pod -w
```

## Expose service for deployment
```base
$ kubectl expose deployment spring-boot-k8s --type=NodePort
```
## Get the URL of the expose service
```bash
$ minikube service spring-boot-k8s --url
```
## Access the application
```bash
$ curl http://192.168.64.2:32352/k8s/sidara
```
* Response
```
HTTP/1.1 200 
Content-Type: text/plain;charset=UTF-8
Content-Length: 62
Date: Sun, 01 Sep 2019 05:48:36 GMT
Hi sidara- I am a SpringBoot Application run inside Kubernetes
```
## Delete service
```bash
$ kubectl delete service spring-boot-k8s
```


## Switch 
## Create  namespace

```yaml
apiVersion: v1
kind: Namespace
metadata: 
  name: example-k8s
```

```bash
$ kubectl create -f k8s/create-namespce.yml
```

## Delete namespace
```bash
$ kubectl delete namespaces example-k8s
```


