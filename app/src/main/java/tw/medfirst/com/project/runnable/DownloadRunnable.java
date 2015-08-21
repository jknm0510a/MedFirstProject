package tw.medfirst.com.project.runnable;

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

/**
 * Created by KCTsai on 2015/8/7.
 */
public class DownloadRunnable implements Runnable{
    private DownloadEntity downloadEntity;
    int index;
    String savePath = null;

    public DownloadRunnable(DownloadEntity d, int index){
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
                || downloadEntity.getPath().equals(""))
            return;
        if(Application.checkIsFileExist(savePath)) {
            Logger.e("DownloadRunnable", downloadEntity.getName() + " is exist");
            return;
        }

        Logger.e("Thread: ", index);

//            Application.createFileIfNeed(Application.videoPath);
//            Application.createFileIfNeed(Application.imagePath);
        try {
            BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(savePath));
            URL path = new URL(Application.domain + downloadEntity.getPath());
            HttpURLConnection urlConn = (HttpURLConnection) path.openConnection();
            InputStream inputStream = urlConn.getInputStream();
            int responseCode = urlConn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                int bytesRead = -1;
                int length = 0;
                byte[] buffer = new byte[4096];

                while ((bytesRead = inputStream.read(buffer)) != -1) {
//                  Logger.e("bytesRead", bytesRead);
                    length += bytesRead;
                    bout.write(buffer, 0, bytesRead);
                }

                Logger.e("=========" + downloadEntity.getName() + "=========", "Length: " + length);
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
