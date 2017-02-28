package knack.college.learnenglish.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import knack.college.learnenglish.exceptions.EmptyData;
import knack.college.learnenglish.exceptions.MoreMaxSymbols;
import knack.college.learnenglish.exceptions.NoEnglishWord;
import knack.college.learnenglish.exceptions.NoRussianWord;
import knack.college.learnenglish.model.database.DictionaryContract;
import knack.college.learnenglish.model.database.LearnEnglishDatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static knack.college.learnenglish.model.Constant.ExceptionMessage.*;
import static knack.college.learnenglish.model.Constant.KeysForDebug.ERROR_KEY_FOR_DEBUG;
import static knack.college.learnenglish.model.database.DictionaryContract.Dictionary.*;

/** Класс, описывающий словарь */
public class Dictionary {

    private Context context;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String currentDate = sdf.format(new Date());

    public Dictionary(Context c) {
        context = c;
    }

    /** Метод, который добавляет слово на иностранном языке и слово-перевод в словарь */
    public void addWordWithTranslate(String englishWord, String translate) throws Exception {
        Validator validator = new Validator();

        if (englishWord != null && translate != null) {
            if (!englishWord.isEmpty() && !translate.isEmpty()) {
                if (!validator.isWordMoreMaxSymbols(englishWord)
                        && !validator.isWordMoreMaxSymbols(translate)) {
                    if (validator.isEnglishCharactersInWord(englishWord)) {
                        if (validator.isRussianCharactersInWord(translate)) {
                            LearnEnglishDatabaseHelper helper = new LearnEnglishDatabaseHelper(context);

                            SQLiteDatabase database = helper.getWritableDatabase();

                            ContentValues values = new ContentValues();
                            values.put(GUID_COLUMN_NAME, UUID.randomUUID().toString());
                            values.put(ENGLISH_WORD_COLUMN_NAME, englishWord);
                            values.put(TRANSLATE_WORD_COLUMN_NAME, translate);

                            database.insert(DICTIONARY_TABLE_NAME, null, values);
                        } else throw new NoRussianWord(NO_RUSSIAN_WORD_EXCEPTION_MESSAGE);
                    } else throw new NoEnglishWord(NO_ENGLISH_WORD_EXCEPTION_MESSAGE);
                } else throw new MoreMaxSymbols(WORD_MORE_MAX_SYMBOLS_EXCEPTION_MESSAGE);
            } else throw new EmptyData(NO_DATA_EXCEPTION_MESSAGE);
        }
    }

    /** Метод, который удаляет словарь */
    public void delete() throws Exception {
        LearnEnglishDatabaseHelper helper = new LearnEnglishDatabaseHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();

        database.execSQL(getDropDictionaryTableQuery().toString());
    }

    /** Метод, который очищяет словарь*/
    public void clear() throws Exception {
        LearnEnglishDatabaseHelper helper = new LearnEnglishDatabaseHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();

        database.execSQL(getDeleteAllRowsInTableQuery().toString());
    }

    /** Метод, который курсор со всеми строками и столбцами из словаря */
    private Cursor getAllWordsCursor() throws Exception {
        LearnEnglishDatabaseHelper helper = new LearnEnglishDatabaseHelper(context);
        SQLiteDatabase database = helper.getReadableDatabase();

        String[] columnSelectList = {
                OUID_COLUMN_NAME,
                GUID_COLUMN_NAME,
                ENGLISH_WORD_COLUMN_NAME,
                TRANSLATE_WORD_COLUMN_NAME,
                LAST_TRAINING_DATE
        };

        return database.query(
                DictionaryContract.Dictionary.DICTIONARY_TABLE_NAME,
                columnSelectList,
                null,
                null,
                null,
                null,
                null
        );
    }

