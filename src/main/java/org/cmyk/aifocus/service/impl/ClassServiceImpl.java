package org.cmyk.aifocus.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upyun.RestManager;
import com.upyun.UpException;
import org.apache.ibatis.jdbc.Null;
import org.cmyk.aifocus.constants.ResponseStatus;
import org.cmyk.aifocus.constants.UserRole;
import org.cmyk.aifocus.dao.ClassMapper;
import org.cmyk.aifocus.dao.UserClassMapper;
import org.cmyk.aifocus.entity.Clazz;
import org.cmyk.aifocus.entity.User;
import org.cmyk.aifocus.entity.UserClass;
import org.cmyk.aifocus.service.ClassService;
import org.cmyk.aifocus.service.ExamService;
import org.cmyk.aifocus.service.UserService;
import org.cmyk.aifocus.utils.InviteCodeUtil;
import org.cmyk.aifocus.utils.Response;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Objects;

@Transactional
@Service
public class ClassServiceImpl extends ServiceImpl<ClassMapper, Clazz> implements ClassService {
    @Resource
    private ClassMapper classMapper;

    @Resource
    private UserService userService;

    @Resource
    private UserClassMapper userClassMapper;

    @Resource
    private ExamService examService;

    @Resource
    private RestManager restManager;

    @Override
    public Response<String> addClass(String teacherId, Clazz clazz) {
        if (classExist(teacherId, clazz.getClassName())) {
            return new Response<>(ResponseStatus.FAILURE, "你已经有同名班级", null);
        } else if (inviteCodeExist(clazz.getInviteCode())) {
            return new Response<>(ResponseStatus.FAILURE, "邀请码已存在，请换一个", null);
        }
        String uuid = IdWorker.get32UUID();
        clazz.setId(uuid);
        clazz.insert();

        // 将老师加入班级
        UserClass.builder()
                .id(IdWorker.get32UUID())
                .classId(clazz.getId())
                .userId(teacherId)
                .UserType(UserRole.TEACHER)
                .className(clazz.getClassName())
                .build()
                .insert();
        return new Response<>(ResponseStatus.SUCCESS, "", uuid);
    }

    @Override
    public Response<Clazz> getClass(String classId) {
        Clazz clazz = getOne(Wrappers.lambdaQuery(Clazz.class).eq(Clazz::getId, classId));
        if (clazz != null) {
            return new Response<>(ResponseStatus.SUCCESS, "", clazz);
        } else {
            return new Response<>(ResponseStatus.FAILURE, "班级不存在", null);
        }
    }

    @Override
    public Response<Page<Clazz>> getUserClasses(Page<Clazz> page, String userId) {
        if (userService.getById(userId) == null) {
            return new Response<>(ResponseStatus.FAILURE, "没有该用户", null);
        }
        Page<Clazz> classPage = classMapper.selectUserClassPage(page, userId);
        return new Response<>(ResponseStatus.SUCCESS, "", classPage);
    }

    @Override
    public Response<Null> leaveClass(Integer role, String userId, String classId) {
        if (getById(classId) == null) {
            return new Response<>(ResponseStatus.FAILURE, "没有该班级", null);
        }
        if (userService.getById(userId) == null) {
            return new Response<>(ResponseStatus.FAILURE, "没有该用户", null);
        }
        if (UserRole.STUDENT.equals(role)) {
            userClassMapper.delete(Wrappers.lambdaQuery(UserClass.class)
                    .eq(UserClass::getUserId, userId).eq(UserClass::getClassId, classId));
        } else if (UserRole.TEACHER.equals(role)) {
            userClassMapper.delete(Wrappers.lambdaQuery(UserClass.class)
                    .eq(UserClass::getUserId, userId).eq(UserClass::getClassId, classId));
            removeById(classId);
        }
        return new Response<>(ResponseStatus.SUCCESS, "", null);
    }

    @Override
    public Response<Page<Clazz>> searchClass(Page<Clazz> page, String keyword) {
        Page<Clazz> clazzPage = page(page, Wrappers.lambdaQuery(Clazz.class)
                .like(Clazz::getClassName, "%" + keyword + "%")
                .eq(Clazz::getIsVisible, true));
        return new Response<>(ResponseStatus.SUCCESS, "", clazzPage);
    }

    @Override
    public boolean classExist(String userId, String className) {
        UserClass userClass = userClassMapper.selectOne(Wrappers.lambdaQuery(UserClass.class).eq(UserClass::getClassName, className));
        return userClass != null;
    }

    @Override
    public boolean inviteCodeExist(String inviteCode) {
        Clazz clazz = getOne(Wrappers.lambdaQuery(Clazz.class).eq(Clazz::getInviteCode, inviteCode));
        return clazz != null;
    }

