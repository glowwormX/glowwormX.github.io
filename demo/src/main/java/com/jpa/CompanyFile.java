package com.jpa;


import javax.persistence.*;

/**
 * @author 徐其伟
 * @Description:
 * @date 19-8-3 上午9:04
 */
@Entity
@Table(indexes = {@Index(columnList = "testIndex")})
public class CompanyFile {
    @Id
    @GeneratedValue
    private Integer id;

    private String path;
    private String testIndex;
    private String testSearch;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTestIndex() {
        return testIndex;
    }

    public void setTestIndex(String testIndex) {
        this.testIndex = testIndex;
    }

    public String getTestSearch() {
        return testSearch;
    }

    public void setTestSearch(String testSearch) {
        this.testSearch = testSearch;
    }
}
