/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package store.shopping;

/**
 *
 * @author vankh
 */
public class OrderDetailDTO {
    private int price;
    private int quantity;
    private int orderID;
    private String productID;

    public OrderDetailDTO() {
    }

    public OrderDetailDTO( int price, int quantity, int orderID, String productID) {
        this.price = price;
        this.quantity = quantity;
        this.orderID = orderID;
        this.productID = productID;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }
    
    
}
