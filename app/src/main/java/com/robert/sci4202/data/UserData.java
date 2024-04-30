package com.robert.sci4202.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_data")
public class UserData {

    // personal account details
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "user_id")
    public String userID;

    @ColumnInfo(name = "password")
    public String password;

    @ColumnInfo(name = "user_name")
    public String userName;

    @ColumnInfo(name = "contact")
    public String contact;

    @ColumnInfo(name = "full_name")
    public String fullName;

    @ColumnInfo(name = "gender")
    public String gender;

    @ColumnInfo(name = "access_token")
    public String accessToken;

    @ColumnInfo(name = "user_type")
    public String userType;

    @ColumnInfo(name = "private_key")
    public String privateKey;

    @ColumnInfo(name = "public_key_modulus")
    public String publicKeyModulus;

    @ColumnInfo(name = "public_key_exponent")
    public String publicKeyExponent;

    @ColumnInfo(name = "private_key_modulus")
    public String privateKeyModulus;

    @ColumnInfo(name = "private_key_exponent")
    public String privateKeyExponent;

    @ColumnInfo(name = "public_key")
    public String publicKey;

    //details for the user if he/she is a doctor
    @ColumnInfo(name = "organisation")
    public String organisation;

    @ColumnInfo(name = "occupation")
    public String occupation;


    @ColumnInfo(name = "ignore_login")
    public boolean ingoreLogin;
}
