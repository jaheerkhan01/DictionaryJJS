package com.example.dictionaryjjs;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Dictionary;

public class MainActivityAPI extends AppCompatActivity {
    String url;
    private TextView showDef;
    private EditText enterword;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_api);
        showDef = findViewById(R.id.showDef);
        enterword = findViewById(R.id.enterword);
        button = findViewById(R.id.findBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(MainActivityAPI.this, "67", Toast.LENGTH_SHORT).show();
                sendrequestonclick(view);
            }
        });

    }

    public String dictionaryEntries() {
        final String language = "en-gb";
        final String word = enterword.getText().toString();
        final String fields = "definitions";
        final String strictMatch = "false";
        final String word_id = word.toLowerCase();
        return "https://od-api.oxforddictionaries.com:443/api/v2/entries/" + language + "/" + word_id + "?" + "fields=" + fields + "&strictMatch=" + strictMatch;
    }

    public void sendrequestonclick(View V) {
        dictionaryrequest Dr = new dictionaryrequest(this, showDef);
        url = dictionaryEntries();
//        Toast.makeText(this, Toast.LENGTH_SHORT).show()show;
        showDef.setText(String.valueOf(url));
        Dr.execute(url);
    }
}