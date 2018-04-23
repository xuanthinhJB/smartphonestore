package com.tonyjstudio.cuahangdienthoaionline.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

public class AdapterPhone extends BaseAdapter {
    Context context;
    ArrayList<Product> arrProduct;

    public AdapterPhone(Context context, ArrayList<Product> arrProduct) {
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
        TextView txtPhoneName, txtPhonePrice, txtPhoneDescribe;
        ImageView imgPhoneItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.phone_item, null);
            viewHolder.imgPhoneItems = convertView.findViewById(R.id.imgPhoneItem);
            viewHolder.txtPhoneName = convertView.findViewById(R.id.txtPhoneItemName);
            viewHolder.txtPhonePrice = convertView.findViewById(R.id.txtPhoneItemPrice);
            viewHolder.txtPhoneDescribe = convertView.findViewById(R.id.txtPhoneItemDescribe);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Product product = arrProduct.get(position);
        viewHolder.txtPhoneName.setText(product.getProductName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
        viewHolder.txtPhonePrice.setText("Gía: " +  decimalFormat.format(product.getProductPrice()) + "đ");
        viewHolder.txtPhoneDescribe.setMaxLines(2);
        viewHolder.txtPhoneDescribe.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtPhoneDescribe.setText(product.getProductDescribe());
        Picasso.get().load(product.getProductImage())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(viewHolder.imgPhoneItems);
        return convertView;
    }
}
