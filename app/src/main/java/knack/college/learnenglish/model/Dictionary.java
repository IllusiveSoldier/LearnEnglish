package knack.college.learnenglish.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.UUID;

import knack.college.learnenglish.exceptions.MoreMaxSymbols;
import knack.college.learnenglish.exceptions.NoEnglishWord;
import knack.college.learnenglish.exceptions.NoRussianWord;
import knack.college.learnenglish.model.database.DictionaryDatabaseHelper;

import static knack.college.learnenglish.model.Constant.ExceptionMessage.NO_ENGLISH_WORD_EXCEPTION_MESSAGE;
import static knack.college.learnenglish.model.Constant.ExceptionMessage.NO_RUSSIAN_WORD_EXCEPTION_MESSAGE;
import static knack.college.learnenglish.model.Constant.ExceptionMessage.WORD_MORE_MAX_SYMBOLS_EXCEPTION_MESSAGE;
import static knack.college.learnenglish.model.database.DictionaryContract.Dictionary.DICTIONARY_TABLE_NAME;
import static knack.college.learnenglish.model.database.DictionaryContract.Dictionary.ENGLISH_WORD_COLUMN_NAME;
import static knack.college.learnenglish.model.database.DictionaryContract.Dictionary.GUID_COLUMN_NAME;
import static knack.college.learnenglish.model.database.DictionaryContract.Dictionary.TRANSLATE_WORD_COLUMN_NAME;

/** Класс, описывающий словарь */
public class Dictionary {

    private Context context;

    public Dictionary() {}

    public Dictionary(Context c) {
        context = c;
    }


    /** Метод, который добавляет слово на иностранном языке и слово-перевод в базу данных */
    public void addWordWithTranslate(String englishWord, String translate) throws Exception{
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
            }
        }
    }
}
