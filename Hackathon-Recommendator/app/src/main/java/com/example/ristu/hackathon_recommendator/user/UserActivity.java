package com.example.ristu.hackathon_recommendator.user;

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
import com.example.ristu.hackathon_recommendator.model.SubjectDTO;
import com.example.ristu.hackathon_recommendator.util.AppStorage;
import com.example.ristu.hackathon_recommendator.util.DataTransfer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class UserActivity extends AppCompatActivity implements IUserActivity {
    private static final String TAG = "UserActivity";
    private RecyclerView list;
    private UserAdapter adapter;
    private RecyclerView.LayoutManager listLayoutManager;
    private AppStorage appStorage;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = LayoutInflater.from(this).inflate(R.layout.user_activity, null, false);
        setContentView(view);

        appStorage = AppStorage.getInstance();

        list = (RecyclerView) findViewById(R.id.my_recycler_view);

        listLayoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(listLayoutManager);

        adapter = new UserAdapter(this);
        list.setAdapter(adapter);



//        appStorage.subjectDTOs = SERVER RESPONSE
        String link = "http://192.168.1.6:8080/get_hot_courses";
        String query = "?user_id=a";
        String url = link + query;
        new GettingData().execute(url);
        adapter.setData(appStorage.subjectDTOs);
    }

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
            try {
                //kiểm tra xem có tồn tại thuộc tính courses hay không
                Log.e(TAG, "begin");
                if(jsonObj.has("courses")) {
                    Log.e(TAG, "middle");
                    StringBuilder sss = new StringBuilder(jsonObj.getString("courses").toString());
                    sss.replace(0,1,"");
                    sss.replace(sss.length() - 1, sss.length(), "");
                    Log.e(TAG, sss.toString());
                    String[] ssses = sss.toString().replace('"',' ').split(",");
                    for (int i = 0; i < ssses.length; ++i) {
                        Log.e(TAG, "Adding " + ssses[i]);
                        SubjectDTO subj = new SubjectDTO(ssses[i]);
                        Log.e(TAG, "creating " + subj.name);
                        appStorage.subjectDTOs.add(subj);
                        Log.e(TAG, "fail");
                    }
                }

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
    }

    @Override
    public void showRate(SubjectDTO subjectDTO) {
    }

    @Override
    public void rate(SubjectDTO subjectDTO) {
        Snackbar snackbar = Snackbar.make(view, "rate " + subjectDTO.name + " success", Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    @Override
    public void register(SubjectDTO subjectDTO) {
    }
}


