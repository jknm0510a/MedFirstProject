package tw.medfirst.com.project.runnable;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;

import com.google.gson.Gson;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;

import tw.medfirst.com.project.manager.HttpManager;


/**
 * Created by KCTsai on 2015/6/10.
 */
public abstract class BaseRunnable implements Runnable{
    private static final int SOAP_VERSION = SoapEnvelope.VER11;

    protected static final String DEVICE_ID = "deviceID";
    protected static final String IP_ADDRESS = "ipAddress";

    protected SoapObject request;
    protected Context context;
    protected Handler handler;
    protected String action;
    protected String deviceID;
    protected String ipAddress;

    public BaseRunnable(Context context, Handler handler,
                        String deviceID, String ipAddress, String action, String method) {
        this.context = context;
        this.handler = handler;
        this.deviceID = deviceID;
        this.ipAddress = ipAddress;
        this.action = action;
        request = new SoapObject(HttpManager.NAME_SPACE, method);
        request.addProperty(DEVICE_ID, deviceID);
        request.addProperty(IP_ADDRESS, ipAddress);
    }

    @Override
    public void run() {
        if(request == null) {
            return;
        }
        try
        {
            SoapSerializationEnvelope envelope
                    = new SoapSerializationEnvelope(SOAP_VERSION);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(HttpManager.API_URL);
            androidHttpTransport.call(action, envelope);
            Object result = envelope.getResponse();
//            Log.e("json result: ", result.toString());
            if(result != null){
                Gson gson = new Gson();
                HashMap<String, Object> resultData = gson.fromJson(result.toString(), HashMap.class);
//                Log.e("json result: ", resultData.toString());
                Result(resultData);
            }
        }
        catch (IOException e){
            Log.e("IOException", e.toString());
        }
        catch (XmlPullParserException e){
            Log.e("XmlPullParserException", e.toString());
        }


    }




    protected abstract void Result(HashMap<String, Object> resultData);
}
