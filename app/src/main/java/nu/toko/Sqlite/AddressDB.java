package nu.toko.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import nu.toko.Model.AddressModel;

import static nu.toko.Model.AddressModel.CREATE_TABLE;
import static nu.toko.Model.AddressModel.TABLE_NAME;
import static nu.toko.Utils.Staticvar.ADDRESS;
import static nu.toko.Utils.Staticvar.ID_ADDRESS;
import static nu.toko.Utils.Staticvar.KEC;
import static nu.toko.Utils.Staticvar.KODEPOS;
import static nu.toko.Utils.Staticvar.KOTAKAB;
import static nu.toko.Utils.Staticvar.PROVINSI;

public class AddressDB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "address";

    public AddressDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);
    }

    public void insert(AddressModel data) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ADDRESS, data.getAddress());
        values.put(PROVINSI, data.getProvinsi());
        values.put(KOTAKAB, data.getKotakab());
        values.put(KEC, data.getKec());
        values.put(KODEPOS, data.getKodepos());

        db.insert(TABLE_NAME, null, values);

        db.close();
    }

    public AddressModel get(String id) {
        AddressModel data = new AddressModel();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                new String[]{ID_ADDRESS, ADDRESS, PROVINSI, KOTAKAB, KEC, KODEPOS},ID_ADDRESS + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);


        if (cursor.getCount() == 1) {
            cursor.moveToFirst();

        data = new AddressModel(
                cursor.getInt(cursor.getColumnIndex(ID_ADDRESS)),
                cursor.getString(cursor.getColumnIndex(ADDRESS)),
                cursor.getString(cursor.getColumnIndex(PROVINSI)),
                cursor.getString(cursor.getColumnIndex(KOTAKAB)),
                cursor.getString(cursor.getColumnIndex(KEC)),
                cursor.getString(cursor.getColumnIndex(KODEPOS)));
        }

        cursor.close();

        return data;
    }

    public int update(AddressModel data) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ADDRESS, data.getAddress());
        values.put(PROVINSI, data.getProvinsi());
        values.put(KOTAKAB, data.getKotakab());
        values.put(KEC, data.getKec());
        values.put(KODEPOS, data.getKodepos());

        // updating row
        return db.update(TABLE_NAME, values, ID_ADDRESS + " = ?",
                new String[]{String.valueOf(data.getId_address())});
    }

    public List<AddressModel> getAll() {
        List<AddressModel> datas = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                AddressModel data = new AddressModel();
                data.setId_address(cursor.getInt(cursor.getColumnIndex(ID_ADDRESS)));
                data.setAddress(cursor.getString(cursor.getColumnIndex(ADDRESS)));
                data.setProvinsi(cursor.getString(cursor.getColumnIndex(PROVINSI)));
                data.setKotakab(cursor.getString(cursor.getColumnIndex(KOTAKAB)));
                data.setKec(cursor.getString(cursor.getColumnIndex(KEC)));
                data.setKodepos(cursor.getString(cursor.getColumnIndex(KODEPOS)));

                datas.add(data);
            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();

        return datas;
    }

    public int getCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public void delete(AddressModel data) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID_ADDRESS + " = ?",
                new String[]{String.valueOf(data.getId_address())});
        db.close();
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
        db.close();
    }
}