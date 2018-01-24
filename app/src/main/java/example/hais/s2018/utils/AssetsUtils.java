package example.hais.s2018.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by Huanghs on 2018/1/19.
 */

public class AssetsUtils {
    /**
     * 从Assets读取字符串
     * @param context 上下文
     * @param fileName 文件名
     * @return
     */
    public static String getFromAssets(Context context, String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * getFromRaw 从raw中返回字符串
     * @param context 上下文
     * @param resId 资源id
     * @return
     */
    public static String getFromRaw(Context context, int  resId){
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().openRawResource(resId));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            String Result="";
            while((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
