package com.example.seminar5.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProfilDao {
    @Insert
    void insert(Profil profil);
    @Query("select * from profile order by email")
    List<Profil> getAll();
    @Delete
    void delete(Profil profil);
}
