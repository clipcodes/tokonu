package nu.toko.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import nu.toko.Model.ProductModel;

import static nu.toko.Model.ProductModel.CREATE_TABLE;
import static nu.toko.Model.ProductModel.TABLE_NAME;
import static nu.toko.Utils.Staticvar.DESCRIPTION;
import static nu.toko.Utils.Staticvar.ID;
import static nu.toko.Utils.Staticvar.IMAGES;
import static nu.toko.Utils.Staticvar.PRICE;
import static nu.toko.Utils.Staticvar.TITLE;

public class FavoritesDB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "favoritedb";

    public FavoritesDB(Context context) {
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

    public void insert(ProductModel data) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TITLE, data.getTitle());
        values.put(IMAGES, data.getImages());
        values.put(DESCRIPTION, data.getDescription());
        values.put(PRICE, data.getPrice());

        db.insert(TABLE_NAME, null, values);

        db.close();
    }

    public ProductModel get(String id) {
        ProductModel data = new ProductModel();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                new String[]{ID, TITLE, IMAGES, DESCRIPTION, PRICE},ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);


        if (cursor.getCount() == 1) {
            cursor.moveToFirst();

        data = new ProductModel(
                cursor.getString(cursor.getColumnIndex(TITLE)),
                cursor.getString(cursor.getColumnIndex(IMAGES)),
                cursor.getString(cursor.getColumnIndex(DESCRIPTION)),
                cursor.getInt(cursor.getColumnIndex(PRICE)));
        }

        cursor.close();

        return data;
    }

    public int update(ProductModel data) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TITLE, data.getTitle());
        values.put(IMAGES, data.getImages());
        values.put(DESCRIPTION, data.getDescription());
        values.put(PRICE, data.getPrice());

        // updating row
        return db.update(TABLE_NAME, values, ID + " = ?",
                new String[]{String.valueOf(data.getId())});
    }

    public List<ProductModel> getAll() {
        List<ProductModel> datas = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ProductModel data = new ProductModel();
                data.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                data.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
                data.setPrice(cursor.getInt(cursor.getColumnIndex(PRICE)));
                data.setImages(cursor.getString(cursor.getColumnIndex(IMAGES)));
                data.setDescription(cursor.getString(cursor.getColumnIndex(DESCRIPTION)));

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

    public void delete(ProductModel data) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID + " = ?",
                new String[]{String.valueOf(data.getId())});
        db.close();
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
        db.close();
    }
}