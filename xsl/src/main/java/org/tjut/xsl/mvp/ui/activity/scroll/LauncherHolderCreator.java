package org.tjut.xsl.mvp.ui.activity.scroll;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;

import org.tjut.xsl.mvp.ui.activity.scroll.LauncherHolder;

public class LauncherHolderCreator implements CBViewHolderCreator<LauncherHolder> {
    @Override
    public LauncherHolder createHolder() {
        return new LauncherHolder();
    }
}
