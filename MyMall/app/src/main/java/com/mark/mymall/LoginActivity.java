package com.mark.mymall;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mark.mymall.Model.Users;
import com.mark.mymall.Prevalent.Prevalent;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {
    private Button LogInButton;
    private EditText InputPassword,InputPhone;
    private ProgressDialog loading;
    private String parentDbName ="Users";
    private CheckBox mCheckBox;
    private TextView AdminPanelTextView,NotAdmenLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AdminPanelTextView=(TextView)findViewById(R.id.login_adminpanel_link);
        NotAdmenLink=(TextView)findViewById(R.id.login_notadminpanel_link);
       LogInButton=(Button)findViewById(R.id.login_login_btn);
        InputPassword=(EditText)findViewById(R.id.login_password_input);
        InputPhone=(EditText)findViewById(R.id.login_phone_number_input);
        mCheckBox=(CheckBox)findViewById(R.id.remember_me);
        Paper.init(this);
   loading=new ProgressDialog(this);
   LogInButton.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           LoginUser();
       }
   });
  AdminPanelTextView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          LogInButton.setText("Admin Login");
          AdminPanelTextView.setVisibility(View.INVISIBLE);
          NotAdmenLink.setVisibility(View.VISIBLE);
          parentDbName ="Admins" ;
      }
  });
NotAdmenLink.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        LogInButton.setText("User Login");
        AdminPanelTextView.setVisibility(View.VISIBLE);
        NotAdmenLink.setVisibility(View.INVISIBLE);
        parentDbName ="Users" ;
    }
});
    }

    private void LoginUser() {
        String password=InputPassword.getText().toString();
        String phone=InputPhone.getText().toString();
         if (TextUtils.isEmpty(password)){
            Toast.makeText(LoginActivity.this,"Please Write Your Password",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phone)){
            Toast.makeText(LoginActivity .this,"Please Write Your phone",Toast.LENGTH_SHORT).show();
        }
         else {loading.setTitle("Login Account");
             loading.setMessage("Please Wait...");
             loading.setCanceledOnTouchOutside(false);
             loading.show();



             AllowAccessToAccount(phone,password);
         }
    }

    private void AllowAccessToAccount(final String phone, final String password) {
       if (mCheckBox.isChecked()){Paper.book().write(Prevalent.UserPhoneKey,phone);
       Paper.book().write(Prevalent.UserPasswordKey,password);}
        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(parentDbName).child(phone).exists())
                {
                    Users users =dataSnapshot.child(parentDbName).child(phone).getValue(Users.class);
                    if (users.getPhone().equals(phone)){
                        if (users.getPassword().equals(password)){
                            if(parentDbName.equals("Admins")){ Toast.makeText(LoginActivity.this,"Welcome "+users.getName().toString()+".",Toast.LENGTH_SHORT).show();
                                loading.dismiss();
                                Intent HomeIntent =new Intent(LoginActivity.this,AdminCategoryActivity.class);
                                startActivity(HomeIntent);}else if (parentDbName.equals("Users")){ Toast.makeText(LoginActivity.this,"Welcome "+users.getName().toString()+".",Toast.LENGTH_SHORT).show();
                                loading.dismiss();
                                Intent HomeIntent =new Intent(LoginActivity.this,HomeActivity.class);
                                startActivity(HomeIntent);}

                    }else{Toast.makeText(LoginActivity.this,"wrong password",Toast.LENGTH_SHORT).show();
                        loading.dismiss();}
                    }


                }else {

                    Toast.makeText(LoginActivity.this,"Account with this "+phone+"number doesn`y exist ,please Create an account to login",Toast.LENGTH_SHORT).show();
                    Intent Registerintent =new Intent(LoginActivity.this,RegisterActivity.class);
                    startActivity(Registerintent);
                loading.dismiss();}
                }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
