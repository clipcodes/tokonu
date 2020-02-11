package nu.toko.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import nu.toko.Model.NotificationModel;

import static nu.toko.Utils.Staticvar.CREATED_AT;
import static nu.toko.Utils.Staticvar.ID;
import static nu.toko.Utils.Staticvar.NOTIFICATION;
import static nu.toko.Utils.Staticvar.TYPE;
import static nu.toko.Utils.Staticvar.USERID;

public class NotificationDB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "notification";

    public NotificationDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NotificationModel.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NotificationModel.TABLE_NAME);

        onCreate(db);
    }

    public void insert(NotificationModel data) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ID, data.getId());
        values.put(NOTIFICATION, data.getNotification());
        values.put(USERID, data.getUserid());
        values.put(TYPE, data.getType());
        values.put(CREATED_AT, data.getCreated_at());

        db.insert(NotificationModel.TABLE_NAME, null, values);

        db.close();
    }

    public NotificationModel get(String id) {
        NotificationModel data = new NotificationModel();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(NotificationModel.TABLE_NAME,
                new String[]{ID,  NOTIFICATION, TYPE, USERID, CREATED_AT},ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);


        if (cursor.getCount() == 1) {
            cursor.moveToFirst();

        data = new NotificationModel(
                cursor.getInt(cursor.getColumnIndex(ID)),
                cursor.getString(cursor.getColumnIndex(NOTIFICATION)),
                cursor.getInt(cursor.getColumnIndex(TYPE)),
                cursor.getInt(cursor.getColumnIndex(USERID)),
                cursor.getString(cursor.getColumnIndex(CREATED_AT)));
        }

        cursor.close();

        return data;
    }

    public int update(NotificationModel data) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ID, data.getId());
        values.put(NOTIFICATION, data.getNotification());
        values.put(USERID, data.getUserid());
        values.put(TYPE, data.getType());
        values.put(CREATED_AT, data.getCreated_at());

        // updating row
        return db.update(NotificationModel.TABLE_NAME, values, ID + " = ?",
                new String[]{String.valueOf(data.getId())});
    }

    public List<NotificationModel> getAll() {
        List<NotificationModel> datas = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + NotificationModel.TABLE_NAME + " ORDER BY " + CREATED_AT + " DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                NotificationModel data = new NotificationModel();
                data.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                data.setNotification(cursor.getString(cursor.getColumnIndex(NOTIFICATION)));
                data.setType(cursor.getInt(cursor.getColumnIndex(TYPE)));
                data.setUserid(cursor.getInt(cursor.getColumnIndex(USERID)));
                data.setCreated_at(cursor.getString(cursor.getColumnIndex(CREATED_AT)));

                datas.add(data);
            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();

        return datas;
    }

    public int getCount() {
        String countQuery = "SELECT  * FROM " + NotificationModel.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public void delete(NotificationModel data) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(NotificationModel.TABLE_NAME, ID + " = ?",
                new String[]{String.valueOf(data.getId())});
        db.close();
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + NotificationModel.TABLE_NAME);
        db.close();
    }
}