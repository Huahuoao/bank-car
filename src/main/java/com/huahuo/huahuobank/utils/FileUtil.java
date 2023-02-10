package com.huahuo.huahuobank.utils;

public class FileUtil {

    // 图片允许的后缀扩展名
    public static String[] IMAGE_FILE_EXTD = new String[] { "m4a","png", "bmp", "jpg", "jpeg","pdf","mp4" ,"mp3","wma","wav","flac","m4a","avi","flv","mkv","mov"};

    public static boolean isFileAllowed(String fileName) {
        for (String ext : IMAGE_FILE_EXTD) {
            if (ext.equals(fileName)) {
                return true;
            }
        }
        return false;
    }
}
