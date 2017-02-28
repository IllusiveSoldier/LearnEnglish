package knack.college.learnenglish.model;

import static knack.college.learnenglish.model.database.DictionaryContract.Dictionary
        .OUID_COLUMN_NAME;

public final class Constant {

    public final static String ALL_WORDS_FROM_DICTIONARY = "allWordsFromDictionary";
    public final static String ALL_WORDS_FROM_DICTIONARY_SECOND_EDITION =
            "allWordsFromDictionarySecondEdition";
    public final static String FORGOTTEN_WORDS_FROM_DICTIONARY = "forgottenWordsFromDictionary";
    public final static String FORGOTTEN_WORDS_FROM_DICTIONARY_SECOND_EDITION =
            "forgottenWordsFromDictionarySecondEdition";
    public final static String ALL_WORDS_FROM_DICTIONARY_TRAINING_TITLE =
            "Прогон по всем словам";
    public final static String ALL_WORDS_FROM_DICTIONARY_TRAINING_TITLE_SECOND_EDITION =
            "Прогон по всем словам (без ввода слов)";
    public final static String FORGOTTEN_WORDS_FROM_DICTIONARY_TITLE =
            "Прогон по нетренированным словам";
    public final static String FORGOTTEN_WORDS_FROM_DICTIONARY_TITLE_SECOND_EDITION =
            "Прогон по нетренированным словам (без ввода слов)";
    public final static String FRAGMENT_CODE = "fragmentCode";
    public final static String NUMBER_WORDS = "numberWords";
    public final static String CORRECT_ANSWER = "correctAnswer";
    public final static String WRONG_ANSWER = "wrongAnswer";

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
    }

    final class Number {
         final static int MAX_WORD_LENGTH = 255;
    }

    public final class Translator {
        public final static String EN_RU = "en-ru";
        public final static String RU_EN = "ru-en";
    }
}
