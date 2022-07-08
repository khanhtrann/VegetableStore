package store.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import store.utils.DBUtils;

public class UserDAO {

    private static final String LOGIN = "SELECT fullName, roleID FROM tblUsers WHERE userID=? AND password=?";
    private static final String CHECK_DUPLICATE = "SELECT fullName FROM tblUsers WHERE userID=?";
    //private static final String CREATE = "INSERT INTO tblUsers(userID, fullName, password, roleID) VALUES(?,?,?,?)";
    private static final String CREATE_GMAIL_USER = "INSERT INTO tblUsers(userID, fullName, password, roleID, status) VALUES(?,?,?,?,?)";
    
    public boolean createGmailUser(String email) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                stm = conn.prepareStatement(CREATE_GMAIL_USER);
                stm.setString(1, email);   
                stm.setString(2, "");   
                stm.setString(3, "");   
                stm.setString(4, "US");  
                stm.setInt(5, 1);
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

    public boolean checkDuplicate(String userID) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                stm = conn.prepareStatement(CHECK_DUPLICATE);
                stm.setString(1, userID);
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

    public UserDTO checkLogin(String userID, String password) throws SQLException {
        UserDTO user = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                stm = conn.prepareStatement(LOGIN);
                stm.setString(1, userID);
                stm.setString(2, password);
                rs = stm.executeQuery();
                if (rs.next()) {
                    String fullName = rs.getString("fullName");
                    String roleID = rs.getString("roleID");
                    user = new UserDTO(userID, fullName, "", roleID);
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
        return user;
    }

}
