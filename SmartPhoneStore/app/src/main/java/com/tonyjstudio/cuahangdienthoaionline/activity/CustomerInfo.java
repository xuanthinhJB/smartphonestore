package com.tonyjstudio.cuahangdienthoaionline.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tonyjstudio.cuahangdienthoaionline.R;
import com.tonyjstudio.cuahangdienthoaionline.utilities.CheckConnection;
import com.tonyjstudio.cuahangdienthoaionline.utilities.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CustomerInfo extends AppCompatActivity {

    EditText txtName, txtPhone, txtEmail;
    Button btnConfirm, btnComeBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_info);

        addUI();

        btnComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            eventButton();
        } else {
            CheckConnection.showToast_Short(getApplicationContext(), "No network");
        }
    }

    public void addUI() {
        txtEmail = findViewById(R.id.txtEditCustomerEmail);
        txtName = findViewById(R.id.txtEditCustomerName);
        txtPhone = findViewById(R.id.txtEditCustomerPhone);
        btnComeBack = findViewById(R.id.btnComeBack);
        btnConfirm = findViewById(R.id.btnConfirm);
    }

    public void eventButton() {
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = txtName.getText().toString();
                final String phone = txtPhone.getText().toString();
                final String email = txtEmail.getText().toString();
                if (name.length() > 0 && phone.length() > 0 && email.length() > 0) {
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.pathToCart
                            , new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String response) {
                            Log.d("Ma don hang", response);
                            if (Integer.parseInt(response) > 0) {
                                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                StringRequest request = new StringRequest(Request.Method.POST, Server.pathToBill
                                        , new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if (response.equals("1")) {
                                            MainActivity.arrCart.clear();
                                            CheckConnection.showToast_Short(getApplicationContext(), "Ban da them gio hang thanh cong");
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                            CheckConnection.showToast_Short(getApplicationContext(), "Moi ban tiep tuc mua hang");
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        CheckConnection.showToast_Short(getApplicationContext(), "Du lieu cua ban bi loi");

                                    }
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray = new JSONArray();

                                        for (int i = 0; i < MainActivity.arrCart.size(); i++) {
                                            JSONObject jsonObject = new JSONObject();
                                            try {
                                                jsonObject.put("madonhang", response);
                                                jsonObject.put("masanpham", MainActivity.arrCart.get(i).getIdSP());
                                                jsonObject.put("tensanpham", MainActivity.arrCart.get(i).getNameSP());
                                                jsonObject.put("giasanpham", MainActivity.arrCart.get(i).getPriceSP());
                                                jsonObject.put("soluongsanpham", MainActivity.arrCart.get(i).getSlSP());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                            jsonArray.put(jsonObject);
                                        }
                                        HashMap<String, String> hashMap = new HashMap<String, String>();
                                        hashMap.put("json", jsonArray.toString());
                                        return hashMap;
                                    }
                                };
                                queue.add(request);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> hashMap = new HashMap<String, String>();
                            hashMap.put("tenkhachhang", name);
                            hashMap.put("sodienthoai", phone);
                            hashMap.put("email", email);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                } else {
                    CheckConnection.showToast_Short(getApplicationContext(), "Hay kiem tra lai du lieu");
                }
            }
        });
    }
}
