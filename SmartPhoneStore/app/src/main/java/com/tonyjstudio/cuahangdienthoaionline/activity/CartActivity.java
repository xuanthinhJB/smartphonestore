package com.tonyjstudio.cuahangdienthoaionline.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.tonyjstudio.cuahangdienthoaionline.R;
import com.tonyjstudio.cuahangdienthoaionline.adapter.AdapterCart;
import com.tonyjstudio.cuahangdienthoaionline.model.Cart;
import com.tonyjstudio.cuahangdienthoaionline.utilities.CheckConnection;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    ListView lvCart;
    TextView txtClaim;
    static TextView txtSum;
    Button btnThanhToan, btnTiepTuc;
    Toolbar toolbarCart;
    AdapterCart adapterCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        addUI();
        actionToolbar();
        checkData();
        eventUtils();
        listViewItemListener();
        btnEvents();
    }

    public void addUI() {
        txtClaim = findViewById(R.id.txtClaim);
        txtSum = findViewById(R.id.txtCartValue);
        btnThanhToan = findViewById(R.id.btnThanhToan);
        btnTiepTuc = findViewById(R.id.btnContinueBuy);
        toolbarCart = findViewById(R.id.toolbarCart);

        lvCart = findViewById(R.id.lvCart);
        adapterCart = new AdapterCart(CartActivity.this, MainActivity.arrCart);
        lvCart.setAdapter(adapterCart);
    }

    public void actionToolbar(){
        setSupportActionBar(toolbarCart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarCart.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void checkData() {
        if (MainActivity.arrCart.size() <= 0) {
            adapterCart.notifyDataSetChanged();
            txtClaim.setVisibility(View.VISIBLE);
            lvCart.setVisibility(View.INVISIBLE);
        } else {
            adapterCart.notifyDataSetChanged();
            txtClaim.setVisibility(View.INVISIBLE);
            lvCart.setVisibility(View.VISIBLE);
        }
    }

    public static void eventUtils() {
        long tongTien = 0;
        for (int i = 0; i < MainActivity.arrCart.size(); i++) {
            tongTien += MainActivity.arrCart.get(i).getPriceSP();
        }

        DecimalFormat format = new DecimalFormat("###,###.###");
        txtSum.setText(format.format(tongTien) + "đ");
    }

    public void listViewItemListener() {
        lvCart.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                builder.setTitle("Xác nhận xóa sản phẩm ");
                builder.setMessage("Bạn có chắc muốn xóa sản phẩm ?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (MainActivity.arrCart.size() <= 0) {
                            txtClaim.setVisibility(View.VISIBLE);
                        } else {
                            MainActivity.arrCart.remove(position);
                            adapterCart.notifyDataSetChanged();
                            eventUtils();
                            if (MainActivity.arrCart.size() <= 0) {
                                txtClaim.setVisibility(View.VISIBLE);
                            } else {
                                txtClaim.setVisibility(View.INVISIBLE);
                                adapterCart.notifyDataSetChanged();
                                eventUtils();
                            }
                        }
                    }
                });

                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapterCart.notifyDataSetChanged();
                        eventUtils();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public void btnEvents() {
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.arrCart.size() > 0) {
                    Intent intent = new Intent(CartActivity.this, CustomerInfo.class);
                    startActivity(intent);
                } else {
                    CheckConnection.showToast_Short(getApplicationContext(), "Giỏ hàng của bạn rỗng");
                }
            }
        });

        btnTiepTuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
