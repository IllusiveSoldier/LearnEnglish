package knack.college.learnenglish.model.statistic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import knack.college.learnenglish.R;
import knack.college.learnenglish.model.RecordsFromDictionaryTrainingStatistic;
import knack.college.learnenglish.model.database.BaseQueries;
import knack.college.learnenglish.model.database.DictionaryContract;
import knack.college.learnenglish.model.database.LearnEnglishDatabaseHelper;
import knack.college.learnenglish.model.toasts.ToastWrapper;

import static knack.college.learnenglish.model.database.DictionaryContract.DictionaryTrainingStatistic.IS_CORRECT;
import static knack.college.learnenglish.model.database.DictionaryContract.DictionaryTrainingStatistic.TYPE;
import static knack.college.learnenglish.model.database.DictionaryContract.DictionaryTrainingStatistic.WORD_GUID;

public class DictionaryTrainingStatistic extends BaseQueries {
    // Context
    private Context context;
    // Toast
    private ToastWrapper toast;
    // Database helper
    private LearnEnglishDatabaseHelper helper;
    // Database instance
    private SQLiteDatabase database;

    public DictionaryTrainingStatistic(Context context) throws Exception {
        super(context);

        this.context = context;

        initializeToast();
        initializeDatabaseHelper();
        initializeDatabase();
    }

