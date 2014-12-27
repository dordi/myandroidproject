package com.example.douraid.spellingright;

/**
 * Created by douraid on 27/12/14.
 */

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import java.util.Random;

public class Learn extends Activity implements OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        findViewById(R.id.btn1).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                int x = new Random().nextInt(4) + 1;
                Uri myUri = Uri.parse("android.resource://com.example.douraid.spellingright/raw/w" + x);
                Toast.makeText(this,"random is " + x,Toast.LENGTH_SHORT).show();
                try {
                    MediaPlayer mp = new MediaPlayer();
                    mp.setDataSource(this,myUri);
                    mp.prepare();
                    Log.w("Learn", "hana bech nabdou");
                    mp.start();
                    Log.w("Learn", "oumour cv");
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this,"file doesn't exist",Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }
}
