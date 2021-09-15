package uk.co.falcona.mvpproject;

import android.content.Context;

import com.kaopiz.kprogresshud.KProgressHUD;

public class Helper {
    public static String name = "name";
    public static String app = "Mvp";
    public static String email = "email";
    public static String phone = "phone";
    public static String image = "imge";

    public static KProgressHUD initProgressHud(Context context) {
        return KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                ;
    }

    public static void setData(Context context, String data, String key) {
        context.getSharedPreferences(app, Context.MODE_PRIVATE).edit().putString(key, data).apply();
    }

    public static String getData(Context context, String key) {
        return context.getSharedPreferences(app, Context.MODE_PRIVATE).getString(key, "");
    }
}
