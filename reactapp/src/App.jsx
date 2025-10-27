import React, { useState, useEffect } from 'react';
import Navbar from './components/Navbar';
import { getAllItems, addItem, updateItem, deleteItem, getItemsByCategory, getItemsSortedByPrice } from './services/api';
import './App.css';

function App() {
  const [items, setItems] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    fetchItems();
  }, []);

  const fetchItems = async () => {
    try {
      setLoading(true);
      const response = await getAllItems();
      setItems(response.data);
    } catch (error) {
      console.error('Error fetching items:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleAddItem = async (itemData) => {
    try {
      await addItem(itemData);
      fetchItems();
    } catch (error) {
      console.error('Error adding item:', error);
    }
  };

  return (
    <div className="App">
      <Navbar />
      <div className="container">
        <h1>Item Management System</h1>
        {loading ? (
          <p>Loading...</p>
        ) : (
          <div className="items-grid">
            {items.map(item => (
              <div key={item.id} className="item-card">
                <h3>{item.itemName}</h3>
                <p>Category: {item.category}</p>
                <p>Price: ${item.price}</p>
                <p>Available: {item.available ? 'Yes' : 'No'}</p>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}

export default App;