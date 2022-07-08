<%-- 
    Document   : addProduct
    Created on : Mar 1, 2022, 10:19:25 PM
    Author     : vankh
--%>

<%@page import="store.shopping.ProductError"%>
<%@page import="store.user.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add a New Product</title>
    </head>
    <body>
        <%
            UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
            if (loginUser == null || !"AD".equals(loginUser.getRoleID())) {
                response.sendRedirect("login.jsp");
                return;
            }
            ProductError productError = (ProductError) request.getAttribute("PRODUCT_ERROR");
            if (productError == null) {
                productError = new ProductError();
            }
        %>
        <h1>New Product</h1>
        <form action="MainController" method="POST">
            <table>
                <tr>
                    <td>Product ID</td>
                    <td><input type="text" name="productID" required=""></td>
                    <td><%= productError.getProductID()%><br></td>
                </tr>
                <tr>
                    <td>Product Name</td>
                    <td><input type="text" name="productName" required=""></td>
                    <td><%= productError.getProductName()%><br></td>
                </tr>
                <tr>
                    <td>Image</td>
                    <td><input type="text" name="image" required=""></td>
                    <td><%= productError.getImage()%><br></td>
                </tr>
                <tr>
                    <td>Price</td>
                    <td><input type="text" name="price" min="0" required=""></td>
                </tr>
                <tr>
                    <td>Quantity</td>
                    <td><input type="text" name="quantity" min="0" required=""></td>
                </tr>
                <tr>
                    <td>Category ID</td>
                    <td><input type="text" name="categoryID" required=""></td>
                    <td><%= productError.getCategoryID()%><br></td>
                </tr>
                <tr>
                    <td>Import Date</td>
                    <td><input type="date" name="importDate" required=""></td>
                </tr>
                <tr>
                    <td>Using Date</td>
                    <td><input type="date" name="usingDate" required=""></td>
                </tr>
                <tr>
                    <td>Status</td>
                    <td><input type="number" name="status" required="" min="0" max="1"></td>
                </tr>
                <tr>
                    <td><input type="submit" name="action" value="Add Product"></td>
                    <td><input type="reset" value="Clear"></td>
                </tr>
            </table>
        </form>
    </body>
</html>
