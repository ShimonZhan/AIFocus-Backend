package org.cmyk.aifocus;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.cmyk.aifocus.entity.Exam;
import org.cmyk.aifocus.service.ExamService;
import org.cmyk.aifocus.service.MarkService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MarkTest {

    @Resource
    private MarkService markService;

    @Resource
    private ExamService examService;

    @Test
    @Ignore
    public void test1() {
        markService.markPaper("6886443ee43e780b5f97369159eeccbc", "25d26c4eb4c29d57c9948bf5af20dc3f");
        markService.markPaper("6886443ee43e780b5f97369159eeccbc", "3239b29e922c20ae2f65ccd10a83a7d3");
    }

    @Test
    public void test2() {
        List<Exam> exams = examService.list(Wrappers.lambdaQuery(Exam.class)
                .eq(Exam::getIsAutoMarkMessageSend, true)
                .lt(Exam::getFinishTime, LocalDateTime.now())
                .select(e -> false));
        exams.forEach(System.out::println);
    }

}
