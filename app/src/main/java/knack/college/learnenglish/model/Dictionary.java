package knack.college.learnenglish.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import knack.college.learnenglish.R;
import knack.college.learnenglish.exceptions.EmptyData;
import knack.college.learnenglish.exceptions.MoreMaxSymbols;
import knack.college.learnenglish.exceptions.NoEnglishWord;
import knack.college.learnenglish.exceptions.NoRussianWord;
import knack.college.learnenglish.model.database.BaseQueries;
import knack.college.learnenglish.model.database.DictionaryContract;
import knack.college.learnenglish.model.database.LearnEnglishDatabaseHelper;
import knack.college.learnenglish.model.toasts.ToastWrapper;

import static knack.college.learnenglish.model.database.DictionaryContract.Dictionary.DICTIONARY_TABLE_NAME;
import static knack.college.learnenglish.model.database.DictionaryContract.Dictionary.ENGLISH_WORD_COLUMN_NAME;
import static knack.college.learnenglish.model.database.DictionaryContract.Dictionary.GUID_COLUMN_NAME;
import static knack.college.learnenglish.model.database.DictionaryContract.Dictionary.LAST_TRAINING_DATE;
import static knack.college.learnenglish.model.database.DictionaryContract.Dictionary.OUID_COLUMN_NAME;
import static knack.college.learnenglish.model.database.DictionaryContract.Dictionary.TRANSLATE_WORD_COLUMN_NAME;
import static knack.college.learnenglish.model.database.DictionaryContract.Dictionary.getCountRowsInTableQuery;
import static knack.college.learnenglish.model.database.DictionaryContract.Dictionary.getDeleteAllRowsInTableQuery;
import static knack.college.learnenglish.model.database.DictionaryContract.Dictionary.getDropDictionaryTableQuery;

/** Класс, описывающий словарь */
public class Dictionary extends BaseQueries {

    private static final String WORD_MORE_MAX_SYMBOLS_EXCEPTION_MESSAGE =
            "Слово, которое вы ввели больше 255 символов.";
    private static final String NO_ENGLISH_WORD_EXCEPTION_MESSAGE =
            "Первое слово для словаря должно содержать в себе только латинские символы";
    private static final String NO_RUSSIAN_WORD_EXCEPTION_MESSAGE =
            "Слово для перевода должно содержать в себе только русские символы";
    private static final String NO_DATA_EXCEPTION_MESSAGE = "Не хватает данных для заполнения";

    private Context context;

    private ToastWrapper toast;
    private Validator validator;
    private LearnEnglishDatabaseHelper helper;
    private SQLiteDatabase database;

    public Dictionary(Context c) throws Exception {
        super(c);

        context = c;

        initializeToast();
        initializeValidator();
        initializeDatabaseHelper();
        initializeDatabase();
    }

