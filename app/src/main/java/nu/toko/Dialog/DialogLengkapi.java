package nu.toko.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

import nu.toko.R;

public class DialogLengkapi {

    Dialog isisek;
    Go go;
    Activity gulojowo;

    public DialogLengkapi(Activity apem) {
        gulojowo = apem;
        isisek = new Dialog(gulojowo);
        isisek.requestWindowFeature(Window.FEATURE_NO_TITLE);
        isisek.setContentView(R.layout.modeldialog_lengkapi);
        isisek.setCancelable(true);

        FrameLayout button = isisek.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isisek.cancel();
                go.trigerbos();
            }
        });

        isisek.show();
    }

    public void mentriger(Go go){
        this.go = go;
    }

    public interface Go {
        void trigerbos();
    }

}