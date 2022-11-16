package com.example.towerdriver.base_drivier_coin.model;

import java.util.List;

/**
 * @author 53288
 * @description 骑行币
 * @date 2021/6/19
 */
public class DriverCoinBean {


    /**
     * balance : 0.00
     * total_page : 1
     * withdraw : [{"price":"0.00","createtime":"2021-07-12 18:03"}]
     */

    private String balance;
    private Integer total_page;
    private List<WithdrawBean> withdraw;

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public Integer getTotal_page() {
        return total_page;
    }

    public void setTotal_page(Integer total_page) {
        this.total_page = total_page;
    }

    public List<WithdrawBean> getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(List<WithdrawBean> withdraw) {
        this.withdraw = withdraw;
    }

    public static class WithdrawBean {
        /**
         * price : 0.00
         * createtime : 2021-07-12 18:03
         */

        private String price;
        private String createtime;

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;

        }
    }
}
