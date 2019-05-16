package org.tjut.xsl.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import org.tjut.xsl.di.module.HallFragmentModule;
import org.tjut.xsl.mvp.contract.HallFragmentContract;

import com.jess.arms.di.scope.FragmentScope;

import org.tjut.xsl.mvp.ui.fragment.HallFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/17/2019 01:14
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = HallFragmentModule.class, dependencies = AppComponent.class)
public interface HallFragmentComponent {
    void inject(HallFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        HallFragmentComponent.Builder view(HallFragmentContract.View view);

        HallFragmentComponent.Builder appComponent(AppComponent appComponent);

        HallFragmentComponent build();
    }
}