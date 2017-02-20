package knack.college.learnenglish.model.statistic;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import knack.college.learnenglish.model.database.LearnEnglishDatabaseHelper;

import static knack.college.learnenglish.model.database.StatisticDictionaryTrainingContract.StatisticDictionaryTraining.getDeleteAllRowsInStatisticDictionaryTrainingTableQuery;


/** Базовый класс для ведения статистики */
public abstract class LEStatistic {
    public abstract void addRecord() throws Exception;
    public void clear(Context context) throws Exception {
        LearnEnglishDatabaseHelper helper = new LearnEnglishDatabaseHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();

        database.execSQL(getDeleteAllRowsInStatisticDictionaryTrainingTableQuery().toString());
    }
}
