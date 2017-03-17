package knack.college.learnenglish.model.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import knack.college.learnenglish.R;
import knack.college.learnenglish.exceptions.DatabaseLEException;

/**
 * Базовый класс, который содержит типичные запросы к базе данных.
 * Любой класс, который описывающий таблицу в приложении наследуется от данного класса
 * Содержит запросы на проверку существования таблицы, получения количества строк.
 * */
public abstract class BaseQueries {

    // Context
    private Context context;
    // Database helper
    private LearnEnglishDatabaseHelper databaseHelper;
    // Database
    private SQLiteDatabase database;

    // Constructors
    public BaseQueries(Context context) throws Exception {
        this.context = context;

        // Initialize db instance
        initializeDatabaseHelper();
        initializeDatabase();
    }

    /**
     * Метод, который осуществляет проверку на существование таблицы
     * @return true - таблица существует, false - нет.
     */
    protected boolean isExistTable(String columnSelect, String tableName) throws Exception {
        Cursor cursor = null;
        int numberOfWords = 0;

        if (database != null) {
            try {
                cursor = database.rawQuery(
                        getExistTableQuery(columnSelect, tableName).toString(), null
                );
                numberOfWords = cursor.getCount();
            } finally {
                if (cursor != null)
                    cursor.close();
            }
        }

        return numberOfWords>= 1;
    }

    /**
     * Метод, который возвращает количество строк в конкретной таблице
     * @param tableName - Имя таблиы
     * @return - Количество строк в таблице
     * @throws Exception
     */
    public int getNumberOfRows(String tableName) throws Exception {
        Cursor cursor = null;
        int numberOfRows = 0;

        if (database != null) {
            try {
                cursor = database.rawQuery(
                        getNumberOfRowsQuery(tableName).toString(), null
                );
                cursor.moveToFirst();
                numberOfRows = cursor.getInt(0);
            } catch (Exception e) {
                numberOfRows = 0;
                throw new DatabaseLEException(e);
            } finally {
                if (cursor != null)
                    cursor.close();
            }
        }

        return numberOfRows;
    }

    private StringBuilder getNumberOfRowsQuery(String tableName) {
        return new StringBuilder("SELECT COUNT(*) FROM ")
                .append(tableName != null ? tableName : "");
    }

    private void initializeDatabaseHelper() throws Exception {
        try {
            databaseHelper = new LearnEnglishDatabaseHelper(context);
        } catch (Exception e) {
            throw new DatabaseLEException(
                    context.getResources().getString(R.string
                            .error_message_failed_initialize_database_helper)
            );
        }
    }

    private void initializeDatabase() throws Exception {
        try {
            database = databaseHelper.getWritableDatabase();
        } catch (Exception e) {
            throw new DatabaseLEException(
                    context.getResources().getString(R.string
                            .error_message_failed_initialize_database)
            );
        }
    }

    private StringBuilder getExistTableQuery(String columnSelect, String tableName) {
        return new StringBuilder("SELECT ").append(columnSelect != null ? columnSelect : "*")
                .append(" FROM sqlite_master WHERE name = '").append(tableName)
                .append("' AND type = 'table';");
    }
}
