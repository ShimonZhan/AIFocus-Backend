package org.cmyk.aifocus.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.jdbc.Null;
import org.cmyk.aifocus.entity.Clazz;
import org.cmyk.aifocus.entity.User;
import org.cmyk.aifocus.utils.Response;
import org.springframework.web.multipart.MultipartFile;

public interface ClassService extends IService<Clazz> {

    boolean classExist(String userId, String classId);

    boolean inviteCodeExist(String inviteCode);

    Response<String> addClass(String teacherId, Clazz clazz);

    Response<Clazz> getClass(String classId);

    Response<Page<Clazz>> getUserClasses(Page<Clazz> page, String userId);

    Response<Null> leaveClass(Integer role, String userId, String classId);

    Response<Page<Clazz>> searchClass(Page<Clazz> page, String keyword);

    Response<User> getTeacherFromClass(String classId);

    Response<Page<User>> getStudentsFromClass(Page<User> page, String classId);

    Response<Null> updateClass(Clazz clazz);

    Response<Null> updateClassInviteCode(String ClassId, String inviteCode);

    Response<Null> studentJoinClass(String studentId, String classId);

    Response<Null> deleteClass(String classId);

    Response<Null> uploadClassAvatar(String classId, MultipartFile file);

    Response<Clazz> searchClassByCode(String code);
}
