#!/bin/bash

echo "=== System Status Check ==="

# Check MySQL
if pgrep -f mysql > /dev/null; then
    echo "✅ MySQL: Running"
    mysql -u root -p'Aathan@2004' -e "SELECT COUNT(*) as menu_items FROM menu_items;" 2>/dev/null || echo "❌ MySQL: Connection failed"
else
    echo "❌ MySQL: Not running"
fi

# Check Backend
if curl -s http://localhost:8080/api/menu-items?size=1 > /dev/null; then
    echo "✅ Backend: Running on port 8080"
    ITEM_COUNT=$(curl -s "http://localhost:8080/api/menu-items?size=1" | jq -r '.totalElements')
    echo "   📊 Total menu items: $ITEM_COUNT"
else
    echo "❌ Backend: Not responding"
fi

# Check Frontend (if running)
if curl -s http://localhost:3000 > /dev/null; then
    echo "✅ Frontend: Running on port 3000"
else
    echo "⚠️  Frontend: Not running (start with: cd my-react-app && npm start)"
fi

echo ""
echo "=== Quick Test ==="
echo "Search for pizza:"
curl -s "http://localhost:8080/api/menu-items?q=pizza&size=1" | jq -r '.content[0].name // "No results"'