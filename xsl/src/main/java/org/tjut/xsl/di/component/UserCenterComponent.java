package org.tjut.xsl.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import org.tjut.xsl.di.module.UserCenterModule;
import org.tjut.xsl.mvp.contract.UserCenterContract;

import com.jess.arms.di.scope.ActivityScope;

import org.tjut.xsl.mvp.ui.activity.UserCenterActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/26/2019 17:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = UserCenterModule.class, dependencies = AppComponent.class)
public interface UserCenterComponent {
    void inject(UserCenterActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        UserCenterComponent.Builder view(UserCenterContract.View view);

        UserCenterComponent.Builder appComponent(AppComponent appComponent);

        UserCenterComponent build();
    }
}