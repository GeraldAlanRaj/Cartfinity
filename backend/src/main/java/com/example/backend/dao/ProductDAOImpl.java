package com.example.backend.dao;

import com.example.backend.model.Product;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull; // Import NonNull annotation
import org.springframework.stereotype.Repository;

import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class ProductDAOImpl implements ProductDAO {

    private final JdbcTemplate jdbcTemplate;

    public ProductDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    @Override
    public List<Product> getAllProducts() {
        String sql = "SELECT * FROM products";
        return jdbcTemplate.query(sql, new ProductRowMapper());
    }


    private static class ProductRowMapper implements RowMapper<Product> {
        @Override
        public Product mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
            Product product = new Product();
            product.setId(rs.getInt("id"));
            product.setName(rs.getString("name"));
            product.setDescription(rs.getString("description"));
            product.setPrice(rs.getDouble("price"));
            product.setImage(rs.getString("image"));
            return product;
        }
    }


    @Override
public void saveCartItems(List<Product> cartItems) {
    String sql = "INSERT INTO checkout (product_id, product_name, quantity, price) VALUES (?, ?, ?, ?)";
    try {
        for (Product product : cartItems) {
            jdbcTemplate.update(sql, product.getId(), product.getName(), product.getQuantity(), product.getPrice());
        }
    } catch (Exception e) {
        e.printStackTrace();
        throw new RuntimeException("Error saving cart items", e);
    }
}



// ProductDAOImpl.java
@Override
public List<Map<String, Object>> getOrderHistory() {
    String sql = "SELECT order_id, product_id,product_name, quantity, price,order_date FROM checkout";
    List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
    System.out.println("Order History: " + result);  // Log the result
    return result;
}


@Override
public void deleteOrder(int orderId) {
    String sql = "DELETE FROM checkout WHERE order_id = ?";
    jdbcTemplate.update(sql, orderId);
}




}
