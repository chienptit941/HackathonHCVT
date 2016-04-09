package com.example.ristu.hackathon_recommendator.user;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.ristu.hackathon_recommendator.R;
import com.example.ristu.hackathon_recommendator.model.SubjectDTO;
import com.example.ristu.hackathon_recommendator.subject.SubjectAdapter;
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
public class UserActivity extends AppCompatActivity {
    private TextView name;
    private TextView name_text;
    private TextView interest;
    private TextView interest_text;

    private static final String TAG = "SubjectActivity";
    private RecyclerView list;
    private UserAdapter adapter;
    private RecyclerView.LayoutManager listLayoutManager;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = LayoutInflater.from(this).inflate(R.layout.subject_activity, null, false);
        setContentView(view);

        list = (RecyclerView) findViewById(R.id.my_recycler_view);

        listLayoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(listLayoutManager);

        adapter = new SubjectAdapter(this);
        list.setAdapter(adapter);



//        appStorage.subjectDTOs = SERVER RESPONSE
        String link = "http://" + Constants.IP + ":8080/get_suggested_courses";
        String query = "?user_id=a";
        String url = link + query;
        new GettingData().execute(url, "start");
        adapter.setData(appStorage.subjectDTOs);
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
                List<SubjectDTO> subjects = new ArrayList();
                if(jsonObj.has("course_ids") && jsonObj.has("courses")) {
                    StringBuilder ids = new StringBuilder(jsonObj.getString("courses").toString());
                    StringBuilder sss = new StringBuilder(jsonObj.getString("courses").toString());
                    sss.replace(0, 1, "");
                    ids.replace(0, 1, "");
                    sss.replace(sss.length() - 1, sss.length(), "");
                    ids.replace(ids.length() - 1, ids.length(), "");
                    String[] ssses = sss.toString().replace('"',' ').split(",");
                    String[] idses = ids.toString().replace('"',' ').split(",");
                    for (int i = 0; i < ssses.length; ++i) {
                        SubjectDTO subj = new SubjectDTO(idses[i].trim(), ssses[i].trim());
                        subj.isRegister=false;
                        Log.e(TAG, "creating " + subj.id + " " + subj.name);
                        subjects.add(subj);
                    }
                    appStorage.subjectDTOs = subjects;
                }
                adapter.setData(appStorage.subjectDTOs);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            adapter.setData(appStorage.subjectDTOs);
            super.onPostExecute(result);
        }
    }
}