package com.tonyjstudio.cuahangdienthoaionline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tonyjstudio.cuahangdienthoaionline.R;
import com.tonyjstudio.cuahangdienthoaionline.model.KindOfProduct;

import java.util.ArrayList;

public class AdapterKindOfProduct extends BaseAdapter {
    ArrayList<KindOfProduct> kindOfProductArrayList;
    Context context;

    public AdapterKindOfProduct(ArrayList<KindOfProduct> kindOfProductArrayList, Context context) {
        this.kindOfProductArrayList = kindOfProductArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return kindOfProductArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return kindOfProductArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        TextView txtTyptProductName;
        ImageView imgTypeProduct;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.kind_product_item, null);
            viewHolder.txtTyptProductName = convertView.findViewById(R.id.textviewTypeProduct);
            viewHolder.imgTypeProduct = convertView.findViewById(R.id.imgTypeProduct);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        KindOfProduct productType = kindOfProductArrayList.get(position);
        viewHolder.txtTyptProductName.setText(productType.getKindName());
        Picasso.get().load(productType.getKindImage())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(viewHolder.imgTypeProduct);
        return convertView;
    }
}
