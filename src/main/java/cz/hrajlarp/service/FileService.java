package cz.hrajlarp.service;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;

/**
 * This Service is about working with Files.
 */
@Service
public class FileService {
    public String getFileType(String fileName){
        String[] fileParts = fileName.trim().split("\\.");
        if(fileParts.length > 0){
            return fileParts[fileParts.length - 1];
        } else {
            return "";
        }
    }

    public String saveImage(MultipartFile imageToSave, String newName, ServletContext context) {
        /* create directories if necessary */
        boolean dirsExists = true;
        String path = "";

        File dir = new File(context.getRealPath("assets/img/upload/"));
        if (!(dirsExists = dir.exists())) {
            if (!(dirsExists = dir.mkdirs())) {
                System.out.println("Files could not be created!");
            }
        }

                /* copy attached file into new file on given path */
        if (dirsExists) {
            String fileType = getFileType(imageToSave.getOriginalFilename());
            String basePath = "/" + newName + "_" + System.currentTimeMillis() + "." + fileType;
            path = (context.getRealPath("assets/img/upload/") + basePath);
            try {
                imageToSave.transferTo(new File(path));
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("It was not possible to save image.");
            }
            path = "/img/upload/" + basePath;
        }

        return path;
    }
}