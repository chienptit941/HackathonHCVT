package com.example.ristu.hackathon_recommendator.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.ristu.hackathon_recommendator.R;
import com.example.ristu.hackathon_recommendator.model.SubjectDTO;
import com.example.ristu.hackathon_recommendator.user.IUserActivity;

/**
 * Created by ristu on 4/9/2016.
 */

public class SubjectDetailDialog extends Dialog {
    private ViewHolder holder;
    private IUserActivity listenner;
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

    public void setListener(IUserActivity listenner) {
        this.listenner = listenner;
    }

    public void setData(SubjectDTO subjectDTO) {
        this.subjectDTO = subjectDTO;

        holder.name.setText(subjectDTO.name);
        holder.description.setText(subjectDTO.description);
        holder.startcourse.setText(subjectDTO.startcourse);
        holder.endcourse.setText(subjectDTO.endcourse);
        holder.numberclass.setText(subjectDTO.numberclass);

        if (subjectDTO.isRegister) {
            holder.register.setVisibility(View.GONE);
        } else {
            holder.register.setVisibility(View.VISIBLE);
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
