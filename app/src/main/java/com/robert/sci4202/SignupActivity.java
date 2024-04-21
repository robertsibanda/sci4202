package com.robert.sci4202;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.robert.sci4202.data.UserData;
import com.robert.sci4202.data.UserDatabase;
import com.robert.sci4202.security.Keys;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Security.addProvider(new BouncyCastleProvider());


        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars =
                    insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top,
                    systemBars.right, systemBars.bottom);
            return insets;
        });
        UserDatabase userDatabase =
                UserDatabase.getINSTANCE(this.getApplicationContext());

        userDatabase.userDataDAO().deleteAll();

        findViewById(R.id.btnNext).setOnClickListener(l -> {
            Keys keys = new Keys();
            try {
                keys.generateKeys();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            PublicKey publicKey = keys.publicKey;
            PrivateKey privateKey = keys.privateKey;

            String pem = Keys.convertPEM(publicKey);

            System.out.println("Public key : " + publicKey);
            System.out.println("Created public key : " + pem);

            EditText etUsername, etPassword, etFullName, etContact;
            etUsername = findViewById(R.id.etUsername);
            etPassword = findViewById(R.id.etPassword);
            etFullName = findViewById(R.id.etFullName);
            etContact = findViewById(R.id.etEmail);


            try {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String fullname = etFullName.getText().toString();
                String contact = etContact.getText().toString();

                UserData userData = new UserData();
                userData.userName = username;
                userData.fullName = fullname;
                userData.password = password;
                userData.contact = contact;
                userData.privateKey = Keys.convertPEM(privateKey);
                userData.publicKey = Keys.convertPEM(publicKey);

                RSAPublicKey pubkey = (RSAPublicKey) publicKey;
                userData.publicKeyExponent =
                        String.valueOf(pubkey.getPublicExponent());
                userData.publicKeyModulus =
                        String.valueOf(pubkey.getModulus());

                RSAPrivateKey privkey = (RSAPrivateKey) privateKey;
                userData.privateKeyExponent =
                        String.valueOf(privkey.getPrivateExponent());
                userData.privateKeyModulus =
                        String.valueOf(privkey.getModulus());

                userDatabase.userDataDAO().addUserData(userData);
                startActivity(new Intent(this,
                        SignupCompleteActivity.class));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        });

    }
}