    @Override
    public Response<User> getTeacherFromClass(String classId) {
        Clazz clazz = getById(classId);
        if (clazz == null) {
            return new Response<>(ResponseStatus.FAILURE, "没有该班级", null);
        }
        UserClass userClass = userClassMapper.selectOne(Wrappers.lambdaQuery(UserClass.class)
                .eq(UserClass::getClassId, clazz.getId())
                .eq(UserClass::getUserType, UserRole.TEACHER));
        User teacher = userService.getById(userClass.getUserId());
        return new Response<>(ResponseStatus.SUCCESS, "", teacher);
    }

    @Override
    public Response<Page<User>> getStudentsFromClass(Page<User> page, String classId) {
        if (getById(classId) == null) {
            return new Response<>(ResponseStatus.FAILURE, "没有该班级", null);
        }
        return new Response<>(ResponseStatus.SUCCESS, "", classMapper.selectStudentsFromClassPage(page, classId));
    }

    @Override
    public Response<Null> updateClass(Clazz clazz) {
        Clazz clazzOrigin = getById(clazz.getId());
        if (clazzOrigin == null) {
            return new Response<>(ResponseStatus.FAILURE, "没有该班级", null);
        }
        clazzOrigin.setCourseName(clazz.getCourseName());
        clazzOrigin.setClassName(clazz.getClassName());
        clazzOrigin.setIsVisible(clazz.getIsVisible());
        clazzOrigin.setAvater(null);

        clazzOrigin.updateById();
        return new Response<>(ResponseStatus.SUCCESS, "", null);
    }

    @Override
    public Response<Null> updateClassInviteCode(String ClassId, String inviteCode) {
        Clazz clazz = getById(ClassId);
        if (clazz == null) {
            return new Response<>(ResponseStatus.FAILURE, "没有该班级", null);
        }
        clazz.setInviteCode(InviteCodeUtil.changeOrDefaultInviteCode(inviteCode));
        clazz.setInviteCodeExpireTime(InviteCodeUtil.changeOrDefaultExpireTime(""));
        clazz.updateById();
        return new Response<>(ResponseStatus.SUCCESS, "", null);
    }

    @Override
    public Response<Null> studentJoinClass(String studentId, String classId) {
        User student = userService.getById(studentId);
        Clazz clazz = getById(classId);
        // 判断学生和班级是否存在
        if (student == null) {
            return new Response<>(ResponseStatus.FAILURE, "没有该用户", null);
        } else if (clazz == null) {
            return new Response<>(ResponseStatus.FAILURE, "没有该班级", null);
        }
        // 判断是否已经加入过该班级
        if (userClassMapper.selectOne(Wrappers.lambdaQuery(UserClass.class)
                .eq(UserClass::getClassId, classId).eq(UserClass::getUserId, studentId)) != null) {
            return new Response<>(ResponseStatus.FAILURE, "已加入该班级", null);
        }

        // 加入班级
        UserClass userClass = UserClass.builder()
                .id(IdWorker.get32UUID())
                .classId(classId)
                .className(clazz.getClassName())
                .userId(studentId)
                .UserType(UserRole.STUDENT)
                .build();
        userClass.insert();
        return new Response<>(ResponseStatus.SUCCESS, "", null);
    }

    @Override
    public Response<Null> deleteClass(String classId) {
        if (!removeById(classId)) {
            return new Response<>(ResponseStatus.FAILURE, "班级不存在", null);
        } else {
            userClassMapper.delete(Wrappers.lambdaQuery(UserClass.class).eq(UserClass::getClassId, classId));
//            examService.list(Wrappers.lambdaQuery(Exam.class).eq(Exam::getClassId, classId))
//                    .forEach(e -> examService.deleteExam(e.getId()));
            return new Response<>(ResponseStatus.SUCCESS, "", null);
        }
    }

    @Override
    public Response<Null> uploadClassAvatar(String classId, MultipartFile file) {
        Clazz clazz = getById(classId);
        if (clazz == null) {
            return new Response<>(ResponseStatus.FAILURE, "没有该班级", null);
        }
        String ext = Objects.requireNonNull(file.getContentType()).toLowerCase().replace("image/", "");
        String filename = classId + "." + ext;
        String filePath = "/ClassAvater/" + filename;
        try {
            restManager.writeFile(filePath, file.getInputStream(), null);
        } catch (IOException e) {
            return new Response<>(ResponseStatus.FAILURE, "文件有误", null);
        } catch (UpException e) {
            return new Response<>(ResponseStatus.FAILURE, "上传失败", null);
        }

        clazz.setAvater("https://cdn.aiexam.zhanxm.cn" + filePath);
        clazz.updateById();
        return new Response<>(ResponseStatus.SUCCESS, filename, null);
    }

    @Override
    public Response<Clazz> searchClassByCode(String code) {
        Clazz clazz = getOne(Wrappers.lambdaQuery(Clazz.class).eq(Clazz::getInviteCode, code));
        if (clazz == null) {
            return new Response<>(ResponseStatus.FAILURE, "邀请码输入错误或班级不存在", null);
        }
        return new Response<>(ResponseStatus.SUCCESS, "", clazz);
    }
}
