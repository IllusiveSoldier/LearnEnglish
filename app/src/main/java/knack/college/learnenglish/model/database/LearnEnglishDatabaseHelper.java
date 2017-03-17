package knack.college.learnenglish.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static knack.college.learnenglish.model.database.DictionaryContract.Dictionary.getCreateDictionaryTableQuery;
import static knack.college.learnenglish.model.database.DictionaryContract.Dictionary.getDropDictionaryTableQuery;


public class LearnEnglishDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "learn_english.db";
    public static final int DATABASE_VERSION = 1;

    public LearnEnglishDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(getCreateDictionaryTableQuery().toString());
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(getDropDictionaryTableQuery().toString());
        onCreate(sqLiteDatabase);
    }
}
