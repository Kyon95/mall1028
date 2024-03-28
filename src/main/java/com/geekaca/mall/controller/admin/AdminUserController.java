package com.geekaca.mall.controller.admin;

import com.auth0.jwt.interfaces.Claim;
import com.geekaca.mall.common.Constants;
import com.geekaca.mall.common.MallConstants;
import com.geekaca.mall.controller.admin.param.AdminLoginParam;
import com.geekaca.mall.domain.AdminUser;
import com.geekaca.mall.service.AdminUserService;
import com.geekaca.mall.utils.JwtUtil;
import com.geekaca.mall.utils.Result;
import com.geekaca.mall.utils.ResultGenerator;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.ConstantBootstraps;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Random;

/**
 * 后台用户管理接口
 */
@CrossOrigin
@RestController
@RequestMapping("/manage-api/v1")
public class AdminUserController {
    @Value("${upload.path}")
    private String UPLOAD_PATH;

    @Autowired
    private AdminUserService adminUserService;

    @PostMapping("/adminUser/login")
    public Result login(@Valid @RequestBody AdminLoginParam adminLoginParam) {
        String loginToken = adminUserService.login(adminLoginParam);
        if (loginToken == null) {
            return ResultGenerator.genFailResult("登录失败");
        } else {
            Result result = ResultGenerator.genSuccessResult();
            result.setData(loginToken);
            return result;
        }
    }



    @GetMapping("/adminUser/profile")
    @ApiOperation(value = "获取管理员信息",notes = "获取管理员信息显示在前端界面")
    public Result profile(HttpServletRequest request){
        String token = request.getHeader("token");
        Map<String, Claim> stringClaimMap = JwtUtil.verifyToken(token);
        Claim idClaim = stringClaimMap.get("id");
        String uid = idClaim.asString();
        Long longId = Long.valueOf(uid);
        Claim userNameClaim = stringClaimMap.get("userName");
        String userName = userNameClaim.asString();

        AdminUser adminUser = adminUserService.selectAdminById(longId);

        Result result = ResultGenerator.genSuccessResult(adminUser);
        return  result;
    }

    @PostMapping("/upload/file")
    @ResponseBody
    public Result uploadFile(@RequestParam("file") MultipartFile fileUpload,HttpServletRequest httpServletRequest) throws URISyntaxException {
        System.out.println("upload....");
        Result rs = new Result();
//        String fileName = fileUpload.getOriginalFilename();


        //目标：接收用户上传的图片，改名，而且不能冲突
        String fileName = fileUpload.getOriginalFilename();
        /**
         * 取文件的扩展名
         * avatar.png   jpg
         */
        //首先找到.在文件名字中最后一次出现的索引
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dft = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String curDtime = localDateTime.format(dft);
        Random r = new Random();
        StringBuilder tempName = new StringBuilder();
        //为了生成随机的文件名字 + 扩展名
        tempName.append(curDtime).append(r.nextInt(100)).append(suffixName);
        String newFileName = tempName.toString();
        File fileDirectory = new File(MallConstants.FILE_UPLOAD_DIC);

        //创建文件
        String tmpFilePath = UPLOAD_PATH;
        String dataPath = "http://localhost:" + httpServletRequest.getServerPort() +"/goods-img/" + newFileName;
        File upFile = new File(tmpFilePath, newFileName);
        try {
            fileUpload.transferTo(upFile);
            rs.setResultCode(200);
            rs.setData(dataPath);
            rs.setMessage("图片上传成功");

        } catch (IOException e) {
            rs.setResultCode(500);
            rs.setMessage("图片上传失败");
            e.printStackTrace();
        }
        return rs;
    }
}
