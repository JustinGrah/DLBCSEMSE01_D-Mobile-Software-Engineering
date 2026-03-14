package com.example.myapplication.Session;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Relation;

import com.example.myapplication.Rating.Rating;

import java.util.List;

import kotlin.ParameterName;

@Entity
public class SessionWithRatings {
    @Embedded Session session;
    @Relation(
            parentColumn = "id",
            entityColumn = "session_id"
    )
    public List<Rating> ratings;
}
