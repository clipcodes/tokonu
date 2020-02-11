package nu.toko.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import nu.toko.R;

public class Transaction extends Fragment {

    FrameLayout container;
//    FrameLayout purchase, billing;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.page_transaction, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        init(view);
        declare();
    }

    void init(View root){
        container = root.findViewById(R.id.container);
    }

    void declare(){
        loadPage(new Purchase());
    }

    class click implements View.OnClickListener {
        View root;

        public click(View root) {
            this.root = root;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.purchase:
                    root.findViewById(R.id.indpurchase).setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimary));
                    root.findViewById(R.id.indbilling).setBackgroundColor(getActivity().getResources().getColor(R.color.greytranspa2));
                    loadPage(new Purchase());
                    break;
                case R.id.billing:
                    root.findViewById(R.id.indpurchase).setBackgroundColor(getActivity().getResources().getColor(R.color.greytranspa2));
                    root.findViewById(R.id.indbilling).setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimary));
                    loadPage(new Billing());
                    break;
            }
        }
    }

    private boolean loadPage(Fragment fragment) {
        if (fragment != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_transaction, fragment)
                    .addToBackStack(null)
                    .commit();
            return true;
        }
        return false;
    }
}