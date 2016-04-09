/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;
import dao.connection.ConnectionDB;
import java.sql.*;
import java.util.ArrayList;
import model.Course;
import model.Rate;
/**
 *
 * @author Admin
 */
public class CourseDAO {
    public static ArrayList<Course> getAllCourse(){
        ArrayList<Course> courses = new ArrayList<>();
        String sql = "SELECT tblsubjects.id, tblsubjects.name , tblcategory.name, tblcategory.id " +
                "FROM tblsubjects, tblcategory " +
                "WHERE tblsubjects.tblcategory_id = tblcategory.id;";
        Connection conn = ConnectionDB.getInstance().getConnection();
        try {
            PreparedStatement pst = conn.prepareStatement(sql);            
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Course c = new Course();
                c.setId(rs.getInt(1));
                c.setName(rs.getString(2));
                c.setMajor(rs.getString(3));                
                c.setMajorID(rs.getInt(4));
                courses.add(c);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return courses;
    }
    
    public static void main(String[] args) {
        getAllCourse();
    }
}
