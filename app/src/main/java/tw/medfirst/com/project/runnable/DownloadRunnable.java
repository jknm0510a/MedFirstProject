package tw.medfirst.com.project.runnable;

import android.os.Handler;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import tw.medfirst.com.project.Application;
import tw.medfirst.com.project.Entity.DownloadEntity;
import tw.medfirst.com.project.baseunit.Logger;
import tw.medfirst.com.project.manager.MessageManager;

/**
 * Created by KCTsai on 2015/8/7.
 */
public class DownloadRunnable implements Runnable{
    private DownloadEntity downloadEntity;
    int index;
    String savePath = null;
    Handler handler;

    public DownloadRunnable(Handler handler, DownloadEntity d, int index){
        this.handler = handler;
        this.downloadEntity = d;
        this.index = index;
        init();
    }

    private void init() {
        if(downloadEntity == null)
            return;
        savePath = Application.getPath(downloadEntity.getName(), downloadEntity.getType());
    }


    @Override
    public void run() {
        if(downloadEntity == null || savePath == null || downloadEntity.getPath() == null
                || downloadEntity.getPath().equals("") || handler == null)
            return;
        if(Application.checkIsFileExist(savePath)) {
            Logger.e("DownloadRunnable", downloadEntity.getName() + " is exist");
            return;
        }

//        Logger.e("Thread: ", index);

//            Application.createFileIfNeed(Application.videoPath);
//            Application.createFileIfNeed(Application.imagePath);
        try {
            BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(savePath));
//            URL path = new URL(Application.domain + downloadEntity.getPath());
            URL path = new URL(downloadEntity.getPath());
            HttpURLConnection urlConn = (HttpURLConnection) path.openConnection();
            InputStream inputStream = urlConn.getInputStream();
            int responseCode = urlConn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                int bytesRead = -1;
                int length = urlConn.getContentLength();
                byte[] buffer = new byte[4096];
                int count = 0;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    count += bytesRead;
                    Application.sendMessage(handler, MessageManager.UPDATE_DOWNLOAD_PROGRESS,
                            0, 0, (int)(((float) count / length) * 100));
//                    Logger.e(downloadEntity.getName(), progress);
                    bout.write(buffer, 0, bytesRead);
                }

                Logger.e("=========" + downloadEntity.getName() + "=========", "Length: " + length);
                Application.sendMessage(handler, MessageManager.DOWNLOAD_COMPLETE, 0, 0, null);
                bout.close();
                inputStream.close();
            }else{
                retryDownload();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            retryDownload();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            retryDownload();
        } catch (IOException e) {
            e.printStackTrace();
            retryDownload();
        }
    }


    private void retryDownload() {
        if(downloadEntity == null)
            return;
        Application.deleteFile2(downloadEntity.getName(), downloadEntity.getType());
        if(downloadEntity.getTryCount() < 3){
            downloadEntity.setTryCount(downloadEntity.getTryCount() + 1);
            run();
        }else{
            Logger.e("=========" + index + "_" + downloadEntity.getName() + "=========", "ERROR!!!!!!");
        }

    }
}
