package org.tjut.xsl.JPush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.tjut.xsl.database.DatabaseManager;
import org.tjut.xsl.mvp.model.entity.Message;
import org.tjut.xsl.mvp.model.entity.MessageDao;
import org.tjut.xsl.mvp.ui.activity.TaskDetailActivity;

import java.text.SimpleDateFormat;

import cn.jpush.android.api.JPushInterface;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MyBroadcastReceiver extends BroadcastReceiver {
    private final String TAG = this.getClass().getSimpleName();
    private String taskId;

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            String ex = null;
            if (bundle != null) {
                ex = bundle.getString(JPushInterface.EXTRA_EXTRA);
            }
            if (ex != null && !ex.isEmpty()) {
                JSONObject jsonObject = JSON.parseObject(ex);
                taskId = jsonObject.getString("taskId");
            }
            Timber.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + (bundle));

            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                if (bundle != null) {
                    String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                }
//                Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
                //send the Registration Id to your server...

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                if (bundle != null && bundle.getString(JPushInterface.EXTRA_MESSAGE) != null) {
                    Timber.d(bundle.getString(JPushInterface.EXTRA_MESSAGE));
                    JSONObject jsonObject = JSON.parseObject(bundle.getString(JPushInterface.EXTRA_MESSAGE));
                    processCustomMessage(context, jsonObject);
                }

//                Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
//                processCustomMessage(context, bundle);

            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
//                Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
//                Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
//                Log.d(TAG, "[MyReceiver] 用户点击打开了通知");

                //打开自定义的Activity
                Intent i = new Intent(context, TaskDetailActivity.class);
                if (bundle != null) {
                    i.putExtras(bundle);
                    i.putExtra("taskId", taskId);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(i);
                }

            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
//                Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
//                Log.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
            } else {
//                Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e) {

        }
    }

    private void processCustomMessage(Context context, JSONObject jsonObject) {
        Timber.tag(TAG).d(jsonObject.toJSONString());
        Observable.just(jsonObject)
                .map(jsonObject1 -> {
                    Message message = new Message();
                    message.setTitle(jsonObject1.getString("title"));
                    message.setMsg_content(jsonObject1.getString("msg_content"));
                    jsonObject1 = jsonObject1.getJSONObject("extras");
                    message.setTaskId(jsonObject1.getString("taskId"));
                    Long time = System.currentTimeMillis();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                    String timeString = simpleDateFormat.format(time);
                    message.setDate(timeString);

                    MessageDao messageDao = DatabaseManager.getInstance().getMessageDao();
                    messageDao.insertOrReplace(message);
                    Timber.tag(TAG).d(message.toString());
                    return message;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<Message>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Message message) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
