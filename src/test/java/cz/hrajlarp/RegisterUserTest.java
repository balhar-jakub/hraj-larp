package cz.hrajlarp;

import cz.hrajlarp.service.MailService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mail.MailSender;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Acceptance tests for logic associated with registering new user.
 */
public class RegisterUserTest extends AcceptanceTest{
    @Autowired private MailService mails;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

        MailSender mockSender = Mockito.mock(MailSender.class);
        ReflectionTestUtils.setField(mails, "mailSender", mockSender);
    }

    @Test
    public void registerNewValidUser() throws Exception {
        mockMvc.perform(post("/user/register").param("name", "Jakub").param("lastName", "Balhar").param("userName", "Balda")
                .param("password", "heslo").param("passwordAgain", "heslo").param("email", "balhar.jakub@gmail.com")
                .param("phone", "723354354").param("genderForm", "M"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/login"));
    }
}
