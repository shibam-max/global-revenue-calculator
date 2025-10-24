# Deployment Guide

## Prerequisites
- Java 17+
- Docker & Docker Compose
- Kubernetes (optional)
- PostgreSQL 13+
- Apache Kafka
- Redis

## Local Development

### 1. Start Infrastructure
```bash
cd docker
docker-compose up -d postgres redis kafka
```

### 2. Run Application
```bash
mvn spring-boot:run
```

### 3. Access Services
- Application: http://localhost:8080
- API Documentation: http://localhost:8080/swagger-ui.html
- Health Check: http://localhost:8080/actuator/health
- Metrics: http://localhost:8080/actuator/prometheus

## Docker Deployment

### 1. Build Application
```bash
mvn clean package
```

### 2. Start All Services
```bash
cd docker
docker-compose up -d
```

## Kubernetes Deployment

### 1. Create Namespace
```bash
kubectl create namespace revenue-calculator
```

### 2. Create Secrets
```bash
kubectl create secret generic revenue-db-secret \
  --from-literal=username=revenue_user \
  --from-literal=password=revenue_pass \
  -n revenue-calculator
```

### 3. Deploy Application
```bash
kubectl apply -f k8s/revenue-calculator-deployment.yaml -n revenue-calculator
```

## Production Configuration

### Environment Variables
- `DB_USERNAME`: Database username
- `DB_PASSWORD`: Database password
- `SPRING_PROFILES_ACTIVE`: Set to 'production'

### Resource Requirements
- **CPU**: 500m (minimum), 1000m (recommended)
- **Memory**: 1Gi (minimum), 2Gi (recommended)
- **Storage**: 10Gi for database

### Monitoring Setup
1. Deploy Prometheus
2. Configure Grafana dashboards
3. Set up alerting rules

## Security Considerations
- Use HTTPS in production
- Configure proper authentication
- Set up network policies
- Enable audit logging
- Regular security updates