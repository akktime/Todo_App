package com.example.to_do_list_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.to_do_list_app.Adapter.TodoAdapter;
import com.example.to_do_list_app.Model.TodoModel;
import com.example.to_do_list_app.Utils.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Ondialogcloselistener {

    private RecyclerView mRecycler;
    private FloatingActionButton fab;
    private DatabaseHelper mydB;

    private List<TodoModel> mylist;
    private TodoAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecycler=findViewById(R.id.recyclerview);
        fab=findViewById(R.id.fab);
        mydB=new DatabaseHelper(MainActivity.this);
        mylist =new ArrayList<>();
        adapter=new TodoAdapter(mydB,MainActivity.this);


        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(adapter);

        mylist = mydB.getAllTasks();

        Collections.reverse(mylist);

        adapter.setTask(mylist);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddnewTask.newInstance().show(getSupportFragmentManager() , AddnewTask.TAG);

            }
        });

        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(new RecyclerviewTouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(mRecycler);


    }

    @Override
    public void onDialogClose(DialogInterface dialogInterface) {
        mylist = mydB.getAllTasks();
        Collections.reverse(mylist);
        adapter.setTask(mylist);
        adapter.notifyDataSetChanged();
    }
}