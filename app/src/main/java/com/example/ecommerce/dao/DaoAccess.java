package com.example.ecommerce.dao;



import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ecommerce.model.Note;

import java.util.List;

@Dao
public interface DaoAccess {

    @Insert
    Long insertTask(Note note);


    @Query("SELECT * FROM Note ORDER BY created_at desc")
    LiveData<List<Note>> fetchAllTasks();


    @Query("SELECT * FROM Note WHERE id =:taskId")
    LiveData<Note> getTask(int taskId);


    @Update
    void updateTask(Note note);


    @Query("UPDATE Note SET count=:count WHERE position = :position")
    void update(Integer count, int position);



    @Delete
    void deleteTask(Note note);

    @Query("DELETE FROM Note WHERE position = :position")
    void deleteById(int position);


    @Query("DELETE FROM Note")
     void nukeTable();


}
