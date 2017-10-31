package com.mark.sqlitetestdemo.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mark.sqlitetestdemo.MyApp;
import com.mark.sqlitetestdemo.been.User;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by LuoShiQiang on 2017/10/31
 */

public class DemoDBManager {
    private static final String TAG = "DemoDBManager";
    private static DemoDBManager dbMgr = new DemoDBManager();
    private DbOpenHelper dbHelper;

    private DemoDBManager() {
        dbHelper = DbOpenHelper.getInstance(MyApp.getInstance().getApplicationContext());
    }

    public static synchronized DemoDBManager getInstance() {
        if (dbMgr == null) {
            dbMgr = new DemoDBManager();
        }
        return dbMgr;
    }

    /**
     * 储存用户数据
     */
    public synchronized void saveContacList(List<User> userList) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            for (User user : userList) {
                db.delete(UseDao.TABLE_NAME, UseDao.COLUMN_NAME_ID + "=?", new String[]{user.getUserName()});
                ContentValues values = new ContentValues();
                values.put(UseDao.COLUMN_NAME_ID, user.getUserName());
                if (user.getNick() != null) {
                    values.put(UseDao.COLUMN_NAME_NICK, user.getNick());
                }
                if (user.getAvatar() != null) {
                    values.put(UseDao.COLUMN_NAME_AVATAR, user.getAvatar());
                }

                db.replace(UseDao.TABLE_NAME, null, values);
            }
        }
    }

    /**
     * 获取所有用户数据
     */
    public synchronized Map<String, User> getContactMap() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Map<String, User> userMap = new Hashtable<>();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from " + "uers" /* + " desc" */, null);
            while (cursor.moveToNext()) {
                String userName = cursor.getString(cursor.getColumnIndex(UseDao.COLUMN_NAME_ID));
                String nick = cursor.getString(cursor.getColumnIndex(UseDao.COLUMN_NAME_NICK));
                String avatar = cursor.getString(cursor.getColumnIndex(UseDao.COLUMN_NAME_AVATAR));
                User user = new User();
                user.setNick(nick);
                user.setUserName(userName);
                user.setAvatar(avatar);
                userMap.put(userName, user);
            }
            cursor.close();
        }
        return userMap;
    }

    /**
     * 删除user
     */
    public synchronized void deleteContact(String usrName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (db.isOpen()) {
            db.delete(UseDao.TABLE_NAME, UseDao.COLUMN_NAME_ID + " = ?", new String[]{usrName});
        }
    }

    /**
     * 保存单个user
     */
    public synchronized void saveContact(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UseDao.COLUMN_NAME_ID, user.getUserName());
        if (user.getNick() != null) {
            values.put(UseDao.COLUMN_NAME_NICK, user.getNick());
        }
        if (user.getAvatar() != null) {
            values.put(UseDao.COLUMN_NAME_AVATAR, user.getAvatar());
        }
        if (db.isOpen()) {
            db.replace(UseDao.TABLE_NAME, null, values);
        }
    }

    /**
     * 获取单个user
     */
    public synchronized User getContact(String userName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.query(UseDao.TABLE_NAME, new String[]{UseDao.COLUMN_NAME_ID,
                            UseDao.COLUMN_NAME_AVATAR, UseDao.COLUMN_NAME_NICK}, UseDao.COLUMN_NAME_ID + "=?",
                    new String[]{userName}, null, null, null);
            Log.e(TAG, "query");
            if (cursor.moveToNext()) {
                String nick = cursor.getString(cursor.getColumnIndex(UseDao.COLUMN_NAME_NICK));
                String avatar = cursor.getString(cursor.getColumnIndex(UseDao.COLUMN_NAME_AVATAR));
                String string = cursor.getString(cursor.getColumnIndex(UseDao.COLUMN_NAME_ID));
                Log.e(TAG, "nick:" + nick);
                Log.e(TAG, "userName:" + userName);
                Log.e(TAG, "avatar:" + avatar);
                User user = new User();
                user.setNick(nick);
                user.setAvatar(avatar);
                user.setUserName(string);
                cursor.close();
                return user;
            }
        }
        return null;
    }

    /**
     * 更新单个user的数据
     */
    public synchronized void updataUser(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            ContentValues values = new ContentValues();
            values.put(UseDao.COLUMN_NAME_NICK, user.getNick());
            values.put(UseDao.COLUMN_NAME_AVATAR, user.getAvatar());
            values.put(UseDao.COLUMN_NAME_ID, user.getUserName());
            db.update(UseDao.TABLE_NAME, values, UseDao.COLUMN_NAME_ID + "=?", new String[]{user.getUserName()});
        }
    }

    /**
     *
     * */
    public synchronized void clearSQL() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.delete(UseDao.TABLE_NAME, null, null);
        }
    }

    public synchronized void closeDb() {
        if (dbHelper != null) {
            dbHelper.closeDB();
        }
        dbMgr = null;
    }

}
