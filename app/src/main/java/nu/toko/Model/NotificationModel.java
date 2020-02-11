package nu.toko.Model;

import static nu.toko.Utils.Staticvar.CREATED_AT;
import static nu.toko.Utils.Staticvar.ID;
import static nu.toko.Utils.Staticvar.NOTIFICATION;
import static nu.toko.Utils.Staticvar.TYPE;
import static nu.toko.Utils.Staticvar.USERID;

public class NotificationModel {

    public static final String TABLE_NAME = "notification";

    private int id;
    private String notification;
    private int type;
    private int userid;
    private String created_at;

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + ID + " TEXT,"
                    + NOTIFICATION + " TEXT,"
                    + USERID + " TEXT,"
                    + CREATED_AT + " TEXT,"
                    + TYPE + " TEXT"
                    + ")";

    public NotificationModel() {}

    public NotificationModel(int id, String notification, int type, int userid, String created_at) {
        this.id = id;
        this.setNotification(notification);
        this.type = type;
        this.userid = userid;
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
