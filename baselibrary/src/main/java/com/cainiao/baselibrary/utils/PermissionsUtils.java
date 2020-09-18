package com.cainiao.baselibrary.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;


/**
 * @author liangtao
 * @date 2018/12/28 14:26
 * @describe 6.0动态权限管理帮助类
 */
public class PermissionsUtils {

    public static final int REQUEST_CODE = 1;


    /**
     * 判断权限
     *
     * @param context     context
     * @param permissions 权限列表
     */
    public static boolean checkPermissions(Activity context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> permissionsList = new ArrayList<>();
            if (permissions != null && permissions.length != 0) {
                for (String permission : permissions) {
                    if (!isHavePermissions(context, permission)) {
                        permissionsList.add(permission);
                    }
                }
                if (permissionsList.size() > 0) {
                    // 遍历完后申请
                    applyPermissions(context, permissionsList);
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 检查是否授权某权限
     */
    private static boolean isHavePermissions(Activity context, String permissions) {
        return ContextCompat.checkSelfPermission(context,permissions) == PackageManager.PERMISSION_GRANTED;
    }


    /**
     * 申请权限
     */
    private static void applyPermissions(Activity context, List<String> permissions) {
        if (!permissions.isEmpty()) {
            ActivityCompat.requestPermissions(context, permissions.toArray(new String[permissions.size()]), REQUEST_CODE);
        }
    }


}
