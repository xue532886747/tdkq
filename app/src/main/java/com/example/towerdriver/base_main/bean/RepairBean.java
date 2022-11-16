package com.example.towerdriver.base_main.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 53288
 * @description 维修点
 * @date 2021/6/15
 */
public class RepairBean implements Serializable {


    private List<StationBean> station;

    public List<StationBean> getStation() {
        return station;
    }

    public void setStation(List<StationBean> station) {
        this.station = station;
    }

    public static class StationBean implements Serializable {
        /**
         * id : 74
         * name : 站点1
         * area_id : 2
         * address : 陕西省西安市未央区凤城一路海璟台北湾小区进门左拐就是
         * phone : 18535913153
         * lng : 108.939097
         * lat : 34.32014
         * createtime : 1623742398
         * area_name : 西安市
         * image :
         * images : ["http://pic.tdpower.net/uploads/20210607/73d670be5bc297679365b3691fdeca0e.png","http://pic.tdpower.net/uploads/20210607/86f430e8e33f4234f5f7be10d7f17bc2.png"]
         * search_address : 陕西省西安市未央区凤城一路
         */

        private Integer id;
        private String name;
        private Integer area_id;
        private String address;
        private String phone;
        private String lng;
        private String lat;
        private Integer createtime;
        private String area_name;
        private String image;
        private String search_address;
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

        public Integer getArea_id() {
            return area_id;
        }

        public void setArea_id(Integer area_id) {
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

        public String getSearch_address() {
            return search_address;
        }

        public void setSearch_address(String search_address) {
            this.search_address = search_address;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}
