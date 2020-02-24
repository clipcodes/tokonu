package nu.toko.Model;

import nu.toko.Utils.Base64;

public class FeedModel {

    private String display_url;
    private String shortcode;

    public FeedModel() {

    }

    public String getDisplay_url() {
        return display_url;
    }

    public void setDisplay_url(String display_url) {
        this.display_url = display_url;
    }

    public String getShortcode() {
        return shortcode;
    }

    public void setShortcode(String shortcode) {
        this.shortcode = shortcode;
    }
}
