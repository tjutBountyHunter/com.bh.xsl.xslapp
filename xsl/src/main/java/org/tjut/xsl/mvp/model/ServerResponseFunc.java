package org.tjut.xsl.mvp.model;

import org.tjut.xsl.app.ServerException;
import org.tjut.xsl.mvp.model.entity.BaseResponse;

import io.reactivex.functions.Function;

public class ServerResponseFunc<T> implements Function<BaseResponse, T> {
    Class<T> aClass;

    public ServerResponseFunc(Class<T> cClass) {
        aClass = cClass;
    }

    @Override
    public T apply(BaseResponse baseResponse) throws Exception {
        if (!baseResponse.isSuccess())
            throw new ServerException(baseResponse.getCode(), baseResponse.getMsg());
        return baseResponse.getData(aClass);
    }
}
