package com.yangmao.model.admin.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 邮件实体模板
 * Created by liyongfeng on 16/9/1.
 */
public class EmailInstanceTemplateModel {

    /**
     * 模板id
     */
    private long templateId;

    /**
     * 邮件标题
     */
    private String title;

    /**
     * 邮件内容
     */
    private String content;

    /**
     * 选品列表
     */
    private List<EmailInstanceSectionModel> sectionModelList = new ArrayList<>();

    public long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(long templateId) {
        this.templateId = templateId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<EmailInstanceSectionModel> getSectionModelList() {
        return sectionModelList;
    }

    public void setSectionModelList(List<EmailInstanceSectionModel> sectionModelList) {
        this.sectionModelList = sectionModelList;
    }
}
