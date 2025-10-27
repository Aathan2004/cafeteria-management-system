import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api/items';

export const addItem = async (item) => {
  return await axios.post(`${API_BASE_URL}/addItem`, item);
};

export const getAllItems = async () => {
  return await axios.get(`${API_BASE_URL}/allItems`);
};

export const getItemsByCategory = async (category) => {
  return await axios.get(`${API_BASE_URL}/byCategory?category=${category}`);
};

export const getItemsSortedByPrice = async () => {
  return await axios.get(`${API_BASE_URL}/sortedByPrice`);
};

export const updateItem = async (id, item) => {
  return await axios.put(`${API_BASE_URL}/${id}`, item);
};

export const deleteItem = async (id) => {
  return await axios.delete(`${API_BASE_URL}/${id}`);
};