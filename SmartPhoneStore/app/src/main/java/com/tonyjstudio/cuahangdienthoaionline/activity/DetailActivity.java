package com.tonyjstudio.cuahangdienthoaionline.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tonyjstudio.cuahangdienthoaionline.R;
import com.tonyjstudio.cuahangdienthoaionline.model.Cart;
import com.tonyjstudio.cuahangdienthoaionline.model.Product;

import java.text.DecimalFormat;

public class DetailActivity extends AppCompatActivity {

    Toolbar toolbarDetail;
    ImageView imgDetail;
    TextView txtDetailName, txtDetailPrice, txtDetailDescribe;
    Spinner spnAmount;
    Button btnAddToCart;

    int id = 0;
    String detailName = "";
    int detailPrice = 0;
    String detailImage = "";
    String detailDescribe = "";
    int idProduct = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        addUI();
        actionToolbar();
        getInfo();
        catchEventSpinner();
        eventButtonClick();
    }

    public void addUI() {
        toolbarDetail = findViewById(R.id.toolbarDetailProduct);
        imgDetail = findViewById(R.id.imgDetail);
        txtDetailName = findViewById(R.id.txtDetailProductName);
        txtDetailPrice = findViewById(R.id.txtDetailProductPrice);
        txtDetailDescribe = findViewById(R.id.txtDescribeDetailProduct);
        spnAmount = findViewById(R.id.spnAmount);
        btnAddToCart = findViewById(R.id.btnAddToCart);
    }

    public void actionToolbar() {
        setSupportActionBar(toolbarDetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarDetail.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void getInfo() {


        Product product = (Product) getIntent().getSerializableExtra("product_info");
        id = product.getId();
        detailName = product.getProductName();
        detailPrice = product.getProductPrice();
        detailImage = product.getProductImage();
        detailDescribe = product.getProductDescribe();
        idProduct = product.getProductTypeId();

        txtDetailName.setText(detailName);
        DecimalFormat format = new DecimalFormat("###,###.###");
        txtDetailPrice.setText(format.format(detailPrice));
        txtDetailDescribe.setText(detailDescribe);
        Picasso.get().load(detailImage)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(imgDetail);
    }

    public void catchEventSpinner() {
        Integer[] amount = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, amount);
        spnAmount.setAdapter(arrayAdapter);
    }

    public void eventButtonClick() {
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.arrCart.size() > 0) {
                    int am = Integer.parseInt(spnAmount.getSelectedItem().toString());
                    boolean exist = false;
                    for (int i = 0; i < MainActivity.arrCart.size(); i++) {
                        if (MainActivity.arrCart.get(i).getIdSP() == id) {
                            MainActivity.arrCart.get(i).setSlSP(MainActivity.arrCart.get(i).getSlSP() + am);
                            if (MainActivity.arrCart.get(i).getSlSP() >= 10) {
                                MainActivity.arrCart.get(i).setSlSP(10);
                            }
                            MainActivity.arrCart.get(i).setPriceSP(detailPrice * MainActivity.arrCart.get(i).getSlSP());
                            exist = true;
                        }
                    }

                    if (!exist) {
                        int amount = Integer.parseInt(spnAmount.getSelectedItem().toString());
                        long newPrice = amount * detailPrice;
                        MainActivity.arrCart.add(new Cart(id, detailName, newPrice, detailImage, amount));
                    }
                } else {
                    int amount = Integer.parseInt(spnAmount.getSelectedItem().toString());
                    long newPrice = amount * detailPrice;
                    MainActivity.arrCart.add(new Cart(id, detailName, newPrice, detailImage, amount));
                }

                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuCart:
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
        }
        return true;
    }
}
