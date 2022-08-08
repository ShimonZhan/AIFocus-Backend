package org.cmyk.aifocus;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.cmyk.aifocus.entity.ExamMarkRecord;
import org.cmyk.aifocus.entity.User;
import org.cmyk.aifocus.service.ClassService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StreamTest {
    private static Stream<User> stream;
    @Resource
    private ClassService classService;

    @Test
    @Ignore
    public void test1() {
        stream.map((e) ->
                ExamMarkRecord.builder()
                        .id(null)
                        .examId("2333")
                        .studentId(e.getId())
                        .blankScore(0)
                        .judgeScore(0)
                        .singleScore(0)
                        .multipleScore(0)
                        .subjectiveScore(0)
                        .totalScore(0)
                        .build()
        ).collect(Collectors.toList()).forEach(System.out::println);
    }

    @Before
    public void setUp() {
        stream = classService.getStudentsFromClass(new Page<>(1, Long.MAX_VALUE), "64fba23bc0b8ec8e692d1fbb6711ca03").getContent().getRecords().stream();
    }
}
