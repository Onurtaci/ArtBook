package com.onurtaci.artbook.roomdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.onurtaci.artbook.model.Art;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface ArtDao {
    @Query("SELECT * FROM Art")
    Flowable<List<Art>> getAll();
    @Insert
    Completable insert(Art art);
    @Delete
    Completable delete(Art art);
}
