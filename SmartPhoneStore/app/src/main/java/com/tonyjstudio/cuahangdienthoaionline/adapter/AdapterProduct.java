package com.tonyjstudio.cuahangdienthoaionline.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tonyjstudio.cuahangdienthoaionline.R;
import com.tonyjstudio.cuahangdienthoaionline.activity.DetailActivity;
import com.tonyjstudio.cuahangdienthoaionline.model.Product;
import com.tonyjstudio.cuahangdienthoaionline.utilities.CheckConnection;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ItemHolder> {
    Context context;
    ArrayList<Product> arrayListProduct;

    public AdapterProduct(Context context, ArrayList<Product> arrayListProduct) {
        this.context = context;
        this.arrayListProduct = arrayListProduct;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newest_product_item, null);
        ItemHolder itemHolder = new ItemHolder(view);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Product product = arrayListProduct.get(position);
        holder.txtTenSP.setText(product.getProductName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGiaSP.setText("Giá:" + decimalFormat.format(product.getProductPrice()) + "đ");
        Picasso.get().load(product.getProductImage())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(holder.productImage);
    }

    @Override
    public int getItemCount() {
        return arrayListProduct.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        public ImageView productImage;
        public TextView txtTenSP, txtGiaSP;

        public ItemHolder (View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.imgProduct);
            txtTenSP = itemView.findViewById(R.id.txtProductName);
            txtGiaSP = itemView.findViewById(R.id.txtProductPrice);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("product_info", arrayListProduct.get(getAdapterPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                     CheckConnection.showToast_Short(context, arrayListProduct.get(getAdapterPosition()).getProductName());
                    context.startActivity(intent);
                }
            });
        }
    }
}
