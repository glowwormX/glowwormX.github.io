//package com.jpa;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
//import org.springframework.data.jpa.repository.Lock;
//
//import javax.persistence.LockModeType;
//import java.util.Optional;
//
///**
// * @author 徐其伟
// * @Description:
// * @date 19-8-3 上午10:08
// */
//public interface ICompanyFileDao extends JpaRepository<CompanyFile, Integer>, JpaSpecificationExecutor<CompanyFile> {
//    @Lock(LockModeType.PESSIMISTIC_WRITE)
//    Optional<CompanyFile> findById(Integer id);
//
//    @Lock(LockModeType.PESSIMISTIC_WRITE)
//    CompanyFile findByTestIndex(String path);
//
//    @Lock(LockModeType.PESSIMISTIC_WRITE)
//    CompanyFile findByTestSearch(String test);
//}