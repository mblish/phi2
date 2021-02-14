package com.example.phi2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.phi2.R.layout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.cert.X509Certificate;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FormActivity extends AppCompatActivity {

String state = null;
int state_position = 0;
    String State_choice = "nothing selected";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String eid_data_debug = getIntent().getStringExtra("eid");
        TextView eid_label = (TextView) findViewById(R.id.equipId);
        eid_label.setText(eid_data_debug);



        Button updateButton = (Button) findViewById(R.id.buttonUpdate) ;
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    try {

                        SendPostRequest letsDoit = new SendPostRequest();
                        letsDoit.execute();
                    }catch (Exception e)
                    {
                        Snackbar snackbar;
                        Snackbar.make(findViewById(android.R.id.content),"The text is Press Back to quit",Snackbar.LENGTH_LONG).show();
                    }

            }
        });
    }





    public class SendPostRequest extends AsyncTask<String, Void, String> {
           String msg ="no msg";
           String help_me = "no luck";
        ProgressDialog progressDialog;
        final OkHttpClient client = new OkHttpClient();
        public  final MediaType MEDIA_TYPE = MediaType.parse("application/json");
        protected void onPreExecute()
        {
           progressDialog = ProgressDialog.show(FormActivity.this,"Please Wait","Updating Data...");
        }

        protected String doInBackground(String... arg0)
        {



            String eid_data = getIntent().getStringExtra("eid");

            EditText doo = (EditText) findViewById(R.id.doo_textBox);
            EditText name = (EditText) findViewById(R.id.name_textBox);
            EditText state = (EditText) findViewById(R.id.state_textBox);
            EditText comment = (EditText) findViewById(R.id.comment_textBox);
            EditText updater = (EditText) findViewById(R.id.updater_textBox);
            EditText mid = (EditText) findViewById(R.id.mid_textBox);
            String date_of_origin = doo.getText().toString();
            String name_desc= name.getText().toString();
            String equipment_state = state.getText().toString();
            String update_comment = comment.getText().toString();
            String name_of_updater = updater.getText().toString();
            String machine_ID = mid.getText().toString();

//.url()
            try {

               // HttpUrl.Builder urlBuilder = HttpUrl.parse("http://sandstorm.rf.gd/sql_myscript.php").newBuilder();
             //   urlBuilder.addQueryParameter("fan",equipment_state);
              //  String url = urlBuilder.build().toString();
                RequestBody formBody = new FormBody.Builder()
                        .add("eid",eid_data)
                        .add("name",name_desc)
                        .add("doo",date_of_origin)
                       .add("state",equipment_state)
                        .add("updatecomment",update_comment)
                        .add("updater",name_of_updater)
                        .add("mid",machine_ID)
                        .build();

                Request request = new Request.Builder()
                        .url("http://mebnet.heliohost.us/sql_helioscript.php")
                        .post(formBody)
                        .build();

                Call call = client.newCall(request);
                Response response = call.execute();

                return response.body().string();

            }catch (IOException e)
            {
                return e.getMessage()+" error";
            }
            /*
            //codezx
            String eid_data = getIntent().getStringExtra("eid");
            EditText doo = (EditText) findViewById(R.id.doo_textBox);
            EditText name = (EditText) findViewById(R.id.name_textBox);
            EditText state = (EditText) findViewById(R.id.state_textBox);
            EditText comment = (EditText) findViewById(R.id.comment_textBox);
            EditText updater = (EditText) findViewById(R.id.updater_textBox);
            EditText mid = (EditText) findViewById(R.id.mid_textBox);
            String date_of_origin = doo.getText().toString();
            String name_desc= name.getText().toString();
            String equipment_state = state.getText().toString();
            String update_comment = comment.getText().toString();
            String name_of_updater = updater.getText().toString();
            String machine_ID = mid.getText().toString();
            try {
                JSONObject json = new JSONObject();
                json.put("UserName", "test2");
                json.put("FullName", "1234567");
                json.put("eid", eid_data);
                json.put("name", name_desc);
                json.put("state", equipment_state);
                json.put("updatecomment", update_comment);
                json.put("updater", name_of_updater);
                json.put("mid", machine_ID);
                json.put("doo", date_of_origin);
                HttpParams httpParams = new BasicHttpParams();
                //HttpConnectionParams.setConnectionTimeout(httpParams,
                      // 5000);
              //  HttpConnectionParams.setSoTimeout(httpParams, 5000);
                HttpClient client = new DefaultHttpClient(httpParams);
                //
                //String url = "http://10.0.2.2:8080/sample1/webservice2.php?" +
                //             "json={\"UserName\":1,\"FullName\":2}";
                String url ="https://sandstorm.rf.gd/sql_myscript.php";

                HttpPost request = new HttpPost(url);
                request.setEntity(new ByteArrayEntity(json.toString().getBytes(
                        "UTF8")));
                request.setHeader("json", json.toString());
                HttpResponse response = client.execute(request);
                HttpEntity entity = response.getEntity();
                // If the response does not enclose an entity, there is no need
                if (entity != null) {
                    InputStream instream = entity.getContent();

                   //String result = RestClient.convertStreamToString(instream);
                   // Log.i("Read from server", result);
                    //Toast.makeText(this,  result,
                            //Toast.LENGTH_LONG).show();
                }
            } catch (Throwable t) {
                Toast.makeText(getApplicationContext(), "Request failed: " + t.toString(),
                        Toast.LENGTH_LONG).show();
            }




  /*


            JSONObject postDataParams = new JSONObject();
            try{

                URL url = new URL("https://sandstorm.rf.gd/sql_myscript.php");


                postDataParams.put("eid", eid_data);
                postDataParams.put("name", name_desc);
                postDataParams.put("state", equipment_state);
                postDataParams.put("updatecomment", update_comment);
                postDataParams.put("updater", name_of_updater);
                postDataParams.put("mid", machine_ID);
                postDataParams.put("doo", date_of_origin);
                Log.e("params",postDataParams.toString());
            }
            catch(Exception e){
                msg ="Exception: " + e.getMessage();
            }

            RequestBody body = RequestBody.create(MEDIA_TYPE, postDataParams.toString());

            final Request request = new Request.Builder()
                    .url("https://sandstorm.rf.gd/sql_myscript.php")
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("craft", postDataParams.toString())
                    //.addHeader("cache-control", "no-cache")
                    .build();


            try {

                Snackbar snackbar;
                snackbar = Snackbar.make(findViewById(android.R.id.content),"great",Snackbar.LENGTH_LONG);
                Response  response = client.newCall(request).execute();
                help_me = response.body().toString();


            } catch (IOException e) {
                e.printStackTrace();
                Snackbar snackbar;
                snackbar = Snackbar.make(findViewById(android.R.id.content),"error",Snackbar.LENGTH_LONG);
            }

            //String serverResponse = ;

   */
  //codezx

            //TurboGAP2020   meblish heliohost

                  //  Snackbar snackbar;
/*
                try{


                    String link="https://sandstorm.rf.gd/sql_myscript.php";
                    String data  = URLEncoder.encode("eid", "UTF-8") + "=" +
                            URLEncoder.encode(eid_data, "UTF-8");
                    data += "&" + URLEncoder.encode("name", "UTF-8") + "=" +
                            URLEncoder.encode(name_desc, "UTF-8");
                    data += "&" + URLEncoder.encode("state", "UTF-8") + "=" +
                            URLEncoder.encode(equipment_state, "UTF-8");
                    data += "&" + URLEncoder.encode("doo", "UTF-8") + "=" +
                            URLEncoder.encode(date_of_origin, "UTF-8");
                    data += "&" + URLEncoder.encode("updatecomment", "UTF-8") + "=" +
                            URLEncoder.encode(update_comment, "UTF-8");
                    data += "&" + URLEncoder.encode("updater", "UTF-8") + "=" +
                            URLEncoder.encode(name_of_updater, "UTF-8");
                    data += "&" + URLEncoder.encode("mid", "UTF-8") + "=" +
                            URLEncoder.encode(machine_ID, "UTF-8");

                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write( data );
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new
                            InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    while((line = reader.readLine()) != null) {
                        sb.append(line);
                        break;
                    }

                    return sb.toString();
                } catch(Exception e){
                    snackbar = Snackbar.make(findViewById(android.R.id.content),"hela",Snackbar.LENGTH_LONG);
                    return new String("Exception: " + e.getMessage());


                }*/

            }
           // return msg;

        protected void onPostExecute(String result) {

            progressDialog.dismiss();
            TextView resutBox = (TextView) findViewById(R.id.textView4);
            resutBox.setText(result);
            Log.i("Response re baba", result);



        }



        }


    }

