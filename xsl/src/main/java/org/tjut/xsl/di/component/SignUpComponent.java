package org.tjut.xsl.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import org.tjut.xsl.di.module.SignUpModule;
import org.tjut.xsl.mvp.contract.SignUpContract;

import com.jess.arms.di.scope.ActivityScope;

import org.tjut.xsl.mvp.ui.activity.SignUpActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/09/2019 13:17
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = SignUpModule.class, dependencies = AppComponent.class)
public interface SignUpComponent {
    void inject(SignUpActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SignUpComponent.Builder view(SignUpContract.View view);

        SignUpComponent.Builder appComponent(AppComponent appComponent);

        SignUpComponent build();
    }
}