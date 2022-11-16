package com.example.towerdriver.base_member_level.model.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @author 53288
 * @description 等级bean
 * @date 2021/6/8
 */
public class LevelBean implements Serializable {

    /**
     * member_info : {"member_img":"http://pic.tdpower.net/member/default.png","integral":0,"integral_image":"http://pic.tdpower.net/uploads/20210607/78f4e110dcaa0d9d33a20b18286a2588.png"}
     * operate_integral : [{"id":1,"title":"第一阶段","name":"铜牌会员","start":0,"end":999,"explain":"无","image":"/uploads/20210607/78f4e110dcaa0d9d33a20b18286a2588.png","scope":"0~999"},{"id":2,"title":"第二阶段","name":"银牌会员","start":1000,"end":2999,"explain":"无","image":"/uploads/20210607/a64ccd820864cb7b027f302b1b54e34a.png","scope":"1000~2999"},{"id":3,"title":"第三阶段","name":"金牌会员","start":3000,"end":4999,"explain":"无","image":"/uploads/20210607/86f430e8e33f4234f5f7be10d7f17bc2.png","scope":"3000~4999"},{"id":4,"title":"第四阶段","name":"钻石会员","start":5000,"end":10000,"explain":"无","image":"/uploads/20210607/73d670be5bc297679365b3691fdeca0e.png","scope":"5000~10000"}]
     */

    private MemberInfoBean member_info;
    private List<OperateIntegralBean> operate_integral;

    public MemberInfoBean getMember_info() {
        return member_info;
    }

    public void setMember_info(MemberInfoBean member_info) {
        this.member_info = member_info;
    }

    public List<OperateIntegralBean> getOperate_integral() {
        return operate_integral;
    }

    public void setOperate_integral(List<OperateIntegralBean> operate_integral) {
        this.operate_integral = operate_integral;
    }

    public static class MemberInfoBean implements Serializable {
        /**
         * member_img : http://pic.tdpower.net/member/default.png
         * integral : 0
         * integral_image : http://pic.tdpower.net/uploads/20210607/78f4e110dcaa0d9d33a20b18286a2588.png
         */

        private String member_img;
        private Integer integral;
        private String integral_image;

        public String getMember_img() {
            return member_img;
        }

        public void setMember_img(String member_img) {
            this.member_img = member_img;
        }

        public Integer getIntegral() {
            return integral;
        }

        public void setIntegral(Integer integral) {
            this.integral = integral;
        }

        public String getIntegral_image() {
            return integral_image;
        }

        public void setIntegral_image(String integral_image) {
            this.integral_image = integral_image;
        }
    }

    public static class OperateIntegralBean implements Serializable , MultiItemEntity {
        /**
         * id : 1
         * title : 第一阶段
         * name : 铜牌会员
         * start : 0
         * end : 999
         * explain : 无
         * image : /uploads/20210607/78f4e110dcaa0d9d33a20b18286a2588.png
         * scope : 0~999
         */

        private Integer id;
        private String title;
        private String name;
        private Integer start;
        private Integer end;
        private String explain;
        private String image;
        private String scope;
        private int type;           //

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getStart() {
            return start;
        }

        public void setStart(Integer start) {
            this.start = start;
        }

        public Integer getEnd() {
            return end;
        }

        public void setEnd(Integer end) {
            this.end = end;
        }

        public String getExplain() {
            return explain;
        }

        public void setExplain(String explain) {
            this.explain = explain;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        @Override
        public int getItemType() {
            return type;
        }
    }
}
