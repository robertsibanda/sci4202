package com.robert.sci4202.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {UserData.class}, version = 3)
public abstract class UserDatabase extends RoomDatabase {

    public abstract UserDataDAO userDataDAO();

    public static UserDatabase INSTANCE;

    public static UserDatabase getINSTANCE(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, UserDatabase.class,
                            "ehr_database")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}
