package Hackathon.MysqlDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
 
public class MySQL {
 
    private Connection connection;
    
    private static MySQL instance = null;
    
    public MySQL getInstance(){
    	if (instance == null) instance = new MySQL();
    	return instance;
    }
    
    public void insertCategory(Category category) throws SQLException{
    	String sql = "INSERT INTO `hcvt`.`tblcategory`(`name`,`description`) VALUES (?,?);";
    	PreparedStatement ps = connection.prepareStatement(sql); 
    	
    	ps.setString(1, category.getName());
    	ps.setString(2, category.getDescription());
    	
    	ps.execute();
    	
    }
 
    public MySQL(){
    	
    	
        try {
            Class.forName("com.mysql.jdbc.Driver");  // Nạp driver cho việc kết nối
            // Class.forName("sun.jdbc.odbc.JdbcOdbcDriver"); dùng cái này nếu là sqlserver của microsoft
 
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
 
        String url = "jdbc:mysql://192.168.43.69:3306/hcvt"; // trong đó ://127.0.0.1:3306/test là tên và đường dẫn tới CSDL.
        // String Url = "jdbc:odbc:" + dataBaseName; dùng cái này nếu là SQLServer
        try {
            // Kết nối tới CSDL theo đường dẫn url, tài khoản đăng nhập là root, pass là khoaninh, nếu CSDL của bạn khi tạo không có pass thi  bỏ qua nó, chỉ cần viết getConnection(url)
            connection = DriverManager.getConnection(url, "root", "toor");
            System.out.print("Kết nối thành công");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
 
    public static void main(String ar[]){
        new MySQL();
    }
}