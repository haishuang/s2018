package example.hais.s2018.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * Created by Huang hai-sen on 2016/7/25 15:07.
 */
public class FileUtils {
    /**
     * 判断SDCard是否存在 [当没有外挂SD卡时，内置ROM也被识别为存在sd卡]
     *
     * @return
     */
    public static boolean isSdCardExist() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }
    /**
     * 获取文件大小
     */
    public static long getFileSize(File file) {
        if (file.exists() && file.isFile()) {
            return file.length();
        }
        return 0;
    }

    /**
     * 通过文件大小转化为相近单位的格式
     *
     * @param fileSize
     * @return
     */
    public static String fileSizeFormat(long fileSize) {
        String size = "";
        DecimalFormat df = new DecimalFormat("#.00");
        if (fileSize < 1024) {
            size = df.format((double) fileSize) + "BT";
        } else if (fileSize < 1024 * 1024) {
            size = df.format((double) fileSize / 1024) + "KB";
        } else if (fileSize < 1024 * 1024 * 1024) {
            size = df.format((double) fileSize / (1024 * 1024)) + "MB";
        } else if (fileSize < 1024 * 1024 * 1024 * 1024) {
            size = df.format((double) fileSize / (1024 * 1024 * 1024)) + "GB";
        } else {
            size = df.format((double) fileSize / (1024 * 1024 * 1024 * 1024)) + "TB";
        }
        return size;
    }

    public static File createFile(String str) {
        //判断文件夹是否存在
        checkFile(str);

        //判断文件是否存在
        File file = new File(str);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }


    public static Bitmap compressImage(Bitmap image) { //v3.0.3 compress 报NullPointException
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 80;
        //L.e("压缩前..........................."+fileSizeFormat(baos.toByteArray().length ));
        while (baos.toByteArray().length / 1024 > 300) {
            //循环判断如果压缩后图片是否大于100kb,大于继续压缩

            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
            //L.e("压缩后..........................."+fileSizeFormat(baos.toByteArray().length));
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    public static Bitmap getimage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 1280f;//这里设置高度为800f
        float ww = 720f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

        return null==bitmap?null:compressImage(bitmap);//压缩好比例大小后再进行质量压缩  compressImage
    }

    public static File BitmapToFile(Bitmap bitmap, String filePath) {
        //判断文件夹是否存在
        checkFile(filePath);

        File file = new File(filePath);
        BufferedOutputStream bos;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,bos);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return file;
    }

    /**
     * 检验文件夹是否存在，不在则创建
     *
     * @param filePath
     */
    public static void checkFile(String filePath) {
        File file1 = new File(filePath.substring(0, filePath.lastIndexOf("/")));
        if (!file1.exists()) {
                try {
                    file1.mkdirs();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    public static void deleteAllFiles() {
        String path = Environment.getExternalStorageDirectory()+"/youledong/camera/";
        File dir = new File(path);
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                new File(dir, children[i]).delete();
            }
        }
    }
    public static void deleteAllFiles(File root) {
        //L.e(root.getPath()+"----"+root.getAbsolutePath());
        File files[] = root.listFiles();
        if (files != null)
            for (File f : files) {
                if (f.isDirectory()) { // 判断是否为文件夹
                    deleteAllFiles(f);
                    try {
                        f.delete();
                    } catch (Exception e) {
                    }
                } else {
                    if (f.exists()) { // 判断是否存在
                        deleteAllFiles(f);
                        try {
                            f.delete();
                        } catch (Exception e) {
                        }
                    }
                }
            }
    }
}
