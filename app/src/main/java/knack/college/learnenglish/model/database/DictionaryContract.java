package knack.college.learnenglish.model.database;

import android.provider.BaseColumns;

/** Класс-контракт, определяющий параметры таблицы */
public class DictionaryContract {

    public static abstract class Dictionary implements BaseColumns {
        /** Название таблицы для словаря */
        public static final String DICTIONARY_TABLE_NAME = "DICTIONARY";
        /** Первичный ключ */
        public static final String OUID_COLUMN_NAME = "OUID";
        /** Глоабльный уникальный идентификатор */
        public static final String GUID_COLUMN_NAME = "GUID";
        /** Слово на английском */
        public static final String ENGLISH_WORD_COLUMN_NAME = "ENGLISH_WORD";
        /** Слово-перевод */
        public static final String TRANSLATE_WORD_COLUMN_NAME = "TRANSLATE_WORD";

        /** Метод, который возвращает текст запрсоа на создание таблицы */
        public static StringBuilder getCreateDictionaryTableQuery() {
            return new StringBuilder("CREATE TABLE ").append(DICTIONARY_TABLE_NAME)
                    .append("(")
                    .append(OUID_COLUMN_NAME).append(" INTEGER PRIMARY KEY ASC, ")
                    .append(GUID_COLUMN_NAME).append(" VARCHAR(255), ")
                    .append(ENGLISH_WORD_COLUMN_NAME).append(" VARCHAR(255), ")
                    .append(TRANSLATE_WORD_COLUMN_NAME).append(" VARCHAR(255)")
                    .append(");");
        }
    }
}
