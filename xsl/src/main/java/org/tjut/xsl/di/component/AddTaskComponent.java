package org.tjut.xsl.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import org.tjut.xsl.di.module.AddTaskModule;
import org.tjut.xsl.mvp.contract.AddTaskContract;

import com.jess.arms.di.scope.ActivityScope;

import org.tjut.xsl.mvp.ui.activity.AddTaskActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/26/2019 10:45
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = AddTaskModule.class, dependencies = AppComponent.class)
public interface AddTaskComponent {
    void inject(AddTaskActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AddTaskComponent.Builder view(AddTaskContract.View view);

        AddTaskComponent.Builder appComponent(AppComponent appComponent);

        AddTaskComponent build();
    }
}