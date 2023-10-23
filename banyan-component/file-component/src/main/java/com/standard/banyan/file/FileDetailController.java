package com.standard.banyan.file;

import cn.xuyanwu.spring.file.storage.FileInfo;
import cn.xuyanwu.spring.file.storage.FileStorageService;
import com.standard.banyan.common.CommonAssert;
import com.standard.banyan.common.util.ColaBeanUtil;
import com.standard.banyan.file.vo.UpImageVO;
import com.standard.banyan.web.common.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Path;

/**
 * 文件操作
 *
 * @author dingchengfeng*/
@RestController
@RequestMapping("/api/v2/file/")
public class FileDetailController {

    @Autowired
    private FileStorageService fileStorageService;

    /**
     * 上传文件，成功返回文件 url
     */
    @PostMapping("/")
    public ApiResult<String> upload(MultipartFile file) {
        FileInfo fileInfo = fileStorageService.of(file)
                .setPath("/file/")
                .setSaveFilename(file.getOriginalFilename())
                .upload();
        CommonAssert.notNull(fileInfo, FileErrorCode.FILE_UPLOAD_FAIL);
        return ApiResult.success(fileInfo.getUrl());
    }

    /**
     * 上传图片，成功返回文件 url
     */
    @PostMapping("/image")
    public ApiResult<UpImageVO> uploadImage(MultipartFile file) {
        FileInfo fileInfo = fileStorageService.of(file)
                .setPath("/image/")
                .setSaveFilename(file.getOriginalFilename())
                .setSaveThFilename("thabc_" + file.getOriginalFilename())
                .thumbnail()
                .upload();
        CommonAssert.notNull(fileInfo, FileErrorCode.FILE_UPLOAD_FAIL);

        UpImageVO imageVO = ColaBeanUtil.copy(fileInfo, UpImageVO::new);
        return ApiResult.success(imageVO);
    }

    /**
     * 文件下载
     * @param filePath 文件路径
     * @param fileName 文件名称
     * @param response 响应
     * @throws IOException
     */
    @GetMapping("/{filePath}/{fileName}")
    public void download(@PathVariable String filePath, @PathVariable String fileName, HttpServletResponse response) throws IOException {
        String uriString = Path.of("/", filePath, fileName).toString();
        FileInfo info = fileStorageService.getFileInfoByUrl(uriString);
        CommonAssert.notNull(info, FileErrorCode.FILE_NOT_FOUND);
        response.setContentType(info.getContentType());
        fileStorageService.download(info)
                .outputStream(response.getOutputStream());
    }


}
