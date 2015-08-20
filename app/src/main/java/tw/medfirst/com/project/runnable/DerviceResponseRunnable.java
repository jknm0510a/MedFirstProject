package tw.medfirst.com.project.runnable;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import java.util.HashMap;

import tw.medfirst.com.project.manager.HttpManager;

/**
 * Created by KCTsai on 2015/6/10.
 */
public class DerviceResponseRunnable extends BaseRunnable{

    private static final String DEVICE_ID = "deviceID";
    private static final String IP_ADDRESS = "ipAddress";

    private String deviceID;
    private String ipAddress;


    public DerviceResponseRunnable(Context context, Handler handler,
                                   String deviceID, String ipAddress, String action) {
        super(context, handler, deviceID, ipAddress, action, HttpManager.METHOD_DEVICE_RESPONSE);

//        this.context = context;
//        this.handler = handler;
//        this.deviceID = deviceID;
//        this.ipAddress = ipAddress;
//        this.action = action;
//        request = new SoapObject(HttpManager.NAME_SPACE, HttpManager.METHOD_DEVICE_RESPONSE);
//        request.addProperty(DEVICE_ID, deviceID);
//        request.addProperty(IP_ADDRESS, ipAddress);

    }

    @Override
    protected void Result(HashMap<String, Object> resultData) {
        if(resultData == null) { //error!

        }else{
            Log.e("Result:" , Integer.toString(resultData.size()) + " , " + resultData.get("resultCode").toString());
        }
    }
}
