package com.example.ristu.hackathon_recommendator.user;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.ristu.hackathon_recommendator.R;
import com.example.ristu.hackathon_recommendator.model.SubjectDTO;
import com.example.ristu.hackathon_recommendator.util.AppStorage;
import com.example.ristu.hackathon_recommendator.util.DataTransfer;
import com.example.ristu.hackathon_recommendator.util.GettingData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class UserActivity extends AppCompatActivity implements IUserActivity {
    private RecyclerView list;
    private UserAdapter adapter;
    private RecyclerView.LayoutManager listLayoutManager;
    private AppStorage appStorage;
    private View view;
    private TextView test_view;

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

        test_view = (TextView) findViewById(R.id.test_view);



//        appStorage.subjectDTOs = SERVER RESPONSE
        String link = "http://192.168.1.6:8080/get_hot_courses";
        String query = "?user_id=a";
        String url = link + query;
        new GettingData().execute(url);
        JSONObject json = null;
        try {
            json = DataTransfer.readJsonFromUrl(url);
            if (json.has("courses"))
                test_view.setText(json.get("courses").toString());
            else
                test_view.setText("fail");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter.setData(appStorage.subjectDTOs);
    }

    @Override
    public void showSubjectDetail(SubjectDTO subjectDTO) {
    }

    @Override
    public void showRate(SubjectDTO subjectDTO) {
    }

    @Override
    public void rate(SubjectDTO subjectDTO) {
    }

    @Override
    public void register(SubjectDTO subjectDTO) {
    }
}

