package knack.college.learnenglish.model;

import static knack.college.learnenglish.model.database.DictionaryContract.Dictionary.OUID_COLUMN_NAME;

public final class Constant {

    public final static String ALL_WORDS_FROM_DICTIONARY = "allWordsFromDictionary";

    public final class DatabaseKeywords {
        public final static String CREATE_TABLE_SQL_KEYWORD = "CREATE TABLE ";
        public final static String DROP_TABLE_SQL_KEYWORD = "DROP TABLE ";
        public final static String IF_SQL_KEYWORD = "IF ";
        public final static String EXISTS_TABLE_SQL_KEYWORD = "EXISTS ";
        public final static String INTEGER_SQL_KEYWORD = "INTEGER ";
        public final static String VARCHAR_SQL_KEYWORD = "VARCHAR ";
        public final static String PRIMARY_KEY_SQL_KEYWORD = "PRIMARY KEY ";
        public final static String ASC_SQL_KEYWORD = "ASC ";
        public final static String DESC_SQL_KEYWORD = "DESC ";
        public final static String DELETE_SQL_KEYWORD = "DELETE ";
        public final static String FROM_SQL_KEYWORD = "FROM ";
        public final static String VACUUM_SQL_KEYWORD = "VACUUM ";
        public final static String DESC_OUID_SQL_KEYWORD = OUID_COLUMN_NAME + " DESC";
        public final static String ASC_OUID_SQL_KEYWORD = OUID_COLUMN_NAME + " ASC";
        public final static String SELECT_SQL_KEYWORD = "SELECT ";
        public final static String COUNT_SQL_KEYWORD = "count";
        public final static String STAR_SQL_KEYWORD = "*";
    }

    public final class SpecialCharacters {
        public final static String NEW_LINE = "\n";
        public final static String TAB = "\t";
        public final static String SPACE = " ";
        public final static String COMMA = ", ";
        public final static String LEFT_BRACE = "( ";
        public final static String LEFT_BRACE_WITHOUT_SPACE = "(";
        public final static String RIGHT_BRACE = ") ";
        public final static String RIGHT_BRACE_WITHOUT_SPACE = ")";
        public final static String SEMICOLON = "; ";
    }

    public final class DatabaseNumberValues {
        public final static String VARCHAR_255 = "VARCHAR(255)";
        public final static String VARCHAR_50 = "VARCHAR(50)";
        public final static String VARCHAR_36 = "VARCHAR(36)";
    }

    final class Number {
         final static int MAX_WORD_LENGTH = 255;
    }

    final class ExceptionMessage {
        final static String WORD_MORE_MAX_SYMBOLS_EXCEPTION_MESSAGE =
                "Слово, которое вы ввели больше 255 символов.";
        final static String NO_ENGLISH_WORD_EXCEPTION_MESSAGE =
                "Первое слово для словаря должно содержать в себе только латинские символы";
        final static String NO_RUSSIAN_WORD_EXCEPTION_MESSAGE =
                "Слово для перевода должно содержать в себе только русские символы";
        final static String NO_DATA_EXCEPTION_MESSAGE = "Не хватает данных для заполнения";
    }

    public final class KeysForDebug {
        public final static String ERROR_KEY_FOR_DEBUG = "ERROR_LEARN_ENGLISH";
    }

    public final class Dialog {
        public final static String UNIQUE_NAME_ADD_WORD_TO_DATABASE_DIALOG = "addToDatabaseDialog";
    }
}
