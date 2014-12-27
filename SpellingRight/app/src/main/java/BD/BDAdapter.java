package BD;

/**
 * Created by douraid on 27/12/14.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


public class BDAdapter {
    DatabaseHelper	DBHelper;
    Context			context;
    SQLiteDatabase	db;

    public BDAdapter(Context context){
        this.context = context;
        DBHelper = new DatabaseHelper(context);
    }

    public class DatabaseHelper extends SQLiteOpenHelper{

        Context			context;

        public DatabaseHelper(Context context) {
            super(context, "words", null, 1);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table words (_id integer primary key autoincrement, "
                    + "path text not null, "
                    + "wordspell text not null"
                    + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Toast.makeText(context, "Mise à jour de la Base de données version "+oldVersion+" vers "+newVersion, Toast.LENGTH_SHORT).show();
            db.execSQL("DROP TABLE IF EXISTS words");		onCreate(db);
        }

    }

    public BDAdapter open(){
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        db.close();
    }

    public void Truncate(){
        db.execSQL("DELETE FROM words");
    }

    public long insererUnword(String path,  String wordspell){
        ContentValues values = new ContentValues();
        values.put("path", path);
        values.put("wordspell", wordspell);
        return db.insert("words", null, values);
    }


    public boolean supprimerword(long id){
        return db.delete("words", "_id="+id, null)>0;
    }

    public Cursor recupererLaListeDeswords(){
        return db.query("words", new String[]{
                "_id",
                "path",
                "wordspell"}, null, null, null, null, null);
    }

    public Cursor getpathandword(long index) {
        return db.query("words", new String[]{
                "path",
                "wordspell"}, "words._id=" + index, null, null, null, null);
    }

}
