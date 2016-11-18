package com.example.edwin.ed_mobile;

import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv_hw = (TextView) findViewById(R.id.tv_hello_world);
        String now = DateUtils.formatDateTime(getApplicationContext(), (new Date()).getTime(), DateFormat.FULL);
        tv_hw.setText(getString(R.string.hello_world) + "\n" + "Date :" + now);
    }

    public void Clickhere(View v) {

        Toast.makeText(getApplicationContext(), getString(R.string.msg), Toast.LENGTH_LONG).show();

    }

    public void ChangeDate(View v) {
        final TextView tv_hw = (TextView) findViewById(R.id.tv_hello_world);
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = (month % 12) + 1; //parce que la date déconne
                tv_hw.setText(getString(R.string.hello_world) + "\n" + "Date :" + dayOfMonth + "/" + month + "/" + year);
            }
        };
        DatePickerDialog dpd = new DatePickerDialog(this, listener, 2016, 11, 07);
        dpd.show();
    }

    public void Search(View v){

        EditText edit =  (EditText) findViewById(R.id.web_text);
        String recherche = edit.getText().toString();

        Intent web = new Intent(Intent.ACTION_WEB_SEARCH );
        web.putExtra(SearchManager.QUERY, recherche);
        startActivity(web);
    }

    public void Notif_Me(View v) {
        Notif_Myself();
    }

    public void Notif_Myself() {
        int notif_id = 001;
        Toast.makeText(getApplicationContext(), "Envoyé", Toast.LENGTH_LONG).show();

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle(getString(R.string.notification_title));
        mBuilder.setSmallIcon(R.drawable.ic_not);
        mBuilder.setContentText(getString(R.string.notification_desc));

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notif_id, mBuilder.build());
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.marecherche) {
            Toast.makeText(getApplicationContext(), "Recherche depuis l'action bar !", Toast.LENGTH_LONG).show();
        }
        if (id == R.id.icone) {
            Intent seconde = new Intent(getApplicationContext(), SecondeActivity.class);
            startActivity(seconde);
        }

        if (id == R.id.position) {
            Intent local = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=Paris"));
            startActivity(local);
        }
        return super.onOptionsItemSelected(item);
    }

}