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
* Create create-namespace.yml
```bash
$ kubectl create -f k8s/create-namespace.yml
```
## Get namespace
```bash
$ kubectl get ns
```
## Swithc namespace
```bash
$ kubectl config set-context minikube --namespace example-k8s
```
## Describe namespace
```bash
$  kubectl describe ns example-k8s
```
## Delete namespace
```bash
$ kubectl delete namespaces example-k8s
```

## Create ResourceQuota
* Create create-resource-quota.yaml
```yaml
apiVersion: v1
kind: ResourceQuota
metadata:
  name: resource-quota-mem-cpu-example
  namespace: example-k8s
spec:
  hard: 
    requests.cpu: "1"
    requests.memory: 1Gi
    limits.cpu: "2"
    limits.memory: 4Gi
```

```bash
$ kubectl create -f k8s/create-resource-quota.yaml 
```
* Get the resource
```bash
$ kubectl describe ns example-k8s
```
* ===>
```bash
Name:		example-k8s
Labels:		app=spring-boot
Annotations:	<none>
Status:		Active

Resource Quotas
 Name:			resource-quota-mem-cpu-example
 Resource		Used	Hard
 --------		---	---
 limits.cpu		0	2
 limits.memory		0	4Gi
 requests.cpu		0	1
 requests.memory	0	1Gi
```
## Create Deployment
* Create create-deployment.yaml file
```yaml
kind: Deployment
apiVersion: extensions/v1beta1
metadata:
  name:   
  labels:
    app: spring-boot-k8s
  namespace: example-k8s
spec:
  replicas: 1
  selector:
    matchLabels:
      app: spring-boot-k8s
  template:
    metadata:
      labels:
        app: spring-boot-k8s
    spec:
      containers:
      - name: spring-boot-k8s
        image: spring-boot-k8s:v1
        ports:
        - containerPort: 9977
        resources:
          limits:
            memory: "2Gi"
            cpu: "1000m"
          requests: 
            memory: "1Gi"
            cpu: "500m"
```
```bash
$ kubectl create -f k8s/create-deployment.yaml
```
### Scale deployment
```bash
$ kubectl scale deployment spring-boot-k8s-deployment --replicas=2
```

### Update deployment
``` bash
 kubectl set image deployment/spring-boot-k8s-deployment spring-boot-k8s=spring-boot-k8s:v5 --record
```

## Create service access outside the cluster
* Create create-service.yaml file
```yaml
apiVersion: v1
kind: Service
metadata:
  labels:
    app: spring-boot-k8s-service
  name: spring-boot-k8s-service
  namespace: example-k8s
spec:
  ports:
  - nodePort: 30001 # Port access outside the cluser
    port: 9977 # Port access inside the cluster
    protocol: TCP
    targetPort: 9977 # Port forward to inside the port which container running
  selector:
    app: spring-boot-k8s
  type: NodePort
```

```bash
$ kubectl create -f k8s/create-service.yaml
```
## Access the application
```bash
$  curl http://$(minikube ip):30001/k8s/dara
```
## Create configmap
```bash
$ kubectl create configmap spring-boot-k8s --from-file k8s/user.properties 
```

