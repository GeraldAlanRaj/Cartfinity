import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function OrderHistory() {
  const [orderHistory, setOrderHistory] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    axios.get('http://localhost:8080/api/products/orders')
      .then(res => setOrderHistory(res.data))
      .catch(err => {
        console.error(err);
        alert("Error fetching order history.");
      });
  }, []);

  const deleteOrder = (orderId) => {
    if (window.confirm("Are you sure you want to delete this order?")) {
      axios.delete(`http://localhost:8080/api/products/orders/${orderId}`)
        .then(res => {
          alert(res.data);
          setOrderHistory(orderHistory.filter(order => order.order_id !== orderId));
        })
        .catch(err => {
          console.error(err);
          alert("Error deleting order.");
        });
    }
  };

  return (
    <div>
      <header>
        <h1>Order History</h1>
        <button onClick={() => navigate('/')}>Back to Shop</button>
      </header>

      <main className="order-history-container">
        <h2>Your Order History</h2>
        <table>
          <thead>
            <tr>
              <th>Order ID</th>
              <th>Product ID</th>
              <th>Product Name</th>
              <th>Quantity</th>
              <th>Price</th>
              <th>Order Date</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {orderHistory.map(order => (
              <tr key={order.order_id}>
                <td>{order.order_id}</td>
                <td>{order.product_id}</td>
                <td>{order.product_name}</td>
                <td>{order.quantity}</td>
                <td>₹{order.price}</td>
                <td>{order.order_date}</td>
                <td>
                  <button onClick={() => deleteOrder(order.order_id)}>Delete</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </main>
    </div>
  );
}

export default OrderHistory;
