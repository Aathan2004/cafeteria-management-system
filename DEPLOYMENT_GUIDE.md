# 🚀 Deployment Guide - Cafeteria Management System

## Option 1: Quick Local Deployment (Recommended)

### 1. Build Frontend for Production
```bash
cd my-react-app
npm run build
```

### 2. Build Backend JAR
```bash
cd springapp
./mvnw clean package -DskipTests
```

### 3. Deploy Script
```bash
# Start MySQL
brew services start mysql

# Start Backend
java -jar springapp/target/*.jar &

# Serve Frontend (using Python)
cd my-react-app/build
python3 -m http.server 3000 &

echo "🚀 Deployed!"
echo "Frontend: http://localhost:3000"
echo "Backend: http://localhost:8080"
```

## Option 2: Cloud Deployment (Heroku - Free)

### 1. Prepare for Heroku
```bash
# Install Heroku CLI
brew install heroku/brew/heroku

# Login
heroku login
```

### 2. Deploy Backend
```bash
cd springapp
echo "web: java -jar target/*.jar" > Procfile
git init
git add .
git commit -m "Deploy backend"
heroku create your-cafeteria-backend
heroku addons:create cleardb:ignite
heroku config:set SPRING_PROFILES_ACTIVE=production
git push heroku main
```

### 3. Deploy Frontend
```bash
cd my-react-app
npm run build
npm install -g serve
echo "web: serve -s build -l $PORT" > Procfile
git init
git add .
git commit -m "Deploy frontend"
heroku create your-cafeteria-frontend
git push heroku main
```

## Option 3: Docker Deployment

### 1. Create Dockerfiles
```dockerfile
# Backend Dockerfile
FROM openjdk:21-jdk-slim
COPY target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
```

```dockerfile
# Frontend Dockerfile
FROM nginx:alpine
COPY build/ /usr/share/nginx/html/
EXPOSE 80
```

### 2. Docker Compose
```yaml
version: '3.8'
services:
  mysql:
    image: mysql:8.0
    environment:
      MYSQL_ROOT_PASSWORD: Aathan@2004
      MYSQL_DATABASE: appdb
    ports:
      - "3306:3306"
  
  backend:
    build: ./springapp
    ports:
      - "8080:8080"
    depends_on:
      - mysql
  
  frontend:
    build: ./my-react-app
    ports:
      - "3000:80"
```

## Option 4: Simple Production Setup

### 1. Create Production Scripts
```bash
# production-start.sh
#!/bin/bash
echo "Starting Cafeteria Management System..."

# Start MySQL
sudo systemctl start mysql

# Start Backend
cd springapp
nohup java -jar target/*.jar > backend.log 2>&1 &

# Start Frontend
cd ../my-react-app
nohup npm start > frontend.log 2>&1 &

echo "✅ System started!"
echo "Frontend: http://localhost:3000"
echo "Backend: http://localhost:8080"
```

## 🎯 Recommended for Your Staff:

### **Quick Demo Deployment:**
```bash
# 1. Build everything
cd springapp && ./mvnw clean package -DskipTests
cd ../my-react-app && npm run build

# 2. Start services
mysql.server start
java -jar springapp/target/*.jar &
cd my-react-app && npm start

# 3. Access
# Frontend: http://localhost:3000
# Backend: http://localhost:8080
```

## 📱 What to Tell Your Staff:

**"The project is ready for deployment with multiple options:**
1. **Local Production**: Ready to run on any server
2. **Cloud Deployment**: Can deploy to Heroku, AWS, or Azure
3. **Docker**: Containerized for easy deployment
4. **Enterprise**: Can be deployed on company servers

**Current Status:**
- ✅ 20 food items across 5 cuisines
- ✅ Multiple user authentication
- ✅ Advanced filtering & search
- ✅ MySQL database ready
- ✅ Production-ready code"

Choose Option 1 for immediate demo deployment!