/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package store.user;

import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 *
 * @author vankh
 */
public class UserDAONGTest {
    
    public UserDAONGTest() {
    }

    /**
     * Test of createGmailUser method, of class UserDAO.
     */
    @Test
    public void testCreateGmailUser() throws Exception {
        System.out.println("createGmailUser");
        String email = "";
        UserDAO instance = new UserDAO();
        boolean expResult = false;
        boolean result = instance.createGmailUser(email);
        assertEquals(result, expResult);
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkDuplicate method, of class UserDAO.
     */
    @Test
    public void testCheckDuplicate() throws Exception {
        System.out.println("checkDuplicate");
        String userID = "";
        UserDAO instance = new UserDAO();
        boolean expResult = false;
        boolean result = instance.checkDuplicate(userID);
        assertEquals(result, expResult);
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkLogin method, of class UserDAO.
     */
    @Test
    public void testCheckLogin() throws Exception {
        System.out.println("checkLogin");
        String userID = "";
        String password = "";
        UserDAO instance = new UserDAO();
        UserDTO expResult = null;
        UserDTO result = instance.checkLogin(userID, password);
        assertEquals(result, expResult);
        fail("The test case is a prototype.");
    }
    
}
