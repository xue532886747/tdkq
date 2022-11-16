package com.example.towerdriver.staff.base_approval.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author 53288
 * @description 我发起的
 * @date 2021/7/8
 */
public class SponsorListBean implements Serializable {


    /**
     * total_page : 1
     * list : [{"id":2,"staff_name":"赵晋聪","audit_name":"薛瑞丰","title":"请假审批","content":"周一到周三请假三天","status_name":"审批中","status":1,"createtime":"2021-07-07 11:17"},{"id":1,"staff_name":"赵晋聪","audit_name":"薛瑞丰","title":"请假审批","content":"周一到周三请假三天","status_name":"通过","status":2,"createtime":"2021-07-07 11:16"}]
     */

    private Integer total_page;
    private List<ListBean> list;

    public Integer getTotal_page() {
        return total_page;
    }

    public void setTotal_page(Integer total_page) {
        this.total_page = total_page;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable {
        /**
         * id : 2
         * staff_name : 赵晋聪
         * audit_name : 薛瑞丰
         * title : 请假审批
         * content : 周一到周三请假三天
         * status_name : 审批中
         * status : 1
         * createtime : 2021-07-07 11:17
         */

        private Integer id;
        private String staff_name;
        private String audit_name;
        private String title;
        private String content;
        private String status_name;
        private Integer status;
        private String createtime;

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
    }
}
