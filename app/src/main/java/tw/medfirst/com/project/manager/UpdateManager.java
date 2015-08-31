package tw.medfirst.com.project.manager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import tw.medfirst.com.project.Application;
import tw.medfirst.com.project.Entity.DownloadEntity;
import tw.medfirst.com.project.R;
import tw.medfirst.com.project.runnable.DownloadRunnable;

/**
 * Created by KCTsai on 2015/8/4.
 */
public class UpdateManager {
    private final static int MAX_THREAD = 1;
    private Context mContext;
    private String downloadPath;
    private Dialog noticeDialog;
    private Dialog downloadDialog;
    private String name;
    private ProgressBar mProgress;
    private Handler activityHandler;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MessageManager.UPDATE_DOWNLOAD_PROGRESS:
                    mProgress.setProgress((int)msg.obj);
                    break;
                case MessageManager.DOWNLOAD_COMPLETE:
                    installApk();
                    break;
                default:
                    break;
            }
        }

    };


    public UpdateManager(Context context, Handler activityHandler, String path, String name) {
        this.mContext = context;
        this.downloadPath = path;
        this.name = name;
        this.activityHandler = activityHandler;
    }

    public void checkUpdateInfo(){
        showNoticeDialog();
    }


    private void showNoticeDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(mContext.getString(R.string.download_title));
        builder.setMessage(mContext.getString(R.string.download_message));
        builder.setPositiveButton(mContext.getString(R.string.download_enter), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                showDownloadDialog();
//                mHandler.sendEmptyMessage(DOWN_OVER);
            }
        });
        builder.setNegativeButton(mContext.getString(R.string.download_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        noticeDialog = builder.create();
        noticeDialog.show();
    }

    private void showDownloadDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(mContext.getString(R.string.download_title));

        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.progress_view, null);
        mProgress = (ProgressBar)v.findViewById(R.id.progress);

        builder.setView(v);
        builder.setNegativeButton(mContext.getString(R.string.download_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        downloadDialog = builder.create();
        downloadDialog.show();

        downloadApk();
    }

    /**
     *
     * @param
     */

    private void downloadApk(){
        if(downloadPath == null || name == null || activityHandler == null)
            return;
        final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(MAX_THREAD);
        final DownloadEntity d = new DownloadEntity("a", downloadPath, name);
        fixedThreadPool.execute(new DownloadRunnable(mHandler, d, 0));
    }
    /**
     *
     * @param
     */
    private void installApk(){
        String savePath = Application.getPath(name, "a");
        if(!Application.checkIsFileExist(savePath))
            return;

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setData(Uri.parse("file://" + savePath));
        i.setClassName("com.android.packageinstaller", "com.android.packageinstaller.PackageInstallerActivity");
        mContext.startActivity(i);
        downloadDialog.dismiss();
    }
}



