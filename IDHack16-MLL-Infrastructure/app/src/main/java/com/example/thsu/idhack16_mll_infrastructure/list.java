package com.example.thsu.idhack16_mll_infrastructure;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class list extends FragmentActivity implements OnMapReadyCallback {
    JSONObject e1;
    JSONObject e2;
    JSONObject e3;
    private GoogleMap mMap;
    TextView e1Title;
    TextView e1Desc;
    TextView e2Title;
    TextView e2Desc;
    TextView e3Title;
    TextView e3Desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://id-hack-magma-server.herokuapp.com/api/magma";
        // Request a string response from the provided URL
        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //TextView tv = (TextView) findViewById(R.id.listTextView);
                        //tv.setText(response.toString());
                        e1Title = (TextView) findViewById(R.id.e1Title);
                        e1Desc = (TextView) findViewById(R.id.e1Desc);
                        e2Title = (TextView) findViewById(R.id.e2Title);
                        e2Desc = (TextView) findViewById(R.id.e2Desc);
                        e3Title = (TextView) findViewById(R.id.e3Title);
                        e3Desc = (TextView) findViewById(R.id.e3Desc);
                        try {
                            e1 = response.getJSONObject(0);
                            e1Title.setText(e1.getString("title"));
                            e1Desc.setText(e1.getString("description"));
                            e2 = response.getJSONObject(1);
                            e2Title.setText(e2.getString("title"));
                            e2Desc.setText(e2.getString("description"));
                            e3 = response.getJSONObject(2);
                            e3Title.setText(e3.getString("title"));
                            e3Desc.setText(e3.getString("description"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        loadmap();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                TextView tv = (TextView) findViewById(R.id.listTextView);
                tv.setText("Something went wrong. ");
            }

        });
        queue.add(jsonRequest);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.



    }
    public void loadmap() {
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void submitButton(View view) {
        Intent intent = new Intent(this, Form.class);
        startActivity(intent);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng e1ll = null;
        LatLng e2ll = null;
        LatLng e3ll = null;
        /*
        if (e1 != null) {
            try {
                e1ll = new LatLng(Double.parseDouble(e1.getString("gps_latitude")), Double.parseDouble(e1.getString("gps_longitude")));
            } catch (JSONException e) {
                //do nothing.
            }
        }
        if (e2 != null) {
            try {
                e2ll = new LatLng(Double.parseDouble(e2.getString("gps_latitude")), Double.parseDouble(e2.getString("gps_longitude")));
            } catch (JSONException e) {
                //do nothing.
            }
        }
        if (e3 != null) {
            try {
                e3ll = new LatLng(Double.parseDouble(e3.getString("gps_latitude")), Double.parseDouble(e3.getString("gps_longitude")));
            } catch (JSONException e) {
                //do nothing.
            }
        }
        */
        //LatLng sydney = new LatLng(-34, 151);
        /*
        if (e1ll != null) {
            mMap.addMarker(new MarkerOptions().position(e1ll).title(e1Title.toString()));
        }
        if (e2ll != null) {
            mMap.addMarker(new MarkerOptions().position(e2ll).title(e2Title.toString()));
        }
        if (e3ll != null) {
            mMap.addMarker(new MarkerOptions().position(e3ll).title(e3Title.toString()));
        }
        */

        try {
            e1ll = new LatLng(Double.parseDouble(e1.getString("gps_latitude")), Double.parseDouble(e1.getString("gps_longitude")));
            e2ll = new LatLng(Double.parseDouble(e2.getString("gps_latitude")), Double.parseDouble(e2.getString("gps_longitude")));
            e3ll = new LatLng(Double.parseDouble(e3.getString("gps_latitude")), Double.parseDouble(e3.getString("gps_longitude")));

        } catch (JSONException e) {
            // nada
        }
        mMap.addMarker(new MarkerOptions().position(e1ll).title(e1Title.getText().toString()));
        mMap.addMarker(new MarkerOptions().position(e2ll).title(e2Title.getText().toString()));
        mMap.addMarker(new MarkerOptions().position(e3ll).title(e3Title.getText().toString()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(e1ll));

        //mMap.addMarker(new MarkerOptions().position(sydney).title("sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public void e1confirm(View view){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = null;
        try {
            url = "https://id-hack-magma-server.herokuapp.com/api/magma/" + e1.getString("_id") + "/upvote";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                        //tv.setText(myReq.toString());
                        //startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //tv.setText("That didn't work!");
            }
        });
        queue.add(jsonRequest);
        final Intent intent = new Intent(this, FormAfter.class);
        startActivity(intent);
    }
    public void e2confirm(View view){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = null;
        try {
            url = "https://id-hack-magma-server.herokuapp.com/api/magma/" + e2.getString("_id") + "/upvote";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                        //tv.setText(myReq.toString());
                        //startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //tv.setText("That didn't work!");
            }
        });
        queue.add(jsonRequest);
        final Intent intent = new Intent(this, FormAfter.class);
        startActivity(intent);
    }
    public void e3confirm(View view){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = null;
        try {
            url = "https://id-hack-magma-server.herokuapp.com/api/magma/" + e3.getString("_id") + "/upvote";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                        //tv.setText(myReq.toString());
                        //startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //tv.setText("That didn't work!");
            }
        });
        queue.add(jsonRequest);
        final Intent intent = new Intent(this, FormAfter.class);
        startActivity(intent);
    }
    public void e1deny(View view){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = null;
        try {
            url = "https://id-hack-magma-server.herokuapp.com/api/magma/" + e1.getString("_id") + "/downvote";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                        //tv.setText(myReq.toString());
                        //startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //tv.setText("That didn't work!");
            }
        });
        queue.add(jsonRequest);
        final Intent intent = new Intent(this, FormAfter.class);
        startActivity(intent);
    }
    public void e2deny(View view){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = null;
        try {
            url = "https://id-hack-magma-server.herokuapp.com/api/magma/" + e2.getString("_id") + "/downvote";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                        //tv.setText(myReq.toString());
                        //startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //tv.setText("That didn't work!");
            }
        });
        queue.add(jsonRequest);
        final Intent intent = new Intent(this, FormAfter.class);
        startActivity(intent);
    }
    public void e3deny(View view){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = null;
        try {
            url = "https://id-hack-magma-server.herokuapp.com/api/magma/" + e3.getString("_id") + "/downvote";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                        //tv.setText(myReq.toString());
                        //startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //tv.setText("That didn't work!");
            }
        });
        queue.add(jsonRequest);
        final Intent intent = new Intent(this, FormAfter.class);
        startActivity(intent);
    }

}


