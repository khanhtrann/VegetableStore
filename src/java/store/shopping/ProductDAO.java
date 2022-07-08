/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package store.shopping;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import store.utils.DBUtils;

/**
 *
 * @author vankh
 */
public class ProductDAO {

    private static final String SEARCH = "SELECT productID, productName, image, price, quantity, categoryID, importDate, usingDate, status FROM tblProduct WHERE productName LIKE ?";
    private static final String SEARCH_ALL = "SELECT productID, productName, image, price, quantity, categoryID, importDate, usingDate, status FROM tblProduct";
    private static final String SEARCH_ID = "SELECT productID, productName, price, quantity FROM tblProduct WHERE productID=?";
    private static final String DELETE = "UPDATE tblProduct SET status=0 WHERE productID=?";
    private static final String UPDATE = "UPDATE tblProduct SET productName=?, image=?, price=?, quantity=?, categoryID=?, importDate=?, usingDate=?, status=? WHERE productID=?";
    private static final String INSERT = "INSERT INTO tblProduct(productID, productName, image, price, quantity, categoryID, importDate, usingDate, status) VALUES(?,?,?,?,?,?,?,?,?)";
    private static final String CHECK_DUPLICATE = "SELECT productName FROM tblProduct WHERE productID=?";
    private static final String INSERT_ORDER = "INSERT INTO tblOrder(orderID, orderDate, total, userID) VALUES(?,?,?,?)";
    private static final String SELECT_ORDERID = "SELECT TOP 1 orderID FROM tblOrder ORDER BY orderID DESC";
    private static final String INSERT_ORDER_DETAIL = "INSERT INTO tblOrderDetail(price, quantity, orderID, productID) VALUES(?,?,?,?)";
    private static final String UPDATE_PRODUCT_QUANTITY = "UPDATE tblProduct SET quantity=? WHERE productID=?";

    public List<ProductDTO> getListProduct(String search) throws SQLException {
        List<ProductDTO> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH);
                ptm.setString(1, "%" + search + "%");
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String productID = rs.getString("productID");
                    String productName = rs.getString("productName");
                    String image = rs.getString("image");
                    int price = rs.getInt("price");
                    int quantity = rs.getInt("quantity");
                    int categoryID = rs.getInt("categoryID");
                    Date importDate = rs.getDate("importDate");
                    Date usingDate = rs.getDate("usingDate");
                    boolean status = rs.getBoolean("status");
                    list.add(new ProductDTO(productID, productName, image, price, quantity, categoryID, importDate, usingDate, status));
                }
            }
        } catch (Exception e) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return list;
    }

    public List<ProductDTO> getAllProduct() throws SQLException {
        List<ProductDTO> list = new ArrayList();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_ALL);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String productID = rs.getString("productID");
                    String productName = rs.getString("productName");
                    String image = rs.getString("image");
                    int price = rs.getInt("price");
                    int quantity = rs.getInt("quantity");
                    int categoryID = rs.getInt("categoryID");
                    Date importDate = rs.getDate("importDate");
                    Date usingDate = rs.getDate("usingDate");
                    boolean status = rs.getBoolean("status");
                    list.add(new ProductDTO(productID, productName, image, price, quantity, categoryID, importDate, usingDate, status));
                }
            }
        } catch (Exception e) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return list;
    }

    public ProductDTO getProductByID(String productID) throws SQLException {
        ProductDTO product = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_ID);
                ptm.setString(1, productID);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    String productName = rs.getString("productName");
                    int price = rs.getInt("price");
                    int quantity = rs.getInt("quantity");
                    product = new ProductDTO(productID, productName, price, quantity);
                }
            }
        } catch (Exception e) {
        } finally {
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return product;
    }

    public boolean addProduct(ProductDTO product) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                stm = conn.prepareStatement(INSERT);
                stm.setString(1, product.getProductID());
                stm.setString(2, product.getProductName());
                stm.setString(3, product.getImage());
                stm.setInt(4, product.getPrice());
                stm.setInt(5, product.getQuantity());
                stm.setInt(6, product.getCategoryID());
                stm.setDate(7, new java.sql.Date((product.getImportDate()).getTime()));
                stm.setDate(8, new java.sql.Date((product.getUsingDate()).getTime()));
                stm.setBoolean(9, true);
                check = stm.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;
    }

    public boolean checkDuplicate(String productID) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                stm = conn.prepareStatement(CHECK_DUPLICATE);
                stm.setString(1, productID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    check = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;
    }

    public boolean deleteProduct(String productID) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                stm = conn.prepareStatement(DELETE);
                stm.setString(1, productID);
                check = stm.executeUpdate() > 0 ? true : false;
            }

        } catch (Exception e) {
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;
    }

    public boolean updateProduct(ProductDTO product) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                stm = conn.prepareStatement(UPDATE);
                stm.setString(1, product.getProductName());
                stm.setString(2, product.getImage());
                stm.setInt(3, product.getPrice());
                stm.setInt(4, product.getQuantity());
                stm.setInt(5, product.getCategoryID());
                stm.setDate(6, new java.sql.Date((product.getImportDate()).getTime()));
                stm.setDate(7, new java.sql.Date((product.getUsingDate()).getTime()));
                stm.setBoolean(8, product.isStatus());
                stm.setString(9, product.getProductID());
                check = stm.executeUpdate() > 0 ? true : false;
            }

        } catch (Exception e) {
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;
    }

    public boolean updateProductQuantity(int quantity, String productID) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                stm = conn.prepareStatement(UPDATE_PRODUCT_QUANTITY);
                stm.setInt(1, quantity);
                stm.setString(2, productID);
                check = stm.executeUpdate() > 0 ? true : false;
            }

        } catch (Exception e) {
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;
    }

    public int getOrderID() throws SQLException {
        int orderID = 0;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SELECT_ORDERID);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    orderID = rs.getInt("orderID");
                }
            }
        } catch (Exception e) {
        } finally {
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return orderID;
    }

    public boolean addOrder(OrderDTO order) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                stm = conn.prepareStatement(INSERT_ORDER);
                stm.setInt(1, order.getOrderID());
                stm.setDate(2, order.getOrderDate());
                stm.setInt(3, order.getTotal());
                stm.setString(4, order.getUserID());
                check = stm.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;

    }

    public boolean addOrderDetail(OrderDetailDTO orderDetail) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                stm = conn.prepareStatement(INSERT_ORDER_DETAIL);
                stm.setInt(1, orderDetail.getPrice());
                stm.setInt(2, orderDetail.getQuantity());
                stm.setInt(3, orderDetail.getOrderID());
                stm.setString(4, orderDetail.getProductID());
                check = stm.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;
    }

}
