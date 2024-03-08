package com.robert.sci4202.data;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserDataDAO {

    @Insert
    void addUserData(UserData userData);

    @Update
    void updateUserData(UserData userData);

    @Delete
    void deleteUserData(UserData userData);


    @Query("SELECT * FROM user_data")
    List<UserData> getAllUserData();
    @Query("SELECT * from user_data where user_name==:username")
    UserData getUserData(String username);
}
