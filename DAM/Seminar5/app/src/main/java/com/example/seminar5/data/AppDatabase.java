package com.example.seminar5.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Profil.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DB_NAME = "profile.db";

    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
      if (instance == null) {
          instance = Room.databaseBuilder(context, AppDatabase.class, DB_NAME)
                  // .allowMainThreadQueries() // testare rapida a codului, nou fir de executie ig (Executor)
                  .fallbackToDestructiveMigration()
                  .build();
      }
      return instance;
    }

    public abstract ProfilDao getProfilDao();
}
