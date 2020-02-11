package nu.toko.Model;

import static nu.toko.Utils.Staticvar.ADDRESS;
import static nu.toko.Utils.Staticvar.ID_ADDRESS;
import static nu.toko.Utils.Staticvar.KEC;
import static nu.toko.Utils.Staticvar.KODEPOS;
import static nu.toko.Utils.Staticvar.KOTAKAB;
import static nu.toko.Utils.Staticvar.PROVINSI;

public class AddressModel {

    public static final String TABLE_NAME = "tables";

    int id_address;
    private String address;
    String provinsi;
    String kotakab;
    String kec;
    String kodepos;

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + ID_ADDRESS + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + ADDRESS + " TEXT,"
                    + PROVINSI + " TEXT,"
                    + KOTAKAB + " TEXT,"
                    + KEC + " TEXT,"
                    + KODEPOS + " TEXT"
                    + ")";

    public AddressModel() {

    }

    public AddressModel(int id_address, String address, String provinsi, String kotakab, String kec, String kodepos) {
        this.id_address = id_address;
        this.address = address;
        this.provinsi = provinsi;
        this.kotakab = kotakab;
        this.kec = kec;
        this.kodepos = kodepos;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public String getKotakab() {
        return kotakab;
    }

    public void setKotakab(String kotakab) {
        this.kotakab = kotakab;
    }

    public String getKec() {
        return kec;
    }

    public void setKec(String kec) {
        this.kec = kec;
    }

    public String getKodepos() {
        return kodepos;
    }

    public void setKodepos(String kodepos) {
        this.kodepos = kodepos;
    }

    public int getId_address() {
        return id_address;
    }

    public void setId_address(int id_address) {
        this.id_address = id_address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
