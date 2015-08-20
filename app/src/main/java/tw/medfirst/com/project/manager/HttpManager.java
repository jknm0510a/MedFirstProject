package tw.medfirst.com.project.manager;

import android.content.Context;
import android.os.Handler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import tw.medfirst.com.project.runnable.DerviceResponseRunnable;
import tw.medfirst.com.project.runnable.GudanceInfoRunnable;
import tw.medfirst.com.project.runnable.LayoutInfoRunnable;
import tw.medfirst.com.project.runnable.ProductInfoRunnable;
import tw.medfirst.com.project.runnable.ProductMenuRunnable;

/**
 * Created by KCTsai on 2015/6/10.
 */
public class HttpManager {

    public static final String METHOD_DEVICE_RESPONSE = "deviceResponse";
    public static final String METHOD_PRODUCT_MENU = "productClass";
    public static final String METHOD_LAYOUT_INFO = "layoutInfo";
    public static final String METHOD_PRODUCT_INFO = "productInfo";
    public static final String METHOD_GUDANCE_INFO = "loopMediaInfo";

    public static final String NAME_SPACE = "http://tempuri.org/";
    private static final String SERVICE_NAME_ = "IMedFirstService";
    public static final String API_URL = "http://172.17.20.150/Medfirst/WCF/MedFirstService.svc?wsdl";

    private ExecutorService mExecutorService = Executors.newFixedThreadPool(10);

    private volatile static HttpManager instance;
    public static HttpManager getInstance() {

        if (instance == null) {
            synchronized (HttpManager.class) {
                if (instance == null) {
                    instance = new HttpManager();
                }
            }
        }
        return instance;
    }

    public void release() {
        mExecutorService.shutdownNow();
        instance = null;
    }

    /**
     * �t�κʱ��^��
     * @param context
     * @param handler
     * @param deviceID    device�ߤ@�X
     * @param ipAddress   device IP
     */
    public void getDerviceResponseRunnableFromHttp(Context context, Handler handler,
                                                   String deviceID, String ipAddress){
        String action = NAME_SPACE + SERVICE_NAME_+ "/" + METHOD_DEVICE_RESPONSE;
        mExecutorService.execute(new DerviceResponseRunnable(context, handler, deviceID, ipAddress, action));
    }

    /**
     * �ӫ~���Э���T
     * @param context
     * @param handler
     * @param deviceID   device�ߤ@�X
     * @param ipAddress  device IP
     */
    public void getProductMenuRunnableFromHttp(Context context, Handler handler,
                                               String deviceID, String ipAddress){
        String action = NAME_SPACE + SERVICE_NAME_+ "/" + METHOD_PRODUCT_MENU;
        mExecutorService.execute(new ProductMenuRunnable(context, handler, deviceID, ipAddress, action));
    }


    /**
     * ������T
     * @param context
     * @param handler
     * @param deviceID
     * @param ipAddress
     */
    public void getLayoutInfoRunnableFromHttp(Context context, Handler handler,
                                               String deviceID, String ipAddress){
        String action = NAME_SPACE + SERVICE_NAME_+ "/" + METHOD_LAYOUT_INFO;
        mExecutorService.execute(new LayoutInfoRunnable(context, handler, deviceID, ipAddress, action));
    }


    /**
     *
     * @param context
     * @param handler
     * @param deviceID   device�ߤ@�X
     * @param ipAddress  device IP
     */
    public void getProductInfoRunnableFromHttp(Context context, Handler handler,
                                               String deviceID, String ipAddress, String productNoList){
        String action = NAME_SPACE + SERVICE_NAME_+ "/" + METHOD_PRODUCT_INFO;
        mExecutorService.execute(new ProductInfoRunnable(context, handler, deviceID, ipAddress, action, productNoList));
    }

    public void getGudanceInfoRunnableFromHttp(Context context, Handler handler,
                                               String deviceID, String ipAddress){
        String action = NAME_SPACE + SERVICE_NAME_+ "/" + METHOD_GUDANCE_INFO;
        mExecutorService.execute(new GudanceInfoRunnable(context, handler, deviceID, ipAddress, action));
    }

}
