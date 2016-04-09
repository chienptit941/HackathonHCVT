package com.example.ristu.hackathon_recommendator.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.ristu.hackathon_recommendator.R;
import com.example.ristu.hackathon_recommendator.model.SubjectDTO;
import com.example.ristu.hackathon_recommendator.subject.ISubjectActivity;
import com.example.ristu.hackathon_recommendator.util.Constants;
import com.example.ristu.hackathon_recommendator.util.DataTransfer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by ristu on 4/9/2016.
 */

public class SubjectDetailDialog extends Dialog {
    private ViewHolder holder;
    private ISubjectActivity listenner;
    private SubjectDTO subjectDTO;

    public SubjectDetailDialog(Context context) {
        super(context);
        init();
    }

    private void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        View v = LayoutInflater.from(getContext()).inflate(R.layout.subject_detail_dialog, null, false);
        setContentView(v);
        holder = new ViewHolder(v);

        holder.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenner.register(subjectDTO);
                dismiss();
            }
        });
    }

    public void setListener(ISubjectActivity listenner) {
        this.listenner = listenner;
    }

    public void setData(SubjectDTO subjectDTO) {

        String link = "http://" + Constants.IP + ":8080/get_course_detail";
        String query = "?course_id="+subjectDTO.id;
        String url = link + query;
        new GettingData().execute(url, "middle");
        if (subjectDTO.isRegister) {
            holder.register.setVisibility(View.GONE);
        } else {
            holder.register.setVisibility(View.VISIBLE);
        }
        this.subjectDTO = subjectDTO;
//
//        holder.name.setText(subjectDTO.name);
//        holder.description.setText(subjectDTO.description);
//        holder.startcourse.setText(subjectDTO.startcourse);
//        holder.endcourse.setText(subjectDTO.endcourse);
//        holder.numberclass.setText(subjectDTO.numberclass);
    }

    public class GettingData extends AsyncTask<String, JSONObject, Void>
    {
        private String url;
        private String period;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(String... params) {
            //Lấy URL truyền vào
            url=params[0];
            period=params[1];
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
            JSONObject jsonObj=values[0];
            try {
                if(jsonObj.has("name"))
                    holder.name.setText(jsonObj.getString("name"));
                if(jsonObj.has("description"))
                    holder.description.setText(jsonObj.getString("description"));
                if(jsonObj.has("startcourse"))
                    holder.startcourse.setText("Ngày bắt đầu: "+jsonObj.getString("startcourse"));
                if(jsonObj.has("endcourse"))
                    holder.endcourse.setText("Ngày kết thúc: "+jsonObj.getString("endcourse"));
                if(jsonObj.has("numberclass"))
                    holder.numberclass.setText("Số buổi: "+jsonObj.getString("numberclass"));
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

    public static class ViewHolder {
        public View view;
        public TextView name;
        public TextView description;
        public TextView startcourse;
        public TextView endcourse;
        public TextView numberclass;
        public Button register;

        public ViewHolder(View v) {
            view = v;
            name = (TextView) view.findViewById(R.id.subject_detail_subject_name);
            description = (TextView) view.findViewById(R.id.subject_detail_description);
            startcourse = (TextView) view.findViewById(R.id.subject_detail_startcourse);
            endcourse = (TextView) view.findViewById(R.id.subject_detail_endcourse);
            numberclass = (TextView) view.findViewById(R.id.subject_detail_numberclass);
            register = (Button) view.findViewById(R.id.subject_detail_submit);
        }
    }
}
