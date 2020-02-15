package nu.toko.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import nu.toko.Model.ProductModelNU;

import static nu.toko.Model.ProductModelNU.CREATE_TABLE;
import static nu.toko.Model.ProductModelNU.TABLE_NAME;
import static nu.toko.Utils.Staticvar.BERAT_PRODUK;
import static nu.toko.Utils.Staticvar.CREATED_AT;
import static nu.toko.Utils.Staticvar.DBONGKIR;
import static nu.toko.Utils.Staticvar.DESKRIPSI_PRODUK;
import static nu.toko.Utils.Staticvar.DISKON;
import static nu.toko.Utils.Staticvar.GAMBARFIRST;
import static nu.toko.Utils.Staticvar.HARGA_ADMIN;
import static nu.toko.Utils.Staticvar.HARGA_MITRA;
import static nu.toko.Utils.Staticvar.ID_MITRA;
import static nu.toko.Utils.Staticvar.ID_PRODUK;
import static nu.toko.Utils.Staticvar.ID_SUB_KATEGORI;
import static nu.toko.Utils.Staticvar.KONDISI_PRODUK;
import static nu.toko.Utils.Staticvar.NAMA_PRODUK;
import static nu.toko.Utils.Staticvar.QTY;
import static nu.toko.Utils.Staticvar.STOK;
import static nu.toko.Utils.Staticvar.TERJUAL;

