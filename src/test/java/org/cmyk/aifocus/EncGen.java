package org.cmyk.aifocus;

import org.jasypt.encryption.StringEncryptor;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"notificationQueue.name=test"})
public class EncGen {
    @Resource
    private StringEncryptor stringEncryptor;

    @Test
    @Ignore
    public void gen() {
        System.out.println(stringEncryptor.encrypt("xxxxxxxxxx"));
    }
}