    private void initializeToast() {
        try {
            toast = new ToastWrapper(context.getApplicationContext());
        } catch (Exception e) {
            Toast.makeText(
                    context.getApplicationContext(),
                    context.getString(R.string.error_message_failed_initialize_toast),
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    private void initializeDatabaseHelper() {
        try {
            helper = new LearnEnglishDatabaseHelper(context);
        } catch (Exception e) {
            toast.show(
                    context.getResources().getString(R.string
                            .error_message_failed_initialize_database_helper)
            );
        }
    }

    private void initializeDatabase() {
        try {
            database = helper.getWritableDatabase();
        } catch (Exception e) {
            toast.show(
                    context.getResources().getString(R.string
                            .error_message_failed_initialize_database)
            );
        }
    }

    public void addRecord(String type, HashMap<String, String> words, long trainingDate) {
        try {
            if (type != null && !type.isEmpty() && words != null && words.size() > 0) {
                if (isExistTable(null, DictionaryContract.DictionaryTrainingStatistic
                        .TABLE_NAME)) {
                    String guid = UUID.randomUUID().toString();
                    ArrayList<ContentValues> valuesArrayList =
                            getFillingContentValuesStatistic(words, guid, type);
                    for (ContentValues values : valuesArrayList) {
                        database.insert(
                                DictionaryContract.DictionaryTrainingStatistic.TABLE_NAME,
                                null,
                                values
                        );
                    }
                    if (isExistTable(null, DictionaryContract.DictionaryTrainingDateStatistic
                            .TABLE_NAME)) {
                        database.insert(
                                DictionaryContract.DictionaryTrainingDateStatistic.TABLE_NAME,
                                null,
                                getFillingContentValuesStatisticDate(guid, trainingDate)
                        );
                    } else {
                        database.execSQL(DictionaryContract.DictionaryTrainingDateStatistic
                                .getCreateTableQuery().toString());
                        database.insert(
                                DictionaryContract.DictionaryTrainingDateStatistic.TABLE_NAME,
                                null,
                                getFillingContentValuesStatisticDate(guid, trainingDate)
                        );
                    }
                } else {
                    // Если таблицы нет - создаём
                    database.execSQL(
                            DictionaryContract.DictionaryTrainingStatistic.
                                    getCreateTableQuery().toString()
                    );
                    String guid = UUID.randomUUID().toString();
                    ArrayList<ContentValues> valuesArrayList =
                            getFillingContentValuesStatistic(words, guid, type);
                    for (ContentValues values : valuesArrayList) {
                        database.insert(
                                DictionaryContract.DictionaryTrainingStatistic.TABLE_NAME,
                                null,
                                values
                        );
                    }
                }
            }
        } catch (Exception e) {
            toast.show(
                    context.getResources()
                            .getString(R.string.error_message_failed_add_statistic_record)
            );
        }
    }

    private ArrayList<ContentValues> getFillingContentValuesStatistic(HashMap<String, String> values,
                                                           String guid, String type) {
        if (values != null && values.size() > 0) {
            ArrayList<ContentValues> valuesArrayList = new ArrayList<ContentValues>();
            for (Map.Entry<String, String> entry : values.entrySet()) {
                ContentValues contentValues = new ContentValues();
                if (entry.getKey() != null && entry.getValue() != null) {
                    contentValues.put(GUID, guid);
                    contentValues.put(
                            DictionaryContract.DictionaryTrainingStatistic.WORD_GUID,
                            entry.getKey()
                    );
                    contentValues.put(
                            DictionaryContract.DictionaryTrainingStatistic.IS_CORRECT,
                            entry.getValue()
                    );
                    contentValues.put(
                            DictionaryContract.DictionaryTrainingStatistic.TYPE,
                            type
                    );
                    valuesArrayList.add(contentValues);
                }
            }

            return valuesArrayList;
        } else return new ArrayList<ContentValues>();
    }

    private ContentValues getFillingContentValuesStatisticDate(String guid, long trainingDate) {
        if (guid != null && !guid.isEmpty()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(GUID, UUID.randomUUID().toString());
            contentValues.put(
                    DictionaryContract.DictionaryTrainingDateStatistic.TRAINING_GUID,
                    guid
            );
            contentValues.put(
                    DictionaryContract.DictionaryTrainingDateStatistic.TRAINING_DATE,
                    String.valueOf(trainingDate)
            );

            return contentValues;
        } else return new ContentValues();
    }

    /**
     * Очищает таблицу со статистикой с тренировкой по словарю
     */
    public void clear() {
        try {
            if (isExistTable(null, DictionaryContract.DictionaryTrainingStatistic.TABLE_NAME)) {
                truncateTable(DictionaryContract.DictionaryTrainingStatistic.TABLE_NAME);
            }
        } catch (Exception e) {
            toast.show(
                    context.getResources().getString(R.string
                            .error_message_failed_clear_dictionary_training_statistic)
            );
        }
    }

    /**
     * Удаляет таблицу со статистикой по словарю
     */
    public void delete() {
        try {
            if (isExistTable(null, DictionaryContract.DictionaryTrainingStatistic.TABLE_NAME)) {
                dropTable(DictionaryContract.DictionaryTrainingStatistic.TABLE_NAME);
            }
            if (isExistTable(null, DictionaryContract.DictionaryTrainingDateStatistic.TABLE_NAME)) {
                dropTable(DictionaryContract.DictionaryTrainingDateStatistic.TABLE_NAME);
            }
        } catch (Exception e) {
            toast.show(
                    context.getResources().getString(R.string
                            .error_message_failed_delete_dictionary_training_statistic)
            );
        }
    }

    /**
     * Обновляет запись со статистикой по словарю.
     * @param tableName - Название таблицы
     * @param values - В каком столбце какое значение обновить.
     * @param condition - Предложение WHERE
     */
    public void updateRecord(String tableName, ContentValues values, String condition) {
        try {
            updateValue(DictionaryContract.DictionaryTrainingStatistic.TABLE_NAME, null, null);
        } catch (Exception e) {
            toast.show(
                    context.getResources().getString(R.string
                    .error_message_failed_update_dictionary_training_statistic_records)
            );
        }
    }

    /**
     * Возвращает все записи со статистикой с тренировкой по словарю.
     * @return - Все записи со статистикой.
     * @throws Exception
     */
    public ArrayList<RecordsFromDictionaryTrainingStatistic> getRecords() throws Exception {
        ArrayList<RecordsFromDictionaryTrainingStatistic> records = new ArrayList<>();

        Cursor cursor = null;

        try {
            if (isExistTable(null, DictionaryContract.DictionaryTrainingStatistic.TABLE_NAME)) {
                cursor = getRecordsCursor();

                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        RecordsFromDictionaryTrainingStatistic record =
                                new RecordsFromDictionaryTrainingStatistic();
                        record.setOuid(cursor.getInt(cursor.getColumnIndex(OUID)));
                        record.setGuid(cursor.getString(cursor.getColumnIndex(GUID)));
                        record.setWordGuid(cursor.getString(cursor.getColumnIndex(WORD_GUID)));
                        record.setType(cursor.getString(cursor.getColumnIndex(TYPE)));
                        record.setIsCorrect(cursor.getInt(cursor.getColumnIndex(IS_CORRECT)));

                        records.add(record);
                    }
                }
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return records;
    }

    private Cursor getRecordsCursor() throws Exception {
        if (isExistTable(null, DictionaryContract.DictionaryTrainingStatistic.TABLE_NAME)) {
            return database.query(
                    DictionaryContract.DictionaryTrainingStatistic.TABLE_NAME,
                    new String[] {
                            OUID,
                            GUID,
                            WORD_GUID,
                            TYPE,
                            IS_CORRECT
                    },
                    null,
                    null,
                    null,
                    null,
                    null
            );
        } else return null;
    }
}
