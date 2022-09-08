package com.exam.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import ObjectClass.Account;

public class RegisterPage extends AppCompatActivity {

    private Button LoginBut, submitBut;
    private EditText name, email, password, passwordConfirm;
    private View[] views;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private int Counter;
    private int time;

    Thread timeCount = new Thread() {
        @Override
        public void run() {
            for (time = 0; time < 2; time++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            time = 0;
            Counter = 0;
        }


    };

    void validateFinish() {

        if (timeCount.isAlive()) {
            if (Counter >= 3) {
                System.out.println("masuk ke finish");
                finish();
            } else if (Counter < 3) {
                Toast.makeText(this, String.format("Press %d times again for exit", (3 - Counter)),Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        Counter++;

        if (Counter == 1) {
            timeCount.start();
        }

        validateFinish();



    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register_page);
        LoginBut = findViewById(R.id.LoginFromRegister);
        LoginBut.setAllCaps(false);
        name = findViewById(R.id.fullNameTextField);
        submitBut = findViewById(R.id.submit);
        email = findViewById(R.id.emailField);

        password = findViewById(R.id.passwordField);
        passwordConfirm = findViewById(R.id.confirmPasswordField);
        views = new View[]{name, submitBut, email, password, passwordConfirm};

//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//
//        Map<String, Object> Elvin = new HashMap<>();
//        Elvin.put("first", "Alan");
//        Elvin.put("middle", "Mathison");
//        Elvin.put("last", "Turing");
//        Elvin.put("born", 1912);

//// Add a new document with a generated ID
////        db.collection("users")
////                .add(Elvin)
////                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
////                    @Override
////                    public void onSuccess(DocumentReference documentReference) {
////                        Log.d("Success", "DocumentSnapshot added with ID: " + documentReference.getId());
////                    }
////                })
////                .addOnFailureListener(new OnFailureListener() {
////                    @Override
////                    public void onFailure(@NonNull Exception e) {
////                        Log.w("Failed", "Error adding document", e);
////                    }
////                });

//        db.collection("users")
//                .document("US001")
//                .set(new Account("elvin", "Elvin.sestomi@binus.ac.id", "Elvin123"))
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void unused) {
//                                Log.d("Success", "Success");
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w("unsuccessfull", "Error",  e);
//                    }
//                });

        addOnclickLIstener();


    }

    private void addOnclickLIstener() {

        LoginBut.setOnClickListener(view -> {
            Intent LoginPage = new Intent(RegisterPage.this, loginActivity.class);
            startActivity(LoginPage);
            this.finish();
        });

        submitBut.setOnClickListener( e -> {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            System.out.println("Submit Button has been Clicked");
            String Fullname = name.getText().toString();
            String Email = email.getText().toString();
            String Password = (password.getText().toString().equals(passwordConfirm.getText().toString())) ? password.getText().toString() : null;

            if(!Fullname.isEmpty() && !Email.isEmpty() && !Password.isEmpty() && emailIsValid(Email) && namaUserValid(Fullname)) {
                Log.d("Masuk di sini", "addOnclickLIstener: ");
                mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Log.d("Success create", "onComplete: ");
                        } else {
                            Log.w("ERROR create", "onComplete: ", task.getException());
                        }
                    }
                });
                Account acc = new Account(Fullname,Email,Password);
//                HashMap<String, String> userdata = new HashMap<>();
//
//                userdata.put("Full Name", Fullname);
//                userdata.put("Email", Email);
//                userdata.put("Password", Password);

                db.collection("users").document(Email).set(acc).addOnSuccessListener(s -> {
                    System.out.println("Your Name is " + Fullname + ". Your Account has been successfully been Created in " + acc.getDateCreated());
                    String Format = String.format("Your name is %s, your account has been created on %s", acc.getName(), acc.getDateCreated());
                    System.out.println(Format);
                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).setTitle("Congratulation").setMessage("Your Account has been Successfully Created");
                    AlertDialog alt = alertDialog.create();
                    alt.show();

                    Button but1 = alt.getButton(AlertDialog.BUTTON_POSITIVE);
                    but1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent loginPage = new Intent(RegisterPage.this, loginActivity.class);
                            startActivity(loginPage);
                            alt.dismiss();
                            finish();
                        }
                    });
                    LinearLayout.LayoutParams but1Layout = (LinearLayout.LayoutParams) but1.getLayoutParams();
                    but1Layout.width = LinearLayout.LayoutParams.MATCH_PARENT;
                    but1Layout.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    but1Layout.gravity = Gravity.CENTER;
                    alt.getButton(AlertDialog.BUTTON_POSITIVE).setLayoutParams(but1Layout);
                }).addOnFailureListener(f -> {
                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).setMessage("Your Account aren't Created Yet please Check your data").setTitle("ERROR");

                    AlertDialog alt = alertDialog.create();
                    alt.show();

                    Button but1 = alt.getButton(AlertDialog.BUTTON_POSITIVE);
                    LinearLayout.LayoutParams butLayout = (LinearLayout.LayoutParams)but1.getLayoutParams();
                    butLayout.width = LinearLayout.LayoutParams.MATCH_PARENT;
                    butLayout.gravity = Gravity.CENTER;
                    alt.getButton(AlertDialog.BUTTON_POSITIVE).setLayoutParams(butLayout);
                });
            } else {
                for (View view: views
                ) {
                    if (view instanceof EditText) {
                        EditText et = (EditText) view;
                        if (et.getText().toString().isEmpty())
                            et.setError("Please Fill out this Field,\nThis field Is mandatory");
                    }
                }
            }
        });
    }


    private boolean emailIsValid(String Email) {
        boolean v = true;

        if (!Email.contains("@")) {
            v = false;
            return v;
        }

        if (!Email.endsWith(".com")) {
            v = false;
            return v;
        }

        return v;
    }

    private boolean namaUserValid(String Name) {
        boolean v = true;

        if(Name.length() < 5) {
            v = false;
            return v;
        }
        return v;
    }


}