package com.example.towerdriver.base_notice.model;

import java.util.List;

/**
 * @author 53288
 * @description
 * @date 2021/6/17
 */
public class NoticeBean {

    /**
     * total_page : 1
     * notice_list : [{"id":11,"title":"7","describe":"7","content":"<p>7<\/p>","createtime":"2021-06-10","url":"http://rentcar.me/api/article/notice_detail?id=11","member_id":4,"type":1,"status":0},{"id":10,"title":"6","describe":"6","content":"<p>6<\/p>","createtime":"2021-06-10","url":"http://rentcar.me/api/article/notice_detail?id=10","member_id":4,"type":1,"status":0},{"id":9,"title":"5","describe":"5","content":"<p>5<\/p>","createtime":"2021-06-10","url":"http://rentcar.me/api/article/notice_detail?id=9","member_id":4,"type":1,"status":0},{"id":8,"title":"4","describe":"4","content":"<p>4<\/p>","createtime":"2021-06-10","url":"http://rentcar.me/api/article/notice_detail?id=8","member_id":4,"type":1,"status":0},{"id":7,"title":"3","describe":"3","content":"<p>3<\/p>","createtime":"2021-06-10","url":"http://rentcar.me/api/article/notice_detail?id=7","member_id":4,"type":1,"status":0},{"id":6,"title":"3","describe":"3","content":"<p>3<\/p>","createtime":"2021-06-10","url":"http://rentcar.me/api/article/notice_detail?id=6","member_id":4,"type":1,"status":0}]
     */

    private Integer total_page;
    private List<NoticeListBean> notice_list;

    public Integer getTotal_page() {
        return total_page;
    }

    public void setTotal_page(Integer total_page) {
        this.total_page = total_page;
    }

    public List<NoticeListBean> getNotice_list() {
        return notice_list;
    }

    public void setNotice_list(List<NoticeListBean> notice_list) {
        this.notice_list = notice_list;
    }

    public static class NoticeListBean {
        /**
         * id : 11
         * title : 7
         * describe : 7
         * content : <p>7</p>
         * createtime : 2021-06-10
         * url : http://rentcar.me/api/article/notice_detail?id=11
         * member_id : 4
         * type : 1
         * status : 0
         */

        private Integer id;
        private String title;
        private String describe;
        private String content;
        private String createtime;
        private String url;
        private Integer member_id;
        private Integer type;
        private Integer status;
        private boolean isRead;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Integer getMember_id() {
            return member_id;
        }

        public void setMember_id(Integer member_id) {
            this.member_id = member_id;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public boolean isRead() {
            return isRead;
        }

        public void setRead(boolean read) {
            isRead = read;
        }
    }
}
