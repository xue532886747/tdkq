package com.example.towerdriver.base_order_list.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author 53288
 * @description 提车还车仓库列表
 * @date 2021/6/25
 */
public class EntrepotBean  implements Serializable {


    private List<WarehouseBean> warehouse;

    public List<WarehouseBean> getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(List<WarehouseBean> warehouse) {
        this.warehouse = warehouse;
    }

    public static class WarehouseBean implements Serializable {
        /**
         * id : 16
         * name : 提车点1
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

        @Override
        public String toString() {
            return "WarehouseBean{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "EntrepotBean{" +
                "warehouse=" + warehouse +
                '}';
    }
}
