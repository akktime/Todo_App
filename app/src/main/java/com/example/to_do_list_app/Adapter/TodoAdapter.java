package com.example.to_do_list_app.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_do_list_app.AddnewTask;
import com.example.to_do_list_app.MainActivity;
import com.example.to_do_list_app.Model.TodoModel;
import com.example.to_do_list_app.R;
import com.example.to_do_list_app.Utils.DatabaseHelper;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.MyViewHolder> {

    private List<TodoModel> mlist;
    private MainActivity activity;
    private DatabaseHelper myDB;

    public TodoAdapter(DatabaseHelper myDB,MainActivity activity){
        this.activity= activity;
        this.myDB=myDB;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout,parent,false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final TodoModel items = mlist.get(position);
        holder.mycheckbox.setText(items.getTask());
        holder.mycheckbox.setChecked(tobolean(items.getStatus()));
        holder.mycheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()){
                    myDB.updateStatus(items.getId(), 1);
                }
                else{
                    myDB.updateStatus(items.getId(),0);
                }
            }
        });
    }

    public boolean tobolean(int num){
        return num!=0;
    }

    public Context getContect(){
        return activity;
    }

    public void setTask(List<TodoModel> mlist){
        this.mlist=mlist;
        notifyDataSetChanged();
    }

    public void deletTask(int position){
        TodoModel item = mlist.get(position);
        myDB.deleteTask(item.getId());
        mlist.remove(position);
        notifyItemRemoved(position);
    }


    public void editItem(int position){
        TodoModel item = mlist.get(position);

        Bundle bundle = new Bundle();
        bundle.putInt("id" , item.getId());
        bundle.putString("task" , item.getTask());

        AddnewTask task = new AddnewTask();
        task.setArguments(bundle);
        task.show(activity.getSupportFragmentManager() , task.getTag());


    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        CheckBox mycheckbox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mycheckbox=itemView.findViewById(R.id.mcheckbox);
        }
    }

}