    /** Метод, который возвращает список слов со словаря */
    public List<WordFromDictionary> getAllWordsList() throws Exception {
        List<WordFromDictionary> allWordsList = new ArrayList<>();

        Cursor cursor = null;

        try {
            cursor = getAllWordsCursor();

            while (cursor.moveToNext()) {
                WordFromDictionary word = new WordFromDictionary();
                word.setOuid(cursor.getString(cursor.getColumnIndex(OUID_COLUMN_NAME)));
                word.setGuid(cursor.getString(cursor.getColumnIndex(GUID_COLUMN_NAME)));
                word.setEnglishWord(cursor.getString(cursor.
                        getColumnIndex(ENGLISH_WORD_COLUMN_NAME)));
                word.setTranslateWord(cursor.getString(cursor.
                        getColumnIndex(TRANSLATE_WORD_COLUMN_NAME)));

                allWordsList.add(word);
            }
        } catch (Exception ex) {
            Log.d(ERROR_KEY_FOR_DEBUG, ex.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return allWordsList;
    }

    /** Метод, который возвращает количество слов в словаре */
    public int getNumberOfWords() throws Exception {
        int count;
        LearnEnglishDatabaseHelper helper = new LearnEnglishDatabaseHelper(context);
        SQLiteDatabase database = helper.getReadableDatabase();

        Cursor cursor = database.rawQuery(getCountRowsInTableQuery().toString(), null);
        cursor.moveToFirst();
        count = cursor.getInt(0);
        cursor.close();

        return count;
    }

    /** Метод, который удаляет слово из словаря */
    public void deleteWord(String wordGuid) throws Exception {
        if (wordGuid != null) {
            if (!wordGuid.isEmpty()) {
                LearnEnglishDatabaseHelper helper = new LearnEnglishDatabaseHelper(context);
                SQLiteDatabase database = helper.getWritableDatabase();
                database.delete(
                        DICTIONARY_TABLE_NAME,
                        GUID_COLUMN_NAME + " = " + "'" + wordGuid + "'",
                        null
                );
            }
        }
    }

    /** Метод, который слову сетает последнюю дату тренировки */
    public void setLastTrainingWordDate(String wordGuid) throws Exception {
        LearnEnglishDatabaseHelper helper = new LearnEnglishDatabaseHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        // Current date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(new Date());

        // New value
        ContentValues values = new ContentValues();
        values.put(LAST_TRAINING_DATE, strDate);

        db.update(
            DICTIONARY_TABLE_NAME,
            values,
            GUID_COLUMN_NAME + " = " + "'" + wordGuid + "'",
            null
        );
    }

    /** Метод, который возвращает курсор с словами, в которых не проставлена
     * дата последней тренировки
     * или с момента тренировки прошло больше 1 дня */
    public Cursor getForgottenWordsCursor() throws Exception {
        LearnEnglishDatabaseHelper helper = new LearnEnglishDatabaseHelper(context);
        SQLiteDatabase database = helper.getReadableDatabase();

        return database.rawQuery("SELECT " + OUID_COLUMN_NAME + ", " + GUID_COLUMN_NAME + ", " +
                ENGLISH_WORD_COLUMN_NAME + ", " + TRANSLATE_WORD_COLUMN_NAME + ", " +
                LAST_TRAINING_DATE + " FROM DICTIONARY AS dictionary WHERE julianday('" + currentDate +
                "') - julianday(dictionary.LAST_TRAINING_DATE) >= 1 OR " +
                "dictionary.LAST_TRAINING_DATE IS NULL" , null);
    }

    /** Метод, который возвращает коллекцию слов, в которых не проставлена
     * дата последней тренировки
     * или с момента тренировки прошло больше 1 дня */
    public List<WordFromDictionary> getForgottenWords() throws Exception {
        List<WordFromDictionary> allWordsList = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = getForgottenWordsCursor();

            while (cursor.moveToNext()) {
                WordFromDictionary word = new WordFromDictionary();
                word.setOuid(cursor.getString(cursor.getColumnIndex(OUID_COLUMN_NAME)));
                word.setGuid(cursor.getString(cursor.getColumnIndex(GUID_COLUMN_NAME)));
                word.setEnglishWord(cursor.getString(cursor.
                        getColumnIndex(ENGLISH_WORD_COLUMN_NAME)));
                word.setTranslateWord(cursor.getString(cursor.
                        getColumnIndex(TRANSLATE_WORD_COLUMN_NAME)));

                allWordsList.add(word);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return allWordsList;
    }

    /** Метод, который восстанавливает слово в словаре */
    public void restoreWord(WordFromDictionary wordFromDictionary) throws Exception {
        if (wordFromDictionary != null) {
            LearnEnglishDatabaseHelper helper = new LearnEnglishDatabaseHelper(context);

            SQLiteDatabase database = helper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(OUID_COLUMN_NAME, wordFromDictionary.getOuid());
            values.put(GUID_COLUMN_NAME, wordFromDictionary.getGuid());
            values.put(ENGLISH_WORD_COLUMN_NAME, wordFromDictionary.getEnglishWord());
            values.put(TRANSLATE_WORD_COLUMN_NAME, wordFromDictionary.getTranslateWord());

            database.insert(DICTIONARY_TABLE_NAME, null, values);
        }
    }
}
