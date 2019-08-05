package org.tjut.xsl.mvp.contract;

import com.jess.arms.http.imageloader.ImageConfig;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import org.tjut.xsl.mvp.model.entity.Hunter;
import org.tjut.xsl.mvp.model.entity.TaskDetail;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/26/2019 16:18
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface TaskDetailContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void showTask(TaskDetail taskDetail);

        ImageConfig getTxConfig();

        void showHunterCompleted();

        void showMasterComplet();

        void showReciveComplet();

        void showCancelTaskComplet();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        Observable<TaskDetail> getTask(String taskId);

        Observable<String> reciveTask(String taskId,String hunterId);

        Observable<String> cancelTask(String taskId);

        Observable<Hunter> hunterComplet(String taskId, String hunterId);

        Observable<Hunter> MasterComplet(String taskId, String hunterId);
    }
}
