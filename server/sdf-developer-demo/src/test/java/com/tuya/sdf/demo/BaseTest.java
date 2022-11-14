package com.tuya.sdf.demo;

import com.tuya.connector.api.config.Configuration;
import com.tuya.sdf.demo.ability.SpaceAbility;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author 哲也
 * @className BaseTest
 * @desc
 * @since 2021/11/19
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SdfDemoApplication.class)
public class BaseTest {

    @Autowired
    private Configuration configuration;

    @Autowired
    private SpaceAbility spaceAbility;

    @Test
    public void test() {
        String ak = "9uvegqh3b2onvefmcpwj";
        String sk = "0882aeec6b004a219376710ce9ebb1f0";
        String pc = "p1636367209615y43v5a";

        configuration.getApiDataSource().setAk(ak);
        configuration.getApiDataSource().setSk(sk);

        System.out.println(spaceAbility.querySpace(pc, "MICRO_APP_DEFAULT"));

    }
}
