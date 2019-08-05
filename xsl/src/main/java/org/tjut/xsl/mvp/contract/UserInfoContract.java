package org.tjut.xsl.mvp.contract;

import com.jess.arms.http.imageloader.ImageConfig;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import org.tjut.xsl.mvp.model.entity.User;

import java.util.WeakHashMap;

import io.reactivex.Completable;
import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/26/2019 18:47
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface UserInfoContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void showUserInfo(User user);

        ImageConfig getTxConfig();

        void showSignOut();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        Observable<User> getUser(String userId);

        Observable<String> updataInfo(WeakHashMap<String,Object> parmes);

        Observable<String> signOut(String phone);

        Observable<User> getUserInfoLoacal(String userId);
    }
}
