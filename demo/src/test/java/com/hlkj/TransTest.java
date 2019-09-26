package com.hlkj;

import com.jpa.CompanyFile;
import com.jpa.DbSyncServiceImpl;
import com.jpa.ICompanyFileDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransTest {
    @Autowired
    DbSyncServiceImpl dbSyncService;
    @Autowired
    ICompanyFileDao companyFileDao;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void add() {
        CompanyFile entity = new CompanyFile();
        entity.setPath("11");
        companyFileDao.save(entity);
    }
    @Test
    public void testTran1() {
        dbSyncService.test(1, "1");
    }
    @Test
    public void testTran2() {
        dbSyncService.test(1, "2");
    }
    @Test
    public void testTran3() {
        CountDownLatch c = new CountDownLatch(1);
        for (int i = 0; i < 10; i++) {
            Thread g = new Thread(() -> {
                try {
                    logger.info("wait");
                    c.await();
                    logger.info("go");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                dbSyncService.test(1, "g");
            });
            g.start();
        }
        try {
            Thread.sleep(1000);
            c.countDown();
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
