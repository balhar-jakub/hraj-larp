package cz.hrajlarp.dao;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jbalhar on 7/9/2014.
 */

@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback=true)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:spring/hrajlarp/servlet-context.xml",
        "classpath:spring/hrajlarp/spring-security.xml"
})
@TestExecutionListeners({
        DbUnitTestExecutionListener.class
})
public class AbstractDaoTest {
}
