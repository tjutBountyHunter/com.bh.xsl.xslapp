package org.tjut.xsl.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import org.tjut.xsl.mvp.contract.ChangePasswordContract;
import org.tjut.xsl.mvp.model.ChangePasswordModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/25/2019 20:54
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class ChangePasswordModule {

    @Binds
    abstract ChangePasswordContract.Model bindChangePasswordModel(ChangePasswordModel model);
}