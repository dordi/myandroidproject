package com.example.douraid.spellingright;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;

public class Play_me extends Activity implements View.OnClickListener{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playme);
        findViewById(R.id.learn).setOnClickListener(this);
        findViewById(R.id.manDB).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.learn :
                i = new Intent(Play_me.this,Learn.class);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                startActivity(i);
                break;
            case R.id.manDB :
                i = new Intent(this.getBaseContext(), BDManager.class);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                startActivity(i);
                break;
        }
    }
}