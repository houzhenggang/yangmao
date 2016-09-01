package com.yangmao.model.admin.dto;

/**
 * Created by liyongfeng on 16/9/1.
 */
public class EmailInstanceSectionModel {

    /**
     * 品类id
     */
    private Long sectionId;

    /**
     * 品类名称
     */
    private String section;

    /**
     * 品类数量
     */
    private Integer sectionAmount;

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public Integer getSectionAmount() {
        return sectionAmount;
    }

    public void setSectionAmount(Integer sectionAmount) {
        this.sectionAmount = sectionAmount;
    }
}
