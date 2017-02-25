package knack.college.learnenglish.model.statistic;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import knack.college.learnenglish.model.database.LearnEnglishDatabaseHelper;

import static knack.college.learnenglish.model.database.DictionaryContract.Dictionary.GUID_COLUMN_NAME;
import static knack.college.learnenglish.model.database.StatisticDictionaryTrainingContract.StatisticDictionaryTraining.BEGIN_TRAINING;
import static knack.college.learnenglish.model.database.StatisticDictionaryTrainingContract.StatisticDictionaryTraining.CORRECT_ANSWER;
import static knack.college.learnenglish.model.database.StatisticDictionaryTrainingContract.StatisticDictionaryTraining.COUNT_OF_WORDS_IN_DICTIONARY;
import static knack.college.learnenglish.model.database.StatisticDictionaryTrainingContract.StatisticDictionaryTraining.STATISTIC_DICTIONARY_TRAINING;
import static knack.college.learnenglish.model.database.StatisticDictionaryTrainingContract.StatisticDictionaryTraining.WRONG_ANSWER;

public class DictionaryTrainingStatistic extends LEStatistic {
    private int wordsCount;
    private int correctAnswer;
    private int wrongAnswer;

    private Activity activity;

    public DictionaryTrainingStatistic(Activity act) {
        activity = act;
    }

    @Override
    public void addRecord() throws Exception {
        LearnEnglishDatabaseHelper helper = new LearnEnglishDatabaseHelper(activity
                .getApplicationContext());

        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        if (wordsCount >= 0 && correctAnswer >= 0 && wrongAnswer >= 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String strDate = sdf.format(new Date());

            values.put(GUID_COLUMN_NAME, UUID.randomUUID().toString());
            values.put(COUNT_OF_WORDS_IN_DICTIONARY, wordsCount);
            values.put(CORRECT_ANSWER, correctAnswer);
            values.put(WRONG_ANSWER, wrongAnswer);
            values.put(BEGIN_TRAINING, strDate);
        }

        database.insert(STATISTIC_DICTIONARY_TRAINING, null, values);
    }

    public int getWordsCount() {
        return wordsCount;
    }

    public void setWordsCount(int wordsCount) {
        this.wordsCount = wordsCount;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public int getWrongAnswer() {
        return wrongAnswer;
    }

    public void setWrongAnswer(int wrongAnswer) {
        this.wrongAnswer = wrongAnswer;
    }

    private Cursor getLastDictionaryTrainingDateCursor() {
        LearnEnglishDatabaseHelper helper = new LearnEnglishDatabaseHelper(activity
                .getApplicationContext());
        SQLiteDatabase database = helper.getWritableDatabase();

        return database.rawQuery("SELECT MAX(date(BEGIN_TRAINING)) AS BEGIN_TRAINING " +
                "FROM STATISTIC_DICTIONARY_TRAINING", null);
    }

    public String getLastDictionaryTrainingDate() throws Exception {
        String lastDate = null;
        Cursor cursor = null;

        try {
            cursor = getLastDictionaryTrainingDateCursor();

            while (cursor.moveToNext()) {
               lastDate = cursor.getString(cursor.getColumnIndex(BEGIN_TRAINING));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return lastDate;
    }
}
