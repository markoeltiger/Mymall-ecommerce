package com.mark.mymall.ViewHolder;

import androidx.annotation.NonNull;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mark.mymall.Interface.itemClickListner;
import com.mark.mymall.R;

public class ProductViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtProductName,txtProductDesc,txtProductPrice;
    public ImageView imageView;
    public itemClickListner listner;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
    imageView=(ImageView)itemView.findViewById(R.id.Product_Image);
    txtProductName=(TextView) itemView.findViewById(R.id.Product_Name);
    txtProductDesc=(TextView) itemView.findViewById(R.id.Product_Descreption);
    txtProductPrice=(TextView) itemView.findViewById(R.id.Product_Price);
    }
public void setitemClickListner(itemClickListner listner){
        this.listner=listner;
}
    @Override
    public void onClick(View view) {
listner.onClick(view,getAdapterPosition(),false);
    }
}
