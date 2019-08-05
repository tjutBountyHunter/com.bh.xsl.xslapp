package org.tjut.xsl.mvp.model;

import org.tjut.xsl.app.DataNullException;
import org.tjut.xsl.app.ServerException;
import org.tjut.xsl.mvp.model.entity.BaseResponse;

import io.reactivex.functions.Function;

public class ServerResponseFunc<T> implements Function<BaseResponse<T>, T> {

    public ServerResponseFunc() {
    }

    @Override
    public T apply(BaseResponse<T> baseResponse) throws Exception {
        if (!baseResponse.isSuccess())
            throw new ServerException(baseResponse.getCode(), baseResponse.getMsg());
        else if (baseResponse.getData() == null)
            throw new DataNullException(baseResponse.getCode(), "数据为空");
        return baseResponse.getData();
    }
}
