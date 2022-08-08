package org.cmyk.aifocus.task;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.cmyk.aifocus.constants.ExamStatus;
import org.cmyk.aifocus.entity.Exam;
import org.cmyk.aifocus.service.ExamService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Slf4j
@Component
public class ExamStatusTasker {

    @Resource
    private ExamService examService;

    @Scheduled(initialDelay = 3000, fixedDelay = 10000)
    public void startOrFinishExam() {
        examService.update(Wrappers.lambdaUpdate(Exam.class)
                .eq(Exam::getStatus, ExamStatus.RELEASE)
                .le(Exam::getStartTime, LocalDateTime.now())
                .set(Exam::getStatus, ExamStatus.EXAM));

//        int count = examService.count(Wrappers.lambdaQuery(Exam.class)
//                .eq(Exam::getStatus, ExamStatus.RELEASE)
//                .le(Exam::getStartTime, LocalDateTime.now()));
//        log.debug("------count examStatus task :"+count);

        examService.update(Wrappers.lambdaUpdate(Exam.class)
                .eq(Exam::getStatus, ExamStatus.EXAM)
                .le(Exam::getFinishTime, LocalDateTime.now())
                .set(Exam::getStatus, ExamStatus.FINISH));
//        log.debug("------count examStatus task :"+examService.count(Wrappers.lambdaUpdate(Exam.class)
//                .eq(Exam::getStatus, ExamStatus.EXAM)
//                .le(Exam::getFinishTime, LocalDateTime.now())));

    }

}
