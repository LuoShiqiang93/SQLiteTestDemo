package com.mark.sqlitetestdemo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mark.sqlitetestdemo.utils.PreferenceManager;

/**
 * Created by LuoShiQiang on 2017/10/31
 */

public class DbOpenHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 6;
    private static DbOpenHelper instance;
    private static final String USERNAME_TABLE_CREATE = "CREATE TABLE "
            + UseDao.TABLE_NAME + " ("
            + UseDao.COLUMN_NAME_NICK + " TEXT, "
            + UseDao.COLUMN_NAME_AVATAR + " TEXT, "
            + UseDao.COLUMN_NAME_ID + " TEXT PRIMARY KEY);";

    private DbOpenHelper(Context context) {
        super(context, getUserDatabaseName(), null, DATABASE_VERSION);
    }

    public static DbOpenHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DbOpenHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(USERNAME_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        /*if (i < 2) {
            sqLiteDatabase.execSQL("ALTER TABLE " + UseDao.TABLE_NAME + " ADD COLUMN " +
                   UseDao.COLUMN_NAME_AVATAR + " TEXT ;");
        }*/
    }

    public void closeDB() {
        if (instance != null) {
            try {
                SQLiteDatabase db = instance.getWritableDatabase();
                db.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            instance = null;
        }
    }

    private static String getUserDatabaseName() {
        return PreferenceManager.getInstance().getCurrentUsername() + "_sql.db";
    }
}
