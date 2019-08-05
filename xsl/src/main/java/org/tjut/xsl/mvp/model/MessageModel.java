package org.tjut.xsl.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import org.tjut.xsl.app.DataNullException;
import org.tjut.xsl.database.DatabaseManager;
import org.tjut.xsl.mvp.contract.MessageContract;
import org.tjut.xsl.mvp.model.entity.Message;
import org.tjut.xsl.mvp.model.entity.MessageDao;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/26/2019 23:55
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class MessageModel extends BaseModel implements MessageContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MessageModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<List<Message>> getMessages(int i) {
        return Observable.just(i)
                .map(integer -> {
                    MessageDao messageDao = DatabaseManager.getInstance().getMessageDao();
                    List<Message> messages = messageDao.loadAll();
                    if (messages == null || messages.isEmpty()) {
                        throw new DataNullException(0, "没有消息");
                    }
                    return messages;
                });
    }
}