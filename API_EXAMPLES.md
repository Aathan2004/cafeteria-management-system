# API Examples

## Owner Endpoints

### Create Menu
```bash
curl -X POST http://localhost:8080/api/owners/1/menus \
  -H "Content-Type: application/json" \
  -d '{"name": "Italian Classics", "description": "Traditional Italian dishes"}'
```

### Add Menu Item
```bash
curl -X POST http://localhost:8080/api/owners/menus/1/items \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Margherita Pizza",
    "description": "Classic tomato and mozzarella",
    "price": 12.99,
    "category": "Pizza",
    "tags": "vegetarian,classic,italian"
  }'
```

### Bulk Add Items
```bash
curl -X POST http://localhost:8080/api/owners/menus/1/items/bulk \
  -H "Content-Type: application/json" \
  -d '[
    {
      "name": "Carbonara",
      "description": "Creamy pasta with bacon",
      "price": 14.50,
      "category": "Pasta",
      "tags": "bacon,creamy"
    },
    {
      "name": "Tiramisu",
      "description": "Coffee dessert",
      "price": 6.99,
      "category": "Dessert",
      "tags": "coffee,sweet"
    }
  ]'
```

### Toggle Availability
```bash
curl -X PUT http://localhost:8080/api/owners/menu-items/1/availability \
  -H "Content-Type: application/json" \
  -d '{"available": false}'
```

## Customer Search Examples

### 1. Search by text and category with price range
```bash
curl "http://localhost:8080/api/menu-items?q=pizza&category=Pizza&minPrice=10&maxPrice=20&sort=price,asc&page=0&size=10"
```

### 2. Filter by tags and availability
```bash
curl "http://localhost:8080/api/menu-items?tags=vegetarian,spicy&available=true&sort=name,desc"
```

### 3. Search by owner and date range
```bash
curl "http://localhost:8080/api/menu-items?ownerId=1&createdAfter=2024-01-01T00:00:00&sort=createdAt,desc"
```

## Response Format
```json
{
  "content": [
    {
      "id": 1,
      "menuId": 1,
      "menuName": "Italian Classics",
      "name": "Margherita Pizza",
      "description": "Classic tomato and mozzarella",
      "price": 12.99,
      "category": "Pizza",
      "available": true,
      "tags": "vegetarian,classic,italian",
      "createdAt": "2024-01-15T10:30:00"
    }
  ],
  "totalElements": 25,
  "totalPages": 3,
  "size": 10,
  "number": 0
}
```