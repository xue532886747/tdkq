package com.example.towerdriver.base_main.bean;

import java.util.List;

/**
 * @author 53288
 * @description
 * @date 2021/6/6
 */
public class AdvertiBean {

    private List<AdvBean> adv;

    public List<AdvBean> getAdv() {
        return adv;
    }

    public void setAdv(List<AdvBean> adv) {
        this.adv = adv;
    }

    public static class AdvBean {
        /**
         * id : 1
         * title : 转介绍有好礼啦
         * image : http://pic.tdpower.net/uploads/20210603/b6a37b03210f0c9cfaaa54beee0b0653.jpg
         * url : http://rentcar.me/api/article/adv_article?id=1
         */

        private Integer id;
        private String title;
        private String image;
        private String url;

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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "AdvBean{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", image='" + image + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "AdvertiBean{" +
                "adv=" + adv +
                '}';
    }
}
