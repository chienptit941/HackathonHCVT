/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import dao.connection.ConnectionDB;
import model.Rate;
import java.sql.*;

/**
 *
 * @author Admin
 */
public class RateDAO {

    public static boolean isRated(int idUser, int idCourse) {
        String sql = "SELECT id, tbluser_id, tblcourse_id, score, pastSubject "
                + "FROM tblrate "
                + "WHERE tblrate.tbluser_id = ? AND tblrate.tblcourse_id = ?;";
        Connection conn = ConnectionDB.getInstance().getConnection();
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, idUser);
            pst.setInt(2, idCourse);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Rate rate = new Rate();
                rate.setId(rs.getInt("id"));
                rate.setIdUser(rs.getInt("tbluser_id"));
                rate.setIdCouse(rs.getInt("tblcourse_id"));
                rate.setScore(rs.getFloat("score"));
                rate.setPassed(rs.getInt("pastSubject"));
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    public static void inserRate(Rate rate){
        String sql = "INSERT INTO hcvt.tblrate(score,tblcourse_id,tbluser_id,pastSubject) VALUES(?,?,?,?); ";
        Connection conn = ConnectionDB.getInstance().getConnection();
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setFloat(1, (float) (rate.getScore()*1.0f/2.0));            
            pst.setFloat(2, rate.getIdCouse());
            pst.setInt(3, rate.getIdUser());
            pst.setInt(4, rate.getPassed());
            
            pst.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }        
    }
    
    public static void main(String[] args) {
        System.out.println(isRated(1, 2));
    }
}
