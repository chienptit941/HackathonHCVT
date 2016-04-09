package Hackathon.MysqlDB;

import java.io.BufferedReader;
import java.io.FileReader;

public class MainDriver {
	 public static void main(String[] args) {
		 readFile("E:\\Eclipse Workspace\\MysqlDB\\courses.json");
	 }
	 public static String readFile(String filename) {
		    String result = "";
		    try {
		        BufferedReader br = new BufferedReader(new FileReader(filename));
		        StringBuilder sb = new StringBuilder();
		        String line = br.readLine();
		        while (line != null) {
		            sb.append(line);
		            line = br.readLine();
		            System.out.println(line);
		        }
		        result = sb.toString();
		    } catch(Exception e) {
		        e.printStackTrace();
		    }
		    return result;
		}
}
