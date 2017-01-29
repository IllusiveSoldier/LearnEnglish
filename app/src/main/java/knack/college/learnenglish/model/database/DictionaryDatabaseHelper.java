package knack.college.learnenglish.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static knack.college.learnenglish.model.database.DictionaryContract.Dictionary.getCreateTableQuery;
import static knack.college.learnenglish.model.database.DictionaryContract.Dictionary.getDropTableQuery;


public class DictionaryDatabaseHelper extends SQLiteOpenHelper {

    public final static String DATABASE_NAME = "dictionary.db";
    public final static int DATABASE_VERSION = 1;

    public DictionaryDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(getCreateTableQuery().toString());
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(getDropTableQuery().toString());
        onCreate(sqLiteDatabase);
    }
}
