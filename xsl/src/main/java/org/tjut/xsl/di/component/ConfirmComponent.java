package org.tjut.xsl.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import org.tjut.xsl.di.module.ConfirmModule;
import org.tjut.xsl.mvp.contract.ConfirmContract;

import com.jess.arms.di.scope.ActivityScope;

import org.tjut.xsl.mvp.ui.activity.ConfirmActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/20/2019 02:02
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ConfirmModule.class, dependencies = AppComponent.class)
public interface ConfirmComponent {
    void inject(ConfirmActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ConfirmComponent.Builder view(ConfirmContract.View view);

        ConfirmComponent.Builder appComponent(AppComponent appComponent);

        ConfirmComponent build();
    }
}