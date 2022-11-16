package com.example.towerdriver.base_setmenu.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author 53288
 * @description 套餐列表
 * @date 2021/5/24
 */
public class SelectBean implements Serializable {


    private List<ClassifyBean> classify;
    private List<RentListBean> rent_list;

    public List<ClassifyBean> getClassify() {
        return classify;
    }

    public void setClassify(List<ClassifyBean> classify) {
        this.classify = classify;
    }

    public List<RentListBean> getRent_list() {
        return rent_list;
    }

    public void setRent_list(List<RentListBean> rent_list) {
        this.rent_list = rent_list;
    }

    public static class ClassifyBean implements Serializable {
        /**
         * id : 2
         * name : 分类2
         * weigh : 2
         * createtime : 1622450929
         */

        private Integer id;
        private String name;
        private Integer weigh;
        private Integer createtime;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getWeigh() {
            return weigh;
        }

        public void setWeigh(Integer weigh) {
            this.weigh = weigh;
        }

        public Integer getCreatetime() {
            return createtime;
        }

        public void setCreatetime(Integer createtime) {
            this.createtime = createtime;
        }
    }

    public static class RentListBean implements Serializable {
        /**
         * id : 1
         * company_id : 1
         * classify_id : 1
         * type : 1
         * name : 单电
         * image : http://pic.tdpower.net/member_image/f825cc2f90a3cdfc0471a21bd1d0b5e34jX3WiN79w.jpg
         * describe : 单电
         * weigh : 1
         * cash_price : 200.00
         * month_price : 200.00
         * rent_price : 10.00
         * overdue_price : 5.00
         */

        private Integer id;
        private Integer company_id;
        private Integer classify_id;
        private Integer type;
        private String name;
        private String image;
        private String describe;
        private Integer weigh;
        private String cash_price;
        private String month_price;
        private String rent_price;
        private String overdue_price;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getCompany_id() {
            return company_id;
        }

        public void setCompany_id(Integer company_id) {
            this.company_id = company_id;
        }

        public Integer getClassify_id() {
            return classify_id;
        }

        public void setClassify_id(Integer classify_id) {
            this.classify_id = classify_id;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public Integer getWeigh() {
            return weigh;
        }

        public void setWeigh(Integer weigh) {
            this.weigh = weigh;
        }

        public String getCash_price() {
            return cash_price;
        }

        public void setCash_price(String cash_price) {
            this.cash_price = cash_price;
        }

        public String getMonth_price() {
            return month_price;
        }

        public void setMonth_price(String month_price) {
            this.month_price = month_price;
        }

        public String getRent_price() {
            return rent_price;
        }

        public void setRent_price(String rent_price) {
            this.rent_price = rent_price;
        }

        public String getOverdue_price() {
            return overdue_price;
        }

        public void setOverdue_price(String overdue_price) {
            this.overdue_price = overdue_price;

        }
    }
}
