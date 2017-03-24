package knack.college.learnenglish.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.UUID;

import knack.college.learnenglish.R;
import knack.college.learnenglish.exceptions.DictionaryLEException;
import knack.college.learnenglish.model.database.BaseQueries;
import knack.college.learnenglish.model.database.DictionaryContract;
import knack.college.learnenglish.model.database.LearnEnglishDatabaseHelper;
import knack.college.learnenglish.model.toasts.ToastWrapper;

import static knack.college.learnenglish.model.database.DictionaryContract.Dictionary.ENGLISH_WORD;
import static knack.college.learnenglish.model.database.DictionaryContract.Dictionary.TABLE_NAME;
import static knack.college.learnenglish.model.database.DictionaryContract.Dictionary.TRANSLATE_WORD;

/** Класс, описывающий словарь */
public class Dictionary extends BaseQueries {

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
                            if (isExistTable(null, TABLE_NAME)) {
                                if (!isExistValue(TABLE_NAME, null,
                                        ENGLISH_WORD, englishWord)) {
                                    ContentValues values = new ContentValues();

                                    values.put(GUID, UUID.randomUUID().toString());
                                    values.put(ENGLISH_WORD, englishWord);
                                    values.put(TRANSLATE_WORD, translate);

                                    database.insert(TABLE_NAME, null, values);
                                } else throw new DictionaryLEException(context.getResources()
                                        .getString(R.string.error_message_word_is_already));
                            } else {
                                database.execSQL(
                                        DictionaryContract.Dictionary.getCreateTableQuery()
                                                .toString()
                                );

                                ContentValues values = new ContentValues();

                                values.put(GUID, UUID.randomUUID().toString());
                                values.put(ENGLISH_WORD, englishWord);
                                values.put(TRANSLATE_WORD, translate);

                                database.insert(TABLE_NAME, null, values);
                            }
                        } else throw new DictionaryLEException(context.getResources()
                                .getString(R.string.error_message_no_russian_word));
                    } else throw new DictionaryLEException(context.getResources()
                            .getString(R.string.error_message_no_english_word));
                } else throw new DictionaryLEException(context.getResources()
                        .getString(R.string.error_message_word_more_max_symbols));
            } else throw new DictionaryLEException(context.getResources()
                    .getString(R.string.error_message_no_date));
        }
    }

    /** Метод, который удаляет словарь */
    public void delete() throws Exception {
        if (isExistTable(null, TABLE_NAME))
            dropTable(TABLE_NAME);
    }

    /** Метод, который очищяет словарь*/
    public void clear() throws Exception {
        if (isExistTable(null, TABLE_NAME))
            truncateTable(TABLE_NAME);
    }

    /** Метод, который курсор со всеми строками и столбцами из словаря */
    private Cursor getAllWordsCursor() throws Exception {
        if (isExistTable(null, TABLE_NAME)) {
            String[] columnSelectList = {
                    OUID,
                    GUID,
                    ENGLISH_WORD,
                    TRANSLATE_WORD
            };

            return database.query(
                    TABLE_NAME,
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
    public ArrayList<WordFromDictionary> getAllWordsList() throws Exception {
        ArrayList<WordFromDictionary> allWordsList = new ArrayList<>();

        Cursor cursor = null;

        try {
            if (isExistTable(null, DictionaryContract.Dictionary.TABLE_NAME)) {
                cursor = getAllWordsCursor();

                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        WordFromDictionary word = new WordFromDictionary();
                        word.setOuid(cursor.getString(cursor.getColumnIndex(OUID)));
                        word.setGuid(cursor.getString(cursor.getColumnIndex(GUID)));
                        word.setEnglishWord(
                                cursor.getString(cursor.getColumnIndex(ENGLISH_WORD))
                        );
                        word.setTranslateWord(
                                cursor.getString(cursor.getColumnIndex(TRANSLATE_WORD))
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
        try {
            if (isExistTable(null, DictionaryContract.Dictionary.TABLE_NAME)) {
                count = getNumberOfRows(TABLE_NAME);
            }
        } catch (Exception e) {
            count = 0;
        }

        return count;
    }

    /** Метод, который удаляет слово из словаря */
    public void deleteWord(String wordGuid) throws Exception {
        if (wordGuid != null && !wordGuid.isEmpty()) {
            if (isExistTable(null, DictionaryContract.Dictionary.TABLE_NAME)) {
                database.delete(
                        TABLE_NAME,
                        GUID + " = " + "'" + wordGuid + "'",
                        null
                );
            }
        }
    }

    /** Метод, который восстанавливает слово в словаре */
    public void restoreWord(WordFromDictionary wordFromDictionary) throws Exception {
        if (wordFromDictionary != null) {
            ContentValues values = new ContentValues();
            values.put(OUID, wordFromDictionary.getOuid());
            values.put(GUID, wordFromDictionary.getGuid());
            values.put(ENGLISH_WORD, wordFromDictionary.getEnglishWord());
            values.put(TRANSLATE_WORD, wordFromDictionary.getTranslateWord());

            database.insert(TABLE_NAME, null, values);
        }
    }
}
