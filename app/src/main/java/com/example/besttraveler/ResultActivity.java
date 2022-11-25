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
    String result="",result1="";
    private final String url = "https://api.openweathermap.org/data/2.5/weather";
    private final String appid = "fa211ad253385ab5e5f303af6dfebb44";
    private final String url1 = "https://airlabs.co/api/v9/schedules";
    private final String api_key = "73d36113-08e5-48d7-add3-8b4a6b8a4522";
    DecimalFormat df = new DecimalFormat("#.##");
    String start1,destination1,date1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent = getIntent();
        String start = intent.getStringExtra("start");
        String destination = intent.getStringExtra("destination");
        String date = intent.getStringExtra("date");
        String departure1=intent.getStringExtra("Dep");
        String arrive1=intent.getStringExtra("arr");
        start1=start;
        destination1=destination;
        date1=date;
        result1="Flights from "+start+" To " +destination+" on "+date +" are as follows: \n";

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
            String tempUrl1 = "";
            if (departure1.equals("") || arrive1.equals("")) {
                Toast.makeText(ResultActivity.this, "Departure and arrive fields ars empty!", Toast.LENGTH_SHORT).show();
            } else {
                tempUrl1 = url1 + "?dep_iata=" +departure1+ "&arr_iata=" + arrive1+"&api_key="+api_key+"&_fields=flight_number,dep_estimated";
            }
  //         Toast.makeText(ResultActivity.this, tempUrl1, Toast.LENGTH_LONG).show();
            AsyncTaskRunner1 runner1 = new AsyncTaskRunner1();
            runner1.execute(tempUrl1);
            Fragment airline = new airlineFragment();
            Bundle bundle = new Bundle();
            bundle.putString("result1", result1);
            airline.setArguments(bundle);
            FragmentManager fragmentManager2 = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
            fragmentTransaction2.replace(R.id.fragmentContainerView, airline);
            fragmentTransaction2.commit();
        });

        Button next = findViewById(R.id.next1);
        next.setOnClickListener(view -> {
           Intent intent2 = new Intent(this, lastActivity.class);
            startActivity(intent2);
        });
    }

    private class AsyncTaskRunner1 extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            RequestQueue queue1 = Volley.newRequestQueue(ResultActivity.this);
            JsonObjectRequest request1 = new JsonObjectRequest(Request.Method.GET, strings[0], null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jArr=response.getJSONArray("response");
                        String j3="";
                        for(int i=0;i<jArr.length();i++){
                            JSONObject j1=jArr.getJSONObject(i);
                            String j2=j1.getString("flight_number");
                            try{
                            j3=j1.getString("dep_estimated");}catch(Exception e) {
                                j3="";
                            };
                            result1=result1+"flight_number: "+j2+"; "+j3+"\n";
                            if(i>10)
                                break;
                        }
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
            queue1.add(request1);
            result1="Flights from "+start1+" To " +destination1+" on "+date1 +" are as follows: \n";
            return null;
        }
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