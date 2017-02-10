package knack.college.learnenglish.model;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import knack.college.learnenglish.R;
import knack.college.learnenglish.model.database.LearnEnglishDatabaseHelper;

import static knack.college.learnenglish.model.Constant.KeysForDebug.ERROR_KEY_FOR_DEBUG;
import static knack.college.learnenglish.model.database.DictionaryContract.Dictionary.GUID_COLUMN_NAME;
import static knack.college.learnenglish.model.database.DictionaryContract.Dictionary.OUID_COLUMN_NAME;
import static knack.college.learnenglish.model.database.StatisticDictionaryTrainingContract.StatisticDictionaryTraining.CORRECT_ANSWER;
import static knack.college.learnenglish.model.database.StatisticDictionaryTrainingContract.StatisticDictionaryTraining.COUNT_OF_WORDS_IN_DICTIONARY;
import static knack.college.learnenglish.model.database.StatisticDictionaryTrainingContract.StatisticDictionaryTraining.STATISTIC_DICTIONARY_TRAINING;
import static knack.college.learnenglish.model.database.StatisticDictionaryTrainingContract.StatisticDictionaryTraining.WRONG_ANSWER;
import static knack.college.learnenglish.model.database.StatisticDictionaryTrainingContract.StatisticDictionaryTraining.getDeleteAllRowsInStatisticDictionaryTrainingTableQuery;

/** Класс, который реализует методы по доступу к статистике приложения */
public class LearnEnglishStatistic {
    /** Activity */
    private Activity activity;
    /** Количество слов в словаре */
    private int numberOfWordsInDictionary;
    /** Custom'ный toast */
    private LearnEnglishToast toast;

    // Конструкторы
    public LearnEnglishStatistic(Activity act) {
        activity = act;

        initializeToast();
    }

    /** Метод, который возвращает количество слов в словаре */
    public int getNumberOfWordsInDictionary() {
        try {
            Dictionary dictionary = new Dictionary(activity.getApplicationContext());
            numberOfWordsInDictionary = dictionary.getNumberOfWords();
        } catch (Exception ex) {
            toast.show(ex.getMessage(), R.mipmap.ic_sentiment_very_dissatisfied_black_24dp);
        }


        return numberOfWordsInDictionary;
    }

    /** Метод, который добавляет в статистику прохождения испытания "Прогон по словарю" данные */
    public void addInfoToStatisticDictionaryTraining(int countWordsInDictionary,
                                                     int countCorrectAnswer,
                                                     int countWrongAnswer) throws Exception {
        LearnEnglishDatabaseHelper helper = new LearnEnglishDatabaseHelper(activity
                .getApplicationContext());

        SQLiteDatabase database = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(GUID_COLUMN_NAME, UUID.randomUUID().toString());
        values.put(COUNT_OF_WORDS_IN_DICTIONARY, countWordsInDictionary);
        values.put(CORRECT_ANSWER, countCorrectAnswer);
        values.put(WRONG_ANSWER, countWrongAnswer);

        database.insert(STATISTIC_DICTIONARY_TRAINING, null, values);
    }

    /** Метод, который очищяет статистику */
    public void clear() throws Exception {
        LearnEnglishDatabaseHelper helper = new LearnEnglishDatabaseHelper(activity
                .getApplicationContext());
        SQLiteDatabase database = helper.getWritableDatabase();

        database.execSQL(getDeleteAllRowsInStatisticDictionaryTrainingTableQuery().toString());
    }

    /** Метод, который курсор со всеми строками и столбцами из статистики по испытанию
     * "Прогон по словарю" */
    private Cursor getAllItemsCursor() throws Exception {
        LearnEnglishDatabaseHelper helper = new LearnEnglishDatabaseHelper(activity
                .getApplicationContext());
        SQLiteDatabase database = helper.getReadableDatabase();

        String[] columnSelectList = {
                OUID_COLUMN_NAME,
                GUID_COLUMN_NAME,
                COUNT_OF_WORDS_IN_DICTIONARY,
                CORRECT_ANSWER,
                WRONG_ANSWER
        };


        return database.query(
                STATISTIC_DICTIONARY_TRAINING,
                columnSelectList,
                null,
                null,
                null,
                null,
                null
        );
    }

    /** Метод, который возвращает список item'ов из статистики по испытанию "Прогон по словарю" */
    public List<StatisticDictionaryTrainingItem> getAllItemsList() throws Exception {
        List<StatisticDictionaryTrainingItem> allItemsList = new ArrayList<>();

        Cursor cursor = null;

        try {
            cursor = getAllItemsCursor();

            while (cursor.moveToNext()) {
                StatisticDictionaryTrainingItem item = new StatisticDictionaryTrainingItem();
                item.setOuid(cursor.getString(cursor.getColumnIndex(OUID_COLUMN_NAME)));
                item.setGuid(cursor.getString(cursor.getColumnIndex(GUID_COLUMN_NAME)));
                item.setCountWordsInDictionary(cursor.getString(cursor
                        .getColumnIndex(COUNT_OF_WORDS_IN_DICTIONARY)));
                item.setCountCorrectAnswer(cursor.getString(cursor
                        .getColumnIndex(CORRECT_ANSWER)));
                item.setCountWrongAnswer(cursor.getString(cursor
                        .getColumnIndex(WRONG_ANSWER)));

                allItemsList.add(item);
            }
        } catch (Exception ex) {
            Log.d(ERROR_KEY_FOR_DEBUG, ex.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }


        return allItemsList;
    }

    /** Метод, который иницилизирует Toast */
    private void initializeToast() {
        toast = new LearnEnglishToast(activity);
    }
}
