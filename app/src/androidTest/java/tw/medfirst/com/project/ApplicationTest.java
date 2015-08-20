package tw.medfirst.com.project;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.test.ApplicationTestCase;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    private static Bitmap getBitmapFromRes(Context context, int resId)
    {
        try
        {
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inPreferredConfig = Bitmap.Config.RGB_565;
            opt.inPurgeable = true;
            opt.inInputShareable = true;

            InputStream is = context.getResources().openRawResource(resId);
            return BitmapFactory.decodeStream(is, null, opt);
//            Bitmap bitmap = BitmapFactory.decodeFile(sd + "/" + file);
//            return bitmap;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    private static Bitmap getBitmapFromSDCard(String path)
    {
        try
        {
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inPreferredConfig = Bitmap.Config.RGB_565;
            opt.inPurgeable = true;
            opt.inInputShareable = true;


            String sd = Environment.getExternalStorageDirectory().toString() + path;
            File file = new File(sd);
            FileInputStream fileInputStream = null;
            if(file.exists()){
                fileInputStream = new FileInputStream(file);
            }
            return BitmapFactory.decodeStream(fileInputStream, null, opt);
//            Bitmap bitmap = BitmapFactory.decodeFile(sd + "/" + file);
//            return bitmap;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}