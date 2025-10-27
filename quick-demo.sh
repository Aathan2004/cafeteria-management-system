#!/bin/bash

echo "🚀 Starting Cafeteria Management System Demo..."

# Build backend
cd springapp
mvn clean package -DskipTests
echo "✅ Backend built"

# Start MySQL
brew services start mysql
echo "✅ MySQL started"

# Start backend
java -jar target/*.jar &
BACKEND_PID=$!
echo "✅ Backend running on http://localhost:8080"

# Start frontend
cd ../my-react-app
npm start &
FRONTEND_PID=$!
echo "✅ Frontend running on http://localhost:3000"

echo ""
echo "🎉 DEMO READY!"
echo "Frontend: http://localhost:3000"
echo "Backend: http://localhost:8080"
echo ""
echo "Login accounts:"
echo "Owner: owner/owner123"
echo "Customer: john/john123"
echo ""
echo "Press Ctrl+C to stop"

# Wait for user to stop
trap "kill $BACKEND_PID $FRONTEND_PID; exit" INT
wait