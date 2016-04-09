package com.example.ristu.hackathon_recommendator.util;

/**
 * Created by ristu on 4/9/2016.
 */

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

public class DataTransfer {
    public static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
    /**
     * Hàm trả về JSONObject
     * @param url - Truyền link URL có định dạng JSON
     * @return - Trả về JSONOBject
     * @throws IOException
     * @throws JSONException
     */
    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
        //đọc nội dung với Unicode:
            BufferedReader rd = new BufferedReader
                    (new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }
}
/*
//Lớp xử lý đa tiến trình:
public class GettingData extends AsyncTask<String, JSONObject, Void>
{
    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
    }
    @Override
    protected Void doInBackground(String... params) {
        //Lấy URL truyền vào
        String url=params[0];
        JSONObject jsonObj;
        try {
            //đọc và chuyển về JSONObject
            jsonObj = DataTransfer.readJsonFromUrl(url);
            publishProgress(jsonObj);
        } catch (IOException e) {
        // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
        // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onProgressUpdate(JSONObject... values) {
        super.onProgressUpdate(values);
        //ta cập nhật giao diện ở đây:
        JSONObject jsonObj=values[0];
        /*try {
            //kiểm tra xem có tồn tại thuộc tính id hay không
            if(jsonObj.has("id"))
                txtId.setText(jsonObj.getString("id"));
            if(jsonObj.has("first_name"))
                txtFirstName.setText(jsonObj.getString("first_name"));
            if(jsonObj.has("gender"))
                txtGender.setText(jsonObj.getString("gender"));
            if(jsonObj.has("last_name"))
                txtLastName.setText(jsonObj.getString("last_name"));
            if(jsonObj.has("link"))
                txtLink.setText(jsonObj.getString("link"));
            if(jsonObj.has("locale"))
                txtLocale.setText(jsonObj.getString("locale"));
            if(jsonObj.has("name"))
                txtName.setText(jsonObj.getString("name"));
            if(jsonObj.has("username"))
                txtUserName.setText(jsonObj.getString("username"));
        } catch (JSONException e) {
            Toast.makeText(MainActivity.this, e.toString(),
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }*
    }
    @Override
    protected void onPostExecute(Void result) {
    // TODO Auto-generated method stub
        super.onPostExecute(result);
    }
}
*/