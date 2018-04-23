package com.tonyjstudio.cuahangdienthoaionline.activity;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.tonyjstudio.cuahangdienthoaionline.R;
import com.tonyjstudio.cuahangdienthoaionline.adapter.AdapterKindOfProduct;
import com.tonyjstudio.cuahangdienthoaionline.adapter.AdapterProduct;
import com.tonyjstudio.cuahangdienthoaionline.model.Cart;
import com.tonyjstudio.cuahangdienthoaionline.model.KindOfProduct;
import com.tonyjstudio.cuahangdienthoaionline.model.Product;
import com.tonyjstudio.cuahangdienthoaionline.utilities.CheckConnection;
import com.tonyjstudio.cuahangdienthoaionline.utilities.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewHome;
    NavigationView navigationView;
    ListView listViewHome;
    DrawerLayout drawerLayout;

    ArrayList<KindOfProduct> arrKindProduct;
    AdapterKindOfProduct adapterKindOfProduct;

    ArrayList<Product> arrProduct;
    AdapterProduct adapterProduct;

    int id = 0;
    String typeProductName = "";
    String imageTypeProduct = "";

    public static ArrayList<Cart> arrCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addUI();

        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            addEvents();
            actionViewFlipper();
            GetProductData();
            GetNewestProductData();
            CatchOnItemListView();
        } else {
            CheckConnection.showToast_Short(getApplicationContext(), "Kiểm tra kết nối của bạn");
            finish();
        }

    }

    public void CatchOnItemListView() {
        listViewHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_Short(getApplicationContext(), "Kiểm tra kết nối của bạn");
                        }

                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case 1:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, PhoneActivity.class);
                            intent.putExtra("id_product", arrKindProduct.get(position).getId());
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_Short(getApplicationContext(), "Kiểm tra kết nối của bạn");
                        }

                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case 2:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, LaptopActivity.class);
                            intent.putExtra("id_product", arrKindProduct.get(position).getId());
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_Short(getApplicationContext(), "Kiểm tra kết nối của bạn");
                        }

                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case 3:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, ContactActivity
                                    .class);
                            intent.putExtra("id_product", arrKindProduct.get(position).getId());
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_Short(getApplicationContext(), "Kiểm tra kết nối của bạn");
                        }

                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case 4:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                            intent.putExtra("id_product", arrKindProduct.get(position).getId());
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_Short(getApplicationContext(), "Kiểm tra kết nối của bạn");
                        }

                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    public void GetNewestProductData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.pathNewestProduct,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response != null) {
                            int ID =0;
                            String productName = "";
                            int productPrice = 0;
                            String productImage = "";
                            String productDescibe = "";
                            int IDProduct = 0;
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    ID = jsonObject.getInt("id");
                                    productName = jsonObject.getString("tensp");
                                    productPrice = jsonObject.getInt("giasp");
                                    productImage = jsonObject.getString("hinhsp");
                                    productDescibe = jsonObject.getString("motasp");
                                    IDProduct = jsonObject.getInt("idsp");

                                    arrProduct.add(new Product(ID, productPrice, productName, productImage, productDescibe, IDProduct));
                                    adapterProduct.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR DAY NE", error.toString());
                CheckConnection.showToast_Short(getApplicationContext(), error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    public void addUI() {
        toolbar = findViewById(R.id.toolbarHome);
        viewFlipper = findViewById(R.id.viewFlipper);
        recyclerViewHome = findViewById(R.id.recyclerView);
        navigationView = findViewById(R.id.navHome);
        listViewHome = findViewById(R.id.listViewHome);
        drawerLayout = findViewById(R.id.drawerLayout);

        arrKindProduct = new ArrayList<>();
        arrKindProduct.add(new KindOfProduct(0, "Trang chủ", "http://huyentuachua.gov.vn/Cms_Data/Sites/TUACHUA/Themes/Default/img/home1.png"));
        adapterKindOfProduct = new AdapterKindOfProduct(arrKindProduct, getApplicationContext());
        listViewHome.setAdapter(adapterKindOfProduct);

        arrProduct = new ArrayList<>();
        adapterProduct = new AdapterProduct(getApplicationContext(), arrProduct);
        recyclerViewHome.setHasFixedSize(true);
        recyclerViewHome.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerViewHome.setAdapter(adapterProduct);

        if (arrCart != null) {

        } else arrCart = new ArrayList<>();
    }

    public void GetProductData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.pathTypeProduct, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            typeProductName = jsonObject.getString("tenLoaiSP");
                            imageTypeProduct = jsonObject.getString("hinhAnhLoaiSanPham");
                            arrKindProduct.add(new KindOfProduct(id, typeProductName, imageTypeProduct));
                            adapterKindOfProduct.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    arrKindProduct.add(new KindOfProduct(3, "Liên hệ", "http://www.tlu.edu.vn/portals/0/2017/1_files/icon_phone.png"));
                    arrKindProduct.add(new KindOfProduct(4, "Về chúng tôi", "http://cdn1.iconfinder.com/data/icons/Pretty_office_icon_part_2/128/personal-information.png"));
                    adapterKindOfProduct.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.showToast_Short(getApplicationContext(), error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    public void actionViewFlipper() {
        ArrayList<String> arrQuangCao = new ArrayList<>();
        arrQuangCao.add("https://cdn1.tgdd.vn/qcao/13_04_2018_22_47_46_Nokia-7-Plus-800-300.png");
        arrQuangCao.add("https://cdn4.tgdd.vn/qcao/19_04_2018_11_02_16_Galaxy-J7-Plus-ver4_800-300.png");
        arrQuangCao.add("https://cdn.tgdd.vn/qcao/16_04_2018_15_59_29_Oppo-F7-800-300-2.gif");
        arrQuangCao.add("https://cdn3.tgdd.vn/qcao/16_04_2018_14_37_15_Sony-Xperia-XZ2-800-300.png");
        arrQuangCao.add("https://cdn1.tgdd.vn/qcao/05_04_2018_16_41_19_Phu-Kien-800-300.png");

        for (int i = 0; i < arrQuangCao.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());

            // get images from links
            Picasso.get().load(arrQuangCao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }

        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);

        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }

    public void addEvents() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
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
