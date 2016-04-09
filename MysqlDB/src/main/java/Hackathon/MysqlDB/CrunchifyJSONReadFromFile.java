package Hackathon.MysqlDB;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.json.JSONArray;
import org.json.JSONObject;


public class CrunchifyJSONReadFromFile {
	@SuppressWarnings("unchecked")
    public static void main(String[] args) throws FileNotFoundException {
		new CrunchifyJSONReadFromFile().parseObject(readFile("E:\\Eclipse Workspace\\MysqlDB\\courses.json"));
    }
	public void parseObject(String str) throws FileNotFoundException{
		JSONArray jsonArray = new JSONArray(str);
		int count = jsonArray.length(); // get totalCount of all jsonObjects
		PrintWriter printWriter = new PrintWriter(new File("hehe.txt"));
		for(int i=0 ; i< count; i++){   // iterate through jsonArray 
			JSONObject jsonObject = jsonArray.getJSONObject(i);  // get jsonObject @ i position 
			StringBuilder sb = new StringBuilder(jsonObject.get("description").toString());
			sb = sb.replace(0, 2, "");
			String k = "\",\"";
			sb = sb.replace(sb.length()-2, sb.length(), k);
			String[] st = sb.toString().split(k);
			if(st.length>1){
				k =""; //descripton
				for(int i1=1;i1<st.length;i1++) k+=st[i1];
				
			}
			sb = new StringBuilder(jsonObject.get("categories").toString());
			
			sb.replace(0, 1, "");
			sb.replace(sb.length()-1, sb.length(), "");
//			System.out.println(sb);
			String temp = sb.toString();
			temp+=",";
			String categories = "";
			if(temp.length()>3){
				st = temp.split(",");
				
				categories = new JSONObject(st[0]).get("name").toString();
				
				
				
			} else categories = "Chủ đề khác";
			
			String name = jsonObject.get("name").toString();
			
			String curriculums = jsonObject.get("curriculums").toString();
			JSONArray jsonArray1 = new JSONArray(curriculums);
			
			int numberclass = jsonArray1.length();
			
			printWriter.println(name);
			
			Category category = new Category(categories, k);
			
			
			System.out.println("jsonObject " + i + ": "  + " "+ numberclass);
		}
		printWriter.close();
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
//		            System.out.println(line);
		        }
		        result = sb.toString();
		    } catch(Exception e) {
		        e.printStackTrace();
		    }
		    return result;
		}
}
