package cn.com.watchman.database.dao;

import java.util.List;
import java.util.Map;

import android.database.sqlite.SQLiteOpenHelper;

public interface BaseDao<T> {

    public SQLiteOpenHelper getDbHelper();

    public abstract long insert(T entity);

    public abstract void delete(int id);

    public abstract void delete(Integer... ids);

    public abstract void update(T entity);

    public abstract T get(int id);

    public abstract List<T> rawQuery(String sql, String[] selectionArgs);

    public abstract List<T> find();

    public abstract List<T> find(String[] columns, String selection,
                                 String[] selectionArgs, String groupBy, String having,
                                 String orderBy, String limit);

    public abstract boolean isExist(String sql, String[] selectionArgs);

    /**
     * 将查询的结果保存为名值对map.
     *
     * @param sql           查询sql
     * @param selectionArgs 参数值
     * @return 返回的Map中的key全部是小写形式.
     */
    public List<Map<String, String>> query2MapList(String sql,
                                                   String[] selectionArgs);

    /**
     * 封装执行sql代码.
     *
     * @param sql
     * @param selectionArgs
     */
    public void execSql(String sql, Object[] selectionArgs);

}