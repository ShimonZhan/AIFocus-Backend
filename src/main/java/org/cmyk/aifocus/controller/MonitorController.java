package org.cmyk.aifocus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.ibatis.jdbc.Null;
import org.cmyk.aifocus.constants.ResponseStatus;
import org.cmyk.aifocus.dao.ExamMapper;
import org.cmyk.aifocus.entity.Cheat;
import org.cmyk.aifocus.service.ExamService;
import org.cmyk.aifocus.utils.ConvertUtil;
import org.cmyk.aifocus.utils.Response;
import org.cmyk.aifocus.utils.StudentStateInExam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Tag(name = "MonitorController 监控模块")
@RestController
public class MonitorController {
    @Resource
    private ExamMapper examMapper;
    @Resource
    private ExamService examService;

    @Operation(summary = "查看参加考试的学生（可以查询全部学生在线状态和提交试卷状态）")
    @GetMapping("/getStudentsInExam/{examId}")
    public Response<List<StudentStateInExam>> getStudentsInExam(@PathVariable String examId) {
        return new Response<>(ResponseStatus.SUCCESS, "", examMapper.selectStudentsInExam(examId));
    }

    @Operation(summary = "记录学生可疑行为")
    @PostMapping("/recordCheat/{studentId}/{examId}")
    public Response<Null> recordCheat(@PathVariable String studentId,
                                      @PathVariable String examId,
                                      @RequestParam String monitorPhoto,
                                      @RequestParam String timeStamp) {
        return examService.recordCheat(studentId, examId, monitorPhoto, ConvertUtil.localDateTimeChange(timeStamp));
    }

    @Operation(summary = "老师手动更改学生考试状态")
    @PostMapping("/changeStudentExamState/{studentId}/{examId}")
    public Response<Null> changeStudentExamState(@PathVariable String studentId,
                                                 @PathVariable String examId,
                                                 @RequestParam Integer state) {
        return examService.changeStudentExamState(studentId, examId, state);
    }

    @Operation(summary = "查看学生可疑情况（获取监控照片）")
    @GetMapping("/getStudentExamMonitorPhotos/{studentId}/{examId}")
    public Response<List<Cheat>> getStudentExamMonitorPhotos(@PathVariable String studentId,
                                                             @PathVariable String examId) {
        return examService.getStudentExamMonitorPhotos(studentId, examId);
    }
}
