package com.example.myfirstapplication;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.myfirstapplication.broadcast.BroadcastManager;
import com.example.myfirstapplication.broadcast.BroadcastManagerCallerInterface;
import com.example.myfirstapplication.database.AppDatabase;
import com.example.myfirstapplication.gps.GPSManager;
import com.example.myfirstapplication.gps.GPSManagerCallerInterface;
import com.example.myfirstapplication.model.Ubicacion;
import com.example.myfirstapplication.model.User;
import com.example.myfirstapplication.model.CurrentLocation;


import com.example.myfirstapplication.network.SocketManagementService;
import com.example.myfirstapplication.seviceWEB.Test;

import android.preference.PreferenceManager;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;
//Para Overlay
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;
import android.graphics.Color;
//

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GPSManagerCallerInterface , BroadcastManagerCallerInterface {

    GPSManager gpsManager;
    private MapView map;
    private MyLocationNewOverlay mLocationOverlay;
    private EditText editTextMessage;
    private ListView listViewMessages;
    String  user;
    BroadcastManager broadcastManagerForSocketIO;
    ArrayList<String> listOfMessages=new ArrayList<>();
    ArrayAdapter<String> adapter ;
    boolean serviceStarted=false;
    AppDatabase appDatabase;
    Test serviceTest;


    /*#############   INICIALIZACION COMPONENTS   ################*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        //LinearLayout messages_layout = findViewById(R.id.message_layout);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        user=getIntent().getExtras().
                getString("user_name");
        Toast.makeText(
                this,
                "Welcome "+user,Toast.LENGTH_SHORT).
                show();

        editTextMessage = ((EditText)findViewById(R.id.editTextMessage));

        ((Button)findViewById(R.id.buttonSend)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = editTextMessage.getText()+"";
                //sendBroadcastMessage(user+message);
                editTextMessage.setText("");
                if(serviceStarted)
                    if(broadcastManagerForSocketIO!=null){
                        broadcastManagerForSocketIO.sendBroadcast(
                                SocketManagementService.CLIENT_TO_SERVER_MESSAGE,
                                user+": "+message);
                    }

            }
        });
        ((Button)findViewById(R.id.start_service_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(
                        getApplicationContext(),SocketManagementService.class);
                intent.putExtra("SERVER_HOST",((EditText)findViewById(R.id.server_ip_txt)).getText()+"");
                intent.putExtra("SERVER_PORT",Integer.parseInt(((EditText)findViewById(R.id.server_port_txt)).getText()+""));
                intent.setAction(SocketManagementService.ACTION_CONNECT);
                startService(intent);
                serviceStarted=true;
                String server_host = ((EditText)findViewById(R.id.server_ip_txt)).getText()+"";
                // String message = enviarDatosGET(server_host);
                //Toast.makeText(this,message,Toast.LENGTH_LONG).show();
                //listOfMessages.add(message);
                //((ListView)findViewById(R.id.messages_list_view)).setAdapter(adapter);
                //adapter.notifyDataSetChanged();

            }
        });
        initializeDataBase();
        initializeGPSManager();
        initializeOSM();
        initializeBroadcastManagerForSocketIO();
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, listOfMessages);
    }

    public void initializeDataBase(){
        try{
            appDatabase= Room.
                    databaseBuilder(this,AppDatabase.class,
                            "app-database").
                    fallbackToDestructiveMigration().build();
        }catch (Exception error){
            Toast.makeText(this,error.getMessage(),Toast.LENGTH_LONG).show();
        }
        //Ubicacion ubicacion = new com.example.myfirstapplication.model.Ubicacion();

    }

    public void initializeGPSManager(){
        gpsManager=new GPSManager(this,this);
        gpsManager.initializeLocationManager();
    }

    public void initializeBroadcastManagerForSocketIO(){
        broadcastManagerForSocketIO=new BroadcastManager(this,
                SocketManagementService.
                        SOCKET_SERVICE_CHANNEL,this);

    }


    /*#############   CONEXION WEB SERVICE  ################*/

    public String enviarDatosGET(String ip){
        URL url= null;
        String linea = "";
        int respuesta = 0;
        StringBuilder resul =  null;
        String endpoint="http://127.0.0.1:8080/WebServiceREST/resources/server";
        String methodType="GET";
        String action="";

        try{
            url=new URL(endpoint+"/"+action);
            HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();
            //urlConnection.setRequestMethod("POST");
            respuesta = urlConnection.getResponseCode();

            resul = new StringBuilder();

            if (respuesta == HttpURLConnection.HTTP_OK){
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                while ((linea=reader.readLine())!=null){
                    resul.append(linea);
                }
            }

        }catch(Exception error) {
            System.out.println(error.getMessage());
            Toast.makeText(this,error.getMessage(),Toast.LENGTH_LONG).show();
        }
        Toast.makeText(this,resul.toString()+"",Toast.LENGTH_LONG).show();
        return  resul.toString();

    }


    /*#############   BASE DE DATOS DAO   ################*/

    /**
     * Este metodo debe ir en el register
     * @param userName
     * @param userEmail
     * @param userPassword
     */
    public void createUser(String userName, String userEmail,String userPassword){
        final User user=new User();
        user.username=userName;
        user.email=userEmail;
        user.password=userPassword;
        try {
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    appDatabase.UserDao().insertAll(user);
                }
            });

        }catch (Exception error){
            Toast.makeText(this,error.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    /*#############   OVERRIDE   ################*/

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            broadcastManagerForSocketIO.sendBroadcast(SocketManagementService.CLIENT_TO_SERVER_MESSAGE,"test");

        } else if (id == R.id.nav_gallery) {
            createUser("asaad","asaad@uninorte.edu.co","12345");

        } else if (id == R.id.nav_slideshow) {
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    int amount=appDatabase.UserDao().getAll().size();
                }
            });


        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void needPermissions() {
        this.requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION},
                1001);
    }

    @Override
    public void locationHasBeenReceived(final Location location) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((TextView)findViewById(R.id.latitude_text_view)).setText(location.getLatitude()+"");
                ((TextView)findViewById(R.id.longitude_text_view)).setText(location.getLongitude()+"");
                if(map!=null)
                setMapCenter(location);

            }
        });

        if(serviceStarted)
            if(broadcastManagerForSocketIO!=null){
                broadcastManagerForSocketIO.sendBroadcast(
                        SocketManagementService.CLIENT_TO_SERVER_MESSAGE,
                        user+": "+location.getLatitude()+" / "+location.getLongitude());
            }
    }


    @Override
    public void gpsErrorHasBeenThrown(final Exception error) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder=
                        new AlertDialog.
                                Builder(getApplicationContext());
                builder.setTitle("GPS Error")
                        .setMessage(error.getMessage())
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //TODO
                            }
                        });
                builder.show();
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==1001){
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,
                        "Thanks!!",Toast.LENGTH_SHORT).show();
                gpsManager.startGPSRequesting();
            }

        }
        if(requestCode==1002){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                initializeOSM();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


    }

    public void initializeOSM(){
        try{
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    !=PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,
                        new String[]{
                        Manifest.permission.
                                WRITE_EXTERNAL_STORAGE},1002);

                return;
            }
            Context ctx = getApplicationContext();
            Configuration.getInstance().load(ctx,
                    PreferenceManager.
                            getDefaultSharedPreferences(ctx));
            map = (MapView) findViewById(R.id.map);
            map.setTileSource(TileSourceFactory.MAPNIK);
            this.mLocationOverlay =
                    new MyLocationNewOverlay(
                            new GpsMyLocationProvider(
                                    this),map);
            this.mLocationOverlay.enableMyLocation();
            map.getOverlays().add(this.mLocationOverlay);
        }catch (Exception error){
            Toast.makeText(this,error.getMessage(),Toast.LENGTH_SHORT).show();

        }
    }

    public void setMapCenter(Location location){
        IMapController mapController =
                map.getController();
        mapController.setZoom(9.5);
        GeoPoint startPoint = new GeoPoint(
                location.getLatitude(), location.getLongitude());
        mapController.setCenter(startPoint);
    }


    @Override
    public void MessageReceivedThroughBroadcastManager(final String channel,final String type, final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                showMessages(message);
            }
        });

    }

    public void sendBroadcastMessage(String message){
        if(serviceStarted)
            if(broadcastManagerForSocketIO!=null){
                broadcastManagerForSocketIO.sendBroadcast(
                        SocketManagementService.CLIENT_TO_SERVER_MESSAGE,
                        message);
            }
    }

    public void showMessages(String message){
        listOfMessages.add( message);
        listViewMessages = ((ListView)findViewById(R.id.messages_list_view));
        listViewMessages.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        listViewMessages.setSelection(listOfMessages.size() - 1);
    }

    @Override
    public void ErrorAtBroadcastManager(Exception error) {

    }

    @Override
    protected void onDestroy() {
        if(broadcastManagerForSocketIO!=null){
            broadcastManagerForSocketIO.unRegister();
        }
        super.onDestroy();
    }

    public void DrawPointsUsers(ArrayList<CurrentLocation> users) {
        //Recibe puntos gps y los dibuja //
        ArrayList<OverlayItem> offline = new ArrayList<>();
        ArrayList<OverlayItem> online = new ArrayList<>();
        for (CurrentLocation u : users) {
            if (u.status.equals("0")) {
                offline.add(new OverlayItem("User: "+u.username + ", " + u.lastSeen,
                        "", new GeoPoint(Double.valueOf(u.lat), Double.valueOf(u.lon)))); // Lat/Lon decimal degrees
            }else if (u.status.equals("1")){
                online.add(new OverlayItem("User: "+u.username + ", " + u.lastSeen,
                        "", new GeoPoint(Double.valueOf(u.lat), Double.valueOf(u.lon))));
            }
        }

        //the overlay
        ItemizedOverlayWithFocus<OverlayItem> offlineOverlay = new ItemizedOverlayWithFocus<>(offline,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                        //do something
                        return true;
                    }

                    @Override
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        return false;
                    }
                }, this.getApplicationContext());
        offlineOverlay.setFocusItemsOnTap(true);
        offlineOverlay.setMarkerBackgroundColor(Color.RED);
        offlineOverlay.setMarkerDescriptionForegroundColor(Color.WHITE);
        offlineOverlay.setMarkerTitleForegroundColor(Color.WHITE);
        ItemizedOverlayWithFocus<OverlayItem> onlineOverlay = new ItemizedOverlayWithFocus<>(online,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                        //do something
                        return true;
                    }

                    @Override
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        return false;
                    }
                }, this.getApplicationContext());
        onlineOverlay.setFocusItemsOnTap(true);
        onlineOverlay.setMarkerBackgroundColor(Color.GREEN);
        map.getOverlays().clear();
        map.getOverlays().add(offlineOverlay);
        map.getOverlays().add(onlineOverlay);

    }
    public void DrawPointsHistory(ArrayList<Ubicacion> coordenadasHistoricas, String username) {
        ArrayList<OverlayItem> markers = new ArrayList<>();

        for ( Ubicacion c : coordenadasHistoricas) {
            markers.add(new OverlayItem("User: "+username+ ", " + c.lastSeen,
                    "", new GeoPoint(Double.valueOf(c.lat), Double.valueOf(c.lon)))); // Lat/Lon decimal degrees
        }

        //the overlay
        ItemizedOverlayWithFocus<OverlayItem> markersOverlay = new ItemizedOverlayWithFocus<>(markers,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                        //do something
                        return true;
                    }

                    @Override
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        return false;
                    }
                }, this.getApplicationContext());
        markersOverlay.setFocusItemsOnTap(true);
        map.getOverlays().clear();
        map.getOverlays().add(markersOverlay);
    }


}
