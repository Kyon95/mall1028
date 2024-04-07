package com.geekaca.mall.controller.admin;

import com.auth0.jwt.interfaces.Claim;
import com.geekaca.mall.common.MallConstants;
import com.geekaca.mall.controller.admin.param.AdminLoginParam;
import com.geekaca.mall.domain.AdminUser;
import com.geekaca.mall.service.AdminUserService;
import com.geekaca.mall.utils.JwtUtil;
import com.geekaca.mall.utils.Result;
import com.geekaca.mall.utils.ResultGenerator;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Random;

/**
 * 后台用户管理接口
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/manage-api/v1")
public class AdminUserController {
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private JedisPool jedisPool;

    public static URI getHost(URI uri) {
        URI effectiveURI = null;
        try {
            effectiveURI = new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(), null, null, null);
        } catch (Throwable var4) {
            effectiveURI = null;
        }
        return effectiveURI;
    }

    @PostMapping("/adminUser/login")
    public Result login(@Valid @RequestBody AdminLoginParam adminLoginParam) {
        String loginToken = adminUserService.login(adminLoginParam);
        if (loginToken == null) {
            return ResultGenerator.genFailResult("登录失败");
        } else {
            // 把token 存入redis
            Map<String, Claim> stringClaimMap = JwtUtil.verifyToken(loginToken);
            Claim idClaim = stringClaimMap.get("id");
            String uid = idClaim.asString();
            Jedis jedis = jedisPool.getResource();
            jedis.set("userToken:uid:"+uid, loginToken);
            jedis.expire("userToken:uid:"+uid,3600*24);
            Result result = ResultGenerator.genSuccessResult();
            result.setData(loginToken);
            return result;
        }
    }

    @DeleteMapping("/logout")
    public Result logout() {
        log.info("logout");
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/adminUser/profile")
    @ApiOperation(value = "获取管理员信息", notes = "获取管理员信息显示在前端界面")
    public Result profile(HttpServletRequest request) {
        String token = request.getHeader("token");
        Map<String, Claim> stringClaimMap = JwtUtil.verifyToken(token);
        Claim idClaim = stringClaimMap.get("id");
        String uid = idClaim.asString();
        Long longId = Long.valueOf(uid);
        Claim userNameClaim = stringClaimMap.get("userName");
        String userName = userNameClaim.asString();

        AdminUser adminUser = adminUserService.selectAdminById(longId);
        adminUser.setLoginPassword("******");

        Result result = ResultGenerator.genSuccessResult(adminUser);
        return result;
    }

    @PostMapping("/upload/file")
    @ResponseBody
    public Result uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest httpServletRequest) throws URISyntaxException, UnknownHostException {
        Result rs = new Result();
        //目标：接收用户上传的图片，改名，而且不能冲突
        String fileName = file.getOriginalFilename();
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
        File destFile = new File(MallConstants.FILE_UPLOAD_DIC + newFileName);
        try {
            if (!fileDirectory.exists()) {
                if (!fileDirectory.mkdir()) {
                    throw new IOException("文件夹创建失败,路径为：" + fileDirectory);
                }
            }
            //把文件保存到指定位置
            file.transferTo(destFile);
            Result resultSuccess = ResultGenerator.genSuccessResult();
            //给前端返回  图片的访问路径，前端会拿着这个路径url ，执行新增商品
            resultSuccess.setData(getHost(new URI(httpServletRequest.getRequestURL() + "")) + "/goods-img/" + newFileName);
            return resultSuccess;
        } catch (IOException e) {
            e.printStackTrace();
            return ResultGenerator.genFailResult("文件上传失败");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return ResultGenerator.genFailResult("文件上传失败");
        }
    }
}
