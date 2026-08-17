SHELL := /bin/bash

CYAN   := \033[0;36m
RESET  := \033[0m

.PHONY: cluster-up cluster-down build-images import-images secrets deploy-infra deploy-apps all status forward

all: cluster-up secrets build-images import-images deploy-infra deploy-apps forward status

cluster-up:
	@echo -e "$(CYAN)============> [1/7] Creating k3d ars-cluster...$(RESET)"
	@k3d cluster create ars-cluster -p "8000:80@loadbalancer" || true
	@kubectl create namespace infra || true
	@kubectl create namespace apps || true

secrets:
	@echo -e "$(CYAN)============> [2/7] Creating cluster secrets .env...$(RESET)"
	@kubectl create secret generic ars-secret --namespace=apps --from-env-file=ars-secret.env --dry-run=client -o yaml | kubectl apply -f -

build-images:
	@echo -e "$(CYAN)============> [3/7] Building docker images...$(RESET)"
	@echo -e "$(CYAN)============> Building [Gateway]...$(RESET)"
	docker build -f ./ars-gateway/Dockerfile -t ars-gateway:v1 .
	@echo -e "$(CYAN)============> Building [Ad Service]...$(RESET)"
	docker build -f ./ars-ad-service/Dockerfile -t ars-ad-service:v1 .
	@echo -e "$(CYAN)============> Building [User Service]...$(RESET)"
	docker build -f ./ars-user-service/Dockerfile -t ars-user-service:v1 .
	@echo -e "$(CYAN)============> Building [Authenticator]...$(RESET)"
	docker build -f ./ars-authenticator/Dockerfile -t ars-authenticator:v1 .

import-images:
	@echo -e "$(CYAN)============> [4/7] Importing docker images to k3d...$(RESET)"
	k3d image import ars-gateway:v1 ars-ad-service:v1 ars-user-service:v1 ars-authenticator:v1 -c ars-cluster

deploy-infra:
	@echo -e "$(CYAN)============> [5/7] Deploying infrastructure...$(RESET)"
	kubectl apply -f k8s/infra/ -R
	@echo "Waiting for infrastructure pods to be ready..."
	kubectl wait --for=condition=ready pod --all -n infra --timeout=300s

deploy-apps:
	@echo -e "$(CYAN)============> [6/7] Deploying microservices...$(RESET)"
	kubectl apply -f k8s/apps/ -R
	@echo "Waiting for infrastructure pods to be ready..."
	kubectl wait --for=condition=ready pod --all -n apps --timeout=300s

status:
	@echo -e "$(CYAN)============> Cluster pods state:$(RESET)"
	kubectl get pods -n infra
	kubectl get pods -n apps

forward:
	@echo -e "$(CYAN)============> [7/7] Forwarding ports to localhost..."
	@kubectl port-forward svc/ars-gateway 8080:8080 -n apps > /dev/null 2>&1 &
	@kubectl port-forward svc/grafana 3000:3000 -n infra > /dev/null 2>&1 &
	@kubectl port-forward svc/prometheus 9090:9090 -n infra > /dev/null 2>&1 &
	@kubectl port-forward svc/jaeger 16686:16686 -n infra > /dev/null 2>&1 &
	@echo "Swagger UI: http://localhost:8080/swagger-ui.html"
	@echo "Grafana:    http://localhost:3000"
	@echo "Prometheus: http://localhost:9090"
	@echo "Jaeger UI:  http://localhost:16686"

cluster-down:
	@echo -e "$(CYAN)============> Deleting k3d cluster...$(RESET)"
	k3d cluster delete ars-cluster