package com.example.towerdriver.base_invite.model;

import java.util.List;

/**
 * @author 53288
 * @description 邀请好友列表
 * @date 2021/6/18
 */
public class InviteBean {


    /**
     * total_page : 1
     * count : 1
     * list : [{"id":5,"name":"","phone":"18535913154","createtime":"2021-06-03","member_img":"http://pic.tdpower.net/member_image/f825cc2f90a3cdfc0471a21bd1d0b5e34jX3WiN79w.jpg"}]
     */

    private Integer total_page;
    private Integer count;
    private List<ListBean> list;

    public Integer getTotal_page() {
        return total_page;
    }

    public void setTotal_page(Integer total_page) {
        this.total_page = total_page;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 5
         * name :
         * phone : 18535913154
         * createtime : 2021-06-03
         * member_img : http://pic.tdpower.net/member_image/f825cc2f90a3cdfc0471a21bd1d0b5e34jX3WiN79w.jpg
         */

        private Integer id;
        private String name;
        private String phone;
        private String createtime;
        private String member_img;

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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getMember_img() {
            return member_img;
        }

        public void setMember_img(String member_img) {
            this.member_img = member_img;
        }
    }
}
