package com.thinkgem.jeesite.modules.api;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.oauth.entity.Result;
import com.thinkgem.jeesite.common.oauth.entity.ResultStatusCode;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.PathUtil;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.annotation.IgnoreSecurity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.imageio.ImageIO;
import javax.imageio.stream.MemoryCacheImageInputStream;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.UUID;

/**
 * @Description:
 * @Date: 2018/1/9
 * @Author: wcf
 */
@RestController
@RequestMapping("/api/upload")
public class UploadController extends BaseController {


     /**
      * @description 上传图片
      * @param
      * @author wcf
      * @date 2018/1/9
      * @return
      */
    @RequestMapping("uploadImg")
    @IgnoreSecurity
    public Result uploadImg(HttpServletRequest request) throws IOException {
        String source = "/uploadimages/" + Global.getConfig("productEnName");
        String date = DateUtils.getDate("yyyyMMdd");
        String path = PathUtil.initDirUpload(source, date);

        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                MultipartFile file = multiRequest.getFile(iter.next());
                if (file != null) {
                    if (file.getSize() > 1024 * 1024 * 10) {
                        return new Result(Global.ERROR_CODE, "图片大小不能超过10M");
                    } else {
                        String oldFileName = file.getOriginalFilename();
                        String extenstion = oldFileName.substring(oldFileName.lastIndexOf(".")).toLowerCase();
                        if (!extenstion.equals(".jpg") && !extenstion.equals(".jpeg") && !extenstion.equals(".bmp") && !extenstion.equals(".png")) {
                            return new Result(Global.ERROR_CODE, "图片格式不正确");
                        } else {
                            String newFileName = UUID.randomUUID().toString();
                            String newFilePath = path +"/"+ newFileName + extenstion;
                            File localFile = new File(newFilePath);

                            file.transferTo(localFile);
                            String result = source+"/" +date+"/"+ newFileName + extenstion;
                            return new Result(ResultStatusCode.OK, result);
                        }
                    }
                }

            }
        }
        return new Result(Global.ERROR_CODE, "请上传图片");
    }
}
