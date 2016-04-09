package com.example.ristu.hackathon_recommendator.user;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ristu on 4/10/2016.
 */
public class UserActivity extends AppCompatActivity implements IUserActivity {
    private TextView name;
    private TextView name_text;
    private TextView interest;
    private TextView interest_text;
    private Button suggest;

    private static final String TAG = "SubjectActivity";
    private RecyclerView list;
    private UserAdapter adapter;
    private RecyclerView.LayoutManager listLayoutManager;
    private AppStorage appStorage;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        view = LayoutInflater.from(this).inflate(R.layout.subject_activity, null, false);
        setContentView(view);

        list = (RecyclerView) findViewById(R.id.my_recycler_view);

        listLayoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(listLayoutManager);

        adapter = new UserAdapter(this);
        list.setAdapter(adapter);



//        appStorage.subjectDTOs = SERVER RESPONSE
        String link = "http://" + Constants.IP + ":8080/user_profile";
        String query = "?user_id=a";
        String url = link + query;
        new GettingData().execute(url);
        adapter.setData(appStorage.subjectDTOs2);
    }

    private void initView() {
        suggest = (Button) findViewById(R.id.user_profile_button);
        name = (TextView) findViewById(R.id.user_profile_name);
        name_text = (TextView) findViewById(R.id.user_profile_name_text);
        interest = (TextView) findViewById(R.id.user_profile_interest);
        interest_text = (TextView) findViewById(R.id.user_profile_interest_text);
        suggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), SubjectActivity.class);
                intent.putExtra("user_id","a");
                startActivity(intent);
            }
        });
    }

    public class GettingData extends AsyncTask<String, JSONObject, Void>
    {
        private String url;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(String... params) {
            //Lấy URL truyền vào
            url=params[0];
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
            try {
                List<UserDTO> subjects = new ArrayList();
                if(jsonObj.has("name"))
                    name.setText(jsonObj.getString("name"));
                if(jsonObj.has("interest"))
                    name.setText(jsonObj.getString("interest").replace('"', ' ').replace('[', ' ').replace(']', ' ').trim());

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
}
