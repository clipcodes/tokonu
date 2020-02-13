package nu.toko.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

import nu.toko.Dialog.DialogLogout;
import nu.toko.Page.Bantuan;
import nu.toko.Page.CartOrder;
import nu.toko.Page.Chats;
import nu.toko.Page.Login;
import nu.toko.Page.UserSetting;
import nu.toko.R;
import nu.toko.Utils.UserPrefs;

public class Account extends Fragment {

    TextView fullname, email;
    FrameLayout logout, chat;
    FrameLayout setting, cart, chating, bantuan;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.page_accountnew, container, false);

        init(root);

        return root;
    }

    void init(View v){
        setting = v.findViewById(R.id.setting);
        cart = v.findViewById(R.id.cart);
        logout = v.findViewById(R.id.logout);
        fullname = v.findViewById(R.id.fullname);
        email = v.findViewById(R.id.email);
        chat = v.findViewById(R.id.chat);
        chating = v.findViewById(R.id.chating);
        bantuan = v.findViewById(R.id.bantuan);

        fullname.setText(UserPrefs.getNama(getActivity()));
        email.setText(UserPrefs.getEmail(getActivity()));

        setting.setOnClickListener(new onClick());
        cart.setOnClickListener(new onClick());
        chat.setOnClickListener(new onClick());
        chating.setOnClickListener(new onClick());
        bantuan.setOnClickListener(new onClick());
        logout.setOnClickListener(new onClick());
    }

    class onClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent go = null;
            switch (v.getId()){
                case R.id.setting:
                    go = new Intent(getActivity(), UserSetting.class);
                    getActivity().startActivity(go);
                    break;
                case R.id.cart:
                    go = new Intent(getActivity(), CartOrder.class);
                    getActivity().startActivity(go);
                    break;
                case R.id.bantuan:
                    go = new Intent(getActivity(), Bantuan.class);
                    getActivity().startActivity(go);
                    break;
                case R.id.chating:
                    go = new Intent(getActivity(), Chats.class);
                    getActivity().startActivity(go);
                    break;
                case R.id.chat:
                    Intent ad = new Intent(getActivity(), Chats.class);
                    getActivity().startActivity(ad);
                    break;
                case R.id.logout:
                    new DialogLogout(getActivity()).mentriger(new DialogLogout.Go() {
                        @Override
                        public void trigerbos() {
                            UserPrefs.setLogin(getActivity(), false);
                            if (FirebaseAuth.getInstance().getCurrentUser()!=null)FirebaseAuth.getInstance().signOut();
                            FirebaseMessaging.getInstance().unsubscribeFromTopic("user"+ UserPrefs.getId(getActivity()));
                            Intent i = new Intent(getActivity(), Login.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            getActivity().finish();
                        }
                    });
                    break;
            }
        }
    }

}