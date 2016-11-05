package com.xiaofo1022.b5235.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.springframework.stereotype.Component;

import com.google.common.io.Files;

@Component
public class FileUtil {

  private static final String PATH_STR = File.separator;
  
  public void saveImageFileToClasspath(String filename, byte[] imageBytes) throws IOException {
    URL base = this.getClass().getResource("/");
    String classpath = base.getFile();
    String imagepath = classpath + "static" + PATH_STR + "images" + PATH_STR;
    File imageBasePath = new File(imagepath);
    if (!imageBasePath.exists()) {
      imageBasePath.mkdirs();
    }
    String imageFilePath = imagepath + PATH_STR + filename;
    File imageFile = new File(imageFilePath);
    Files.write(imageBytes, imageFile);
    System.out.println("Save image file to: " + imageFilePath);
  }
}
