package nu.toko.Utils;

import android.content.Context;

import nu.toko.Utils.Pref;

import static nu.toko.Utils.Staticvar.EMAIL;
import static nu.toko.Utils.Staticvar.FULLNAME;
import static nu.toko.Utils.Staticvar.ID;
import static nu.toko.Utils.Staticvar.PHONE;
import static nu.toko.Utils.Staticvar.USERNAME;

public class UserPrefs {

    private String email;
    private String nama;
    private String no_telp;
    private String provinsi;
    private String kabupaten;
    private String kecamatan;
    private String kode_pos;
    private String alamat;
    private String url_profil;
    private String id;
    private String namakab;

    public static boolean isLogin(Context c) {
        return Boolean.valueOf(Pref.read(c, "login", "false"));
    }

    public static void setLogin(Context c, boolean login) {
        Pref.write(c, "login", String.valueOf(login));
    }

    public static String getEmail(Context c) {
        return Pref.read(c, "email", "false");
    }

    public static void setEmail(String email, Context c) {
        Pref.write(c, "email", email);
    }

    public static String getNama(Context c) {
        return Pref.read(c, "nama", "false");
    }

    public static void setNama(String nama, Context c) {
        Pref.write(c, "nama", nama);
    }

    public static String getNo_telp(Context c) {
        return Pref.read(c, "no_telp", "false");
    }

    public static void setNo_telp(String no_telp, Context c) {
        Pref.write(c, "no_telp", no_telp);
    }

    public static String getProvinsi(Context c) {
        return Pref.read(c, "provinsi", "false");
    }

    public static void setProvinsi(String provinsi, Context c) {
        Pref.write(c, "provinsi", provinsi);
    }

    public static String getKabupaten(Context c) {
        return Pref.read(c, "kabupaten", "false");
    }

    public static void setKabupaten(String kabupaten, Context c) {
        Pref.write(c, "kabupaten", kabupaten);
    }

    public static String getKecamatan(Context c) {
        return Pref.read(c, "kecamatan", "false");
    }

    public static void setKecamatan(String kecamatan, Context c) {
        Pref.write(c, "kecamatan", kecamatan);
    }

    public static String getKode_pos(Context c) {
        return Pref.read(c, "kode_pos", "false");
    }

    public static void setKode_pos(String kode_pos, Context c) {
        Pref.write(c, "kode_pos", kode_pos);
    }

    public static String getAlamat(Context c) {
        return Pref.read(c, "alamat", "false");
    }

    public static void setAlamat(String alamat, Context c) {
        Pref.write(c, "alamat", alamat);
    }

    public static String getId(Context c) {
        return Pref.read(c, "id", "false");
    }

    public static void setId(String id, Context c) {
        Pref.write(c, "id", id);
    }

    public static String getUrl_profil(Context c) {
        return Pref.read(c, "url_profil", "false");
    }

    public static void setUrl_profil(String url_profil, Context c) {
        Pref.write(c, "url_profil", url_profil);
    }

    public static String getNamakab(Context c) {
        return Pref.read(c, "namakab", "false");
    }

    public static void setNamakab(String namakab, Context c) {
        Pref.write(c, "namakab", namakab);
    }
}
