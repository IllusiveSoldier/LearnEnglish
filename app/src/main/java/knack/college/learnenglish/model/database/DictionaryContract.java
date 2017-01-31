package knack.college.learnenglish.model.database;

import android.provider.BaseColumns;

import static knack.college.learnenglish.model.Constant.DatabaseKeywords.ASC_SQL_KEYWORD;
import static knack.college.learnenglish.model.Constant.DatabaseKeywords.CREATE_TABLE_SQL_KEYWORD;
import static knack.college.learnenglish.model.Constant.DatabaseKeywords.DELETE_SQL_KEYWORD;
import static knack.college.learnenglish.model.Constant.DatabaseKeywords.DROP_TABLE_SQL_KEYWORD;
import static knack.college.learnenglish.model.Constant.DatabaseKeywords.EXISTS_TABLE_SQL_KEYWORD;
import static knack.college.learnenglish.model.Constant.DatabaseKeywords.FROM_SQL_KEYWORD;
import static knack.college.learnenglish.model.Constant.DatabaseKeywords.IF_SQL_KEYWORD;
import static knack.college.learnenglish.model.Constant.DatabaseKeywords.INTEGER_SQL_KEYWORD;
import static knack.college.learnenglish.model.Constant.DatabaseKeywords.PRIMARY_KEY_SQL_KEYWORD;
import static knack.college.learnenglish.model.Constant.DatabaseKeywords.VACUUM_SQL_KEYWORD;
import static knack.college.learnenglish.model.Constant.DatabaseNumberValues.VARCHAR_255;
import static knack.college.learnenglish.model.Constant.SpecialCharacters.COMMA;
import static knack.college.learnenglish.model.Constant.SpecialCharacters.LEFT_BRACE;
import static knack.college.learnenglish.model.Constant.SpecialCharacters.NEW_LINE;
import static knack.college.learnenglish.model.Constant.SpecialCharacters.RIGHT_BRACE;
import static knack.college.learnenglish.model.Constant.SpecialCharacters.SEMICOLON;
import static knack.college.learnenglish.model.Constant.SpecialCharacters.SPACE;
import static knack.college.learnenglish.model.Constant.SpecialCharacters.TAB;

/** Класс-контракт, определяющий параметры таблицы */
public class DictionaryContract {

    public DictionaryContract() {}


    public static abstract class Dictionary implements BaseColumns {
        /** Название таблицы для словаря */
        public final static String DICTIONARY_TABLE_NAME = "DICTIONARY";
        /** Первичный ключ */
        public final static String OUID_COLUMN_NAME = "OUID";
        /** Глоабльный уникальный идентификатор */
        public final static String GUID_COLUMN_NAME = "GUID";
        /** Слово на английском */
        public final static String ENGLISH_WORD_COLUMN_NAME = "ENGLISH_WORD";
        /** Слово-перевод */
        public final static String TRANSLATE_WORD_COLUMN_NAME = "TRANSLATE_WORD";

        /** Метод, который возвращает текст запрсоа на создание таблицы */
        public static StringBuilder getCreateTableQuery() {
            StringBuilder createTableQuery = new StringBuilder();

            createTableQuery.append(CREATE_TABLE_SQL_KEYWORD).append(DICTIONARY_TABLE_NAME).append(NEW_LINE)
                            .append(LEFT_BRACE).append(NEW_LINE)
                            .append(TAB).append(OUID_COLUMN_NAME).append(SPACE)
                                .append(INTEGER_SQL_KEYWORD).append(PRIMARY_KEY_SQL_KEYWORD)
                                .append(ASC_SQL_KEYWORD).append(COMMA).append(NEW_LINE)
                            .append(TAB).append(GUID_COLUMN_NAME).append(SPACE).append(VARCHAR_255).append(COMMA)
                                .append(NEW_LINE)
                            .append(TAB).append(ENGLISH_WORD_COLUMN_NAME).append(SPACE).append(VARCHAR_255)
                                .append(COMMA).append(NEW_LINE)
                            .append(TAB).append(TRANSLATE_WORD_COLUMN_NAME).append(SPACE).append(VARCHAR_255)
                                .append(NEW_LINE)
                            .append(RIGHT_BRACE).append(SEMICOLON);


            return createTableQuery;
        }


        /** Метод, который возвращает текст запрсоа на удаление таблицы */
        public static StringBuilder getDropTableQuery() {
            StringBuilder dropTableQuery = new StringBuilder();

            dropTableQuery.append(DROP_TABLE_SQL_KEYWORD).append(IF_SQL_KEYWORD)
                                .append(EXISTS_TABLE_SQL_KEYWORD).append(DICTIONARY_TABLE_NAME);


            return dropTableQuery;
        }


        /** Метод, который удаляет все записи из таблицы */
        public static StringBuilder getDeleteAllRowsInTableQuery() {
            StringBuilder getDeleteAllRowsInTableQuery = new StringBuilder();

            getDeleteAllRowsInTableQuery.append(DELETE_SQL_KEYWORD).append(FROM_SQL_KEYWORD)
                    .append(DICTIONARY_TABLE_NAME).append(SEMICOLON)
                    .append(NEW_LINE)
                    .append(VACUUM_SQL_KEYWORD).append(SEMICOLON);


            return getDeleteAllRowsInTableQuery;
        }
    }
}
