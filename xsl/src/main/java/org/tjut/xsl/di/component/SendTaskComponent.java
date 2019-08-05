package org.tjut.xsl.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import org.tjut.xsl.di.module.SendTaskModule;
import org.tjut.xsl.mvp.contract.SendTaskContract;

import com.jess.arms.di.scope.FragmentScope;

import org.tjut.xsl.mvp.ui.fragment.SendTaskFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/26/2019 17:47
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = SendTaskModule.class, dependencies = AppComponent.class)
public interface SendTaskComponent {
    void inject(SendTaskFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SendTaskComponent.Builder view(SendTaskContract.View view);

        SendTaskComponent.Builder appComponent(AppComponent appComponent);

        SendTaskComponent build();
    }
}