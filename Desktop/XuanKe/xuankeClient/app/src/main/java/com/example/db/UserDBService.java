package com.example.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.bean.Users;
import com.example.util.T;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * 数据库操作类
 *
 * @author Administrator
 */
public class UserDBService {

    private static UserDBService mInstence;
    private UserDBHelper helper;
    private String TABLE_NAME;


    public class UserDBHelper extends SQLiteOpenHelper {

        public final String TABLE_NAME = "user"; //表名


        public UserDBHelper(Context context) {
            super(context, "user.db", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table " + TABLE_NAME + " (uid integer primary key autoincrement,user varchar(20),pswd varchar(20),sex varchar,name varchar,ke varchar,ban text,type integer)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }


    /**
     * 查询
     *
     * @return
     */
    public Users search(String user, String pswd) {
        Cursor cursor = helper.getReadableDatabase().query(TABLE_NAME, null, "user = ? and pswd = ?", new String[]{user, pswd}, null,
                null, null, null);
        while (cursor.moveToNext()) {
            Users object = new Users();
            object.setUid(cursor.getInt(cursor.getColumnIndex("uid")));
            object.setType(cursor.getInt(cursor.getColumnIndex("type")));
            object.setUser(cursor.getString(cursor.getColumnIndex("user")));
            object.setPswd(cursor.getString(cursor.getColumnIndex("pswd")));
            object.setSex(cursor.getString(cursor.getColumnIndex("sex")));
            object.setName(cursor.getString(cursor.getColumnIndex("name")));

            return object;


        }
        return null;
    }

    public Users search(int uid) {
        Cursor cursor = helper.getReadableDatabase().query(TABLE_NAME, null, "uid = ?", new String[]{uid + ""}, null,
                null, null, null);
        while (cursor.moveToNext()) {
            Users object = new Users();
            object.setUid(cursor.getInt(cursor.getColumnIndex("uid")));
            object.setType(cursor.getInt(cursor.getColumnIndex("type")));
            object.setUser(cursor.getString(cursor.getColumnIndex("user")));
            object.setPswd(cursor.getString(cursor.getColumnIndex("pswd")));
            object.setSex(cursor.getString(cursor.getColumnIndex("sex")));
            object.setName(cursor.getString(cursor.getColumnIndex("name")));
            return object;


        }
        return null;
    }


    /**
     * 判断是否已注册
     *
     * @param user
     * @return
     */
    public boolean isExit(String user) {
        Cursor cursor = helper.getReadableDatabase().query(TABLE_NAME, null, "user = ?", new String[]{user}, null,
                null, null, null);
        while (cursor.moveToNext()) {
            return true;

        }
        return false;
    }


    /**
     * 保存
     */
    public boolean save(int uid,String user, String pswd, String sex, String name,  int type) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("uid", uid);
        contentValues.put("user", user);
        contentValues.put("pswd", pswd);
        contentValues.put("sex", sex);
        contentValues.put("name", name);
        contentValues.put("type", type);
        long result = helper.getWritableDatabase().insert(TABLE_NAME, null, contentValues);
        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }


    public boolean update(JSONObject object) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("pswd", object.optString("pswd"));
        contentValues.put("name", object.optString("name"));
        contentValues.put("sex", object.optString("sex"));
        contentValues.put("ban", object.optString("ban"));
        contentValues.put("ke", object.optString("ke"));
        contentValues.put("type", object.optInt("type"));
        long result = helper.getWritableDatabase().update(TABLE_NAME, contentValues, "uid = ?", new String[]{object.optInt("uid") + ""});

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 根据id删除
     *
     * @param
     * @return
     */
    public boolean delete(int uid) {
        long result = helper.getWritableDatabase().delete(TABLE_NAME, "uid = ?", new String[]{uid + ""});
        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static UserDBService getInstence() {
        if (mInstence == null) {
            synchronized (UserDBService.class) {
                if (mInstence == null) {
                    mInstence = new UserDBService(T.context);
                }
            }
        }
        return mInstence;
    }

    public UserDBService(Context context) {
        close();
        helper = new UserDBHelper(context);
        TABLE_NAME = helper.TABLE_NAME;
    }

    private synchronized void close() {
        if (helper != null) {
            helper.close();
            helper = null;
        }
    }

}
