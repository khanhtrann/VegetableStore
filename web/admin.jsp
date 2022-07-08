<%-- 
    Document   : admin
    Created on : Feb 12, 2022, 2:22:47 PM
    Author     : vankh
--%>

<%@page import="store.shopping.ProductDTO"%>
<%@page import="java.util.List"%>
<%@page import="store.user.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Page</title>
    </head>
    <body>
        <%
            UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
            String search = request.getParameter("search");
            if (search == null) {
                search = "";
            }
            if (loginUser == null || !"AD".equals(loginUser.getRoleID())) {
                response.sendRedirect("login.jsp");
                return;
            }

        %>
        <h1> Welcome <%= loginUser.getFullName()%></h1>

        <form action="MainController" method="POST">
            <input type="submit" name="action" value="Logout">
        </form>

        <form action="MainController">
            Search <input type="text" name="search" required="" value="<%= search%>">
            <input type="submit" name="action" value="Search">
        </form>

        <%
            List<ProductDTO> listProduct = (List<ProductDTO>) request.getAttribute("LIST_PRODUCT");
            if (listProduct != null) {
                if (listProduct.size() > 0) {
        %>         
        <table border="1">
            <tr>
                <th>No</th>
                <th>Product ID</th>
                <th>Product Name</th>                
                <th>Image</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>Category ID</th>
                <th>Import Date</th>
                <th>Using Date</th>
                <th>Status</th>
                <th>Delete</th>
                <th>Update</th>
            </tr>
            <%  int count = 1;
                for (ProductDTO product : listProduct) {

            %>
            <form action="MainController">
                <tr>
                    <td><%= count++%></td>
                    <td><input type="text" name="productID" value="<%= product.getProductID()%>" readonly></td>
                    <td><input type="text" name="productName" value="<%= product.getProductName()%>" pattern=".{3,20}"></td>
                    <td><input type="text" name="image" value="<%= product.getImage()%>"></td>
                    <td><input type="text" name="price" value="<%= product.getPrice()%>" min="0"></td>
                    <td><input type="text" name="quantity" value="<%= product.getQuantity()%>" min="0"></td>
                    <td><input type="text" name="categoryID" value="<%= product.getCategoryID()%>" min="1" max="4"></td>
                    <td><input type="date" name="importDate" value="<%= product.getImportDate()%>" ></td>
                    <td><input type="date" name="usingDate" value="<%= product.getUsingDate()%>"></td>
                            <td><select name="status">
                                    <option value="true" <% if(product.isStatus()) {%> selected <% }%>  >true</option>
                                    <option value="false" <% if(!product.isStatus()) {%> selected <% }%>>false</option>
                        </select>
                    </td>
                    <td><a href="MainController?action=Delete&productID=<%= product.getProductID()%>&search=<%=search%>">Delete</td>
                    <td><input type="submit" name="action" value="Update">
                        <input type="hidden" name="search" value="<%=search%>">
                    </td>
                </tr>
            </form>
            <% }%>
        </table>

        <%
            String error = (String) request.getAttribute("ERROR");
            if (error == null){
                error = "";
            }
        %>
        <%= error%>
        <%
                }
            }
        %>
        <a href="addProduct.jsp">Add a new product</a>
    </body>
</html>
