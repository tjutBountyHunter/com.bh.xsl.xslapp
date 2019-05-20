package org.tjut.xsl.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import org.tjut.xsl.di.module.SelectTagModule;
import org.tjut.xsl.mvp.contract.SelectTagContract;

import com.jess.arms.di.scope.ActivityScope;

import org.tjut.xsl.mvp.ui.activity.SelectTagActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/20/2019 03:26
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = SelectTagModule.class, dependencies = AppComponent.class)
public interface SelectTagComponent {
    void inject(SelectTagActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SelectTagComponent.Builder view(SelectTagContract.View view);

        SelectTagComponent.Builder appComponent(AppComponent appComponent);

        SelectTagComponent build();
    }
}