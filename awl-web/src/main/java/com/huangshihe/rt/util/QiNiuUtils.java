package com.huangshihe.rt.util;

import com.jfinal.kit.PropKit;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

import java.io.File;

/**
 * Created by huang.shihe on 8/16/15.
 */
public class QiNiuUtils {

    public static final String ACCESS_KEY = PropKit.get("ACCESS_KEY");

    public static final String SECRET_KEY = PropKit.get("SECRET_KEY");

    public static final String QINIU_BUCKNAME = PropKit.get("QINIU_BUCKNAME");

    private static Auth auth;
    private static UploadManager uploadManager;

    public static Response upload( File file, String fileName ) throws QiniuException {
        Response res = null;
        if( auth == null ){
            auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        }
        if( uploadManager == null ){
            uploadManager = new UploadManager();
        }
        res = uploadManager.put(file, fileName, auth.uploadToken(QINIU_BUCKNAME));

        return res;
    }

    public static String getUrl(String fileName){
        if( auth == null ){
            auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        }
        String url = "http://7xixlo.com1.z0.glb.clouddn.com/" + fileName;
        //指定时长 ten minutes
        return auth.privateDownloadUrl( url, 60 * 10 );
    }

}
