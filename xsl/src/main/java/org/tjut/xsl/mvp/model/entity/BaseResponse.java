/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.tjut.xsl.mvp.model.entity;

import com.google.gson.Gson;

import org.json.JSONObject;
import org.tjut.xsl.mvp.model.api.Api;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 * ================================================
 * 如果你服务器返回的数据格式固定为这种方式(这里只提供思想,服务器返回的数据格式可能不一致,可根据自家服务器返回的格式作更改)
 * 指定范型即可改变 {@code data} 字段的类型, 达到重用 {@link BaseResponse}, 如果你实在看不懂, 请忽略
 * <p>
 * Created by JessYan on 26/09/2016 15:19
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class BaseResponse implements Serializable {
    private String data;
    private Integer status;
    private String msg;
    private Boolean ok;

    public <T> T getData(Class<T> tClass) {
        Gson gson = new Gson();
        return gson.fromJson(data, tClass);
    }

    public Integer getCode() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public Boolean getOk() {
        return ok;
    }

    /**
     * 请求是否成功
     *
     * @return
     */
    public boolean isSuccess() {
        if (status.equals(Api.RequestSuccess)) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public String toString() {
        return "BaseResponse{" +
                "data=" + data +
                ", status=" + status +
                ", msg='" + msg + '\'' +
                ", ok=" + ok +
                '}';
    }
}
