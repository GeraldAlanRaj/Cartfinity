import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function Shop() {
  const [products, setProducts] = useState([]);
  const [cart, setCart] = useState([]);
  const [showCart, setShowCart] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    axios.get('http://localhost:8080/api/products')
      .then(res => setProducts(res.data))
      .catch(err => {
        console.error(err);
        alert("Error fetching products.");
      });
  }, []);

  const addToCart = (product) => {
    const existing = cart.find(item => item.product.id === product.id);
    if (existing) {
      setCart(cart.map(item =>
        item.product.id === product.id ? { ...item, quantity: item.quantity + 1 } : item
      ));
    } else {
      setCart([...cart, { product, quantity: 1 }]);
    }
  };

  const removeFromCart = (id) => {
    setCart(cart.filter(item => item.product.id !== id));
  };

  const updateQuantity = (id, delta) => {
    setCart(cart.map(item => {
      if (item.product.id === id) {
        const newQty = item.quantity + delta;
        return newQty > 0 ? { ...item, quantity: newQty } : item;
      }
      return item;
    }).filter(item => item.quantity > 0));
  };

  const getTotal = () => {
    return cart.reduce((total, item) => total + item.product.price * item.quantity, 0);
  };

  const checkout = () => {
    if (cart.length === 0) return alert("Your cart is empty!");
    const cartData = cart.map(item => ({
      id: item.product.id,
      name: item.product.name,
      quantity: item.quantity,
      price: item.product.price * item.quantity
    }));

    axios.post('http://localhost:8080/api/products/checkout', cartData)
      .then(() => {
        alert("Order placed successfully!");
        setCart([]);
        setShowCart(false);
      });
  };

  return (
    <div>
      <header>
        <h1>E-Commerce Site</h1>
        <button onClick={() => setShowCart(!showCart)}>Cart ({cart.length})</button>
        <button onClick={() => navigate('/orders')}>Order History</button>
      </header>

      <main>
        <h2>Products</h2>
        <div className="product-grid">
          {products.map(product => (
            <div key={product.id} className="product">
              <img src={product.image} alt={product.name} className="product-image" />
              <h3>{product.name}</h3>
              <p>{product.description}</p>
              <p>Price: ₹{product.price}</p>
              <button onClick={() => addToCart(product)}>Add to Cart</button>
            </div>
          ))}
        </div>
      </main>

      {showCart && (
        <aside className="cart-sidebar show">
          <button className="close-cart" onClick={() => setShowCart(false)}>Close Cart</button>
          <h2>Shopping Cart</h2>
          {cart.length > 0 ? (
            <div className="cart-items">
              {cart.map(item => (
                <div key={item.product.id} className="cart-item">
                  <img src={item.product.image} alt={item.product.name} className="cart-image" />
                  <h3>{item.product.name}</h3>
                  <p>Price: ₹{item.product.price}</p>
                  <p>Quantity: 
                    <button onClick={() => updateQuantity(item.product.id, -1)}>-</button>
                    {item.quantity}
                    <button onClick={() => updateQuantity(item.product.id, 1)}>+</button>
                  </p>
                  <p>Total: ₹{item.product.price * item.quantity}</p>
                  <button onClick={() => removeFromCart(item.product.id)}>Remove from Cart</button>
                </div>
              ))}
              <p><strong>Total Price: ₹{getTotal()}</strong></p>
              <button className="checkout" onClick={checkout}>Checkout</button>
            </div>
          ) : (
            <p>Your cart is empty.</p>
          )}
        </aside>
      )}
    </div>
  );
}

export default Shop;