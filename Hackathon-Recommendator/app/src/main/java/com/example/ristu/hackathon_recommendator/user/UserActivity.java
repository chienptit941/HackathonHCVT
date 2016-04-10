package com.example.ristu.hackathon_recommendator.user;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ristu.hackathon_recommendator.R;
import com.example.ristu.hackathon_recommendator.model.UserDTO;
import com.example.ristu.hackathon_recommendator.subject.SubjectActivity;
import com.example.ristu.hackathon_recommendator.util.AppStorage;
import com.example.ristu.hackathon_recommendator.util.Constants;
import com.example.ristu.hackathon_recommendator.util.DataTransfer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ristu on 4/10/2016.
 */
public class UserActivity extends AppCompatActivity {


    private static final String TAG = "UserActivity";
    private RecyclerView list;
    private UserAdapter adapter;
    private AppStorage appStorage;
    private RecyclerView.LayoutManager listLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity);
// /        view = LayoutInflater.from(this).inflate(R.layout.user_activity, null, false);
//        setContentView(view);
        initView();
        list = (RecyclerView) findViewById(R.id.user_recycler_view);

        listLayoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(listLayoutManager);

        adapter = new UserAdapter();
        list.setAdapter(adapter);
        Log.e(TAG, "test" );



//        appStorage.subjectDTOs = SERVER RESPONSE
        String link = "http://" + Constants.IP + ":8080/user_profile";
        String query = "?user_id="+Constants.USER_ID;
        String url = link + query;
        new GettingData().execute(url);
//        adapter.setData(appStorage.subjectDTOs2);
    }

    private TextView tvName;
    private TextView tvNameText;
    private TextView tvInterest;
    private TextView tvInterest_text;
    private Button btnSuggest;
    private void initView() {
        btnSuggest = (Button) findViewById(R.id.user_profile_button);
        tvName = (TextView) findViewById(R.id.user_profile_name);
        tvNameText = (TextView) findViewById(R.id.user_profile_name_text);
        tvInterest = (TextView) findViewById(R.id.user_profile_interest);
        tvInterest_text = (TextView) findViewById(R.id.user_profile_interest_text);
        btnSuggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "startActivity SubjectActivity.class");
                Intent intent = new Intent(getApplicationContext(), SubjectActivity.class);
                intent.putExtra("user_id", Constants.USER_ID);
                startActivity(intent);
            }
        });
    }

    public class GettingData extends AsyncTask<String, JSONObject, Void> {
        private String url;

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "GettingData: onPreExecute");
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(String... params) {
            Log.i(TAG, "GettingData: doInBackground start");
            //Lấy URL truyền vào
            url = params[0];
            JSONObject jsonObj;
            try {
                //đọc và chuyển về JSONObject
                jsonObj = DataTransfer.readJsonFromUrl(url);
                publishProgress(jsonObj);
            } catch (Exception e) {
                Log.e(TAG, "GettingData: doInBackground ex: " + e.toString());
            }
            Log.i(TAG, "GettingData: doInBackground end");
            return null;
        }
        @Override
        protected void onProgressUpdate(JSONObject... values) {
            super.onProgressUpdate(values);
            Log.i(TAG, "GettingData: onProgressUpdate start");
            //ta cập nhật giao diện ở đây:
            JSONObject jsonObj=values[0];
            try {
                List<UserDTO> subjects = new ArrayList();
                if(jsonObj.has("tvName"))
                    tvName.setText(jsonObj.getString("tvName"));
                if(jsonObj.has("tvInterest"))
                    tvName.setText(jsonObj.getString("tvInterest").replace('"', ' ').replace('[', ' ').replace(']', ' ').trim());

                if(jsonObj.has("rates")) {
                    ArrayList<UserDTO> arr = new ArrayList<>();
                    String[] rates = jsonObj.getString("rate").split(",");

                    for (String item: rates) {
                        String[] kv = item.replace('"', ' ').replace('{', ' ').replace('}', ' ').split(":");
                        UserDTO temp = new UserDTO(kv[0], kv[1]);
                        arr.add(temp);
                    }
                    appStorage.subjectDTOs2 = arr;
                }
                adapter.setData(appStorage.subjectDTOs2);
                adapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i(TAG, "GettingData: onProgressUpdate end");
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }
    }
}
