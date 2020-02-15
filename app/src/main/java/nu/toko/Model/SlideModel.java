package nu.toko.Model;

public class SlideModel {

    private int id_slide;
    private String link_slide;
    private String parameter;
    private String url_slide;

    public int getId_slide() {
        return id_slide;
    }

    public void setId_slide(int id_slide) {
        this.id_slide = id_slide;
    }

    public String getUrl_slide() {
        return url_slide;
    }

    public void setUrl_slide(String url_slide) {
        this.url_slide = url_slide;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getLink_slide() {
        return link_slide;
    }

    public void setLink_slide(String link_slide) {
        this.link_slide = link_slide;
    }
}
