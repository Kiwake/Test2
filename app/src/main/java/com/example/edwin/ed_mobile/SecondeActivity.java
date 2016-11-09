package com.example.edwin.ed_mobile;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SecondeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seconde);
    }

    public void Search(View v){

        EditText edit =  (EditText) findViewById(R.id.web_text);
        String recherche = edit.getText().toString();

        Intent web = new Intent(Intent.ACTION_WEB_SEARCH );
        web.putExtra(SearchManager.QUERY, recherche);
        startActivity(web);
    }
}
