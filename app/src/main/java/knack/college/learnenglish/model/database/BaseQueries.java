package knack.college.learnenglish.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import knack.college.learnenglish.R;
import knack.college.learnenglish.exceptions.DatabaseLEException;

/**
 * Базовый класс, который содержит типичные запросы к базе данных.
 * Любой класс, который описывающий таблицу в приложении наследуется от данного класса.
 * Содержит запросы на проверку существования таблицы, получения количества строк.
 * */
public abstract class BaseQueries {

    // Context
    private Context context;
    // Database helper
    private LearnEnglishDatabaseHelper databaseHelper;
    // Database
    private SQLiteDatabase database;

    // Default table column
    public static final String OUID = "OUID";
    public static final String GUID = "GUID";

    // Constructors
    public BaseQueries(Context context) throws Exception {
        this.context = context;

        // Initialize db instance
        initializeDatabaseHelper();
        initializeDatabase();
    }

    /**
     * Метод, который осуществляет проверку на существование таблицы.
     * @return true - Таблица существует, false - нет.
     * @throws DatabaseLEException
     */
    protected boolean isExistTable(String columnSelect, String tableName)
            throws DatabaseLEException {
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

        return numberOfWords >= 1;
    }

    /**
     * Метод, который возвращает количество строк в конкретной таблице.
     * @param tableName - Название таблиы
     * @return - Количество строк в таблице
     * @throws DatabaseLEException
     */
    protected int getNumberOfRows(String tableName) throws DatabaseLEException {
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

    /**
     * Метод, который производит удаление таблицы.
     * Перед удалением проверяет, существует ли таблица.
     * @param tableName - Название таблицы
     * @throws DatabaseLEException
     */
    protected void dropTable(String tableName) throws DatabaseLEException {
        try {
            if (isExistTable(null, tableName))
                database.execSQL(getDropTableQuery(tableName).toString());
            else throw new DatabaseLEException(context.getResources()
                    .getString(R.string.error_message_database_not_exist));
        } catch (Exception e) {
            throw new DatabaseLEException(e);
        }
    }

    /**
     * Метод, который производит удаление всех строк из таблицы.
     * Перед удалением проверяет, существует ли таблица.
     * @param tableName - Название таблицы
     * @throws DatabaseLEException
     */
    protected void truncateTable(String tableName) throws DatabaseLEException {
        try {
            if (isExistTable(null, tableName))
                database.execSQL(getTruncateTableQuery(tableName).toString());
        } catch (Exception e) {
            throw new DatabaseLEException(e);
        }
    }

    /**
     * Осуществляет создание таблицы.
     * Перед созданием таблицы проверяет, существует ли данная таблица.
     * @param tableName - Название создаваемой таблицы
     * @param params - Параметры для создания. В качестве ключа выступает название столбца.
     *               Каждому ключу соответствует значение, в котором должно содержаться тип данных
     *               для создаваемого поля и.д.
     *               Пример:<br>
     *                  String tableName = "SAMPLE_TABLE_NAME";<br>
     *                  HashMap<String, String> params = new HashMap<String, String>();<br>
     *                  params.put("OUID", "INTEGER PRIMARY KEY ASC");<br>
     *                  params.put("GUID", "VARCHAR(50)");<br>
     *                  params.put("EXAMPLE_VALUE", "VARCHAR(255)");<br>
     *
     *                  try {<br>
     *                      createTable(tableName, params);<br>
     *                  } catch {<br>
     *                      // Exception handling<br>
     *                  }<br>
     * @throws DatabaseLEException
     */
    protected void createTable(String tableName, HashMap<String, String> params)
            throws DatabaseLEException {
        if (!isExistTable(null, tableName)) {
            if (getCreateTableQuery(tableName, params) != null) {
                try {
                    database.execSQL(getCreateTableQuery(tableName, params).toString());
                } catch (Exception e) {
                    throw new DatabaseLEException(e);
                }
            } else throw new DatabaseLEException(
                    context.getResources().getString(R.string.error_message_failed_create_table)
            );
        } else throw new DatabaseLEException(context.getResources()
                .getString(R.string.error_message_database_already_exist));
    }

    /**
     * Проверяет наличие значения в определённом столбце в конкретной таблице.
     * Перед поиском проверяет, что таблица, в которой осуществляется поиск существует.
     * Также перед сравнением значение в искомом столбце и искомое значение приводятся к нижнему
     * регистру.
     * @param tableName - Название таблицы
     * @param selectColumnName - Поле, которое будет идти после "SELECT"
     * @param columnName - Поле, в котором будет осуществляться поиск.
     * @param value - Значение, по которому будет вестись поиск.
     * @return - true - Значение существует, false - значение отсутствует.
     * @throws DatabaseLEException
     */
    protected boolean isExistValue(String tableName, String selectColumnName, String columnName,
                                   String value)
            throws DatabaseLEException {
        Cursor cursor = null;
        int numberSelectedRows = 0;
        if (database != null) {
            try {
                if (isExistTable(null, tableName)) {
                    cursor = database.rawQuery(
                            getExistValueQuery(tableName, selectColumnName, columnName, value)
                                    .toString(),
                            null
                    );
                    numberSelectedRows = cursor.getCount();
                }
            } finally {
                if (cursor != null)
                    cursor.close();
            }
        }

        return numberSelectedRows >= 1;
    }

    /**
     * Обёртка над стандартным методом обновления записей в таблице.
     * Перед обновлением проверяет, что таблица существует.
     * @param tableName - Название таблицы
     * @param values - Какой столбец обновлять и каким значенеим
     * @param condition - Предложение WHERE
     * @throws DatabaseLEException
     */
    protected void updateValue(String tableName, ContentValues values, String condition)
            throws DatabaseLEException {
        try {
            if (database != null && isExistTable(null, tableName) && values != null
                    && values.size() > 0 && condition != null && !condition.isEmpty()) {
                database.update(tableName, values, condition, null);
            }
        } catch (Exception e) {
            throw new DatabaseLEException(e);
        }
    }

    private void initializeDatabaseHelper() throws DatabaseLEException {
        try {
            databaseHelper = new LearnEnglishDatabaseHelper(context);
        } catch (Exception e) {
            throw new DatabaseLEException(
                    context.getResources().getString(R.string
                            .error_message_failed_initialize_database_helper)
            );
        }
    }

    private void initializeDatabase() throws DatabaseLEException {
        try {
            database = databaseHelper.getWritableDatabase();
        } catch (Exception e) {
            throw new DatabaseLEException(
                    context.getResources().getString(R.string
                            .error_message_failed_initialize_database)
            );
        }
    }

    private  StringBuilder getExistTableQuery(String columnSelect, String tableName) {
        return new StringBuilder("SELECT ").append(columnSelect != null ? columnSelect : "*")
                .append(" FROM sqlite_master WHERE name = '").append(tableName)
                .append("' AND type = 'table';");
    }

    private StringBuilder getNumberOfRowsQuery(String tableName) {
        return new StringBuilder("SELECT COUNT(*) FROM ")
                .append(tableName != null ? tableName : "");
    }

    public static StringBuilder getDropTableQuery(String tableName) {
        return new StringBuilder("DROP TABLE ").append("IF EXISTS ")
                .append(tableName != null ? tableName : "");
    }

    private StringBuilder getTruncateTableQuery(String tableName) {
        return new StringBuilder("DELETE FROM ").append(tableName != null ? tableName : "")
                .append("; VACUUM;");
    }

    public static StringBuilder getCreateTableQuery(String tableName, HashMap<String,
            String> params) {
        if (tableName != null && !tableName.isEmpty() && params != null && params.size() > 0
                && !params.containsKey(null) && !params.containsValue(null)) {
            StringBuilder createTableQuery = new StringBuilder("CREATE TABLE ").append(tableName)
                    .append("(");
            Iterator iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry data = (Map.Entry) iterator.next();

                createTableQuery.append(data.getKey()).append(" ").append(data.getValue());
                if (iterator.hasNext())
                    createTableQuery.append(",");
            }
            createTableQuery.append(")");

            return createTableQuery;
        } else return null;
    }

    private StringBuilder getExistValueQuery(String tableName, String selectColumnName,
                                             String columnName, String value) {
        return new StringBuilder("SELECT ").append(selectColumnName != null ? selectColumnName : "*")
                .append(" FROM ").append(tableName)
                .append(" WHERE LOWER(").append(columnName).append(") = LOWER('").append(value).append("')");
    }
}
