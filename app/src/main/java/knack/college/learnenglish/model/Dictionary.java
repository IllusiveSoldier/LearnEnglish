package knack.college.learnenglish.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import knack.college.learnenglish.exceptions.EmptyData;
import knack.college.learnenglish.exceptions.MoreMaxSymbols;
import knack.college.learnenglish.exceptions.NoEnglishWord;
import knack.college.learnenglish.exceptions.NoRussianWord;
import knack.college.learnenglish.model.database.DictionaryContract;
import knack.college.learnenglish.model.database.LearnEnglishDatabaseHelper;

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
import static knack.college.learnenglish.model.database.DictionaryContract.Dictionary.getCountRowsInTableQuery;
import static knack.college.learnenglish.model.database.DictionaryContract.Dictionary.getDeleteAllRowsInTableQuery;
import static knack.college.learnenglish.model.database.DictionaryContract.Dictionary.getDropDictionaryTableQuery;

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
                            LearnEnglishDatabaseHelper helper = new LearnEnglishDatabaseHelper(context);

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

    /** Метод, который добавляет слово на иностранном языке и слово-перевод в словарь */
    public void addWordWithTranslate(HashMap<String, String> words) throws Exception {
        Validator validator = new Validator();

        for (Map.Entry<String, String> entry : words.entrySet()) {
            String englishWord = entry.getKey();
            String translate = entry.getValue();
            if (englishWord != null && translate != null) {
                if (!englishWord.isEmpty() && !translate.isEmpty()) {
                    if (!validator.isWordMoreMaxSymbols(englishWord)
                            && !validator.isWordMoreMaxSymbols(translate)) {
                        if (validator.isEnglishCharactersInWord(englishWord)) {
                            if (validator.isRussianCharactersInWord(translate)) {
                                LearnEnglishDatabaseHelper helper =
                                        new LearnEnglishDatabaseHelper(context);

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
    }

    /** Метод, который восстанавливает словарь на дефолтные слова */
    public void defaultWords() throws Exception {
        addWordWithTranslate(new DefaultWords().getDefaultWords());
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
                database.delete(DICTIONARY_TABLE_NAME, GUID_COLUMN_NAME + " = " + "'"
                        + wordGuid + "'", null);
            }
        }
    }

    private final class DefaultWords {
        private final HashMap<String, String> defaultWords = new HashMap<>();

        // Конструкторы
        DefaultWords() {
            initializeDefaultWords();
        }

        private void initializeDefaultWords() {
            defaultWords.put("Hello", "Привет");
            defaultWords.put("Good morning", "Доброе утро");
            defaultWords.put("Goodbye", "Пока");
            defaultWords.put("Sun", "Солнце");
            defaultWords.put("Day", "День");
            defaultWords.put("Night", "Ночь");
            defaultWords.put("Phone", "Телефон");
            defaultWords.put("Language", "Язык");
            defaultWords.put("Learn", "Изучать");
            defaultWords.put("Number", "Номер");
        }

        HashMap<String, String> getDefaultWords() {
            return defaultWords;
        }
    }
}
