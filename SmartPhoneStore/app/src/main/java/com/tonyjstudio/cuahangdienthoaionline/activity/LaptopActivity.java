package com.tonyjstudio.cuahangdienthoaionline.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tonyjstudio.cuahangdienthoaionline.R;
import com.tonyjstudio.cuahangdienthoaionline.adapter.AdapterLaptop;
import com.tonyjstudio.cuahangdienthoaionline.adapter.AdapterPhone;
import com.tonyjstudio.cuahangdienthoaionline.model.Product;
import com.tonyjstudio.cuahangdienthoaionline.utilities.CheckConnection;
import com.tonyjstudio.cuahangdienthoaionline.utilities.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LaptopActivity extends AppCompatActivity {

    Toolbar toolbar;
    ListView lvLaptop;
    AdapterLaptop adapterLaptop;
    ArrayList<Product> arrLaptop;
    int idLaptop = 0;
    int page = 1;

    View footerView;
    boolean isLoading = false;
    boolean limitData = false;
    LaptopActivity.mHandler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop);

        addUI();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            getIdProductType();
            actionToolbar();
            getData(page);
            loadMoreData();
        } else {
            CheckConnection.showToast_Short(getApplicationContext(), "No network connected");
            finish();
        }
    }

    public void addUI() {
        toolbar = findViewById(R.id.toolBarLaptop);
        lvLaptop = findViewById(R.id.listViewLaptop);
        arrLaptop = new ArrayList<>();
        adapterLaptop = new AdapterLaptop(getApplicationContext(), arrLaptop);
        lvLaptop.setAdapter(adapterLaptop);

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.progress_bar, null);

        mHandler = new LaptopActivity.mHandler();
    }

    public void getIdProductType() {
        idLaptop = getIntent().getIntExtra("id_product", -1);
        Log.e("Product_type", idLaptop + "");
    }

    public void actionToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void getData(int page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String pathToProductPhone = Server.pathToProductPage + String.valueOf(page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, pathToProductPhone,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int id = 0;
                        String productName = "";
                        int productPrice = 0;
                        String productImage = "";
                        String productDescribe = "";
                        int productId = 0;

                        if (response != null && response.length() != 2){
                            lvLaptop.removeFooterView(footerView);
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    id = jsonObject.getInt("id");
                                    productName = jsonObject.getString("tensanpham");
                                    productPrice = jsonObject.getInt("giasanpham");
                                    productImage = jsonObject.getString("hinhsanpham");
                                    productDescribe = jsonObject.getString("motasanpham");
                                    productId = jsonObject.getInt("idsanpham");

                                    arrLaptop.add(new Product(id, productPrice, productName, productImage, productDescribe, productId));
                                    adapterLaptop.notifyDataSetChanged();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            limitData = true;
                            lvLaptop.removeFooterView(footerView);
                            CheckConnection.showToast_Short(getApplicationContext(), "Out of data");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("idsanpham", String.valueOf(idLaptop));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    public class mHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    lvLaptop.addFooterView(footerView);
                    break;
                case 1:
                    page++;
                    getData(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }

    public class ThreadData extends Thread {
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }

    public void loadMoreData() {
        lvLaptop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("product_info", arrLaptop.get(position));
                startActivity(intent);
            }
        });
        lvLaptop.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0 && !isLoading && !limitData) {
                    isLoading = true;
                    LaptopActivity.ThreadData threadData = new LaptopActivity.ThreadData();
                    threadData.start();
                }
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
