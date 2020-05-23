package com.mark.mymall;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mark.mymall.Model.Users;
import com.mark.mymall.Prevalent.Prevalent;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
private Button JoinNowbtn,LogInbtn;
private ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JoinNowbtn =(Button)findViewById(R.id.main_signup_btn);
        LogInbtn=(Button)findViewById(R.id.main_login_btn);
        loading=new ProgressDialog(this);
        Paper.init(this);
        LogInbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginintent =new Intent(MainActivity.this,LoginActivity.class);
                startActivity(loginintent);
            }
        });
        JoinNowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Regesterintent =new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(Regesterintent);
            }
        });
   String UserPhoneKey = Paper.book().read(Prevalent.UserPhoneKey);
   String UserpasswordKey =Paper.book().read(Prevalent.UserPasswordKey);

    if (UserPhoneKey != "" && UserpasswordKey != "" ){

        if (!TextUtils.isEmpty(UserPhoneKey)&&!TextUtils.isEmpty(UserpasswordKey) ){
            AllowAccess(UserPhoneKey,UserpasswordKey);
            loading.setTitle("Already logged In with Account");
            loading.setMessage("Please Wait...");
            loading.setCanceledOnTouchOutside(false);
            loading.show();

        }
    }

    }

    private void AllowAccess(final String phone, final String password) {
        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Users").child(phone).exists())
                {
                    Users users =dataSnapshot.child("Users").child(phone).getValue(Users.class);
                    if (users.getPhone().equals(phone)){
                        if (users.getPassword().equals(password)){
                            Toast.makeText(MainActivity.this,"Welcome "+users.getName().toString()+".",Toast.LENGTH_SHORT).show();
                            loading.dismiss();
                            Intent HomeIntent =new Intent(MainActivity.this,HomeActivity.class);
                            startActivity(HomeIntent);
                        }else{Toast.makeText(MainActivity.this,"wrong password",Toast.LENGTH_SHORT).show();
                            loading.dismiss();}
                    }


                }else {

                    Toast.makeText(MainActivity.this,"Account with this "+phone+"number doesn`y exist ,please Create an account to login",Toast.LENGTH_SHORT).show();
                    Intent Registerintent =new Intent(MainActivity.this,RegisterActivity.class);
                    startActivity(Registerintent);
                    loading.dismiss();}
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    }
