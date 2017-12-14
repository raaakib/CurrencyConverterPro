package com.example.android.currencyconverterpro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    String returnedresult="";
    String fromcurrency="", tocurrency="";
    String url="http://www.apilayer.net/api/live?access_key=a31414f5f79bcc377df83ebdc8e527a1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        //final String fromcurrency, tocurrency;
        setContentView(R.layout.activity_main);
        ///
        final Button about = (Button) findViewById(R.id.about);
        about.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // starting background task to update product
                Intent fp=new Intent(getApplicationContext(),About.class);
                startActivity(fp);
            }
        });
///

        final TextView from = (TextView) findViewById(R.id.from);
        final TextView to = (TextView) findViewById(R.id.to);
        final EditText et= (EditText)findViewById(R.id.et);

        final Button takaleft = (Button) findViewById(R.id.takaleft);
        takaleft.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
            from.setText("BDT");
                tocurrency="BDT";
            }
        });

        final Button takaright = (Button) findViewById(R.id.takaright);
        takaright.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                to.setText("BDT");
                fromcurrency="BDT";
            }
        });

        final Button rupeeleft = (Button) findViewById(R.id.rupeeleft);
        rupeeleft.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                from.setText("Indian Rupee");
                tocurrency="INR";
            }
        });

        final Button rupeeright = (Button) findViewById(R.id.rupeeright);
        rupeeright.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                to.setText("Indian Rupee");
                fromcurrency="INR";
            }
        });
        final Button dollarleft = (Button) findViewById(R.id.dollarleft);
        dollarleft.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                from.setText("US Dollar");
                tocurrency="USD";
            }
        });

        final Button dollarright = (Button) findViewById(R.id.dollarright);
        dollarright.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                to.setText("US Dollar");
                fromcurrency="USD";
            }
        });
        final Button riyalleft = (Button) findViewById(R.id.riyalleft);
        riyalleft.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                from.setText("Saudi Riyal");
                tocurrency="SAR";
            }
        });

        final Button riyalright = (Button) findViewById(R.id.riyalright);
        riyalright.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                to.setText("Saudi Riyal");
                fromcurrency="SAR";
            }
        });
        final Button euroleft = (Button) findViewById(R.id.euroleft);
        euroleft.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                from.setText("Euro");
                tocurrency="EUR";
            }
        });

        final Button euroright = (Button) findViewById(R.id.euroright);
        euroright.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                to.setText("Euro");
                fromcurrency="EUR";
            }
        });



        final Button convert = (Button) findViewById(R.id.convert);
        convert.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String S=et.getText().toString();
                double d=Double.parseDouble(S);
             new MainActivity.GetContacts().execute(d);
            }
        });

    }
    ///
    private class GetContacts extends AsyncTask<Double, Void, String> {



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait while your request is being processed...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected String  doInBackground(Double... arg0) {
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(url);
            String answer="";
            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {

                  JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONObject c = jsonObj.getJSONObject("quotes");

                    String temp1="USD"+fromcurrency;
                    String temp2="USD"+tocurrency;
                    String id1 = c.getString(temp1);
                    String id2 = c.getString(temp2);
                    Log.e(TAG, "Json parsing error: "+id1);
                    Log.e(TAG, "Json parsing error: " + id2);
                    double value1= Double.parseDouble(id1);
                    double value2= Double.parseDouble(id2);
                    double ans= arg0[0]*(value1/value2);
                    //answer = String.valueOf(ans);
                    answer= String.format("%.2f", ans);
                    Log.e(TAG, "Json parsing error: " +answer);
                    returnedresult=answer;
                    return answer;



                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Net connection error, please check your internet connection",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return answer;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();

            TextView tv= (TextView) findViewById(R.id.money);
            tv.setText(returnedresult);

        }

    }
}
