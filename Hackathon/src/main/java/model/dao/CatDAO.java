/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;
import dao.connection.ConnectionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import model.Cat;
import model.User;
/**
 *
 * @author Admin
 */
public class CatDAO {
    public static ArrayList<Cat> getAllCat(){
        ArrayList<Cat> courses = new ArrayList<>();
        String sql = "SELECT * FROM hcvt.tblcategory;";
        Connection conn = ConnectionDB.getInstance().getConnection();
        try {
            PreparedStatement pst = conn.prepareStatement(sql);            
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
               Cat cat = new Cat();
               cat.setId(rs.getInt("id"));
               cat.setName(rs.getString("name"));
               courses.add(cat);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return courses;
    }
    
    
    
    public static HashSet<Integer> getLoveCat(User user){
        HashSet<Integer> cats = new HashSet<>();
        String sql = "SELECT * FROM user_cat WHERE user_cat.user_id = ?; ";
        Connection conn = ConnectionDB.getInstance().getConnection();
        try {
            PreparedStatement pst = conn.prepareStatement(sql);            
            pst.setInt(1, user.getId());
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                int cat_id = rs.getInt("cat_id");
                cats.add(cat_id);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }    
        return cats;
    }
    
    public static void main(String[] args) {
        for (int k : getLoveCat(new User(2, "", ""))){
            System.out.println(k);
        }
    }
}
