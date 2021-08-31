package com.example.ecommerce.repository;

import android.content.Context;
import android.os.AsyncTask;


import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.ecommerce.db.NoteDatabase;
import com.example.ecommerce.model.Note;
import com.example.ecommerce.utility.AppUtils;

import java.util.List;

public class NoteRepository {

    private String DB_NAME = "db_task";

    private NoteDatabase noteDatabase;
    public NoteRepository(Context context) {
        noteDatabase = Room.databaseBuilder(context, NoteDatabase.class, DB_NAME).build();
    }

    public void insertTask(Integer id, String title, String description, String price, String special,String image, String count) {

        Note note = new Note();
        note.setPosition(id);
        note.setTitle(title);
        note.setDescription(description);
        note.setPrice(price);
        note.setSpecial(special);
        note.setImage(image);
        note.setCount(count);
        note.setCreatedAt(AppUtils.getCurrentDateTime());
        insertTask(note);
    }

    public void insertTask(final Note note) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                noteDatabase.daoAccess().insertTask(note);
                return null;
            }
        }.execute();
    }


    public void deleteById(final int id) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                noteDatabase.daoAccess().deleteById(id);
                return null;
            }
        }.execute();
    }

    public void deleteall() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                noteDatabase.daoAccess().nukeTable();
                return null;
            }
        }.execute();
    }

    public void updateTask(final Note note) {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                noteDatabase.daoAccess().updateTask(note);
                return null;
            }
        }.execute();
    }

    public void update(final Integer count, int id) {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                noteDatabase.daoAccess().update(count,id);
                return null;
            }
        }.execute();
    }



    public void deleteTask(final Note note) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                noteDatabase.daoAccess().deleteTask(note);
                return null;
            }
        }.execute();
    }


    public LiveData<List<Note>> getTasks() {
        return noteDatabase.daoAccess().fetchAllTasks();
    }
}
