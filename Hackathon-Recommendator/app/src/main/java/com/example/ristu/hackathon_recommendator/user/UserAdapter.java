package com.example.ristu.hackathon_recommendator.user;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ristu.hackathon_recommendator.R;
import com.example.ristu.hackathon_recommendator.model.SubjectDTO;

import java.util.List;

/**
 * Created by ristu on 4/9/2016.
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private List<SubjectDTO> subjectDTOs;
    private IUserActivity listenner;

    public UserAdapter(IUserActivity listenner) {
        this.listenner = listenner;
    }

    public void setData(List<SubjectDTO> subjectDTOs) {
        this.subjectDTOs = subjectDTOs;
        notifyDataSetChanged();
    }

    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final SubjectDTO subjectDTO = subjectDTOs.get(position);
        final String name = subjectDTO.name;
        final boolean isRegister = subjectDTO.isRegister;

        holder.name.setText(name);
        holder.status.setText("");

        if (isRegister) {
            holder.rate.setVisibility(View.VISIBLE);
        } else {
            holder.rate.setVisibility(View.INVISIBLE);
        }

        holder.rate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listenner.showRate(subjectDTO);
                    }
                }
        );

        holder.view.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listenner.showSubjectDetail(subjectDTO);
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        if (subjectDTOs != null) {
            return subjectDTOs.size();
        } else {
            return 0;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView name;
        public TextView status;
        public Button rate;

        public ViewHolder(View v) {
            super(v);
            view = v;
            name = (TextView) view.findViewById(R.id.user_subject_name);
            status = (TextView) view.findViewById(R.id.user_subject_status);
            rate = (Button) view.findViewById(R.id.user_subject_rate);
        }
    }
}
