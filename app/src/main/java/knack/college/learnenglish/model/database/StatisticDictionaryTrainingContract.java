package knack.college.learnenglish.model.database;

import android.provider.BaseColumns;

import static knack.college.learnenglish.model.Constant.DatabaseKeywords.ASC_SQL_KEYWORD;
import static knack.college.learnenglish.model.Constant.DatabaseKeywords.CREATE_TABLE_SQL_KEYWORD;
import static knack.college.learnenglish.model.Constant.DatabaseKeywords.DATETIME_SQL_KEYWORD;
import static knack.college.learnenglish.model.Constant.DatabaseKeywords.DELETE_SQL_KEYWORD;
import static knack.college.learnenglish.model.Constant.DatabaseKeywords.DROP_TABLE_SQL_KEYWORD;
import static knack.college.learnenglish.model.Constant.DatabaseKeywords.EXISTS_TABLE_SQL_KEYWORD;
import static knack.college.learnenglish.model.Constant.DatabaseKeywords.FROM_SQL_KEYWORD;
import static knack.college.learnenglish.model.Constant.DatabaseKeywords.IF_SQL_KEYWORD;
import static knack.college.learnenglish.model.Constant.DatabaseKeywords.INTEGER_SQL_KEYWORD;
import static knack.college.learnenglish.model.Constant.DatabaseKeywords.PRIMARY_KEY_SQL_KEYWORD;
import static knack.college.learnenglish.model.Constant.DatabaseKeywords.VACUUM_SQL_KEYWORD;
import static knack.college.learnenglish.model.Constant.DatabaseNumberValues.VARCHAR_50;
import static knack.college.learnenglish.model.Constant.SpecialCharacters.COMMA;
import static knack.college.learnenglish.model.Constant.SpecialCharacters.LEFT_BRACE;
import static knack.college.learnenglish.model.Constant.SpecialCharacters.NEW_LINE;
import static knack.college.learnenglish.model.Constant.SpecialCharacters.RIGHT_BRACE;
import static knack.college.learnenglish.model.Constant.SpecialCharacters.SEMICOLON;
import static knack.college.learnenglish.model.Constant.SpecialCharacters.SPACE;
import static knack.college.learnenglish.model.Constant.SpecialCharacters.TAB;

public class StatisticDictionaryTrainingContract {
    public static abstract class StatisticDictionaryTraining implements BaseColumns {
        /** Название таблицы для статистики */
        public final static String STATISTIC_DICTIONARY_TRAINING = "STATISTIC_DICTIONARY_TRAINING";
        /** Первичный ключ */
        public final static String OUID_COLUMN_NAME = "OUID";
        /** Глоабльный уникальный идентификатор */
        public final static String GUID_COLUMN_NAME = "GUID";
        /** Всего слов в словаре на момент прохождения треннинга */
        public final static String COUNT_OF_WORDS_IN_DICTIONARY = "COUNT_OF_WORDS_IN_DICTIONARY";
        /** Правильных ответов */
        public final static String CORRECT_ANSWER = "CORRECT_ANSWER";
        /** Неправильных ответов */
        public final static String WRONG_ANSWER = "WRONG_ANSWER";
        /** Время начала треннинга */
        public final static String BEGIN_TRAINING = "BEGIN_TRAINING";

        /** * Метод, который возвращает текст запрсоа на создание таблицы */
        public static StringBuilder getCreateStatisticDictionaryTrainingTableQuery() {
            StringBuilder createTableQuery = new StringBuilder();

            createTableQuery.append(CREATE_TABLE_SQL_KEYWORD).append(STATISTIC_DICTIONARY_TRAINING).append(NEW_LINE)
                    .append(LEFT_BRACE).append(NEW_LINE)
                    .append(TAB).append(OUID_COLUMN_NAME).append(SPACE)
                    .append(INTEGER_SQL_KEYWORD).append(PRIMARY_KEY_SQL_KEYWORD)
                    .append(ASC_SQL_KEYWORD).append(COMMA).append(NEW_LINE)
                    .append(TAB).append(GUID_COLUMN_NAME).append(SPACE).append(VARCHAR_50)
                    .append(COMMA)
                    .append(NEW_LINE)
                    .append(TAB).append(COUNT_OF_WORDS_IN_DICTIONARY).append(SPACE)
                    .append(INTEGER_SQL_KEYWORD)
                    .append(COMMA).append(NEW_LINE)
                    .append(TAB).append(CORRECT_ANSWER).append(SPACE).append(INTEGER_SQL_KEYWORD)
                    .append(COMMA).append(NEW_LINE)
                    .append(TAB).append(WRONG_ANSWER).append(SPACE).append(INTEGER_SQL_KEYWORD)
                    .append(COMMA).append(NEW_LINE)
                    .append(TAB).append(BEGIN_TRAINING).append(SPACE).append(DATETIME_SQL_KEYWORD)
                    .append(NEW_LINE)
                    .append(RIGHT_BRACE).append(SEMICOLON);

            return createTableQuery;
        }

        /** Метод, который возвращает текст запрсоа на удаление таблицы */
        public static StringBuilder getDropStatisticDictionaryTrainingTableQuery() {
            StringBuilder dropTableQuery = new StringBuilder();

            dropTableQuery.append(DROP_TABLE_SQL_KEYWORD).append(IF_SQL_KEYWORD).append(EXISTS_TABLE_SQL_KEYWORD)
                    .append(STATISTIC_DICTIONARY_TRAINING);

            return dropTableQuery;
        }

        /** Метод, который возвращает текст запроса на удаление всех записей из таблицы */
        public static StringBuilder getDeleteAllRowsInStatisticDictionaryTrainingTableQuery() {
            StringBuilder getDeleteAllRowsInTableQuery = new StringBuilder();

            getDeleteAllRowsInTableQuery.append(DELETE_SQL_KEYWORD).append(FROM_SQL_KEYWORD)
                    .append(STATISTIC_DICTIONARY_TRAINING).append(SEMICOLON).append(NEW_LINE)
                    .append(VACUUM_SQL_KEYWORD).append(SEMICOLON);

            return getDeleteAllRowsInTableQuery;
        }
    }
}
