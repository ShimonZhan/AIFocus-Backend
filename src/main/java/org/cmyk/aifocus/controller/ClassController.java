package org.cmyk.aifocus.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mysql.cj.util.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.ibatis.jdbc.Null;
import org.cmyk.aifocus.constants.ResponseStatus;
import org.cmyk.aifocus.entity.Clazz;
import org.cmyk.aifocus.entity.User;
import org.cmyk.aifocus.service.ClassService;
import org.cmyk.aifocus.utils.Response;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.time.LocalDateTime;

import static org.cmyk.aifocus.utils.InviteCodeUtil.changeOrDefaultExpireTime;
import static org.cmyk.aifocus.utils.InviteCodeUtil.changeOrDefaultInviteCode;

@Tag(name = "ClassController 班级模块")
@RestController
public class ClassController {
    @Resource
    private ClassService classService;

    @Operation(summary = "查询一个班级")
    @GetMapping("/getClass/{classId}")
    public Response<Clazz> getClass(@Parameter(description = "id") @PathVariable String classId) {
        return classService.getClass(classId);
    }

    @Operation(summary = "查询用户班级")
    @GetMapping("/getClasses/{userId}/{current}/{size}")
    public Response<Page<Clazz>> getClasses(@Parameter(description = "用户id") @PathVariable String userId,
                                            @Parameter(description = "当前页") @PathVariable Long current,
                                            @Parameter(description = "每页大小") @PathVariable Long size) {
        Page<Clazz> page = new Page<>(current, size);
        return classService.getUserClasses(page, userId);
    }

    @Operation(summary = "按名字搜索班级")
    @GetMapping("/searchClass/{keyword}/{current}/{size}")
    public Response<Page<Clazz>> searchClass(@Parameter(description = "关键词") @PathVariable String keyword,
                                             @Parameter(description = "当前页") @PathVariable Long current,
                                             @Parameter(description = "每页大小") @PathVariable Long size) {
        Page<Clazz> page = new Page<>(current, size);
        return classService.searchClass(page, keyword);
    }

    @Operation(summary = "按邀请码搜索班级")
    @GetMapping("/searchClassByCode/{code}")
    public Response<Clazz> searchClassByCode(@PathVariable String code) {
        return classService.searchClassByCode(code);
    }

    @Operation(summary = "更新班级")
    @PutMapping("/updateClass/{id}")
    public Response<Null> updateClass(@Parameter(description = "班级id") @PathVariable String id,
                                      @Parameter(description = "班级名") @RequestParam String className,
                                      @Parameter(description = "课程名") @RequestParam String courseName,
                                      @Parameter(description = "是否搜索可见") @RequestParam Boolean isVisible) {
        Clazz clazz = Clazz.builder()
                .id(id)
                .courseName(StringUtils.isNullOrEmpty(courseName) ? null : courseName)
                .className(StringUtils.isNullOrEmpty(className) ? null : className)
                .isVisible(isVisible)
                .build();
        return classService.updateClass(clazz);
    }

    @Operation(summary = "更新邀请码")
    @PutMapping("/updateClassInviteCode/{ClassId}")
    public Response<Null> updateClassInviteCode(@PathVariable String ClassId,
                                                @RequestParam String inviteCode) {
        return classService.updateClassInviteCode(ClassId, inviteCode);
    }

    @Operation(summary = "添加班级")
    @PostMapping("/addClass")
    public Response<String> addClass(@Parameter(description = "老师id") @RequestParam String teacherId,
                                     @Parameter(description = "班级名") @RequestParam String className,
                                     @Parameter(description = "课程名") @RequestParam String courseName,
                                     @Parameter(description = "邀请码") @RequestParam String inviteCode,
                                     @Parameter(description = "是否搜索可见") @RequestParam Boolean isVisible) {


        String code = changeOrDefaultInviteCode(inviteCode);
        if (code == null) {
            return new Response<>(ResponseStatus.FAILURE, "邀请码已重复，请更换一个", null);
        }
        Clazz clazz = Clazz.builder()
                .className(className)
                .courseName(courseName)
                .inviteCode(code)
                .inviteCodeExpireTime(changeOrDefaultExpireTime(""))
                .avater("https://cdn.aiexam.zhanxm.cn/ClassAvater/default.png")
                .isVisible(isVisible)
                .createTime(LocalDateTime.now())
                .build();
        return classService.addClass(teacherId, clazz);
    }

    @Operation(summary = "用户离开班级")
    @DeleteMapping("/leaveClass/{role}/{userId}/{classId}")
    public Response<Null> leaveClass(@Parameter(description = "人员类型") @PathVariable Integer role,
                                     @Parameter(description = "用户id") @PathVariable String userId,
                                     @Parameter(description = "班级id") @PathVariable String classId) {
        return classService.leaveClass(role, userId, classId);
    }

    @Operation(summary = "删除班级")
    @DeleteMapping("/deleteClass/{classId}")
    public Response<Null> deleteClass(@PathVariable String classId) {
        return classService.deleteClass(classId);
    }

    @Operation(summary = "获取班级中的老师")
    @GetMapping("/getTeacherFromClass/{classId}")
    public Response<User> getTeacherFromClass(@Parameter(description = "班级id") @PathVariable String classId) {
        return classService.getTeacherFromClass(classId);
    }

    @Operation(summary = "获取班级中的学生")
    @GetMapping("/getStudentsFromClass/{classId}/{current}/{size}")
    Response<Page<User>> getStudentsFromClass(@Parameter(description = "班级id") @PathVariable String classId,
                                              @Parameter(description = "当前页") @PathVariable Long current,
                                              @Parameter(description = "每页大小") @PathVariable Long size) {
        Page<User> page = new Page<>(current, size);
        return classService.getStudentsFromClass(page, classId);
    }

    @Operation(summary = "学生加入班级")
    @PostMapping("/studentJoinClass")
    public Response<Null> studentJoinClass(@Parameter(description = "学生id") @RequestParam String studentId,
                                           @Parameter(description = "班级id") @RequestParam String classId) {
        return classService.studentJoinClass(studentId, classId);
    }

    @Operation(summary = "上传头像")
    @PutMapping("/uploadClassAvater/{classId}")
    public Response<Null> uploadClassAvater(@PathVariable String classId,
                                            @RequestParam MultipartFile file) {
        return classService.uploadClassAvatar(classId, file);
    }
}
