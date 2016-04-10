package com.example.ristu.hackathon_recommendator.user;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ristu.hackathon_recommendator.R;
import com.example.ristu.hackathon_recommendator.model.UserDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ristu on 4/10/2016.
 */
public class UserAdapter  extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private List<UserDTO> subjectDTOs;
    private LayoutInflater inflater;
    public UserAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        subjectDTOs = new ArrayList<>();
    }

    public void setData(List<UserDTO> subjectDTOs) {
        this.subjectDTOs = subjectDTOs;
        notifyDataSetChanged();
    }

    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.user_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        final UserDTO subjectDTO = subjectDTOs.get(position);
        final String name = subjectDTO.name;
        final String rate= subjectDTO.rate;

        holder.name.setText(name);
        holder.rate.setText(rate);
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
        public TextView rate;

        public ViewHolder(View v) {
            super(v);
            view = v;
            name = (TextView) view.findViewById(R.id.user_profile_subject_name);
            rate = (TextView) view.findViewById(R.id.user_profile_subject_rate);
        }
    }
}
