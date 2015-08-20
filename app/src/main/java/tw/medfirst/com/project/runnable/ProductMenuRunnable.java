package tw.medfirst.com.project.runnable;

import android.content.Context;
import android.os.Handler;

import java.util.HashMap;

import tw.medfirst.com.project.Application;
import tw.medfirst.com.project.baseunit.Logger;
import tw.medfirst.com.project.manager.HttpManager;
import tw.medfirst.com.project.manager.MessageManager;
import tw.medfirst.com.project.manager.TagManager;

/**
 * Created by KCTsai on 2015/6/10.
 */
public class ProductMenuRunnable extends BaseRunnable{

    public ProductMenuRunnable(Context context, Handler handler,
                               String deviceID, String ipAddress, String action) {
        super(context, handler, deviceID, ipAddress, action, HttpManager.METHOD_PRODUCT_MENU);
        //////�g�@��base class��L�̯���bbaseRunnable��������~~~~~/////////////
    }

    @Override
    protected void Result(HashMap<String, Object> resultData) {
        if(resultData == null) { //error!

        }else{
            int resultCode = Integer.parseInt(resultData.get("resultCode").toString());
            if(resultCode == 200)
                Application.sendMessage(handler, MessageManager.PRODUCT_MENU_LOADING_ACCESS, 0, 0, resultData.get("context"));

        }
    }
}
