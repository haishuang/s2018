package example.hais.s2018.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by Huang hai-sen on 2016/6/29 11:33.
 */
public class ToastUtils {
    private ToastUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    private static Toast toast;
    private static Toast longToast;

    public static void showToast(Context context, CharSequence content) {
        if (context != null) {
            if (toast == null) {
                toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
            } else {
                toast.setText(content);
            }
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }

    /***
     * 短时间Toast
     *
     * @param context
     * @param msg
     */
    public static void showShort(Context context, CharSequence msg) {
        //Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        if (context != null) {
            if (toast == null) {
                toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
            } else {
                toast.setText(msg);
            }
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }

    /***
     * 短时间Toast
     *
     * @param context
     * @param msg
     */
    public static void showShort(Context context, int msg) {
        //Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        if (context != null) {
            if (toast == null) {
                toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
            } else {
                toast.setText(msg);
            }
            toast.show();
        }
    }

    /***
     * 长时间Toast
     *
     * @param context
     * @param msg
     */
    public static void showLong(Context context, CharSequence msg) {
        //Toast.makeText(Context, msg, Toast.LENGTH_SHORT).show();
        if (context != null) {
            if (longToast == null) {
                longToast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
            } else {
                longToast.setText(msg);
            }
            longToast.show();
        }
    }

    /***
     * 长时间Toast
     *
     * @param context
     * @param msg
     */
    public static void showLong(Context context, int msg) {
        //Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        if (context != null) {
            if (longToast == null) {
                longToast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
            } else {
                longToast.setText(msg);
            }
            longToast.show();
        }
    }
}
