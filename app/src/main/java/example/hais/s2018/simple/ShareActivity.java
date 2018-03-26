package example.hais.s2018.simple;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import example.hais.s2018.R;
import example.hais.s2018.base.BaseActivity;
import example.hais.s2018.utils.FileOpenUtils;
import example.hais.s2018.utils.LogUtils;

public class ShareActivity extends BaseActivity {

    @Bind(R.id.tv)
    TextView tv;

    String path = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_share);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if(Intent.ACTION_VIEW.equals(action)){
            Uri uri = intent.getData();
            startActivity(FileOpenUtils.openFile(ShareActivity.this, getRealFilePath(ShareActivity.this, uri)));
            path = Uri.decode(uri.getEncodedPath());
            if (path != null) {
                LogUtils.e("大啊啊",path);
                imageUri =  intent.getParcelableExtra(Intent.EXTRA_STREAM);
                tv.setText(path);
            }
        }else if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            } else if (type.startsWith("image/")) {
                handleSendImage(intent); // Handle single image being sent
            } else {
                tv.setText("大家好66");
            }
        } else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                handleSendMultipleImages(intent); // Handle multiple images being sent
            }
        } else {
            // Handle other intents, such as being started from the home screen
            tv.setText("大家好99");
        }
    }

    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        String sharedTitle = intent.getStringExtra(Intent.EXTRA_TITLE);
        if (sharedText != null) {
            // Update UI to reflect text being shared
            tv.setText(sharedText);
        }
    }

    Uri imageUri;

    void handleSendImage(Intent intent) {
        imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (imageUri != null) {
            // Update UI to reflect image being shared
            Log.e("TAG", "uti=" + imageUri.toString());
            tv.setText(imageUri.toString());
        }
    }

    void handleSendMultipleImages(Intent intent) {
        ArrayList<Uri> imageUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
        if (imageUris != null) {
            String s = "";
            for (Uri uri : imageUris) {
                s += "\n" + uri.toString();
                imageUri = uri;
            }
            tv.setText(s);
        }
    }

    @OnClick(R.id.tv)
    public void onViewClicked() {
        if (imageUri != null) {
            Log.e("TAG","12333333333333");
          // startActivity(FileOpenUtils.openFile(ShareActivity.this,imageUri.toString()));
            startActivity(FileOpenUtils.openFile(ShareActivity.this, getRealFilePath(ShareActivity.this, imageUri)));
        }else if(!TextUtils.isEmpty(path)){
            FileOpenUtils.openFileByPath(activity,path);
        }else {
            Log.e("TAG","kong");
        }
    }

    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
}
