package com.candan.smart_watch_test;

import javax.sql.DataSource;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContactServiceJPATest {
    @Autowired
    private DataSource dataSource;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testSaveUpdateDeleteContact() throws Exception{

    }
}
