package com.seezoon.framework.modules.zhfw.util;

/**
 * Created by zengqy on 2018/7/18.
 */
public class TokenUtil {
    /**
     *
     * @return
     */
    public static String genToke(String qdid){
        MD5 md5 = new MD5();
        return md5.GetMD5Code(qdid+SystemParam.TOKEN_SALT+SystemParam.TOKEN_SUFFIX);
    }
}
