package org.cmyk.aifocus.dao;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExamMapperTest {
    @Resource
    private ExamMapper examMapper;

    @Test
    @Ignore
    public void selectExamsInExamStatus() {
        examMapper.selectExamsInExamStatus("25d26c4eb4c29d57c9948bf5af20dc3f");
    }
}