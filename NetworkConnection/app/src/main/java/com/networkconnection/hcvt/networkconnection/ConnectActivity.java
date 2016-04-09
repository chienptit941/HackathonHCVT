package com.networkconnection.hcvt.networkconnection;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class ConnectActivity extends AppCompatActivity {
    private static final String TAG = "ConnectActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        initViews();
    }

    TextView txtNotice;

    public TextView findTextView(int id) {
        return (TextView) findViewById(id);
    }

    private void initViews(){
        txtNotice =findTextView(R.id.txt_notice);

    }

    @Override
    protected void onResume() {
        super.onResume();
        new MyJsonTask().execute("http://api.previmeteo.com/ef71899f020d203c31f5cd64e8887155/ig/api?weather=hanoi");
        txtNotice.setText("STEP 1");
    }



    public class MyJsonTask extends AsyncTask<String, JSONObject, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(String... params) {
            String url=params[0];
            JSONObject jsonObj;
            Log.e(TAG, "doInBackground");
            try {
                jsonObj = MJsonReader.readJsonFromUrl(url);
                Toast.makeText(ConnectActivity.this, "JSON", Toast.LENGTH_LONG).show();
                publishProgress(jsonObj);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(JSONObject... values) {
            super.onProgressUpdate(values);
            JSONObject jsonObj=values[0];
            txtNotice.setText(jsonObj.toString());
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }
    }
}
