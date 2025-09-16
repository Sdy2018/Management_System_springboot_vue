package org.example.manage.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.apache.ibatis.annotations.ResultMap;
import org.example.manage.common.Result;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/api/files")
public class FilesController {


    final String projectPath = System.getProperty("user.dir")+"/src/main/resources/files/";
    final String downloadPath="http://localhost:8083/api/files/download/";

    @GetMapping("/")
    public ResponseEntity<String> getFiles() {


        return ResponseEntity.ok("success");
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "description", required = false) String description
    ) throws IOException {

        // 2. 安全处理文件名
        String originalName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String uniqueName = UUID.randomUUID() + "_" + originalName;


        Path targetPath = Path.of(projectPath);

        if (!Files.exists(targetPath)) {
            try {
                Files.createDirectories(targetPath);  // 创建所有不存在的父目录

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        Files.copy(file.getInputStream(), Path.of(targetPath+"/"+uniqueName),
                StandardCopyOption.REPLACE_EXISTING);
        Map<String, String> response = new HashMap<>();
        response.put("msg", "上传成功");
        response.put("url", downloadPath+uniqueName);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/wangEditor/upload")
    public Map<String, Object> uploadImgFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "description", required = false) String description
    )  {

        // 2. 安全处理文件名
        String originalName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String uniqueName = UUID.randomUUID() + "_" + originalName;


        Path targetPath = Path.of(projectPath);

        if (!Files.exists(targetPath)) {
            try {
                Files.createDirectories(targetPath);  // 创建所有不存在的父目录

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            Files.copy(file.getInputStream(), Path.of(targetPath+"/"+uniqueName),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            Map<String, Object> result = new HashMap<>();
            result.put("errno", 1);  // 必须为数字类型
            result.put("message", "上传失败");
            return result;
        }
        Map<String, Object> result = new HashMap<>();
        result.put("errno", 0);  // 必须为数字类型
        Map<String, Object> data = new HashMap<>();
        data.put("url", downloadPath+uniqueName);  // 必须字段
        result.put("data", data);

        return result;
    }


    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable String fileName,
            HttpServletRequest request
    ) throws IOException {
        // 1. 路径安全校验
        Path filePa = Paths.get(projectPath+fileName).normalize();

        // 2. 文件存在性检查
        Resource resource = new UrlResource(filePa.toUri());
        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        // 3. 设置响应头
        String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
