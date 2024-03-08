package com.robert.sci4202.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_data")
public class UserData {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "access_token")
    public String accessToken;

    @ColumnInfo(name = "refresh_token")
    public String refreshToken;

    @ColumnInfo(name = "user_name")
    public String userName;

    @ColumnInfo(name = "full_name")
    public String fullName;

    @ColumnInfo(name = "user_type")
    public String userType;


}
