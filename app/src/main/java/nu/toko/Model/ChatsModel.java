package nu.toko.Model;

public class ChatsModel {

    private int to;
    private int from;
    private String last;
    private String created;
    private String room;
    private int dibaca;
    private String namauser;

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public int getDibaca() {
        return dibaca;
    }

    public void setDibaca(int dibaca) {
        this.dibaca = dibaca;
    }

    public String getNamauser() {
        return namauser;
    }

    public void setNamauser(String namauser) {
        this.namauser = namauser;
    }
}
