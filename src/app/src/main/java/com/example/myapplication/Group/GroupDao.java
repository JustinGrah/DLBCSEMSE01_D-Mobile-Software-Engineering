package com.example.myapplication.Group;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface GroupDao {
    @Query("SELECT * FROM `Group`")
    List<Group> getAll();

    @Query("SELECT * FROM `Group` WHERE id = :id")
    Group getById(int id);

    @Query("SELECT * FROM `Group` WHERE code = :code")
    Group getByCode(String code);

    @Insert
    void createGroup(Group group);

    @Delete
    void deleteGroup(Group group);

    @Transaction
    @Query("SELECT * FROM `Group` WHERE id = :id")
    public List<GroupWithSessions> getAllGroupSessions(int id);
}
