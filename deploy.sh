#!/bin/bash

echo "🚀 Deploying Cafeteria Management System..."

# Build Frontend
echo "📦 Building Frontend..."
cd my-react-app
npm run build
cd ..

# Build Backend
echo "📦 Building Backend..."
cd springapp
./mvnw clean package -DskipTests
cd ..

# Start MySQL
echo "🗄️ Starting MySQL..."
brew services start mysql || mysql.server start

# Start Backend
echo "⚙️ Starting Backend..."
cd springapp
nohup java -jar target/*.jar > ../backend.log 2>&1 &
cd ..

# Wait for backend to start
echo "⏳ Waiting for backend to start..."
sleep 10

# Start Frontend
echo "🌐 Starting Frontend..."
cd my-react-app
nohup npm start > ../frontend.log 2>&1 &
cd ..

echo ""
echo "✅ Deployment Complete!"
echo ""
echo "🌐 Frontend: http://localhost:3000"
echo "⚙️ Backend: http://localhost:8080"
echo "🗄️ Database: MySQL on localhost:3306"
echo ""
echo "👤 Login Accounts:"
echo "   Owner: owner/owner123"
echo "   Customer: john/john123, sarah/sarah123"
echo "   Or create new account"
echo ""
echo "📊 Features:"
echo "   - 20 food items (5 cuisines)"
echo "   - Advanced search & filtering"
echo "   - User registration"
echo "   - Price in Indian Rupees (₹)"
echo ""
echo "📝 Logs:"
echo "   Backend: backend.log"
echo "   Frontend: frontend.log"