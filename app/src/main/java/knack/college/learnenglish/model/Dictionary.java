package knack.college.learnenglish.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import knack.college.learnenglish.exceptions.EmptyData;
import knack.college.learnenglish.exceptions.MoreMaxSymbols;
import knack.college.learnenglish.exceptions.NoEnglishWord;
import knack.college.learnenglish.exceptions.NoRussianWord;
import knack.college.learnenglish.model.database.DictionaryContract;
import knack.college.learnenglish.model.database.DictionaryDatabaseHelper;

import static knack.college.learnenglish.model.Constant.ExceptionMessage.NO_DATA_EXCEPTION_MESSAGE;
import static knack.college.learnenglish.model.Constant.ExceptionMessage.NO_ENGLISH_WORD_EXCEPTION_MESSAGE;
import static knack.college.learnenglish.model.Constant.ExceptionMessage.NO_RUSSIAN_WORD_EXCEPTION_MESSAGE;
import static knack.college.learnenglish.model.Constant.ExceptionMessage.WORD_MORE_MAX_SYMBOLS_EXCEPTION_MESSAGE;
import static knack.college.learnenglish.model.Constant.KeysForDebug.ERROR_KEY_FOR_DEBUG;
import static knack.college.learnenglish.model.database.DictionaryContract.Dictionary.DICTIONARY_TABLE_NAME;
import static knack.college.learnenglish.model.database.DictionaryContract.Dictionary.ENGLISH_WORD_COLUMN_NAME;
import static knack.college.learnenglish.model.database.DictionaryContract.Dictionary.GUID_COLUMN_NAME;
import static knack.college.learnenglish.model.database.DictionaryContract.Dictionary.OUID_COLUMN_NAME;
import static knack.college.learnenglish.model.database.DictionaryContract.Dictionary.TRANSLATE_WORD_COLUMN_NAME;
import static knack.college.learnenglish.model.database.DictionaryContract.Dictionary.getDeleteAllRowsInTableQuery;
import static knack.college.learnenglish.model.database.DictionaryContract.Dictionary.getDropTableQuery;

/** Класс, описывающий словарь */
public class Dictionary {

    private Context context;

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
                            DictionaryDatabaseHelper helper = new DictionaryDatabaseHelper(context);

                            SQLiteDatabase database = helper.getWritableDatabase();

                            ContentValues values = new ContentValues();
                            values.put(GUID_COLUMN_NAME,
                                    UUID.randomUUID().toString());
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
        DictionaryDatabaseHelper helper = new DictionaryDatabaseHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();

        database.execSQL(getDropTableQuery().toString());
    }

    /** Метод, который очищяет словарь*/
    public void clear() throws Exception {
        DictionaryDatabaseHelper helper = new DictionaryDatabaseHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();

        database.execSQL(getDeleteAllRowsInTableQuery().toString());
    }

    /** Метод, который курсор со всеми строками и столбцами из словаря */
    private Cursor getAllWordsCursor() throws Exception {
        DictionaryDatabaseHelper helper = new DictionaryDatabaseHelper(context);
        SQLiteDatabase database = helper.getReadableDatabase();

        String[] columnSelectList = {
                OUID_COLUMN_NAME,
                GUID_COLUMN_NAME,
                ENGLISH_WORD_COLUMN_NAME,
                TRANSLATE_WORD_COLUMN_NAME
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

        try {
            Cursor cursor = getAllWordsCursor();

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
        }


        return allWordsList;
    }
}
