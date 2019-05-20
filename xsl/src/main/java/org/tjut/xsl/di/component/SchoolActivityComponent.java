package org.tjut.xsl.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import org.tjut.xsl.di.module.SchoolActivityModule;
import org.tjut.xsl.mvp.contract.SchoolActivityContract;

import com.jess.arms.di.scope.ActivityScope;

import org.tjut.xsl.mvp.ui.activity.SchoolActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/19/2019 15:12
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = SchoolActivityModule.class, dependencies = AppComponent.class)
public interface SchoolActivityComponent {
    void inject(SchoolActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SchoolActivityComponent.Builder view(SchoolActivityContract.View view);

        SchoolActivityComponent.Builder appComponent(AppComponent appComponent);

        SchoolActivityComponent build();
    }
}