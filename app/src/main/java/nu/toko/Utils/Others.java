package nu.toko.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Others {

    public static void hideSoftKeyboard(Context ctx, View view) {
        if (view==null)return;
        InputMethodManager imm = (InputMethodManager)ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm==null)return;
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    public static void MahathirOptionGambar(Context kontol){
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .resetViewBeforeLoading(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .delayBeforeLoading(0)
                .imageScaleType(ImageScaleType.EXACTLY)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(kontol)
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);
    }

    public static int HilangkanTitikNol(Double dot) {
        String[] ss = String.valueOf(dot).split("\\.");
        return Integer.valueOf(ss[0]);
    }

    public static String PercantikHarga(int xxx) {
        String benjut = String.valueOf(xxx);
        if (benjut.length()==3){
            return benjut;
        }
        if (benjut.length()==4){
            return benjut.substring(0,1)+"."+benjut.substring(1, 4);
        }
        if (benjut.length()==5){
            return benjut.substring(0,2)+"."+benjut.substring(2, 5);
        }
        if (benjut.length()==6){
            return benjut.substring(0,3)+"."+benjut.substring(3, 6);
        }
        if (benjut.length()==7){
            return benjut.substring(0,1)+"."+benjut.substring(1,4)+"."+benjut.substring(4, 7);
        }

        return benjut;
    }

    public static String parseWaktu(String date){
        PrettyTime p  = new PrettyTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");

        Date convertedDate = new Date();

        try {
            convertedDate = dateFormat.parse(date);
        } catch (ParseException e) {
            Log.i("ERR", e.getMessage());
            e.printStackTrace();
        }

        p.setLocale(new Locale("id"));
        String datetime = p.format(convertedDate);

        return datetime;
    }

    public static String TransactionStatus(int status){
        switch (status){
            case 0:
                return "Belum Dibayar";
            case 1:
                return "Menunggu";
            case 2:
                return "Barang Dikemas";
            case 3:
                return "Barang Dikirim";
            case 4:
                return "Barang Diterima";
            case 5:
                return "Pesanan Ditolak";
        }
        return "Error";
    }
}
