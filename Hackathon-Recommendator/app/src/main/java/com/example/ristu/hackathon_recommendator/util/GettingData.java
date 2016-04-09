package com.example.ristu.hackathon_recommendator.util;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

//Lớp xử lý đa tiến trình:
public class GettingData extends AsyncTask<String, JSONObject, Void>
{
    public JSONObject json;

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
            json = jsonObj;
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
        try {
            //kiểm tra xem có tồn tại thuộc tính id hay không
            if(jsonObj.has("courses"))
                jsonObj.getString("courses");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onPostExecute(Void result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
    }
}
