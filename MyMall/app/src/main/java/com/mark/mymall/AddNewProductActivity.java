package com.mark.mymall;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AddNewProductActivity extends AppCompatActivity {
private String categoryName ,name,descreption,price,savecurrentdate,savecurrenttime;
private ImageView ChooseImage;
private Button AddProduct;
private ProgressDialog loading;
private EditText InputProductName,InputProductDescreption,InputProductPrice;
private static final int GalleryPick=1;
private Uri Imageuri;
private String productrandomkey,DownloadImageUrl;
private StorageReference ProductImageRef;
private DatabaseReference Productref;

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_product);
        ProductImageRef= FirebaseStorage.getInstance().getReference().child("Product Images");
        Productref=FirebaseDatabase.getInstance().getReference().child("Products");
        ChooseImage=(ImageView)findViewById(R.id.select_product_image);
        loading=new ProgressDialog(this);
        AddProduct=(Button)findViewById(R.id.add_new_Product);
        InputProductName=(EditText)findViewById(R.id.product_name);
        InputProductDescreption=(EditText)findViewById(R.id.product_descreption);
        InputProductPrice=(EditText)findViewById(R.id.product_price);
categoryName=getIntent().getExtras().get("category").toString();
Toast.makeText(AddNewProductActivity.this,"the category is "+categoryName,Toast.LENGTH_SHORT).show();
ChooseImage.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        OpenGallery();
    }
});
AddProduct.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        ValidateProductData();


    }
});
    }

    private void ValidateProductData() {
    name=InputProductName.getText().toString();
    descreption=InputProductDescreption.getText().toString();
    price=InputProductPrice.getText().toString();
    if (Imageuri==null){Toast.makeText(AddNewProductActivity.this,"Product Image is manadtory...",Toast.LENGTH_SHORT).show();

    }else if (TextUtils.isEmpty(name)||TextUtils.isEmpty(descreption)||TextUtils.isEmpty(price)){Toast.makeText(AddNewProductActivity.this,"Please Complete All Information About the product",Toast.LENGTH_SHORT).show();}
    else {StoreProductInformation();}
    }

    private void StoreProductInformation() {
        loading.setTitle("Uploading Product");
        loading.setMessage("Please Wait...");
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        Calendar calendar =Calendar.getInstance();
        SimpleDateFormat currentdate=new SimpleDateFormat("MMM dd, yyyy");
        savecurrentdate=currentdate.format(calendar.getTime());
        SimpleDateFormat currenttime=new SimpleDateFormat("HH:mm:ss  a");
        savecurrenttime=currenttime.format(calendar.getTime());
        productrandomkey = savecurrentdate+  savecurrenttime ;
        final StorageReference filepath =ProductImageRef.child(Imageuri.getLastPathSegment()+productrandomkey+".jpg");
        final UploadTask uploadTask =filepath.putFile(Imageuri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message =e.toString();
                Toast.makeText(AddNewProductActivity.this,message,Toast.LENGTH_LONG).show();
                loading.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
           Toast.makeText(AddNewProductActivity.this,"The Product image Hasbeen Uploaded",Toast.LENGTH_SHORT).show();
                Task<Uri> urltask =uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
if (!task.isSuccessful()){
    throw  task.getException();

}
DownloadImageUrl=filepath.getDownloadUrl().toString();
return filepath.getDownloadUrl();

                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
if (task.isSuccessful()){

    Toast.makeText(AddNewProductActivity.this,"The Product image saved successfuly",Toast.LENGTH_SHORT).show();
    loading.dismiss();
       SaveProductInfoToDatabase();
loading.dismiss();
}
                    }
                });


            }
        });


}

    private void SaveProductInfoToDatabase() {
        HashMap<String,Object> ProductMap =new HashMap<>();
        ProductMap.put("pid",productrandomkey);
        ProductMap.put("date",savecurrentdate);
        ProductMap.put("time",savecurrenttime);
        ProductMap.put("name",name);
        ProductMap.put("descrption",descreption);
        ProductMap.put("image",DownloadImageUrl);
        ProductMap.put("price",price);
        ProductMap.put("category",categoryName);
        Productref.child(productrandomkey).updateChildren(ProductMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
           if (task.isSuccessful()){Toast.makeText(AddNewProductActivity.this,"Done",Toast.LENGTH_SHORT).show();}
           else {
               String message=task.getException().toString();
               Toast.makeText(AddNewProductActivity.this,"Error"+message,Toast.LENGTH_SHORT).show();
           }


            }
        });
    }

    private void OpenGallery() {
        Intent GalleryIntent=new Intent();
        GalleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        GalleryIntent.setType("image/*");
        startActivityForResult(GalleryIntent,GalleryPick);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==GalleryPick&&resultCode==RESULT_OK &&  data!=null){
            Imageuri=data.getData();
            ChooseImage.setImageURI(Imageuri);


        }
    }
}
