package com.example.towerdriver.base_main.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 53288
 * @description 提车点
 * @date 2021/6/15
 */
public class PickUpBean implements Serializable {


    private List<WarehouseBean> warehouse;

    public List<WarehouseBean> getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(List<WarehouseBean> warehouse) {
        this.warehouse = warehouse;
    }

    public static class WarehouseBean implements Serializable {
        /**
         * id : 10
         * name : 邮电北巷4号
         * area_id : null
         * address : 西安市碑林区红缨路150号
         * phone : 029-68775555
         * lng : 108.941849
         * lat : 34.252882
         * num : 0
         * createtime : 1607926851
         * area_name : 西安市
         * image : http://pic.tdpower.netqq
         * images : ["http://pic.tdpower.netq","http://pic.tdpower.netq"]
         */

        private Integer id;
        private String name;
        private String area_id;
        private String address;
        private String phone;
        private String lng;
        private String lat;
        private Integer num;
        private Integer createtime;
        private String area_name;
        private String image;
        private List<String> images;

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

        public String getArea_id() {
            return area_id;
        }

        public void setArea_id(String area_id) {
            this.area_id = area_id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public Integer getNum() {
            return num;
        }

        public void setNum(Integer num) {
            this.num = num;
        }

        public Integer getCreatetime() {
            return createtime;
        }

        public void setCreatetime(Integer createtime) {
            this.createtime = createtime;
        }

        public String getArea_name() {
            return area_name;
        }

        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        @Override
        public String toString() {
            return "WarehouseBean{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", area_id='" + area_id + '\'' +
                    ", address='" + address + '\'' +
                    ", phone='" + phone + '\'' +
                    ", lng='" + lng + '\'' +
                    ", lat='" + lat + '\'' +
                    ", num=" + num +
                    ", createtime=" + createtime +
                    ", area_name='" + area_name + '\'' +
                    ", image='" + image + '\'' +
                    ", images=" + images +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "PickUpBean{" +
                "warehouse=" + warehouse +
                '}';
    }
}
