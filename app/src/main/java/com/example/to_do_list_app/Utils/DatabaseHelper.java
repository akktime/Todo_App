package com.example.to_do_list_app.Utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.to_do_list_app.Model.TodoModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    private static final String Database_name="Todo_Database";
    private static final String Table_name="Todo_Table";
    private static final String Col1="ID";
    private static final String Col2="Task";
    private static final String Col3="Status";

    public DatabaseHelper(@Nullable Context context) {
        super(context, Database_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + Table_name + "(ID INTEGER PRIMARY KEY AUTOINCREMENT , TASK TEXT , STATUS INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
        db.execSQL("DROP TABLE IF EXISTS " + Table_name);
        onCreate(db);
    }

    public void insertTask(TodoModel model){
        db=this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(Col2,model.getTask());
        value.put(Col3,0);
        db.insert(Table_name,null,value);

    }
    public  void  updateTask(int id,String task){
        db=this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(Col2,task);
        db.update(Table_name,value,"ID=?",new String[]{String.valueOf(id)});
    }

    public  void updateStatus(int id,int status){
        db=this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(Col3,status);
        db.update(Table_name,value,"ID=?",new String[]{String.valueOf(id)});
    }

    public void deleteTask(int id){
        db=this.getWritableDatabase();
        db.delete(Table_name,"ID=?",new String[]{String.valueOf(id)});
    }

    @SuppressLint("Range")
    public List<TodoModel> getAllTasks(){
        db=this.getWritableDatabase();
        Cursor cursor = null;
        List<TodoModel> modelList=new ArrayList<>();

        db.beginTransaction();

        try{
            cursor=db.query(Table_name,null,null,null, null,null,null);
            if (cursor != null){
                if (cursor.moveToFirst()){
                    do {
                        TodoModel task=new TodoModel();
                        task.setId(cursor.getInt(cursor.getColumnIndex(Col1)));
                        task.setTask(cursor.getString(cursor.getColumnIndex(Col2)));
                        task.setStatus(cursor.getInt(cursor.getColumnIndex(Col3)));
                        modelList.add(task);

                    }while (cursor.moveToNext());

                }
            }
        }finally {
            db.endTransaction();
            assert cursor != null;
            cursor.close();
        }
        return modelList;
    }

}































