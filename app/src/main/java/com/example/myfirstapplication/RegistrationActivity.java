package com.example.myfirstapplication;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;

import android.os.Bundle;


import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.example.myfirstapplication.broadcast.BroadcastManagerCallerInterface;
import com.example.myfirstapplication.database.AppDatabase;
import com.example.myfirstapplication.gps.GPSManager;
import com.example.myfirstapplication.gps.GPSManagerCallerInterface;


import androidx.room.Room;

import java.text.SimpleDateFormat;
import java.util.Date;


public class RegistrationActivity extends Activity implements View.OnClickListener, GPSManagerCallerInterface, BroadcastManagerCallerInterface {

    String username = "";
    String password = "";
    String first_name = "";
    String last_name = "";
    String full_name = "";
    String email = "";
    String lastLat = "";
    String lastLon = "";
    String status = "";
    String lastSeen = "";
    AppDatabase appDatabase;
    GPSManager gpsManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_layout);


        findViewById(R.id.registration_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentToBeCalled = new Intent();
                username = ((EditText) findViewById(R.id.r_user_name)).getText() + "";
                password = ((EditText) findViewById(R.id.r_password)).getText() + "";
                first_name = ((EditText) findViewById(R.id.r_user_name)).getText() + "";
                last_name = ((EditText) findViewById(R.id.r_password)).getText() + "";
                full_name = ((EditText) findViewById(R.id.r_full_name)).getText() + "";
                email = ((EditText) findViewById(R.id.r_email)).getText() + "";
                lastLat =  "9.963889";
                lastLon =  "74.796387";
                status = "online";
                lastSeen = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
                        .format( new Date());


                //String passwordConfirmation = ((EditText) findViewById(R.id.reg_password_confirmation_value)).getText() + "";


                /*WebServiceManager r = new WebServiceManager();
                try {
                    String response = r.execute("http://localhost:8080/WBServices/webresources/web.user", "GET").get();
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    Toast.makeText(getApplicationContext(), "nombre " + jsonObject.getString("email"), Toast.LENGTH_LONG).show();
                    //Log.d("firstname", "onClick: "+ jsonObject.getString("firstName"));
                    //Log.d("lastname", "onClick: "+ jsonObject.getString("lastName"));
                    //Log.d("userid", "onClick: "+ jsonObject.getString("userId"));

                } catch (Exception e) {
                    e.printStackTrace();
                }*/

            }
        });

        initializeDataBase();
        initializeGPSManager();

    }

    public void initializeDataBase(){
        try{
             appDatabase = Room.
                    databaseBuilder(this, AppDatabase.class,
                            "app-database").
                    fallbackToDestructiveMigration().build();
        }catch (Exception error){
            Toast.makeText(this,error.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    public void initializeGPSManager(){
        gpsManager = new GPSManager(this, this);
        gpsManager.initializeLocationManager();
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*((EditText) findViewById(R.id.reg_fname_value)).setText("");
        ((EditText) findViewById(R.id.reg_lname_value)).setText("");
        ((EditText) findViewById(R.id.reg_username_value)).setText("");
        ((EditText) findViewById(R.id.reg_password_value)).setText("");
        ((EditText) findViewById(R.id.reg_password_confirmation_value)).setText("");*/
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(this, "Iniciando registro", Toast.LENGTH_SHORT).show();

    }

    /*public boolean registrarWS() {

        String datos = fname + "/" + lname + "/" + userName + "/" + password;
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost post = new HttpPost("http://localhost:8080/WServices/webresources/web.user/insert");
        //post.setHeader("content-type", "application/json");
        try {
            //Construimos el objeto cliente en formato JSON
            JSONObject dato = new JSONObject();

            dato.put("firstName", fname);
            dato.put("lastName", lname);
            dato.put("email", userName);
            dato.put("password", password);

            StringEntity entity = new StringEntity(dato.toString());
            post.setEntity(entity);

            HttpResponse resp = httpClient.execute(post);
            //resultado = EntityUtils.toString(resp.getEntity());

        } catch (Exception ex) {
            return false;
        }

        return true;
    }*/




    @Override
    public void MessageReceivedThroughBroadcastManager(String channel, String type, String message) {

    }

    @Override
    public void ErrorAtBroadcastManager(Exception error) {

    }

    @Override
    public void needPermissions() {

    }

    @Override
    public void locationHasBeenReceived(Location location) {

    }

    @Override
    public void gpsErrorHasBeenThrown(Exception error) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    /*
    private class consumirServicio extends AsyncTask<Void, Integer, Void>{
        private int progreso;

        @Override
        protected Void doInBackground(Void... voids) {
            registrarWS();
            return null;
        }
        @Override
        protected void onPostExecute(Void result){
            findViewById(R.id.reg_button).setClickable(true);
            Toast.makeText(RegistrationActivity.this,"Registro exitoso", Toast.LENGTH_SHORT).show();
        }
        @Override
        protected void onPreExecute(){
            progreso=0;
            findViewById(R.id.reg_button).setClickable(false);

        }
        @Override
        protected void onProgressUpdate(Integer... values){


        }

    }*/
}
