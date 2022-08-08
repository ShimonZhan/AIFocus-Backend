package org.cmyk.aifocus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.jdbc.Null;
import org.cmyk.aifocus.entity.User;
import org.cmyk.aifocus.utils.LoginBack;
import org.cmyk.aifocus.utils.Response;
import org.springframework.web.multipart.MultipartFile;

public interface UserService extends IService<User> {
    boolean existEmail(String email);

    Response<LoginBack> login(String email, String password);

    Response<Null> register(User user);

    Response<User> getInfo(String email);

    Response<User> updateInfo(User user);

    Response<Null> delete(String email);

    Response<Null> uploadUserAvatar(String email, MultipartFile file);

    Response<Null> activateUser(String email, String checkCode);

    Response<Null> rePostCheckCode(String email);

    Response<Null> forgetPassword(String email, String checkCode, String newPassword);

    Response<User> getInfoById(String id);
}
