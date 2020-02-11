package nu.toko.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Pref {

    public static String read(Context ctx, String namae, String isine) {
        SharedPreferences sharedPref = ctx.getSharedPreferences("ittron", Context.MODE_PRIVATE);
        return sharedPref.getString(namae, isine);
    }

    public static void write(Context ctx, String namae, String isine) {
        SharedPreferences sharedPref = ctx.getSharedPreferences("ittron", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(namae, isine);
        editor.apply();
    }

}
