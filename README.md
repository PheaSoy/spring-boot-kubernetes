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
### Build image
```bash
docker build -t spring-boot-k8s .
```

## Create  namespace

```yaml
apiVersion: v1
kind: Namespace
metadata: 
  name: example-k8s
```

```bash
kubectl create -f k8s/create-namespce.yml
```

## Delete namespace
```bash
kubectl delete namespaces 
```


