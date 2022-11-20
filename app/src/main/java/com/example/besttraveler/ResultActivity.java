package com.example.besttraveler;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class ResultActivity extends AppCompatActivity {
    String result;
    private final String url = "https://api.openweathermap.org/data/2.5/weather";
    private final String appid = "fa211ad253385ab5e5f303af6dfebb44";
    DecimalFormat df = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent = getIntent();
        String start = intent.getStringExtra("start");
        String destination = intent.getStringExtra("destination");
        String date = intent.getStringExtra("date");



        Fragment airline = new airlineFragment();

        Fragment blank=new BlankFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView, blank);
        fragmentTransaction.commit();
        Button airlinebutton = findViewById(R.id.airlinebutton);
        Button riskbutton = findViewById(R.id.riskbutton);

        riskbutton.setOnClickListener(view -> {

            String tempUrl = "";
            String cityName = destination.trim();
            if (cityName.equals("")) {
                Toast.makeText(ResultActivity.this, "City filed is empty!", Toast.LENGTH_SHORT).show();
            } else {
                tempUrl = url + "?q=" +cityName+ "&appid=" + appid;
            }
 //           Toast.makeText(ResultActivity.this, tempUrl, Toast.LENGTH_LONG).show();

            AsyncTaskRunner runner = new AsyncTaskRunner();
            runner.execute(tempUrl);

            Fragment risk = new riskFragment();
            Bundle bundle = new Bundle();
            bundle.putString("result", result);

            risk.setArguments(bundle);
            FragmentManager fragmentManager1 = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
            fragmentTransaction1.replace(R.id.fragmentContainerView, risk);
            fragmentTransaction1.commit();


        });



        airlinebutton.setOnClickListener(view -> {
            FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();
            fragmentTransaction1.replace(R.id.fragmentContainerView, airline);
            fragmentTransaction1.commit();
        });




        Button next = findViewById(R.id.next);
        next.setOnClickListener(view -> {
            Intent intent1 = new Intent(this, lastActivity.class);

            startActivity(intent1);
        });
    }
    private class AsyncTaskRunner extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            RequestQueue queue = Volley.newRequestQueue(ResultActivity.this);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, strings[0], null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject jsonObjectMain = response.getJSONObject("main");
                        double temp = jsonObjectMain.getDouble("temp") - 273.15;

                        double feelslike = jsonObjectMain.getDouble("feels_like") - 273.15;
                        int pressure = jsonObjectMain.getInt("pressure");
                        int humidity = jsonObjectMain.getInt("humidity");

                        JSONArray weather = response.getJSONArray("weather");
                        JSONObject jsonObjectWeather = weather.getJSONObject(0);
                        String description = jsonObjectWeather.getString("description");

                        JSONObject jsonObjectWind = response.getJSONObject("wind");
                        double speed = jsonObjectWind.getDouble("speed");
                        int degree = jsonObjectWind.getInt("deg");

                        JSONObject jsonObjectClouds = response.getJSONObject("clouds");
                        int cloud = jsonObjectClouds.getInt("all");

                        JSONObject jsonObjectSys = response.getJSONObject("sys");
                        String currentCountry = jsonObjectSys.getString("country");
                        String currentCity = response.getString("name");

                        result="Current weather of " + currentCity + " (" + currentCountry + ")\n"
                                + "Temperature: " + df.format(temp) + " \u2103\n"
                                + "Feels like: " + df.format(feelslike) + " \u2103\n"
                                + "Humidity: " + humidity + "%\n"
                                + "Description: " + description + "\n"
                                + "Wind speed: " + speed + "\n"
                                + "Wind degree: " + degree + "\n"
                                + "Cloudiness: " + cloud + "%\n"
                                + "Pressure: " + pressure + " hPa";
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ResultActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            queue.add(request);
            return null;
        }
    }
}