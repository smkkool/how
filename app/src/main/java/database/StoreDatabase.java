package database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by smkko on 12/17/2017.
 */

public class StoreDatabase extends SQLiteOpenHelper {
    public StoreDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //create,update,insert,delete
    public void queryData(String sql) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL(sql);
    }

    //select
    public Cursor getData(String sql) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery(sql, null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
