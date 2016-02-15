package com.example.thsu.idhack16_mll_infrastructure;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.location.Location;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Form extends AppCompatActivity{

    LocationManager locMgr;
    String mLat;
    String mLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                mLat = "" + location.getLatitude();
                mLong = "" + location.getLongitude() ;
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };


        locMgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

        setContentView(R.layout.activity_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void submitButton(View view) {
        // Move to Form after pane.
        final Intent intent = new Intent(this, FormAfter.class);
        final EditText title = (EditText) findViewById(R.id.editTextTitle);
        final EditText description = (EditText) findViewById(R.id.editTextDescription);
        final TextView tv = (TextView) findViewById(R.id.textFieldTarget);
        tv.setText("");




        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://id-hack-magma-server.herokuapp.com/api/magma";
        // Request a string response from the provided URL
        final JSONObject myReq = new JSONObject();
        try {
            myReq.put("title", title.getText());
            myReq.put("description", description.getText());
            myReq.put("gps_longitude", mLong);
            myReq.put("gps_latitude", mLat);
        } catch (JSONException e) {
            System.err.println("JSON exception.");
        }
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, myReq,
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
                tv.setText("That didn't work!");
            }
        });
        queue.add(jsonRequest);
        startActivity(intent);
    }
}
