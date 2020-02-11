package nu.toko.Model;

import static nu.toko.Utils.Staticvar.ADDRESS;
import static nu.toko.Utils.Staticvar.ID_ADDRESS;
import static nu.toko.Utils.Staticvar.KEC;
import static nu.toko.Utils.Staticvar.KODEPOS;
import static nu.toko.Utils.Staticvar.KOTAKAB;
import static nu.toko.Utils.Staticvar.PROVINSI;

public class PayMethodModel {

    private String method;

    public PayMethodModel(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
