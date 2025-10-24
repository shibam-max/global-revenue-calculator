@echo off
REM Global Revenue Calculator - Local Startup Script for Windows

echo 🚀 Starting Global Revenue Calculator System...

REM Check if Docker is running
docker info >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Docker is not running. Please start Docker first.
    exit /b 1
)

REM Start infrastructure services
echo 🏗️  Starting infrastructure services...
cd docker
docker-compose up -d postgres redis kafka zookeeper

REM Wait for services to be ready
echo ⏳ Waiting for services to be ready...
timeout /t 30 /nobreak >nul

REM Check service health
echo 🔍 Checking service health...
docker-compose ps

REM Build and start application
echo 🔨 Building application...
cd ..
mvn clean package -DskipTests

echo 🚀 Starting Revenue Calculator application...
java -jar target/global-revenue-calculator-1.0.0.jar

echo ✅ Global Revenue Calculator is running!
echo 📊 Access the application at: http://localhost:8080
echo 📚 API Documentation: http://localhost:8080/swagger-ui.html
echo 💊 Health Check: http://localhost:8080/actuator/health