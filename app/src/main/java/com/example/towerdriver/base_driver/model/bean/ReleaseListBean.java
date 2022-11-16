package com.example.towerdriver.base_driver.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 53288
 * @description 发布列表
 * @date 2021/6/4
 */
public class ReleaseListBean implements Serializable {

    /**
     * article : [{"id":16,"member_id":3,"title":"1111","content":"1","name":"1","phone":"1","image":["http://pic.tdpower.net/member_image/669b1ff36f6da1fa34c90f6ba559c532bOEBliekfu.png","http://pic.tdpower.net/member_image/5bea2bfa895b6a8cfa56ff9067af9a159PHml0jOR7.jpg"],"createtime":"2021-06-03","status":"审核中","member_image":"http://pic.tdpower.net/member/default.png"}]
     * total_page : 1
     */

    private Integer total_page;
    private List<ArticleBean> article;

    public Integer getTotal_page() {
        return total_page;
    }

    public void setTotal_page(Integer total_page) {
        this.total_page = total_page;
    }

    public List<ArticleBean> getArticle() {
        return article;
    }

    public void setArticle(List<ArticleBean> article) {
        this.article = article;
    }

    public static class ArticleBean implements Serializable {
        /**
         * id : 16
         * member_id : 3
         * title : 1111
         * content : 1
         * name : 1
         * phone : 1
         * image : ["http://pic.tdpower.net/member_image/669b1ff36f6da1fa34c90f6ba559c532bOEBliekfu.png","http://pic.tdpower.net/member_image/5bea2bfa895b6a8cfa56ff9067af9a159PHml0jOR7.jpg"]
         * createtime : 2021-06-03
         * status : 审核中
         * member_image : http://pic.tdpower.net/member/default.png
         */

        private Integer id;
        private Integer member_id;
        private String title;
        private String content;
        private String name;
        private String phone;
        private String createtime;
        private String status;
        private String member_image;
        private List<String> image;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getMember_id() {
            return member_id;
        }

        public void setMember_id(Integer member_id) {
            this.member_id = member_id;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMember_image() {
            return member_image;
        }

        public void setMember_image(String member_image) {
            this.member_image = member_image;
        }

        public List<String> getImage() {
            return image;
        }

        public void setImage(List<String> image) {
            this.image = image;
        }

    }
}
