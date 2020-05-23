package com.mark.mymall;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdminCategoryActivity extends AppCompatActivity {
private ImageView tshirts,sportstshirts,femaledresses,sweater;
private ImageView Glasses,hatscaps,walletsbags,shoes;
private ImageView headphones,labtops,watches,moblies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);
        tshirts=(ImageView)findViewById(R.id.t_shirts);
        sportstshirts=(ImageView)findViewById(R.id.sports);
        femaledresses=(ImageView)findViewById(R.id.female_dresses);
        sweater=(ImageView)findViewById(R.id.sweather);

        Glasses=(ImageView)findViewById(R.id.glasses);
        hatscaps=(ImageView)findViewById(R.id.hats);
        walletsbags=(ImageView)findViewById(R.id.purses_bags);
        shoes=(ImageView)findViewById(R.id.shoesss);

        headphones=(ImageView)findViewById(R.id.headphones);
        labtops=(ImageView)findViewById(R.id.laptops);
        watches=(ImageView)findViewById(R.id.watches);
        moblies=(ImageView)findViewById(R.id.mobiles);


tshirts.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(AdminCategoryActivity.this,AddNewProductActivity.class);
        intent.putExtra("category","tShirts");
        startActivity(intent);

    }
});
sportstshirts.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(AdminCategoryActivity.this,AddNewProductActivity.class);
        intent.putExtra("category","Sports tShirts");
        startActivity(intent);

    }
});
femaledresses.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(AdminCategoryActivity.this,AddNewProductActivity.class);
        intent.putExtra("category","Female Dresses");
        startActivity(intent);

    }
});
sweater.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(AdminCategoryActivity.this,AddNewProductActivity.class);
        intent.putExtra("category","Sweater");
        startActivity(intent);

    }
});

Glasses.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(AdminCategoryActivity.this,AddNewProductActivity.class);
        intent.putExtra("category","Glasses");
        startActivity(intent);

    }
});
hatscaps.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(AdminCategoryActivity.this,AddNewProductActivity.class);
        intent.putExtra("category","HatsCaps");
        startActivity(intent);

    }
});
walletsbags.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(AdminCategoryActivity.this,AddNewProductActivity.class);
        intent.putExtra("category","Wallets Bags");
        startActivity(intent);

    }
});
shoes.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(AdminCategoryActivity.this,AddNewProductActivity.class);
        intent.putExtra("category","Shoes");
        startActivity(intent);

    }
});
headphones.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(AdminCategoryActivity.this,AddNewProductActivity.class);
        intent.putExtra("category","Head phones");
        startActivity(intent);

    }
});
labtops.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(AdminCategoryActivity.this,AddNewProductActivity.class);
        intent.putExtra("category","Labtops");
        startActivity(intent);

    }
});
watches.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        Intent intent=new Intent(AdminCategoryActivity.this,AddNewProductActivity.class);
        intent.putExtra("category","Watches");
        startActivity(intent);
    }
});
moblies.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(AdminCategoryActivity.this,AddNewProductActivity.class);
        intent.putExtra("category","Mobiles");
        startActivity(intent);
    }
});
    }
}
