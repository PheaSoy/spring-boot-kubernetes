#!/bin/bash
date
echo "=> Start cleaning the env"

minikube status |grep localkube |  awk '{print $2}'

#echo "=> Cleaning service"
#
#kubectl delete service spring-boot-service

echo "=> Cleaning deployment"

kubectl delete deployment spring-boot-k8s-deployment

echo "=> Cleaning docker images"
docker image rm $( docker images | grep spring-boot | awk '{print $3}')  


echo "=> Package Spring Boot to .jar file"
mvn clean package -Dmaven.test.skip=true

echo "=> Start build docker image"
docker build -t spring-boot-k8s:v1 .

echo "=> Create deployment"

kubectl create -f k8s/create-deployment.yaml

echo "=> Expose service"
kubectl expose deployment spring-boot-k8s-deployment --name=spring-boot-service --type=NodePort

echo "=> Url here:"

minikube service spring-boot-service -n example-k8s --url
date
go run startup-time-test.go


