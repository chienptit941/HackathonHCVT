package com.example.ristu.hackathon_recommendator.dialog;

/**
 * Created by ristu on 4/9/2016.
 */

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.ristu.hackathon_recommendator.R;
import com.example.ristu.hackathon_recommendator.model.SubjectDTO;
import com.example.ristu.hackathon_recommendator.subject.ISubjectActivity;

public class RateDialog extends Dialog {
    private ViewHolder holder;
    private ISubjectActivity listenner;
    private SubjectDTO subjectDTO;

    public RateDialog(Context context) {
        super(context);
        init();
    }

    private void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        View v = LayoutInflater.from(getContext()).inflate(R.layout.rate_dialog, null, false);
        setContentView(v);
        holder = new ViewHolder(v);

        holder.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenner.rate(subjectDTO);
                dismiss();
            }
        });
    }

    public void setListener(ISubjectActivity listenner) {
        this.listenner = listenner;
    }

    public void setData(SubjectDTO subjectDTO) {
        this.subjectDTO = subjectDTO;

        holder.name.setText(subjectDTO.name);
    }

    public static class ViewHolder {
        public View view;
        public TextView name;
        public Button submit;

        public ViewHolder(View v) {
            view = v;
            name = (TextView) view.findViewById(R.id.rate_subject_name);
            submit = (Button) view.findViewById(R.id.rate_submit);
        }
    }
}
