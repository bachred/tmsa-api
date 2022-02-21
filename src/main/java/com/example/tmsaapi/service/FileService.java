package com.example.tmsaapi.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import com.example.tmsaapi.model.AppUser;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

  private final String UPLOAD_FOLDER = "uploads";

  private final Log logger = LogFactory.getLog(FileService.class);

  public void init() {

    try {

      Path uploadPath = Paths.get(UPLOAD_FOLDER);

      if (!Files.exists(uploadPath)) {

        logger.info("Creating uploads folder ...");

        Files.createDirectory(Paths.get(UPLOAD_FOLDER));

      }

    } catch (IOException e) {

      throw new RuntimeException("Could not initialize folder for upload!");
    }
  }

  public Path saveFile(MultipartFile file) {

    try {

      byte[] bytes = file.getBytes();

      Path path = Paths.get(UPLOAD_FOLDER + "//" + file.getOriginalFilename());

      Files.write(path, bytes);

      return path.getFileName();

    } catch (Exception e) {

      throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
    }

  }

  public List<AppUser> parseContentFile(String filename) throws StreamReadException, DatabindException, IOException {

    try {
      String absolutePath = new FileSystemResource("uploads").getFile().getAbsolutePath();

      List<AppUser> users = Arrays
          .asList(new ObjectMapper().readValue(new File(absolutePath, filename), AppUser[].class));

      return users;

    } catch (Exception e) {

      throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
    }

  }

}
