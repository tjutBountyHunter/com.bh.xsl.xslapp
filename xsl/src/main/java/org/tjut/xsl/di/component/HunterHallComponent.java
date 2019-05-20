package org.tjut.xsl.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import org.tjut.xsl.di.module.HunterHallModule;
import org.tjut.xsl.mvp.contract.HunterHallContract;

import com.jess.arms.di.scope.FragmentScope;

import org.tjut.xsl.mvp.ui.fragment.HunterHallFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/19/2019 12:27
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = HunterHallModule.class, dependencies = AppComponent.class)
public interface HunterHallComponent {
    void inject(HunterHallFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        HunterHallComponent.Builder view(HunterHallContract.View view);

        HunterHallComponent.Builder appComponent(AppComponent appComponent);

        HunterHallComponent build();
    }
}