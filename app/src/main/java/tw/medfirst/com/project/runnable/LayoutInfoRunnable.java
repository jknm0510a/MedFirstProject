package tw.medfirst.com.project.runnable;

import android.content.Context;
import android.os.Handler;

import java.util.HashMap;

import tw.medfirst.com.project.manager.HttpManager;

/**
 * Created by KCTsai on 2015/7/8.
 */
public class LayoutInfoRunnable extends BaseRunnable{

    public LayoutInfoRunnable(Context context, Handler handler,
                              String deviceID, String ipAddress, String action) {
        super(context, handler, deviceID, ipAddress, action, HttpManager.METHOD_LAYOUT_INFO);
    }

    @Override
    protected void Result(HashMap<String, Object> resultData) {

    }
}
