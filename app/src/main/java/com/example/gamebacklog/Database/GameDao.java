package com.example.gamebacklog.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.gamebacklog.Model.Game;

import java.util.List;

@Dao
public interface GameDao {

    @Insert
    void insert(Game game);

    @Update
    void update(Game game);

    @Delete
    void delete(Game game);

    @Delete
    void delete(List<Game> games);

    @Query("SELECT * from gameBacklog_table")
    public LiveData<List<Game>> getAllGames();
}
