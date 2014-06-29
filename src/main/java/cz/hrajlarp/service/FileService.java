package cz.hrajlarp.service;

/**
 * Created by IntelliJ IDEA.
 * User: Jakub Balhar
 * Date: 12.12.12
 * Time: 14:06
 */
public class FileService {
    public static String getFileType(String fileName){
        String[] fileParts = fileName.trim().split("\\.");
        if(fileParts.length > 0){
            return fileParts[fileParts.length - 1];
        } else {
            return "";
        }
    }
}