package com.example.towerdriver.repair.base_warehouse.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author 53288
 * @description
 * @date 2021/7/5
 */
public class MountBean implements Serializable {

    private List<VarietyListBean> variety_list;
    private List<MountingsListBean> mountings_list;

    public List<VarietyListBean> getVariety_list() {
        return variety_list;
    }

    public void setVariety_list(List<VarietyListBean> variety_list) {
        this.variety_list = variety_list;
    }

    public List<MountingsListBean> getMountings_list() {
        return mountings_list;
    }

    public void setMountings_list(List<MountingsListBean> mountings_list) {
        this.mountings_list = mountings_list;
    }

    public static class VarietyListBean implements Serializable {
        /**
         * id : 2
         * name : 工具
         */

        private Integer id;
        private String name;

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
    }

    public static class MountingsListBean implements Serializable {
        /**
         * id : 3
         * name : 后视镜
         * num : 0
         */

        private Integer id;
        private String name;
        private Integer num;

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

        public Integer getNum() {
            return num;
        }

        public void setNum(Integer num) {
            this.num = num;
        }
    }
}
