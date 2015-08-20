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
            Log.e("json result: ", result.toString());
            if(result != null){
                Gson gson = new Gson();
                HashMap<String, Object> resultData = gson.fromJson(result.toString(), HashMap.class);
//                Log.e("json result: ", resultData.toString());
                Result(resultData);
            }
//            if(result != null){
//                HashMap<String, Object> resultData = (HashMap<String, Object>) DataInfoJson.getInstance().toObject(result.toString(), HashMap.class);
//                Result(resultData);
//            }else{
//                Result(null);
//            }


        }
        catch (IOException e){
//            soapMessage.setText(e.toString());
            Log.e("IOException", e.toString());
        }
        catch (XmlPullParserException e){
//            soapMessage.setText(e.toString());
            Log.e("XmlPullParserException", e.toString());
        }


    }

//    public static class DataInfoJson  {
//        private static DataInfoJson cInstance;
//        //
//        private ObjectMapper cJsonObjecMapper = new ObjectMapper();
//
//        public DataInfoJson() {
//            // SerializationFeature for changing how JSON is written
//
//            // to enable standard indentation ("pretty-printing"):
//            //cJsonObjecMapper.enable(SerializationFeature.INDENT_OUTPUT);
//            // to allow serialization of "empty" POJOs (no properties to serialize)
//            // (without this setting, an exception is thrown in those cases)
//            cJsonObjecMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
//            // to write java.util.Date, Calendar as number (timestamp):
//            cJsonObjecMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//
//            // DeserializationFeature for changing how JSON is read as POJOs:
//
//            // to prevent exception when encountering unknown property:
//            cJsonObjecMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//            // to allow coercion of JSON empty String ("") to null Object value:
//            cJsonObjecMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
//
//            //
//            cInstance = this;
//        }
//
//        //
//        public static DataInfoJson getInstance() {
//            if (cInstance == null)
//                cInstance = new DataInfoJson();
//            //
//            return cInstance;
//        }
//
//        public String toJsonString(Object obj) {
//            String rc = this.toString();
//            //
//            try {
//                rc = cJsonObjecMapper.writeValueAsString(obj);
//                //?rc = cJsonObjecMapper.convertValue(obj, String.class);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            //
//            return rc;
//        }
//
//        public byte[] toJsonByteArray(Object obj) {
//            byte[] rc = null;
//            //
//            try {
//                rc = cJsonObjecMapper.writeValueAsBytes(obj);
//                //? rc = cJsonObjecMapper.convertValue(obj, byte[].class);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            //
//            return rc;
//        }
//
//        public Object toObject(String jsonString,  Object obj) {
//            Object rc = null;
//            //
//            try {
//                rc = cJsonObjecMapper.readValue(jsonString, Object.class);
//                //rc = cJsonObjecMapper.readValue(jsonString,  new TypeReference<Map<String, Object>>() {});
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            //
//            return rc;
//        }
//
//        public Object toObject(byte[] jsonByteArray, Object obj) {
//            Object rc = null;
//            //
//            try {
//                rc = cJsonObjecMapper.readValue(jsonByteArray,  Object.class);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            //
//            return rc;
//        }
//    }



    protected abstract void Result(HashMap<String, Object> resultData);
}
