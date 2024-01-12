//package com.jpa;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
///**
// * @author 徐其伟
// * @Description:
// * @date 19-8-3 下午1:19
// */
//@Service
//public class DbSyncServiceImpl {
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//    @Autowired
//    private ICompanyFileDao companyFileDao;
//    @Transactional
//    public void test(Integer id, String path) {
////        CompanyFile companyFile = companyFileDao.findById(id).get();
//        CompanyFile companyFile = companyFileDao.findByTestIndex(id.toString());
//        logger.info(companyFile.getPath());
//        companyFile.setPath(companyFile.getPath() + path);
//        companyFileDao.save(companyFile);
//        CompanyFile companyFile1 = companyFileDao.findById(id).get();
//    }
//}
