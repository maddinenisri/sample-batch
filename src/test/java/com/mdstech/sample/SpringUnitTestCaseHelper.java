package com.mdstech.sample;

import com.mdstech.sample.config.ApplicationConfiguration;
import com.mdstech.sample.config.TestApplicationConfiguration;
import com.mdstech.sample.samplejob1.SampleJob1Config;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfiguration.class, TestApplicationConfiguration.class})
public class SpringUnitTestCaseHelper {
}
