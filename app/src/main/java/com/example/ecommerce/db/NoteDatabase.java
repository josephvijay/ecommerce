package com.example.ecommerce.db;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.ecommerce.dao.DaoAccess;
import com.example.ecommerce.model.Note;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {

    public abstract DaoAccess daoAccess();
}
