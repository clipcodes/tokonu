package nu.toko.Model;

public class TrackingModel {

    private String manifest_code;
    private String manifest_description;
    private String manifest_date;
    private String manifest_time;
    private String city_name;

    public String getManifest_code() {
        return manifest_code;
    }

    public void setManifest_code(String manifest_code) {
        this.manifest_code = manifest_code;
    }

    public String getManifest_description() {
        return manifest_description;
    }

    public void setManifest_description(String manifest_description) {
        this.manifest_description = manifest_description;
    }

    public String getManifest_date() {
        return manifest_date;
    }

    public void setManifest_date(String manifest_date) {
        this.manifest_date = manifest_date;
    }

    public String getManifest_time() {
        return manifest_time;
    }

    public void setManifest_time(String manifest_time) {
        this.manifest_time = manifest_time;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }
}
