/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.sql.*;
import dao.connection.ConnectionDB;
import model.User;

/**
 *
 * @author Admin
 */
public class UserDAO {
    public static User getUser(int id){
        String sql = "SELECT tbluser.id, tbluser.fname, tbluser.lname, tblcategory.name " +
                "FROM tbluser, tblcategory " +
                "WHERE tbluser.id = ? " +
                "AND tbluser.tblcategory_id = tblcategory.id;";
        Connection conn = ConnectionDB.getInstance().getConnection();
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {                
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("fname") + " " + rs.getString("lname"));
                user.setMajor(rs.getString("name"));
                return user;
            } 
        } catch (SQLException ex) {    
            ex.printStackTrace();
        }
        return null;
    }
    
    
    
    public static void main(String[] args) {
        System.out.println(getUser(2));
    }
}
