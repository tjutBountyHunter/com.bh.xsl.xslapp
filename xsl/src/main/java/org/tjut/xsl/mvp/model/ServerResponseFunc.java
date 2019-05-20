package org.tjut.xsl.mvp.model;

import org.tjut.xsl.app.ServerException;
import org.tjut.xsl.mvp.model.entity.BaseResponse;

import io.reactivex.functions.Function;

public class ServerResponseFunc<T> implements Function<BaseResponse, T> {

    public ServerResponseFunc() {
    }

    @Override
    public T apply(BaseResponse baseResponse) throws Exception {
        if (!baseResponse.isSuccess())
            throw new ServerException(baseResponse.getCode(), baseResponse.getMsg());
        return (T) baseResponse.getData();
    }
}
