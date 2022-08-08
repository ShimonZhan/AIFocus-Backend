package org.cmyk.aifocus.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upyun.RestManager;
import com.upyun.UpException;
import org.apache.ibatis.jdbc.Null;
import org.cmyk.aifocus.constants.ResponseStatus;
import org.cmyk.aifocus.constants.UserStatus;
import org.cmyk.aifocus.dao.UserMapper;
import org.cmyk.aifocus.entity.User;
import org.cmyk.aifocus.service.MailService;
import org.cmyk.aifocus.service.UserService;
import org.cmyk.aifocus.utils.LoginBack;
import org.cmyk.aifocus.utils.Response;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

import static org.cmyk.aifocus.utils.CheckCodeUtil.getCheckCode;
import static org.cmyk.aifocus.utils.CheckCodeUtil.getCheckCodeExpireTime;

@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @Resource
    private RestManager restManager;

    @Resource
    private MailService mailService;

    @Override
    public boolean existEmail(String email) {
        return getOne(Wrappers.lambdaQuery(User.class).eq(User::getEmail, email)) != null;
    }

    @Override
    public Response<LoginBack> login(String email, String password) {
        User user = getOne(Wrappers.lambdaQuery(User.class).eq(User::getEmail, email));
        // 判断用户是否存在 且 是否已激活
        if (user != null && UserStatus.INACTIVE.equals(user.getStatus())) {
            return new Response<>(ResponseStatus.FAILURE, "用户未激活", null);
        }
        // 判断用户密码是否正确
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return new Response<>(ResponseStatus.SUCCESS, "",
                    new LoginBack(user.getName(), user.getType(), user.getId(), user.getAvater()));
        } else {
            return new Response<>(ResponseStatus.FAILURE, "邮箱不存在或密码错误", null);
        }
    }

    @Override
    public Response<Null> register(User user) {
        if (existEmail(user.getEmail())) {
            return new Response<>(ResponseStatus.FAILURE, "邮箱已经存在", null);
        } else {
            user.insert();
            mailService.createMail(user.getEmail(), user.getCheckCode(), user.getCheckCodeExpireTime());
            return new Response<>(ResponseStatus.SUCCESS, "", null);
        }
    }

    @Override
    public Response<User> getInfo(String email) {
        User user = getOne(Wrappers.lambdaQuery(User.class).eq(User::getEmail, email));
        if (user != null) {
            return new Response<>(ResponseStatus.SUCCESS, "", user);
        } else {
            return new Response<>(ResponseStatus.FAILURE, "账号不存在", null);
        }
    }

    @Override
    public Response<User> updateInfo(User user) {
        User origin = getOne(Wrappers.lambdaUpdate(User.class).eq(User::getEmail, user.getEmail()));
        if (origin == null) {
            return new Response<>(ResponseStatus.FAILURE, "账号不存在", null);
        } else {
            user.setId(origin.getId());
            user.setStatus(origin.getStatus());
            user.setAvater(origin.getAvater());
            user.setStatus(origin.getStatus());
            user.setCheckCode(origin.getCheckCode());
            user.setCheckCodeExpireTime(origin.getCheckCodeExpireTime());
            user.updateById();
            return new Response<>(ResponseStatus.SUCCESS, "", null);
        }
    }

    @Override
    public Response<Null> delete(String email) {
        User user = getOne(Wrappers.lambdaUpdate(User.class).eq(User::getEmail, email));
        if (user == null) {
            return new Response<>(ResponseStatus.FAILURE, "账号不存在", null);
        } else {
            user.deleteById();
            return new Response<>(ResponseStatus.SUCCESS, "", null);
        }
    }

    @Override
    public Response<Null> uploadUserAvatar(String email, MultipartFile file) {
        User user = getOne(Wrappers.lambdaQuery(User.class).eq(User::getEmail, email));
        if (user != null) {
            String ext = Objects.requireNonNull(file.getContentType()).toLowerCase().replace("image/", "");
            String filename = email + "." + ext;
            String filePath = "/avater/" + filename;
            try {
                restManager.writeFile(filePath, file.getInputStream(), null);
            } catch (IOException e) {
                return new Response<>(ResponseStatus.FAILURE, "文件有误", null);
            } catch (UpException e) {
                return new Response<>(ResponseStatus.FAILURE, "上传失败", null);
            }
            user.setAvater("https://cdn.aiexam.zhanxm.cn" + filePath);
            user.updateById();
            return new Response<>(ResponseStatus.SUCCESS, filename, null);
        } else {
            return new Response<>(ResponseStatus.FAILURE, "用户不存在", null);
        }
    }

    @Override
    public Response<Null> activateUser(String email, String checkCode) {
        User user = getOne(Wrappers.lambdaQuery(User.class).eq(User::getEmail, email));
        if (user == null) {
            return new Response<>(ResponseStatus.FAILURE, "用户不存在", null);
        } else if (UserStatus.ACTIVE.equals(user.getStatus())) {
            return new Response<>(ResponseStatus.FAILURE, "用户已激活", null);
        }
        if (user.getCheckCode().equals(checkCode) && user.getCheckCodeExpireTime().isAfter(LocalDateTime.now())) {
            user.setStatus(UserStatus.ACTIVE);
            user.setCheckCodeExpireTime(LocalDateTime.now());
            user.updateById();
            return new Response<>(ResponseStatus.SUCCESS, "", null);
        } else {
            return new Response<>(ResponseStatus.FAILURE, "验证码错误或已过期", null);
        }
    }

    @Override
    public Response<Null> rePostCheckCode(String email) {
        User user = getOne(Wrappers.lambdaQuery(User.class).eq(User::getEmail, email));
        if (user == null) {
            return new Response<>(ResponseStatus.FAILURE, "用户不存在", null);
        }
        String checkCode = getCheckCode();
        user.setCheckCode(checkCode);
        user.setCheckCodeExpireTime(getCheckCodeExpireTime());
        user.updateById();
        mailService.createMail(email, checkCode, user.getCheckCodeExpireTime());
        return new Response<>(ResponseStatus.SUCCESS, "", null);
    }

    @Override
    public Response<Null> forgetPassword(String email, String checkCode, String newPassword) {
        User user = getOne(Wrappers.lambdaQuery(User.class).eq(User::getEmail, email));
        if (user == null) {
            return new Response<>(ResponseStatus.FAILURE, "用户不存在", null);
        }
        if (user.getCheckCode().equals(checkCode) && user.getCheckCodeExpireTime().isAfter(LocalDateTime.now())) {
            user.setPassword(newPassword);
            user.setCheckCodeExpireTime(LocalDateTime.now());
            user.updateById();
            return new Response<>(ResponseStatus.SUCCESS, "", null);
        } else {
            return new Response<>(ResponseStatus.FAILURE, "验证码错误或已过期", null);
        }
    }

    @Override
    public Response<User> getInfoById(String id) {
        User user = getById(id);
        if (user == null) {
            return new Response<>(ResponseStatus.FAILURE, "用户不存在", null);
        }
        return new Response<>(ResponseStatus.SUCCESS, "", user);
    }
}
