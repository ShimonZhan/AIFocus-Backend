package org.cmyk.aifocus.controller;

import com.mysql.cj.util.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.ibatis.jdbc.Null;
import org.cmyk.aifocus.constants.UserStatus;
import org.cmyk.aifocus.entity.User;
import org.cmyk.aifocus.service.UserService;
import org.cmyk.aifocus.utils.LoginBack;
import org.cmyk.aifocus.utils.Response;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import static org.cmyk.aifocus.utils.CheckCodeUtil.getCheckCode;
import static org.cmyk.aifocus.utils.CheckCodeUtil.getCheckCodeExpireTime;

@Tag(name = "UserController 账号模块", description = "人员类型: student[0], teacher[1]")
@RestController
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @Operation(summary = "login 登陆", description = "登入成功时，message返回人员类型（数字）")
    @PostMapping("/login")
    public Response<LoginBack> login(@Parameter(description = "邮箱") @RequestParam String email,
                                     @Parameter(description = "密码") @RequestParam String password) {
        return userService.login(email, password);
    }

    @Operation(summary = "register 注册")
    @PostMapping("/register")
    public Response<Null> register(@Parameter(description = "人员类型") @RequestParam Integer type,
                                   @Parameter(description = "邮箱") @RequestParam String email,
                                   @Parameter(description = "密码") @RequestParam String password,
                                   @Parameter(description = "姓名") @RequestParam String name,
                                   @Parameter(description = "手机") @RequestParam String phone) {
        User user = User.builder()
                .id(null)
                .type(type)
                .email(email)
                .password(passwordEncoder.encode(password))
                .name(name)
                .phone(phone)
                .avater("https://cdn.aiexam.zhanxm.cn/avater/default.png")
                .checkCode(getCheckCode())
                .checkCodeExpireTime(getCheckCodeExpireTime())
                .status(UserStatus.INACTIVE)
                .isOnline(false)
                .build();
        return userService.register(user);
    }

    @Operation(summary = "激活账号")
    @PostMapping("/activateUser")
    public Response<Null> activateUser(@Parameter(description = "邮箱") @RequestParam String email,
                                       @Parameter(description = "验证码") @RequestParam String checkCode) {
        return userService.activateUser(email, checkCode);
    }

    @Operation(summary = "重新发送验证码")
    @GetMapping("/rePostCheckCode/{email}")
    public Response<Null> rePostCheckCode(@Parameter(description = "邮箱") @PathVariable String email) {
        return userService.rePostCheckCode(email);
    }

    @Operation(summary = "updateUser 更新用户信息")
    @PutMapping("/updateUser/{email}")
    public Response<User> updateUser(@Parameter(description = "邮箱") @PathVariable String email,
                                     @Parameter(description = "人员类型") @RequestParam Integer type,
                                     @Parameter(description = "密码") @RequestParam String password,
                                     @Parameter(description = "姓名") @RequestParam String name,
                                     @Parameter(description = "手机") @RequestParam String phone) {
        User user = User.builder()
                .id(null)
                .type(type)
                .email(email)
                .password(StringUtils.isNullOrEmpty(password) ? null : passwordEncoder.encode(password))
                .name(StringUtils.isNullOrEmpty(name) ? null : name)
                .phone(StringUtils.isNullOrEmpty(phone) ? null : phone)
                .build();
        return userService.updateInfo(user);
    }

    @Operation(summary = "getUser 通过邮箱得到用户信息")
    @GetMapping("/getUser/{email}")
    public Response<User> getUser(@Parameter(description = "邮箱") @PathVariable String email) {
        return userService.getInfo(email);
    }

    @Operation(summary = "getUserById 通过id得到用户信息")
    @GetMapping("/getUserById/{id}")
    public Response<User> getUserById(@PathVariable String id) {
        return userService.getInfoById(id);
    }

    @Operation(summary = "deleteUser 删除用户")
    @DeleteMapping("/deleteUser/{email}")
    public Response<Null> deleteUser(@Parameter(description = "邮箱") @PathVariable String email) {
        return userService.delete(email);
    }

    @Operation(summary = "忘记密码")
    @PutMapping("/forgetPassword/{email}")
    public Response<Null> forgetPassword(@Parameter(description = "邮箱") @PathVariable String email,
                                         @Parameter(description = "验证码") @RequestParam String checkCode,
                                         @Parameter(description = "新密码") @RequestParam String newPassword) {
        return userService.forgetPassword(email, checkCode, passwordEncoder.encode(newPassword));
    }

    @Operation(summary = "上传头像")
    @PutMapping("/uploadUserAvater/{email}")
    public Response<Null> uploadUserAvatar(@Parameter(description = "邮箱") @PathVariable String email,
                                           @Parameter(description = "图片文件") @RequestParam MultipartFile file) {
        return userService.uploadUserAvatar(email, file);
    }
}
