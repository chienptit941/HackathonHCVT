package com.example.ristu.hackathon_recommendator.subject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.ristu.hackathon_recommendator.R;
import com.example.ristu.hackathon_recommendator.dialog.RateDialog;
import com.example.ristu.hackathon_recommendator.dialog.SubjectDetailDialog;
import com.example.ristu.hackathon_recommendator.model.SubjectDTO;
import com.example.ristu.hackathon_recommendator.util.AppStorage;
import com.example.ristu.hackathon_recommendator.util.Constants;
import com.example.ristu.hackathon_recommendator.util.DataTransfer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SubjectActivity extends AppCompatActivity implements ISubjectActivity {
    private static final String TAG = "SubjectActivity";
    private RecyclerView list;
    private SubjectAdapter adapter;
    private RecyclerView.LayoutManager listLayoutManager;
    private AppStorage appStorage;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subject_activity);
        view = LayoutInflater.from(this).inflate(R.layout.subject_activity, null, false);
        Intent intent = getIntent();



        appStorage = AppStorage.getInstance();

        list = (RecyclerView) findViewById(R.id.my_recycler_view);

        listLayoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(listLayoutManager);

        adapter = new SubjectAdapter(this);
        list.setAdapter(adapter);



//        appStorage.subjectDTOs = SERVER RESPONSE
        String link = "http://" + Constants.IP + ":8080/get_suggested_courses";
        String query = "?user_id=" + intent.getStringExtra("user_id");
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
                    Log.i(TAG, jsonObj.get("course_ids").toString()+ "||||"+jsonObj.getString("course_ids") );
                    StringBuilder ids = new StringBuilder(jsonObj.getString("course_ids").toString());
                    StringBuilder sss = new StringBuilder(jsonObj.getString("courses").toString());
                    Log.i(TAG, ids + "&&&&&&&" + sss);
                    sss.replace(0, 1, "");
                    ids.replace(0, 1, "");
                    Log.i(TAG, ids + "&&&&&&&" + sss);
                    sss.replace(sss.length() - 1, sss.length(), "");
                    ids.replace(ids.length() - 1, ids.length(), "");
                    Log.i(TAG, ids + "&&&&&&&" + sss);
                    String[] ssses = sss.toString().replace('"',' ').split(",");
                    String[] idses = ids.toString().replace('"',' ').split(",");
                    Log.i(TAG, idses[0] + "&&&&&&&" + ssses[0]);
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

    @Override
    public void showSubjectDetail(SubjectDTO subjectDTO) {
        SubjectDetailDialog dialog = new SubjectDetailDialog(this);
        dialog.setListener(this);
        dialog.setData(subjectDTO);
        dialog.show();
    }

    @Override
    public void showRate(SubjectDTO subjectDTO) {
        RateDialog dialog = new RateDialog(this);
        dialog.setListener(this);
        dialog.setData(subjectDTO);
        dialog.show();
    }

    @Override
    public void rate(SubjectDTO subjectDTO) {
        Snackbar snackbar = Snackbar.make(view, "rate " + subjectDTO.name + " success", Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    @Override
    public void register(SubjectDTO subjectDTO) {
        if (subjectDTO.id != null)
            Log.i(TAG, subjectDTO.id);
        Snackbar snackbar = Snackbar.make(view, "Register subject " + subjectDTO.id + " success", Snackbar.LENGTH_SHORT);
        snackbar.show();
        String link = "http://" + Constants.IP + ":8080/course_register";
        String query = "?user_id=" + Constants.USER_ID + "&" + "course_id="+subjectDTO.id;
        String url = link + query;
//        DataTransfer.pushDataThroughUrl(url);
        for (SubjectDTO item : appStorage.subjectDTOs) {
            if (item.id.equals(subjectDTO.id)) {
                item.isRegister = true;
            }
        }
        adapter.setData(appStorage.subjectDTOs);
    }
}


