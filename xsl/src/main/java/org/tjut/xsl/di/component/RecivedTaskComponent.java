package org.tjut.xsl.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import org.tjut.xsl.di.module.RecivedTaskModule;
import org.tjut.xsl.mvp.contract.RecivedTaskContract;

import com.jess.arms.di.scope.FragmentScope;

import org.tjut.xsl.mvp.ui.fragment.RecivedTaskFragment;


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
@Component(modules = RecivedTaskModule.class, dependencies = AppComponent.class)
public interface RecivedTaskComponent {
    void inject(RecivedTaskFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        RecivedTaskComponent.Builder view(RecivedTaskContract.View view);

        RecivedTaskComponent.Builder appComponent(AppComponent appComponent);

        RecivedTaskComponent build();
    }
}