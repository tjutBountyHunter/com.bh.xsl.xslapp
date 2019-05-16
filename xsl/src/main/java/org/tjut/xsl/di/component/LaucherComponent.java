package org.tjut.xsl.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import org.tjut.xsl.di.module.LaucherModule;
import org.tjut.xsl.mvp.contract.LaucherContract;

import com.jess.arms.di.scope.ActivityScope;

import org.tjut.xsl.mvp.ui.activity.LaucherActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/11/2019 02:39
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = LaucherModule.class, dependencies = AppComponent.class)
public interface LaucherComponent {
    void inject(LaucherActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        LaucherComponent.Builder view(LaucherContract.View view);

        LaucherComponent.Builder appComponent(AppComponent appComponent);

        LaucherComponent build();
    }
}