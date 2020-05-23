package com.mark.mymall;

import android.app.ProgressDialog;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
private Button CreateAccountButton;
private EditText InputName,InputPhoneNumber,InputPassword;
private ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        CreateAccountButton=(Button)findViewById(R.id.register_login_btn);
        InputName=(EditText)findViewById(R.id.register_name_number_input);
        InputPhoneNumber=(EditText)findViewById(R.id.register_phone_number_input);
        InputPassword=(EditText)findViewById(R.id.register_password_input);
        loading=new ProgressDialog(this);
        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccount();
            }
        });
    }

    private void CreateAccount() {
String name =InputName.getText().toString();
String password=InputPassword.getText().toString();
String phone=InputPhoneNumber.getText().toString();
if (TextUtils.isEmpty(name)){
    Toast.makeText(RegisterActivity.this,"Please Write Your Name",Toast.LENGTH_SHORT).show();
}

      else if (TextUtils.isEmpty(password)){
            Toast.makeText(RegisterActivity.this,"Please Write Your Password",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phone)){
            Toast.makeText(RegisterActivity.this,"Please Write Your Password",Toast.LENGTH_SHORT).show();
        }
else {loading.setTitle("Create Account");
loading.setMessage("Please Wait");
loading.setCanceledOnTouchOutside(false);
loading.show();
ValidatePhoneNumber(name,phone,password);
}
    }

    private void ValidatePhoneNumber(final String name, final String phone, final String password) {
        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();
RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        if (!(dataSnapshot.child("Users").child(phone).exists())){
            HashMap<String,Object> userdatamap=new HashMap<>();
            userdatamap.put("phone",phone);
            userdatamap.put("name",name);
            userdatamap.put("password",password);
            RootRef.child("Users").child(phone).updateChildren(userdatamap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
if (task.isSuccessful()){Toast.makeText(RegisterActivity.this,"your account has been created",Toast.LENGTH_SHORT).show();
loading.dismiss();
    Intent loginintent =new Intent(RegisterActivity.this,LoginActivity.class);
    startActivity(loginintent);}else {Toast.makeText(RegisterActivity.this,"Network is very bad",Toast.LENGTH_SHORT).show();}
                }
            });
        }
        else {Toast.makeText(RegisterActivity.this,"This "+phone+"Is already registered please try another number",Toast.LENGTH_SHORT).show();
        loading.dismiss();
            Intent Mainintent =new Intent(RegisterActivity.this,MainActivity.class);
            startActivity(Mainintent);}
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});
    }
}
