package com.tonyjstudio.cuahangdienthoaionline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tonyjstudio.cuahangdienthoaionline.R;
import com.tonyjstudio.cuahangdienthoaionline.activity.CartActivity;
import com.tonyjstudio.cuahangdienthoaionline.activity.MainActivity;
import com.tonyjstudio.cuahangdienthoaionline.model.Cart;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdapterCart extends BaseAdapter {
    Context context;
    ArrayList<Cart> cartArrayList;

    public AdapterCart(Context context, ArrayList<Cart> cartArrayList) {
        this.context = context;
        this.cartArrayList = cartArrayList;
    }

    @Override
    public int getCount() {
        return cartArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return cartArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        ImageView imgCart;
        TextView txtCartName, txtCartValue;
        Button btnMinus, btnPlus, btnValue;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.cart_item, null);
            viewHolder.btnMinus = convertView.findViewById(R.id.btnMinus);
            viewHolder.btnPlus = convertView.findViewById(R.id.btnPlus);
            viewHolder.btnValue = convertView.findViewById(R.id.btnValues);
            viewHolder.txtCartName = convertView.findViewById(R.id.txtCartName);
            viewHolder.txtCartValue = convertView.findViewById(R.id.txtCartValue);
            viewHolder.imgCart = convertView.findViewById(R.id.imgCart);
            convertView.setTag(viewHolder);
        } else viewHolder = (ViewHolder) convertView.getTag();
        Cart cart = cartArrayList.get(position);
        viewHolder.txtCartName.setText(cart.getNameSP());
        DecimalFormat format = new DecimalFormat("###,###.###");
        viewHolder.txtCartValue.setText(format.format(cart.getPriceSP())  + "đ");
        Picasso.get().load(cart.getImageSP())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error).error(R.drawable.error)
                .into(viewHolder.imgCart);
        viewHolder.btnValue.setText(cart.getSlSP() + "");
        int amount = Integer.parseInt(viewHolder.btnValue.getText().toString());
        if (amount >= 10) {
            viewHolder.btnPlus.setVisibility(View.INVISIBLE);
            viewHolder.btnMinus.setVisibility(View.VISIBLE);
        } else if (amount <= 1) {
            viewHolder.btnPlus.setVisibility(View.VISIBLE);
            viewHolder.btnMinus.setVisibility(View.INVISIBLE);
        } else if (amount >= 1){
            viewHolder.btnPlus.setVisibility(View.VISIBLE);
            viewHolder.btnMinus.setVisibility(View.VISIBLE);
        }

        viewHolder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = MainActivity.arrCart.get(position).getSlSP() + 1;
                int slht = MainActivity.arrCart.get(position).getSlSP();
                long giaht = MainActivity.arrCart.get(position).getPriceSP();
                MainActivity.arrCart.get(position).setSlSP(slmoinhat);
                long giamoinhat = (giaht * slmoinhat / slht);
                MainActivity.arrCart.get(position).setPriceSP(giamoinhat);
                DecimalFormat format = new DecimalFormat("###,###.###");
                viewHolder.txtCartValue.setText(format.format(giamoinhat)  + "đ");
                CartActivity.eventUtils();
                if (slmoinhat > 9) {
                    viewHolder.btnPlus.setVisibility(View.INVISIBLE);
                    viewHolder.btnMinus.setVisibility(View.VISIBLE);
                    viewHolder.btnValue.setText(String.valueOf(slmoinhat));
                } else {
                    viewHolder.btnPlus.setVisibility(View.VISIBLE);
                    viewHolder.btnMinus.setVisibility(View.VISIBLE);
                    viewHolder.btnValue.setText(String.valueOf(slmoinhat));
                }
            }
        });

        viewHolder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = MainActivity.arrCart.get(position).getSlSP() - 1;
                int slht = MainActivity.arrCart.get(position).getSlSP();
                long giaht = MainActivity.arrCart.get(position).getPriceSP();
                MainActivity.arrCart.get(position).setSlSP(slmoinhat);
                long giamoinhat = (giaht * slmoinhat / slht);
                MainActivity.arrCart.get(position).setPriceSP(giamoinhat);
                DecimalFormat format = new DecimalFormat("###,###.###");
                viewHolder.txtCartValue.setText(format.format(giamoinhat)  + "đ");
                CartActivity.eventUtils();
                if (slmoinhat < 2) {
                    viewHolder.btnPlus.setVisibility(View.VISIBLE);
                    viewHolder.btnMinus.setVisibility(View.INVISIBLE);
                    viewHolder.btnValue.setText(String.valueOf(slmoinhat));
                } else {
                    viewHolder.btnPlus.setVisibility(View.VISIBLE);
                    viewHolder.btnMinus.setVisibility(View.VISIBLE);
                    viewHolder.btnValue.setText(String.valueOf(slmoinhat));
                }
            }
        });
        return convertView;
    }
}
