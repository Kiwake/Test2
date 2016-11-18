package com.example.edwin.ed_mobile;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.steamcrafted.loadtoast.LoadToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SecondeActivity extends AppCompatActivity {
    private Context context;
    private TextView tv;
    private RecyclerView rview;
    private LoadToast lt;

    //public static final String BIERS_UPDATE = "com.octip.cours.inf4042_11.BIERS_UPDATE";

    public class BierAdapter extends RecyclerView.Adapter<BierAdapter.BierHolder>{
        private JSONArray biers;

        public BierAdapter(JSONArray biers){
            this.biers=biers;
        }

        @Override
        public BierHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_bier_element, rview, false);
            BierHolder bierHolder = new BierHolder(view);
            return bierHolder;
        }

        @Override
        public void onBindViewHolder(BierHolder bierHolderolder, int position) {
            try {
                JSONObject item = biers.getJSONObject(position);
                String jsonname = item.getString("title");
                String jsonnote = item.getString("director");
                //String jsonname = item.getString("name");
                //bierHolderolder.name.setText(jsonname);
                bierHolderolder.title.setText(jsonname + " " + jsonnote);
               // bierHolderolder.title.setText(jsonnote);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return biers.length();
        }

        public void setNewBiere(){
            this.notifyDataSetChanged();
        }

        public class BierHolder extends RecyclerView.ViewHolder{
            public TextView title;
            //public TextView name;

            public BierHolder(View view) {
                super(view);
                this.title = (TextView) view.findViewById(R.id.rv_bier_element_name);
                //this.name = (TextView) view.findViewById(R.id.rv_bier_element_name);
            }

        }
    }




    public BroadcastReceiver BierUpdate=new BroadcastReceiver(){
        public void onReceive(Context context,Intent intent){
            // Display message from GetBiersServices
            //BierAdapter adapview = (BierAdapter) rview.getAdapter().setNewBiere();
            Bundle b=intent.getExtras();
            if(b!=null){
                //tv.setText(b.getString(GetBiersServices.EXTRA_MESSAGE));
                lt.success();
                Notif_DL();
            }
            //getBiersFromFile();
        }
    };


    public void Notif_DL() {
        int notif_id = 002;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle("Download complete");
        mBuilder.setSmallIcon(R.drawable.ic_dl);
        mBuilder.setContentText("Your download is complete !");
        Notification note = mBuilder.build();
        note.defaults |= Notification.DEFAULT_VIBRATE;
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notif_id, note);
    }

     //PERMET DE LIRE UN FICHER JSON, A VOIR SI UTILE PLUS TARD
    public JSONArray getBiersFromFile(){
        try{
            //InputStream is = new FileInputStream(Environment.getExternalStorageDirectory()+"/"+"bieres.json");
            InputStream is = new FileInputStream(Environment.getExternalStorageDirectory()+"/"+"films.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            return new JSONArray(new String(buffer, "UTF-8"));
        }catch (IOException e){
            e.printStackTrace();
            return new JSONArray();
        }catch (JSONException e){
            e.printStackTrace();
            return new JSONArray();
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seconde);
        tv=(TextView)findViewById(R.id.txtmessage);
    }

    //private static final String BIERS_UPDATE = "com.octip.cours.inf4042_11.BIERS_UPDATE";


    public void AffichageRview(View v){
        rview = (RecyclerView)findViewById(R.id.rv_biere);
        rview.setLayoutManager(new LinearLayoutManager(this));
        rview.setAdapter(new BierAdapter(getBiersFromFile()));
    }

    //DOWNLOAD PART

    public void Service(View v) {
        context=this;
        Intent newIntent=new Intent(context,GetBiersServices.class);
        lt=new LoadToast(context);
        newIntent.setAction(GetBiersServices.ACTION_DOWNLOAD);
        // Start Download Service
        //tv.setText("Downloading...");
        lt.setText("Downloading...");
        lt.show();
        Toast.makeText(getApplicationContext(), "Downloading " + GetBiersServices.urlString, Toast.LENGTH_LONG).show();
        context.startService(newIntent);
        //GetBiersServices.startActionBiers(this);
    }



    protected void onResume(){
        super.onResume();
        // Register receiver to get message from DownloadService
        registerReceiver(BierUpdate, new IntentFilter(GetBiersServices.ACTION_DOWNLOAD));

    }

    protected void onPause(){
        super.onPause();
        // Unregister the receiver
        unregisterReceiver(BierUpdate);

    }

}
