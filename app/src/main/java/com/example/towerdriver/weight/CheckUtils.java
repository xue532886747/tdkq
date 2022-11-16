package com.example.towerdriver.weight;

import com.example.towerdriver.base_main.bean.PickUpBean;
import com.example.towerdriver.base_main.bean.RepairBean;

import java.util.List;

/**
 * @author 53288
 * @description 检查的逻辑
 * @date 2021/6/16
 */
public class CheckUtils {
    /**
     * 根据坐标遍历数据，并且查找到集合的位置
     *
     * @param latitude
     * @param longitude
     * @param list
     * @param <T>
     * @return
     */
    public static <T> int locationCoordinate(double latitude, double longitude, List<T> list) {
        if (list != null && list.size() >= 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) instanceof PickUpBean.WarehouseBean) {
                    PickUpBean.WarehouseBean warehouseBean = (PickUpBean.WarehouseBean) list.get(i);
                    double lat = Double.parseDouble(warehouseBean.getLat());
                    double lng = Double.parseDouble(warehouseBean.getLng());
                    if (latitude == lat && longitude == lng) {
                        return i;
                    }
                }
                //维修点
                if (list.get(i) instanceof RepairBean.StationBean) {
                    RepairBean.StationBean stationBean = (RepairBean.StationBean) list.get(i);
                    double lat = Double.parseDouble(stationBean.getLat());
                    double lng = Double.parseDouble(stationBean.getLng());
                    if (latitude == lat && longitude == lng) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }
}
