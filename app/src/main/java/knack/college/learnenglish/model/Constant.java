package knack.college.learnenglish.model;

import static knack.college.learnenglish.model.database.DictionaryContract.Dictionary
        .OUID_COLUMN_NAME;

public final class Constant {

    public static final String FRAGMENT_CODE = "fragmentCode";
    public static final String NUMBER_WORDS = "numberWords";
    public static final String CORRECT_ANSWER = "correctAnswer";
    public static final String WRONG_ANSWER = "wrongAnswer";

    public static final String BRAINSTORM_TASK_TITLE = "Brainstorm";

    public final class DatabaseKeywords {
        public final static String CREATE_TABLE_SQL_KEYWORD = "CREATE TABLE ";
        public final static String DROP_TABLE_SQL_KEYWORD = "DROP TABLE ";
        public final static String IF_SQL_KEYWORD = "IF ";
        public final static String EXISTS_TABLE_SQL_KEYWORD = "EXISTS ";
        public final static String INTEGER_SQL_KEYWORD = "INTEGER ";
        public final static String VARCHAR_SQL_KEYWORD = "VARCHAR ";
        public final static String DATETIME_SQL_KEYWORD = "DATETIME ";
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
        public final static String WHERE_SQL_KEYWORD = "WHERE ";
    }

    public final class SpecialCharacters {
        public static final String NEW_LINE = "\n";
        public static final String TAB = "\t";
        public static final String SPACE = " ";
        public static final String COMMA = ", ";
        public static final String LEFT_BRACE = "( ";
        public static final String LEFT_BRACE_WITHOUT_SPACE = "(";
        public static final String RIGHT_BRACE = ") ";
        public static final String RIGHT_BRACE_WITHOUT_SPACE = ")";
        public static final String SEMICOLON = "; ";
    }

    public final class DatabaseNumberValues {
        public static final String VARCHAR_255 = "VARCHAR(255)";
        public static final String VARCHAR_50 = "VARCHAR(50)";
    }

    final class Number {
         final static int MAX_WORD_LENGTH = 255;
    }

    public final class Translator {
        public static final String EN_RU = "en-ru";
        public static final String RU_EN = "ru-en";
    }
}