    private void initializeToast() {
        try {
            toast = new ToastWrapper(context);
        } catch (Exception e) {
            Toast.makeText(
                    context,
                    context.getResources().getString(R.string.error_message_failed_initialize_toast),
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    private void initializeValidator() {
        try {
            validator = new Validator();
        } catch (Exception e) {
            toast.show(
                    context.getResources().getString(R.string
                            .error_message_failed_initialize_validator)
            );
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

    /** Метод, который добавляет слово на иностранном языке и слово-перевод в словарь */
    public void addWordWithTranslate(String englishWord, String translate) throws Exception {
        if (englishWord != null && translate != null) {
            if (!englishWord.isEmpty() && !translate.isEmpty()) {
                if (!validator.isWordMoreMaxSymbols(englishWord)
                        && !validator.isWordMoreMaxSymbols(translate)) {
                    if (validator.isEnglishCharactersInWord(englishWord)) {
                        if (validator.isRussianCharactersInWord(translate)) {
                            if (isExistTable(null, DictionaryContract.Dictionary
                                    .DICTIONARY_TABLE_NAME)) {
                                ContentValues values = new ContentValues();

                                values.put(GUID_COLUMN_NAME, UUID.randomUUID().toString());
                                values.put(ENGLISH_WORD_COLUMN_NAME, englishWord);
                                values.put(TRANSLATE_WORD_COLUMN_NAME, translate);

                                database.insert(DICTIONARY_TABLE_NAME, null, values);
                            } else {
                                helper.onCreate(database);

                                ContentValues values = new ContentValues();

                                values.put(GUID_COLUMN_NAME, UUID.randomUUID().toString());
                                values.put(ENGLISH_WORD_COLUMN_NAME, englishWord);
                                values.put(TRANSLATE_WORD_COLUMN_NAME, translate);

                                database.insert(DICTIONARY_TABLE_NAME, null, values);
                            }
                        } else throw new NoRussianWord(NO_RUSSIAN_WORD_EXCEPTION_MESSAGE);
                    } else throw new NoEnglishWord(NO_ENGLISH_WORD_EXCEPTION_MESSAGE);
                } else throw new MoreMaxSymbols(WORD_MORE_MAX_SYMBOLS_EXCEPTION_MESSAGE);
            } else throw new EmptyData(NO_DATA_EXCEPTION_MESSAGE);
        }
    }

    /** Метод, который удаляет словарь */
    public void delete() throws Exception {
        if (isExistTable(null, DictionaryContract.Dictionary.DICTIONARY_TABLE_NAME))
            database.execSQL(getDropDictionaryTableQuery().toString());
    }

    /** Метод, который очищяет словарь*/
    public void clear() throws Exception {
        if (isExistTable(null, DictionaryContract.Dictionary.DICTIONARY_TABLE_NAME))
            database.execSQL(getDeleteAllRowsInTableQuery().toString());
    }

    /** Метод, который курсор со всеми строками и столбцами из словаря */
    private Cursor getAllWordsCursor() throws Exception {
        if (isExistTable(null, DictionaryContract.Dictionary.DICTIONARY_TABLE_NAME)) {
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
        } else return null;
    }

    /** Метод, который возвращает список слов со словаря */
    public List getAllWordsList() throws Exception {
        List<WordFromDictionary> allWordsList = new ArrayList<>();

        Cursor cursor = null;

        try {
            if (isExistTable(null, DictionaryContract.Dictionary.DICTIONARY_TABLE_NAME)) {
                cursor = getAllWordsCursor();

                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        WordFromDictionary word = new WordFromDictionary();
                        word.setOuid(cursor.getString(cursor.getColumnIndex(OUID_COLUMN_NAME)));
                        word.setGuid(cursor.getString(cursor.getColumnIndex(GUID_COLUMN_NAME)));
                        word.setEnglishWord(
                                cursor.getString(cursor.getColumnIndex(ENGLISH_WORD_COLUMN_NAME))
                        );
                        word.setTranslateWord(
                                cursor.getString(cursor.getColumnIndex(TRANSLATE_WORD_COLUMN_NAME))
                        );

                        allWordsList.add(word);
                    }
                }
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return allWordsList;
    }

    /** Метод, который возвращает количество слов в словаре */
    public int getNumberOfWords() throws Exception {
        int count = 0;
        Cursor cursor = null;
        try {
            if (isExistTable(null, DictionaryContract.Dictionary.DICTIONARY_TABLE_NAME)) {
                cursor = database.rawQuery(getCountRowsInTableQuery().toString(), null);
                cursor.moveToFirst();
                count = cursor.getInt(0);
            }
        } catch (Exception e) {
            count = 0;
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return count;
    }

    /** Метод, который удаляет слово из словаря */
    public void deleteWord(String wordGuid) throws Exception {
        if (wordGuid != null && !wordGuid.isEmpty()) {
            if (isExistTable(null, DictionaryContract.Dictionary.DICTIONARY_TABLE_NAME)) {
                database.delete(
                        DICTIONARY_TABLE_NAME,
                        GUID_COLUMN_NAME + " = " + "'" + wordGuid + "'",
                        null
                );
            }
        }
    }

    /** Метод, который восстанавливает слово в словаре */
    public void restoreWord(WordFromDictionary wordFromDictionary) throws Exception {
        if (wordFromDictionary != null) {
            ContentValues values = new ContentValues();
            values.put(OUID_COLUMN_NAME, wordFromDictionary.getOuid());
            values.put(GUID_COLUMN_NAME, wordFromDictionary.getGuid());
            values.put(ENGLISH_WORD_COLUMN_NAME, wordFromDictionary.getEnglishWord());
            values.put(TRANSLATE_WORD_COLUMN_NAME, wordFromDictionary.getTranslateWord());

            database.insert(DICTIONARY_TABLE_NAME, null, values);
        }
    }
}
