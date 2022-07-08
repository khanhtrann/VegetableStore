package store.shopping;

import java.util.HashMap;
import java.util.Map;

public class Cart {

    private Map<String, Item> cart;

    public Cart() {
    }

    public Cart(Map<String, Item> cart) {
        this.cart = cart;
    }

    public Map<String, Item> getCart() {
        return cart;
    }

    public void setCart(Map<String, Item> cart) {
        this.cart = cart;
    }

    public boolean add(Item item) {
        boolean check = false;
        if (this.cart == null) {
            this.cart = new HashMap<>();
        }

        if (this.cart.containsKey(item.getId())) {
            int currentQuantity = this.cart.get(item.getId()).getQuantity();
            item.setQuantity(currentQuantity + item.getQuantity());
        }

        this.cart.put(item.getId(), item);
        check = true;
        return check;
    }

    public boolean remove(String productID) {
        boolean check = false;
        if (this.cart != null) {
            if (this.cart.containsKey(productID)) {
                this.cart.remove(productID);
                check = true;
            }
        }
        return check;
    }

    public boolean update(String productID, Item item) {
        boolean check = false;
        if (this.cart != null) {
            if (this.cart.containsKey(productID)) {
                this.cart.replace(productID, item);
                check = true;
            }
        }
        return check;
    }
}