public class CheckoutDB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "checkoutxx";

    public CheckoutDB(Context context) {
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

    public void insert(ProductModelNU data) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ID_PRODUK, data.getId_produk());
        values.put(ID_MITRA, data.getId_mitra());
        values.put(NAMA_PRODUK, data.getNama_produk());
        values.put(DESKRIPSI_PRODUK, data.getDeskripsi_produk());
        values.put(ID_SUB_KATEGORI, data.getId_sub_kategori());
        values.put(BERAT_PRODUK, data.getBerat_produk());
        values.put(KONDISI_PRODUK, data.getKondisi_produk());
        values.put(TERJUAL, data.getTerjual());
        values.put(STOK, data.getStok());
        values.put(HARGA_MITRA, data.getHarga_mitra());
        values.put(HARGA_ADMIN, data.getHarga_admin());
        values.put(CREATED_AT, data.getCreated_at());
        values.put(QTY, data.getQty());
        values.put(GAMBARFIRST, data.getGambarfirst());
        values.put(DISKON, data.getDiskon());
        values.put(DBONGKIR, data.getOngkir());

        db.insert(TABLE_NAME, null, values);

        db.close();
    }

    public ProductModelNU get(String id) {
        ProductModelNU data = new ProductModelNU();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                new String[]{ID_PRODUK, ID_MITRA, NAMA_PRODUK, DESKRIPSI_PRODUK, ID_SUB_KATEGORI, BERAT_PRODUK, KONDISI_PRODUK, TERJUAL, STOK,
                        HARGA_MITRA, HARGA_ADMIN, CREATED_AT, QTY, GAMBARFIRST, DISKON, DISKON},ID_PRODUK + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);


        if (cursor.getCount() == 1) {
            cursor.moveToFirst();

        data = new ProductModelNU(
                cursor.getString(cursor.getColumnIndex(ID_PRODUK)),
                cursor.getString(cursor.getColumnIndex(ID_MITRA)),
                cursor.getString(cursor.getColumnIndex(NAMA_PRODUK)),
                cursor.getString(cursor.getColumnIndex(DESKRIPSI_PRODUK)),
                cursor.getString(cursor.getColumnIndex(ID_SUB_KATEGORI)),
                cursor.getString(cursor.getColumnIndex(BERAT_PRODUK)),
                cursor.getString(cursor.getColumnIndex(KONDISI_PRODUK)),
                cursor.getString(cursor.getColumnIndex(TERJUAL)),
                cursor.getString(cursor.getColumnIndex(STOK)),
                cursor.getInt(cursor.getColumnIndex(HARGA_MITRA)),
                cursor.getInt(cursor.getColumnIndex(HARGA_ADMIN)),
                cursor.getString(cursor.getColumnIndex(CREATED_AT)),
                cursor.getInt(cursor.getColumnIndex(QTY)),
                cursor.getString(cursor.getColumnIndex(GAMBARFIRST)),
                cursor.getInt(cursor.getColumnIndex(DISKON)),
                cursor.getInt(cursor.getColumnIndex(DBONGKIR)));
        }

        cursor.close();

        return data;
    }

    public int update(String id, int qty) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(QTY, qty);

        // updating row
        return db.update(TABLE_NAME, values, ID_PRODUK + " = ?",
                new String[]{String.valueOf(id)});
    }

    public List<ProductModelNU> getAll() {
        List<ProductModelNU> datas = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " ORDER BY " + ID_MITRA + " DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ProductModelNU data = new ProductModelNU();
                data.setId_produk(cursor.getString(cursor.getColumnIndex(ID_PRODUK)));
                data.setId_mitra(cursor.getString(cursor.getColumnIndex(ID_MITRA)));
                data.setNama_produk(cursor.getString(cursor.getColumnIndex(NAMA_PRODUK)));
                data.setDeskripsi_produk(cursor.getString(cursor.getColumnIndex(DESKRIPSI_PRODUK)));
                data.setId_sub_kategori(cursor.getString(cursor.getColumnIndex(ID_SUB_KATEGORI)));
                data.setBerat_produk(cursor.getString(cursor.getColumnIndex(BERAT_PRODUK)));
                data.setKondisi_produk(cursor.getString(cursor.getColumnIndex(KONDISI_PRODUK)));
                data.setTerjual(cursor.getString(cursor.getColumnIndex(TERJUAL)));
                data.setStok(cursor.getString(cursor.getColumnIndex(STOK)));
                data.setHarga_mitra(cursor.getInt(cursor.getColumnIndex(HARGA_MITRA)));
                data.setHarga_admin(cursor.getInt(cursor.getColumnIndex(HARGA_ADMIN)));
                data.setQty(cursor.getInt(cursor.getColumnIndex(QTY)));
                data.setCreated_at(cursor.getString(cursor.getColumnIndex(CREATED_AT)));
                data.setGambarfirst(cursor.getString(cursor.getColumnIndex(GAMBARFIRST)));
                data.setDiskon(cursor.getInt(cursor.getColumnIndex(DISKON)));
                data.setOngkir(cursor.getInt(cursor.getColumnIndex(DBONGKIR)));

                datas.add(data);
            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();

        return datas;
    }

    public List<ProductModelNU> getAllbyToko() {
        List<ProductModelNU> datas = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " ORDER BY " + ID_MITRA + " DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ProductModelNU data = new ProductModelNU();
                data.setId_produk(cursor.getString(cursor.getColumnIndex(ID_PRODUK)));
                data.setId_mitra(cursor.getString(cursor.getColumnIndex(ID_MITRA)));
                data.setNama_produk(cursor.getString(cursor.getColumnIndex(NAMA_PRODUK)));
                data.setDeskripsi_produk(cursor.getString(cursor.getColumnIndex(DESKRIPSI_PRODUK)));
                data.setId_sub_kategori(cursor.getString(cursor.getColumnIndex(ID_SUB_KATEGORI)));
                data.setBerat_produk(cursor.getString(cursor.getColumnIndex(BERAT_PRODUK)));
                data.setKondisi_produk(cursor.getString(cursor.getColumnIndex(KONDISI_PRODUK)));
                data.setTerjual(cursor.getString(cursor.getColumnIndex(TERJUAL)));
                data.setStok(cursor.getString(cursor.getColumnIndex(STOK)));
                data.setHarga_mitra(cursor.getInt(cursor.getColumnIndex(HARGA_MITRA)));
                data.setHarga_admin(cursor.getInt(cursor.getColumnIndex(HARGA_ADMIN)));
                data.setQty(cursor.getInt(cursor.getColumnIndex(QTY)));
                data.setCreated_at(cursor.getString(cursor.getColumnIndex(CREATED_AT)));
                data.setGambarfirst(cursor.getString(cursor.getColumnIndex(GAMBARFIRST)));
                data.setDiskon(cursor.getInt(cursor.getColumnIndex(DISKON)));
                data.setOngkir(cursor.getInt(cursor.getColumnIndex(DBONGKIR)));

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

    public void delete(ProductModelNU data) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID_PRODUK + " = ?",
                new String[]{String.valueOf(data.getId_produk())});
        db.close();
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
        db.close();
    }
}