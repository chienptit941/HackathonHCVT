package com.example.ristu.hackathon_recommendator.user;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.example.ristu.hackathon_recommendator.R;
import com.example.ristu.hackathon_recommendator.model.SubjectDTO;

public class UserActivity extends AppCompatActivity implements IUserActivity {
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

        appStorage.subjectDTOs = DumpData.dumpSubject();
        adapter.setData(appStorage.subjectDTOs);
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
        Snackbar snackbar = Snackbar.make(view, "Register subject " + subjectDTO.name + " success", Snackbar.LENGTH_SHORT);
        snackbar.show();
        for (SubjectDTO item : appStorage.subjectDTOs) {
            if (item.id.equals(subjectDTO.id)) {
                item.isRegister = true;
            }
        }
        adapter.setData(appStorage.subjectDTOs);
    }
}
