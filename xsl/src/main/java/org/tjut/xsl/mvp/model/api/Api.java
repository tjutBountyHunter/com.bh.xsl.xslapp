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

    String PORT = "80/";

    String API_HOST = "http://" + HOST_ADDR;

    String BASE_URL = API_HOST + ":" + PORT;

    String ROOT_PATH = BASE_URL + "xslService/xsl/";

    String USER_PATH = ROOT_PATH + "user/";

    String LOGIN = USER_PATH + "login";

    String REGIST_CODE = ROOT_PATH + "verify/message";

    String REGIST_CHECK_CODE = ROOT_PATH + "verify/checkmessage";

    String QUICK_REGISTER = USER_PATH + "quickRegister";

    String REQUEST_TASKS_INIT = ROOT_PATH + "task/initTaskInfo";

    String MY_HISTORY_HUNTER = ROOT_PATH + "hunter/queryHistoryHunter";

    String REQUEST_SCHOOLS = ROOT_PATH + "school/schoolClasses";

    String REQUEST_COLLEGE = ROOT_PATH + "school/collegeClasses";

    String REQUEST_MAJOR = ROOT_PATH + "school/majorClasses";

    String TASK_TAG = ROOT_PATH + "tag/queryTag";

    String TASK_CREATE_TAG = ROOT_PATH + "tag/createTag";

    String AUTHENTICATION = ROOT_PATH + "user/userAcc";

    String RELEASE_TASK = ROOT_PATH + "task/sendTask";

    String TASK_DETAIL = ROOT_PATH + "task/queryTaskInfo";

    String TASK_RECIVE = ROOT_PATH + "task/receiveTask";

    String TASK_ALLAREAD = ROOT_PATH + "task/querySendTask";

    String TASK_ALLRECIVE = ROOT_PATH + "task/queryReceiveTask";

    String UPDATA_INFO = USER_PATH + "saveUserInfo";

    String SIGN_OUT = USER_PATH + "logout";

    String REQUEST_LEVEL = USER_PATH + "gethminfo";

    String CANCEL_TASK = ROOT_PATH + "task/cancelTask";

    String HUNTER_COMFIRME_COMPLET = ROOT_PATH + "task/confirmTask";

    String MATER_COMPLET = ROOT_PATH + "task/okTask";

    String SEARCH_TASK = ROOT_PATH + "task/searchTask";

    String HOT_HUNTER = ROOT_PATH + "hunter/queryNBHunter";

    String UPLOAD_USER_TX = ROOT_PATH + "user/userTx";
}
