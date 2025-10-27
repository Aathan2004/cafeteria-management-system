#!/bin/bash

echo "🧪 Testing React Build..."

cd my-react-app

echo "Installing dependencies..."
npm install

echo "Building React app..."
npm run build

if [ $? -eq 0 ]; then
    echo "✅ Build successful! Ready for Netlify deployment."
    echo "Build output is in my-react-app/build/"
else
    echo "❌ Build failed. Check errors above."
fi