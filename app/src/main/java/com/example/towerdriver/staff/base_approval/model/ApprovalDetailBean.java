package com.example.towerdriver.staff.base_approval.model;

import java.util.List;

/**
 * @author 53288
 * @description 审批详情
 * @date 2021/7/10
 */
public class ApprovalDetailBean {

    /**
     * id : 2
     * staff_name : 赵晋聪
     * audit_name : 薛瑞丰
     * title : 请假审批
     * content : 周一到周三请假三天
     * status_name : 审批中
     * status : 1
     * createtime : 2021-07-07 11:17
     * images : ["http://pic.tdpower.net/staff_image/759086a93756bed56460801270cadb9eYa3MvBtifY.jpg","http://pic.tdpower.net/staff_image/759086a93756bed56460801270cadb9eYa3MvBtifY.jpg"]
     * files : ["http://pic.tdpower.net/staff_image/aed43530cf3c19418174d408e885653eoefUfAxJG0.xls","http://pic.tdpower.net/staff_image/aed43530cf3c19418174d408e885653eoefUfAxJG0.xls"]
     */

    private Integer id;
    private String staff_name;
    private String audit_name;
    private String title;
    private String content;
    private String status_name;
    private Integer status;
    private String createtime;
    private List<String> images;
    private List<String> files;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStaff_name() {
        return staff_name;
    }

    public void setStaff_name(String staff_name) {
        this.staff_name = staff_name;
    }

    public String getAudit_name() {
        return audit_name;
    }

    public void setAudit_name(String audit_name) {
        this.audit_name = audit_name;
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

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }
}
