package com.example.towerdriver.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 53288
 * @description
 * @date 2021/5/19
 */
public class AreaBean implements Serializable {
    /**
     * id : 1
     * name : 北京
     * checked : 0
     * ChildrenList : [{"id":2,"name":"北京市","checked":0,"ChildrenList":[{"id":3,"name":"东城区","checked":0},{"id":4,"name":"西城区","checked":0},{"id":5,"name":"朝阳区","checked":0},{"id":6,"name":"丰台区","checked":0},{"id":7,"name":"石景山区","checked":0},{"id":8,"name":"海淀区","checked":0},{"id":9,"name":"门头沟区","checked":0},{"id":10,"name":"房山区","checked":0},{"id":11,"name":"通州区","checked":0},{"id":12,"name":"顺义区","checked":0},{"id":13,"name":"昌平区","checked":0},{"id":14,"name":"大兴区","checked":0},{"id":15,"name":"怀柔区","checked":0},{"id":16,"name":"平谷区","checked":0},{"id":17,"name":"密云区","checked":0},{"id":18,"name":"延庆区","checked":0}]}]
     */

    private Integer id;
    private String name;
    private Integer checked;
    private List<ChildrenListBeanX> ChildrenList;

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

    public Integer getChecked() {
        return checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    public List<ChildrenListBeanX> getChildrenList() {
        return ChildrenList;
    }

    public void setChildrenList(List<ChildrenListBeanX> ChildrenList) {
        this.ChildrenList = ChildrenList;
    }

    public static class ChildrenListBeanX implements Serializable {
        /**
         * id : 2
         * name : 北京市
         * checked : 0
         * ChildrenList : [{"id":3,"name":"东城区","checked":0},{"id":4,"name":"西城区","checked":0},{"id":5,"name":"朝阳区","checked":0},{"id":6,"name":"丰台区","checked":0},{"id":7,"name":"石景山区","checked":0},{"id":8,"name":"海淀区","checked":0},{"id":9,"name":"门头沟区","checked":0},{"id":10,"name":"房山区","checked":0},{"id":11,"name":"通州区","checked":0},{"id":12,"name":"顺义区","checked":0},{"id":13,"name":"昌平区","checked":0},{"id":14,"name":"大兴区","checked":0},{"id":15,"name":"怀柔区","checked":0},{"id":16,"name":"平谷区","checked":0},{"id":17,"name":"密云区","checked":0},{"id":18,"name":"延庆区","checked":0}]
         */

        private Integer id;
        private String name;
        private Integer checked;
        private List<ChildrenListBean> ChildrenList;

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

        public Integer getChecked() {
            return checked;
        }

        public void setChecked(Integer checked) {
            this.checked = checked;
        }

        public List<ChildrenListBean> getChildrenList() {
            return ChildrenList;
        }

        public void setChildrenList(List<ChildrenListBean> ChildrenList) {
            this.ChildrenList = ChildrenList;
        }

        public static class ChildrenListBean implements Serializable {
            /**
             * id : 3
             * name : 东城区
             * checked : 0
             */

            private Integer id;
            private String name;
            private Integer checked;

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

            public Integer getChecked() {
                return checked;
            }

            public void setChecked(Integer checked) {
                this.checked = checked;
            }
        }
    }
}
