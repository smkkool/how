package fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.PhoneAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import database.StoreDatabase;
import minhpvn.com.swipedemo.MainActivity;
import minhpvn.com.swipedemo.R;
import model.Phone;

/**
 * Created by smkko on 12/13/2017.
 */

public class TabThreeFragment extends Fragment {
    @BindView(R.id.rcvPhone)
    RecyclerView rcvPhone;
    String dataBaseFileName = "PhonesDatabase.sqlite";
    Phone phone;
    static ArrayList<Phone> list;
    public static StoreDatabase storeDatabase;
    static PhoneAdapter phoneAdapter;
    MainActivity mainActivity;
    boolean isAdd = false;
    String url = "http://192.168.1.99/appservice/getdata.php";
    String urlAdd = "http://192.168.1.99/appservice/insert.php";
    String urlEdit = "http://192.168.1.99/appservice/update.php";


    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab_three, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).setActionBarTitle("Phones");
        //CREATE DATABASE
        storeDatabase = new StoreDatabase(getContext(), dataBaseFileName, null, 1);
//        storeDatabase.queryData("CREATE TABLE IF NOT EXISTS PHONE(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME VARCHAR(200), PRICE DOUBLE  ,DESCRIPTION NVARCHAR(200))");

//        storeDatabase.queryData("INSERT INTO PHONE VALUES(NULL,'IPHONE X2',299900002,102,'good2')");
//        storeDatabase.queryData("INSERT INTO PHONE VALUES(NULL,'IPHONE X3',299900002,102,'good2')");
//        storeDatabase.queryData("INSERT INTO PHONE VALUES(NULL,'IPHONE X4',299900002,102,'good2')");

        list = new ArrayList<>();
        getPhone(url,getContext());
        phoneAdapter = new PhoneAdapter(getContext(), list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvPhone.setLayoutManager(layoutManager);
        rcvPhone.setAdapter(phoneAdapter);
        //SELECT DATA
//        getData();
        return view;
    }

    private void getData() {
        Cursor cursor = storeDatabase.getData("SELECT * FROM PHONE");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            double price = cursor.getDouble(2);
            String description = cursor.getString(3);
            list.add(new Phone(id, name, price, description));

        }
        phoneAdapter.notifyDataSetChanged();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.tab_three_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_add:
                isAdd = true;
                // Do onlick on menu action here
                DialogAdd(getContext(), 0, null, null, null, isAdd);
                return true;
        }
        return false;
    }

    public void DialogAdd(final Context context, final int id, final String name, final String descreption, final String price, final boolean isAdd) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.tab_three_item_add_edit);
        final EditText phone_name_edit = dialog.findViewById(R.id.phone_name_edit);
        final EditText phone_descreption_edit = dialog.findViewById(R.id.phone_descreption_edit);
        final EditText phone_price_edit = dialog.findViewById(R.id.phone_price_edit);
        final EditText phone_quantity_edit = dialog.findViewById(R.id.phone_quantity_edit);
        TextView title_add = dialog.findViewById(R.id.title_add);
        if (isAdd == false) {
            title_add.setText("EDIT PHONE");
            phone_name_edit.setText(name);
            phone_descreption_edit.setText(descreption);
            phone_price_edit.setText(price);

        }

        Button btn_tab3_save = dialog.findViewById(R.id.btn_tab3_save);
        Button btn_tab3_cancel = dialog.findViewById(R.id.btn_tab3_cancel);
        btn_tab3_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAdd == false) {

                    RequestQueue requestQueue = Volley.newRequestQueue(context);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, urlEdit, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.trim().equals("SUCCESS")) {
                                Toast.makeText(context, "thanh cong", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                phoneAdapter = new PhoneAdapter(context, list);
                                RequestQueue requestQueue = Volley.newRequestQueue(context);
                                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                                        new Response.Listener<JSONArray>() {
                                            @Override
                                            public void onResponse(JSONArray response) {
                                                for (int i = 0; i < response.length(); i++) {
                                                    try {
                                                        JSONObject object = response.getJSONObject(i);
                                                        list.add(new Phone
                                                                (object.getInt("phoneId"),
                                                                        object.getString("phoneName"),
                                                                        object.getDouble("phonePrice"),
                                                                        object.getString("phoneDescription")
                                                                )
                                                        );
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                                phoneAdapter.notifyDataSetChanged();
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                requestQueue.add(jsonArrayRequest);

                            } else {
                                Toast.makeText(context, "loi", Toast.LENGTH_SHORT).show();
                                Log.d("1111", "" + response);
                            }
                        }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, "xay ra loi" + error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("phoneId", String.valueOf(id));
                            params.put("phoneName", phone_name_edit.getText().toString());
                            params.put("phoneDescription", phone_descreption_edit.getText().toString());
                            params.put("phonePrice", String.valueOf(phone_price_edit.getText().toString()));
                            return params;
                        }
                    };
                    requestQueue.add(stringRequest);
                } else {
                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, urlAdd, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.trim().equals("SUCCESS")) {
                                Toast.makeText(getContext(), "thanh cong", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                getPhone(url,context);

                            } else {
                                Toast.makeText(getContext(), "loi", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getContext(), "xay ra loi" + error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("phoneName", phone_name_edit.getText().toString());
                            params.put("phoneDescription", phone_descreption_edit.getText().toString());
                            params.put("phonePrice", String.valueOf(phone_price_edit.getText().toString()));
                            return params;
                        }
                    };
                    requestQueue.add(stringRequest);

                }


            }
        });
        btn_tab3_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        //max dialog
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    public void DialogDelete(final int idPhone, Context context) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        alertDialogBuilder.setTitle("Delete item");

        // set dialog message
        alertDialogBuilder
                .setMessage("Bạn chắc chắn muốn xóa ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        storeDatabase.queryData("DELETE FROM PHONE WHERE ID = " + idPhone + "");
                        dialog.dismiss();
                        getData();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }


    //Volley
    private void getPhone(String url,Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                list.add(new Phone
                                        (object.getInt("phoneId"),
                                                object.getString("phoneName"),
                                                object.getDouble("phonePrice"),
                                                object.getString("phoneDescription")
                                        )
                                );
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        phoneAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    //Volley addPhone
    private void addPhone(String urlAdd, final String name, final String des, final int price) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("SUCCESS")) {
                    Toast.makeText(getContext(), "thanh cong", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "loi", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "xay ra loi" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("phoneName", name);
                params.put("phoneDescription", des);
                params.put("phonePrice", String.valueOf(price));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void addPhone(String url){

    }
}
