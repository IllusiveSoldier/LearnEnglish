package knack.college.learnenglish.model.database;

import java.util.HashMap;

import static knack.college.learnenglish.model.database.BaseQueries.GUID;
import static knack.college.learnenglish.model.database.BaseQueries.OUID;


public class DictionaryContract {

    public static abstract class Dictionary {
        public static final String TABLE_NAME = "DICTIONARY";
        public static final String ENGLISH_WORD = "ENGLISH_WORD";
        public static final String TRANSLATE_WORD = "TRANSLATE_WORD";

        private static final HashMap<String, String> createTableParams;

        static {
            createTableParams = new HashMap<String, String>();
            createTableParams.put(OUID, "INTEGER PRIMARY KEY ASC");
            createTableParams.put(GUID, "VARCHAR(50)");
            createTableParams.put(ENGLISH_WORD, "VARCHAR(255)");
            createTableParams.put(TRANSLATE_WORD, "VARCHAR(255)");
        }

        public static StringBuilder getCreateTableQuery() {
            return BaseQueries.getCreateTableQuery(TABLE_NAME, createTableParams);
        }
    }

    public static abstract class DictionaryTrainingStatistic {
        public static final String TABLE_NAME = "DICTIONARY_TRAINING_STATISTIC";
        public static final String IS_CORRECT = "IS_CORRECT";
        public static final String TYPE = "TYPE";
        public static final String WORD_GUID = "WORD_GUID";

        private static final HashMap<String, String> createTableParams;

        static {
            createTableParams = new HashMap<String, String>();
            createTableParams.put(OUID, "INTEGER PRIMARY KEY ASC");
            createTableParams.put(GUID, "VARCHAR(50)");
            createTableParams.put(TYPE, "VARCHAR(50)");
            createTableParams.put(IS_CORRECT, "INTEGER");
            createTableParams.put(WORD_GUID, "VARCHAR(50)");
        }

        public static StringBuilder getCreateTableQuery() {
            return BaseQueries.getCreateTableQuery(TABLE_NAME, createTableParams);
        }
    }

    public static abstract class DictionaryTrainingDateStatistic {
        public static final String TABLE_NAME = "DICTIONARY_TRAINING_DATE_STATISTIC";
        public static final String TRAINING_GUID = "TRAINING_GUID";
        public static final String TRAINING_DATE = "TRAINING_DATE";

        private static final HashMap<String, String> createTableParams;

        static {
            createTableParams = new HashMap<String, String>();
            createTableParams.put(OUID, "INTEGER PRIMARY KEY ASC");
            createTableParams.put(GUID, "VARCHAR(50)");
            createTableParams.put(TRAINING_GUID, "VARCHAR(255)");
            createTableParams.put(TRAINING_DATE, "INTEGER");
        }

        public static StringBuilder getCreateTableQuery() {
            return BaseQueries.getCreateTableQuery(TABLE_NAME, createTableParams);
        }
    }
}
