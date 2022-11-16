package com.example.towerdriver.staff.base_approval.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @author 53288
 * @description 审批人列表
 * @date 2021/7/8
 */
public class AudioListBean implements Serializable {

    private List<DepartmentListBean> department_list;

    public List<DepartmentListBean> getDepartment_list() {
        return department_list;
    }

    public void setDepartment_list(List<DepartmentListBean> department_list) {
        this.department_list = department_list;
    }


    public static class DepartmentListBean implements Serializable {
        /**
         * id : 1
         * name : 财务部
         * staff_list : [{"id":1,"name":"赵晋聪"},{"id":2,"name":"薛瑞丰"}]
         */

        private Integer id;
        private String name;
        private List<StaffListBean> staff_list;

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

        public List<StaffListBean> getStaff_list() {
            return staff_list;
        }

        public void setStaff_list(List<StaffListBean> staff_list) {
            this.staff_list = staff_list;
        }

        public static class StaffListBean implements Serializable {
            /**
             * id : 1
             * name : 赵晋聪
             */

            private Integer id;
            private String name;
            private String image;

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

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }
        }
    }
}
