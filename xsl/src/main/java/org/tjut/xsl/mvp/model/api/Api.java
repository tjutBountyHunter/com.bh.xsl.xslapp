package org.tjut.xsl.mvp.model.api;

/**
 * ================================================
 * 存放一些与 API 有关的东西,如请求地址,请求码等
 * <p>
 * Created by MVPArmsTemplate on 05/08/2019 15:18
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public interface Api {

    Integer RequestSuccess = 200;

    String HOST_ADDR = "47.93.200.190";

    String PORT = "8081/";

    String API_HOST = "http://" + HOST_ADDR;

    String BASE_URL = API_HOST + ":" + PORT;

    String ROOT_PATH = BASE_URL + "xslService/xsl/";

    String USER_PATH = ROOT_PATH + "user/";

    String LOGIN = USER_PATH + "login";

    String REGIST_CODE = ROOT_PATH + "verify/message";

    String REGIST_CHECK_CODE = ROOT_PATH + "verify/checkmessage";

    String QUICK_REGISTER = USER_PATH + "quickRegister";
}
