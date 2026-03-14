package com.example.myapplication.User;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM User")
    List<User> getAll();

    @Query("SELECT * FROM User WHERE id = :id")
    User getUserById(int id);

    @Query("SELECT * FROM User WHERE name = :name")
    User getUserByName(String name);

    @Query("SELECT * FROM User WHERE id IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Insert
    void createUser(User user);

    @Delete
    void delete(User user);
}
