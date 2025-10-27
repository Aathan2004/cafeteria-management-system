import React from 'react';
import '../Navbar.css';

const Navbar = () => {
  return (
    <nav className="navbar">
      <div className="navbar-brand">
        <h2>Item Management</h2>
      </div>
      <div className="navbar-menu">
        <a href="/" className="navbar-item">Home</a>
        <a href="/items" className="navbar-item">Items</a>
      </div> 
    </nav>
  );
};

export default Navbar;