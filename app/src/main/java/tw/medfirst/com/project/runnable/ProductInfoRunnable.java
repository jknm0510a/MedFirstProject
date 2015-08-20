package tw.medfirst.com.project.runnable;

import android.content.Context;
import android.os.Handler;

import java.util.HashMap;

import tw.medfirst.com.project.Application;
import tw.medfirst.com.project.baseunit.Logger;
import tw.medfirst.com.project.manager.HttpManager;
import tw.medfirst.com.project.manager.MessageManager;

/**
 * Created by KCTsai on 2015/7/31.
 */
public class ProductInfoRunnable extends BaseRunnable{
    private static final String TAG = "ProductInfoRunnable";
    protected static final String PRODUCT_NO = "productNos";
    private String productNoList;


    public ProductInfoRunnable(Context context, Handler handler, String deviceID, String ipAddress,
                               String action, String productNoList) {
        super(context, handler, deviceID, ipAddress, action, HttpManager.METHOD_PRODUCT_INFO);
        this.productNoList = productNoList;
        request.addProperty(PRODUCT_NO, productNoList);
    }

    @Override
    protected void Result(HashMap<String, Object> resultData) {
        if(resultData == null) { //error!

        }else{
            int resultCode = Integer.parseInt(resultData.get("resultCode").toString());
            if(resultCode == 200)
                Application.sendMessage(handler, MessageManager.PRODUCT_INFO_LOADING_ACCESS, 0, 0, resultData.get("productContext"));

        }

    }
}
