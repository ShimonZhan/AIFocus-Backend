package org.cmyk.aifocus;

import com.google.gson.Gson;
import org.cmyk.aifocus.amqp.message.NotificationMessage;
import org.cmyk.aifocus.constants.MessageStatus;
import org.cmyk.aifocus.dao.UserMapper;
import org.cmyk.aifocus.entity.*;
import org.cmyk.aifocus.service.MessageLogService;
import org.cmyk.aifocus.service.QuestionService;
import org.cmyk.aifocus.service.UserService;
import org.cmyk.aifocus.utils.ConvertUtil;
import org.cmyk.aifocus.utils.ExamResultQuestion;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(properties = {"notificationQueue.name=test"})
//@Transactional
public class MyTest {
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserService userService;
    @Resource
    private QuestionService questionService;
    @Resource
    private MessageLogService messageLogService;

    @Test
    @Ignore
    public void test1() {
        User user = new User();
        user.setId("717db41901a4639628d20926606d8b06");
        user.setName("zhanxm");
        user.updateById();
    }

    @Test
    @Ignore
    @Rollback
    public void test2() {
        boolean insert = Cheat.builder()
                .id(null)
                .studentId("213")
                .examId("12313")
                .monitorPhoto("12312312")
                .timeStamp(LocalDateTime.now())
                .build().insert();
        System.out.println(insert);

    }

    @Test
    @Ignore
    public void test3() {
        System.out.println(questionService.getQuestion("1", QuestionBlank.class));
        System.out.println(questionService.getQuestion("3", QuestionJudge.class));
        System.out.println(questionService.getQuestion("2", QuestionMultiple.class));
        System.out.println(questionService.getQuestion("4", QuestionSingle.class));
        System.out.println(questionService.getQuestion("5", QuestionSubjective.class));
        System.out.println(questionService.getQuestion("5", QuestionBlank.class));
    }

    @Test
    @Ignore
    public void test4() {
        System.out.println(ConvertUtil.localDateTimeChange("2020-05-04 23:00:00"));
    }

    @Test
    @Ignore
    public void genNoti() {

        NotificationMessage message = NotificationMessage.builder()
                .messageId("")
                .content("")
                .fromUserId("1234")
                .toUserId("5678")
                .notificationType(0)
                .build();
        System.out.println(new Gson().toJson(message));
    }

    @Test
    @Ignore
    public void testObject() {
        HashMap<Integer, Object> aa = new HashMap<>();
        aa.put(1, "2333");
        aa.put(2, 233);
        aa.put(3, new ExamResultQuestion("1", 3, "3", 333));
        aa.forEach((key, value1) -> {
            System.out.println(value1 instanceof String);
            System.out.println(value1 instanceof ExamResultQuestion);
            System.out.println(value1 instanceof Integer);
            if (value1 instanceof ExamResultQuestion) {
                ExamResultQuestion value = (ExamResultQuestion) value1;
                System.out.println(new Gson().toJson(value));
            }
        });
    }

    @Test
    @Ignore
    public void test33() {
        UserClass tt = UserClass.builder()
                .id("131332d93916f05f8431b96f94b6eaf6")
                .UserType(0)
                .userId("d2eaeba7c1a2a967fe749be6b998458b")
                .classId("3784cd5ef963537e3be222fb0c347332")
                .className("123")
                .build();
        System.out.println(tt.insert());
    }

    @Test
    @Ignore
    public void testMessageChangeStatus() {
        messageLogService.changeMessageStatus("mail-942890268@qq.com$2020-06-10T13:34:13.675", MessageStatus.SEND_SUCCESS, LocalDateTime.now());
    }
}
