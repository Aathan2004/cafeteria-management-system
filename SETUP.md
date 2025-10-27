# Cafeteria Management System - Setup Guide

## Features Added
✅ **Paging**: Navigate through items with configurable page sizes (6, 12, 24 items per page)
✅ **Filtering**: Search by name, filter by category, availability, and price range
✅ **Sorting**: Sort by name, price, or category (ascending/descending)

## Quick Start

### Option 1: Using MySQL (Recommended)
1. **Start MySQL**:
   ```bash
   brew services start mysql  # macOS
   # or
   sudo systemctl start mysql  # Linux
   ```

2. **Start Backend**:
   ```bash
   ./start-backend.sh
   ```

3. **Start Frontend**:
   ```bash
   cd my-react-app
   npm start
   ```



## Backend Endpoints

### Items API (with Paging/Filtering/Sorting)
- `GET /api/items/paged?page=0&size=10&sortBy=itemName&sortDir=asc`
- `GET /api/items/paged?search=chicken&category=Indian&available=true`
- `GET /api/items/paged?minPrice=100&maxPrice=500`

### Owner Dashboard API
- `GET /api/owner/items` - Get all items
- `POST /api/owner/items` - Create new item
- `PUT /api/owner/items/{id}` - Update item
- `DELETE /api/owner/items/{id}` - Delete item
- `PUT /api/owner/items/{id}/toggle` - Toggle availability
- `GET /api/owner/stats` - Get statistics

### Menu API (Legacy)
- `GET /api/menu` - Get menu grouped by categories

## Default Users
- **Owner**: username=`owner`, password=`owner123`
- **Customer**: username=`customer`, password=`customer123`

## Troubleshooting

### Backend Not Starting
1. Check if MySQL is running: `brew services list | grep mysql`
2. Start MySQL: `brew services start mysql`
3. Verify database password in `springapp/src/main/resources/application.properties`

### No Items Showing
1. Backend creates sample data automatically on first run
2. Check browser console for API errors
3. Verify backend is running on `http://localhost:8080`
4. Frontend has fallback mock data if backend is unavailable

### CORS Issues
- Backend has CORS configured for `http://localhost:3000`
- Ensure frontend runs on port 3000

## Database Configuration

### MySQL (Primary)
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/appdb?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=Aathan@2004
```



## IntelliJ IDEA Setup
1. Open the project root directory
2. Navigate to `springapp/src/main/java/com/examly/springapp/SpringappApplication.java`
3. Right-click → Run 'SpringappApplication.main()'
4. Or use Maven: Run → Edit Configurations → Add Spring Boot configuration

## API Testing
- Swagger UI: `http://localhost:8080/swagger-ui.html` (when backend is running)

## Features Overview

### Menu Page
- Search items by name
- Filter by category (Continental, Indian, Arabic, Chinese, South Indian)
- Filter by availability (Available/Unavailable)
- Filter by price range (min/max)
- Sort by name, price, or category
- Pagination with configurable page size
- Add items to order (for customers)

### Owner Dashboard
- View all items with statistics
- Add new items with form validation
- Edit existing items
- Toggle item availability
- Delete items
- Real-time statistics (total, available, unavailable items)

The system now supports efficient handling of large datasets with proper paging, filtering, and sorting capabilities as requested by your reviewer.