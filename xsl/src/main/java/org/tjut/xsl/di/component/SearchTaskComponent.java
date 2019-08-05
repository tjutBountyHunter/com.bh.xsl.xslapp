package org.tjut.xsl.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import org.tjut.xsl.di.module.SearchTaskModule;
import org.tjut.xsl.mvp.contract.SearchTaskContract;

import com.jess.arms.di.scope.ActivityScope;

import org.tjut.xsl.mvp.ui.activity.SearchTaskActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/29/2019 02:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = SearchTaskModule.class, dependencies = AppComponent.class)
public interface SearchTaskComponent {
    void inject(SearchTaskActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SearchTaskComponent.Builder view(SearchTaskContract.View view);

        SearchTaskComponent.Builder appComponent(AppComponent appComponent);

        SearchTaskComponent build();
    }
}