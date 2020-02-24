package nu.toko.Model;

public class KodeVocerModel {

    private int id_kode_vocer;
    private int nominal;
    private int parameter;
    private String keterangan;
    private String kode;
    private String waktuakhir;
    private String waktuawal;

    public int getId_kode_vocer() {
        return id_kode_vocer;
    }

    public void setId_kode_vocer(int id_kode_vocer) {
        this.id_kode_vocer = id_kode_vocer;
    }

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    public int getParameter() {
        return parameter;
    }

    public void setParameter(int parameter) {
        this.parameter = parameter;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getWaktuakhir() {
        return waktuakhir;
    }

    public void setWaktuakhir(String waktuakhir) {
        this.waktuakhir = waktuakhir;
    }

    public String getWaktuawal() {
        return waktuawal;
    }

    public void setWaktuawal(String waktuawal) {
        this.waktuawal = waktuawal;
    }
}
