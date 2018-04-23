package com.tonyjstudio.cuahangdienthoaionline.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tonyjstudio.cuahangdienthoaionline.R;
import com.tonyjstudio.cuahangdienthoaionline.model.Product;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdapterLaptop extends BaseAdapter {
    Context context;
    ArrayList<Product> arrProduct;

    public AdapterLaptop(Context context, ArrayList<Product> arrProduct) {
        this.context = context;
        this.arrProduct = arrProduct;
    }

    @Override
    public int getCount() {
        return arrProduct.size();
    }

    @Override
    public Object getItem(int position) {
        return arrProduct.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        TextView txtLaptopName, txtLaptopPrice, txtLaptopDescribe;
        ImageView imgLaptopItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AdapterLaptop.ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new AdapterLaptop.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.laptop_item, null);
            viewHolder.imgLaptopItems = convertView.findViewById(R.id.imgLaptopItem);
            viewHolder.txtLaptopName = convertView.findViewById(R.id.txtLaptopItemName);
            viewHolder.txtLaptopPrice = convertView.findViewById(R.id.txtLaptopItemPrice);
            viewHolder.txtLaptopDescribe = convertView.findViewById(R.id.txtLaptopItemDescribe);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (AdapterLaptop.ViewHolder) convertView.getTag();
        }
        Product product = arrProduct.get(position);
        viewHolder.txtLaptopName.setText(product.getProductName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
        viewHolder.txtLaptopPrice.setText("Gía: " +  decimalFormat.format(product.getProductPrice()) + "đ");
        viewHolder.txtLaptopDescribe.setMaxLines(2);
        viewHolder.txtLaptopDescribe.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtLaptopDescribe.setText(product.getProductDescribe());
        Picasso.get().load(product.getProductImage())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(viewHolder.imgLaptopItems);
        return convertView;
    }
}
