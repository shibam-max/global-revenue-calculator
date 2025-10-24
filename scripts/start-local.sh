#!/bin/bash

# Global Revenue Calculator - Local Startup Script

echo "🚀 Starting Global Revenue Calculator System..."

# Check if Docker is running
if ! docker info > /dev/null 2>&1; then
    echo "❌ Docker is not running. Please start Docker first."
    exit 1
fi

# Load environment variables
if [ -f .env ]; then
    echo "📋 Loading environment variables from .env file..."
    export $(cat .env | xargs)
else
    echo "⚠️  No .env file found. Using default values."
    echo "💡 Copy .env.example to .env and customize as needed."
fi

# Start infrastructure services
echo "🏗️  Starting infrastructure services..."
cd docker
docker-compose up -d postgres redis kafka zookeeper

# Wait for services to be ready
echo "⏳ Waiting for services to be ready..."
sleep 30

# Check service health
echo "🔍 Checking service health..."
docker-compose ps

# Build and start application
echo "🔨 Building application..."
cd ..
mvn clean package -DskipTests

echo "🚀 Starting Revenue Calculator application..."
java -jar target/global-revenue-calculator-1.0.0.jar

echo "✅ Global Revenue Calculator is running!"
echo "📊 Access the application at: http://localhost:8080"
echo "📚 API Documentation: http://localhost:8080/swagger-ui.html"
echo "💊 Health Check: http://localhost:8080/actuator/health"