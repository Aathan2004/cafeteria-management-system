#!/bin/bash

echo "Starting Cafeteria Management System Backend..."

# Check if MySQL is running
if ! pgrep -x "mysqld" > /dev/null; then
    echo "MySQL is not running. Starting MySQL..."
    brew services start mysql
    sleep 3
fi

echo "MySQL is running. Starting Spring Boot application..."

# Navigate to springapp directory
cd springapp

# Start the application
./mvnw spring-boot:run