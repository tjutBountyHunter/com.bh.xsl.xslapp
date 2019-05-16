package org.tjut.xsl.app;


import org.tjut.xsl.mvp.model.entity.User;

public class AccountManager {

    public static final int NOT_CONFIRM_STATE = 0;
    public static final int NORMAL_STATE = 1;


    private enum SignTag {
        SIGN_TAG,
        USER_ID,
        MASTER_ID,
        HUNTER_ID,
        PHONE,
        PASSWORD,
        STATE,          // 0 未认证 1 正常
        SCHOOL_NAME,
        SCHOOL_ID,
        COLLEGE_ID,
        COLLEGE_NAME,
        MAJOR_ID,
        MAJOR_NAME,
        ICON_URL,
        SNO,
        SEX
    }

    // 保存用户登录状态 ,登陆后调用
    public static void setSignState(User user, boolean state) {
        setSignState(state);
        setUserId(user.getUserid());
        setMasterId(user.getMasterid());
        setHunterId(user.getHunterid());
        setPhone(user.getPhone());
        setPassword(user.getPassword());
        setState(user.getState());
        setSchoolName(user.getSchool());
        setCollegeName(user.getCollege());
        setMajorName(user.getMajor());
        setTxUrl(user.getTxUrl());
        setSno(user.getSno());
        setSex(user.getSex());
    }


    private static void setSignState(boolean state) {
        TeaPreference.setAppFlag(SignTag.SIGN_TAG.name(), state);
    }

    private static boolean getSignState() {
        return TeaPreference.getAppFlag(SignTag.SIGN_TAG.name());
    }

    public static boolean isSignIn() {
        return getSignState();
    }


    public static void setUserId(String userId) {
        TeaPreference.setContextProfile(SignTag.USER_ID.name(), userId);
    }

    public static String getUserId() {
        return TeaPreference.getContextProfile(SignTag.USER_ID.name());
    }

    public static void setMasterId(String masterId) {
        TeaPreference.setContextProfile(SignTag.MASTER_ID.name(), masterId);
    }

    public static String getMasterId() {
        return TeaPreference.getContextProfile(SignTag.MASTER_ID.name());
    }

    public static void setHunterId(String hunterId) {
        TeaPreference.setContextProfile(SignTag.HUNTER_ID.name(), hunterId);
    }

    public static String getHunterId() {
        return TeaPreference.getContextProfile(SignTag.HUNTER_ID.name());
    }

    public static void setPhone(String phone) {
        TeaPreference.setContextProfile(SignTag.PHONE.name(), phone);
    }

    public static String getPhone() {
        return TeaPreference.getContextProfile(SignTag.PHONE.name());
    }


    public static void setTxUrl(String phone) {
        TeaPreference.setContextProfile(SignTag.ICON_URL.name(), phone);
    }

    public static String getTxUrl() {
        return TeaPreference.getContextProfile(SignTag.ICON_URL.name());
    }

    public static void setPassword(String password) {
        TeaPreference.setContextProfile(SignTag.PASSWORD.name(), password);
    }

    public static String getPassword() {
        return TeaPreference.getContextProfile(SignTag.PASSWORD.name());
    }


    public static void setState(Integer state) {
        TeaPreference.setContextInteger(SignTag.STATE.name(), state);
    }

    public static Integer getState() {
        return TeaPreference.getContextInteger(SignTag.STATE.name());
    }


    public static String getSchoolName() {
        return TeaPreference.getContextProfile(SignTag.SCHOOL_NAME.name());
    }

    public static void setSchoolName(String name) {
        TeaPreference.setContextProfile(SignTag.SCHOOL_NAME.name(), name);
    }

    public static String getCollegeName() {
        return TeaPreference.getContextProfile(SignTag.COLLEGE_NAME.name());
    }

    public static void setCollegeName(String name) {
        TeaPreference.setContextProfile(SignTag.COLLEGE_NAME.name(), name);
    }

    public static String getMajorName() {
        return TeaPreference.getContextProfile(SignTag.MAJOR_NAME.name());
    }

    public static void setMajorName(String name) {
        TeaPreference.setContextProfile(SignTag.MAJOR_NAME.name(), name);
    }


    public static String getSchoolId() {
        return TeaPreference.getContextProfile(SignTag.SCHOOL_ID.name());
    }

    public static void setSchoolId(String id) {
        TeaPreference.setContextProfile(SignTag.SCHOOL_ID.name(), id);
    }

    public static String getCollegeId() {
        return TeaPreference.getContextProfile(SignTag.COLLEGE_ID.name());
    }

    public static void setCollegeId(String id) {
        TeaPreference.setContextProfile(SignTag.COLLEGE_ID.name(), id);
    }

    public static String getMajorId() {
        return TeaPreference.getContextProfile(SignTag.MAJOR_ID.name());
    }

    public static void setMajorId(String id) {
        TeaPreference.setContextProfile(SignTag.MAJOR_ID.name(), id);
    }

    public static void setSno(String sno) {
        TeaPreference.setContextProfile(SignTag.SNO.name(), sno);
    }

    public static String getSno() {
        return TeaPreference.getContextProfile(SignTag.SNO.name());
    }

    public static void setSex(String sex) {
        TeaPreference.setContextProfile(SignTag.SEX.name(), sex);
    }

    public static String getSex() {
        return TeaPreference.getContextProfile(SignTag.SEX.name());
    }
}
