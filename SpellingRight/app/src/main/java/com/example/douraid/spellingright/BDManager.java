package com.example.douraid.spellingright;

import android.app.ListActivity;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import BD.BDAdapter;


public class BDManager extends ListActivity implements OnClickListener {

    /**
     * Called when the activity is first created.
     */

    BDAdapter db;
    EditText text;
    EditText text1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);
        getListView().setOnCreateContextMenuListener(this);
        findViewById(R.id.button1).setOnClickListener(this);
        text = ((EditText) findViewById(R.id.text1));
        text1 = ((EditText) findViewById(R.id.text2));
        db = new BDAdapter(this);
        db.open();
        DataBind();
    }

    @Override // Création du menu principal
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 100, 0, "Tout effacer");
        return true;
    }

    @Override // Selection d'un item du menu
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 100:
                db.Truncate();
                DataBind();
                break;
        }
        return true;
    }

    @Override // Selection d'un item de la liste
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Cursor cursor = (Cursor) l.getAdapter().getItem(position);
        String wordspell = cursor.getString(cursor.getColumnIndex("wordspell"));
        Toast.makeText(this, "Item id " + id + " : " + wordspell, Toast.LENGTH_SHORT).show();
        super.onListItemClick(l, v, position, id);
    }

    @Override // Creation du menu contextuel
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Actions");
        menu.add(0, 100, 0, "Supprimer");
        menu.add(0, 200, 0, "Editer");
        menu.add(0, 300, 0, "Lire");
    }


    @Override // Selection d'un item du menu contextuel
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case 100:
                db.supprimerword(info.id);
                DataBind();
                break;
            case 200:
                Toast.makeText(this, "TODO", Toast.LENGTH_SHORT).show();
                break;
            case 300:
                Log.w("DBmanager","nabdou bismeleh");
                Cursor c = db.getpathandword(info.id);
                Log.w("DBmanager","kamelna hmd");
                startManagingCursor(c);
                if (c != null)
                    Toast.makeText(this, c.getString(c.getColumnIndex("path")), Toast.LENGTH_SHORT).show();
                else Toast.makeText(this, "null ara", Toast.LENGTH_SHORT).show();
                Uri myUri = Uri.parse(c.getString(c.getColumnIndex("path")));
                try {
                    MediaPlayer mp = new MediaPlayer();
                    mp.setDataSource(this, myUri);
                    mp.prepare();
                    Log.w("Learn", "hana bech nabdou");
                    mp.start();
                    Log.w("Learn", "oumour cv");
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "file doesn't exist", Toast.LENGTH_SHORT).show();
                }
                break;

        }
        return true;
    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }

    public void DataBind() {
        Cursor c = db.recupererLaListeDeswords();
        startManagingCursor(c);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.list_item2, c, new String[]{"path", "wordspell"},
                new int[]{R.id.textpath, R.id.textwordspell});
        setListAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (!text.getText().toString().equals("") && !text1.getText().toString().equals("")) {
            db.insererUnword(text.getText().toString(), text1.getText().toString());
            DataBind();
        } else Toast.makeText(this, "you have'nt enter any thing", Toast.LENGTH_SHORT).show();
    }

}
