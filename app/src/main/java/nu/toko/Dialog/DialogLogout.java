package nu.toko.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;

import nu.toko.R;

public class DialogLogout {

    Dialog isisek;
    Go go;
    Activity gulojowo;

    public DialogLogout(Activity apem) {
        gulojowo = apem;
        isisek = new Dialog(gulojowo);
        isisek.requestWindowFeature(Window.FEATURE_NO_TITLE);
        isisek.setContentView(R.layout.modeldialog_logout);
        isisek.setCancelable(true);

        FrameLayout button = isisek.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
