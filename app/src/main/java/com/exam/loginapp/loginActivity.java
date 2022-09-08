package com.exam.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

import ObjectClass.Account;

public class loginActivity extends AppCompatActivity {


    private Button RegisterBut;
    private EditText email, password;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.emailFieldLogin);
        password = findViewById(R.id.passwordFieldLogin);
        Button login = findViewById(R.id.Loginbutton);
        RegisterBut = findViewById(R.id.RegisterBut);
        RegisterBut.setOnClickListener(view -> {
            Intent Register = new Intent(loginActivity.this, RegisterPage.class);
            startActivity(Register);
            this.finish();
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Email = email.getText().toString();
                String Password = password.getText().toString();

                Log.d("Emailel", "onClick: " + Email);
                Log.d("Password", "onClick: " + Password);
                mAuthAuth(Email, Password);


            }
        });
    }


    public void mAuthAuth(String Email, String Password) {

        mAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            SharedPreferences saveLoginData = loginActivity.this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = saveLoginData.edit();
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("MAsuk", "onComplete: masuk di success");
                    DocumentReference docref= db.collection("users").document(Email);
                    docref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Log.d("masuk di addnameUser", "onSuccess: ");
                            Account acc = documentSnapshot.toObject(Account.class);
//                SharedPreferences.Editor editor = savedData.edit();

                            editor.putString("name", acc.getName());
                            editor.putString("email", Email);
                            editor.apply();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("Msuk failure", "onFailure: ");
                            Log.w("Errror", "onFailure: ", e);
                        }
                    });






                    Intent home = new Intent(loginActivity.this, mainHome.class);
                    startActivity(home);
                    finish();
                } else {
                    Log.w("Error", "onComplete: ", task.getException());
                }
            }
        });
    }

    private void addNameUser(String Doc, SharedPreferences.Editor editor) {

    }
}