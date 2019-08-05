package org.tjut.xsl.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import org.tjut.xsl.di.module.TaskDetailModule;
import org.tjut.xsl.mvp.contract.TaskDetailContract;

import com.jess.arms.di.scope.ActivityScope;

import org.tjut.xsl.mvp.ui.activity.TaskDetailActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/26/2019 16:18
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = TaskDetailModule.class, dependencies = AppComponent.class)
public interface TaskDetailComponent {
    void inject(TaskDetailActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        TaskDetailComponent.Builder view(TaskDetailContract.View view);

        TaskDetailComponent.Builder appComponent(AppComponent appComponent);

        TaskDetailComponent build();
    }
}