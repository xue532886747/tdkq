package com.example.towerdriver.base_authentication.model.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author 53288
 * @description 身份信息
 * @date 2021/5/25
 */
public class IdCardBean implements Serializable {

    /**
     * words_result : {"姓名":{"words":"张明明","location":{"top":495,"left":667,"width":54,"height":150}},"民族":{"words":"汉","location":{"top":731,"left":577,"width":43,"height":37}},"住址":{"words":"陕西省兴平市庄头镇三渠村大孟组231号","location":{"top":470,"left":343,"width":120,"height":461}},"公民身份号码":{"words":"610481199101253838","location":{"top":660,"left":163,"width":67,"height":618}},"出生":{"words":"19910125","location":{"top":483,"left":493,"width":58,"height":378}},"性别":{"words":"男","location":{"top":491,"left":586,"width":48,"height":41}}}
     * log_id : 1397198040384667648
     * words_result_num : 6
     * idcard_number_type : 1
     * image_status : normal
     */

    private WordsResultBean words_result;
    private Long log_id;
    private Integer words_result_num;
    private Integer idcard_number_type;                 // 1： 身份证正面所有字段全为空 0： 身份证证号不合法，此情况下不返回身份证证号 1： 身份证证号和性别、出生信息一致 2： 身份证证号和性别、出生信息都不一致 3： 身份证证号和出生信息不一致 4： 身份证证号和性别信息不一致
    private String image_status;                        //normal-识别正常, reversed_side-身份证正反面颠倒, non_idcard-上传的图片中不包含身份证 blurred-身份证模糊   //other_type_card-其他类型证照,over_exposure-身份证关键字段反光或过曝,over_dark-身份证欠曝（亮度过低）    unknown-未知状态
    private Integer direction;                          // 图像方向，当图像旋转时，返回该参数。1：未定义，0：正向，1：逆时针90度，2：逆时针180度，3：逆时针270度
    private String risk_type;                           //normal-正常身份,copy-复印件  temporary-临时身份证,screen-翻拍；unknown-其他未知情况
    private String edit_tool;                           //检测身份证被编辑过
    private String error_msg;                           //错误
    private Integer error_code;

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public Integer getError_code() {
        return error_code;
    }

    public void setError_code(Integer error_code) {
        this.error_code = error_code;
    }

    public WordsResultBean getWords_result() {
        return words_result;
    }

    public void setWords_result(WordsResultBean words_result) {
        this.words_result = words_result;
    }

    public Long getLog_id() {
        return log_id;
    }

    public void setLog_id(Long log_id) {
        this.log_id = log_id;
    }

    public Integer getWords_result_num() {
        return words_result_num;
    }

    public void setWords_result_num(Integer words_result_num) {
        this.words_result_num = words_result_num;
    }

    public Integer getIdcard_number_type() {
        return idcard_number_type;
    }

    public void setIdcard_number_type(Integer idcard_number_type) {
        this.idcard_number_type = idcard_number_type;
    }

    public String getImage_status() {
        return image_status;
    }

    public void setImage_status(String image_status) {
        this.image_status = image_status;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public String getRisk_type() {
        return risk_type;
    }

    public void setRisk_type(String risk_type) {
        this.risk_type = risk_type;
    }

    public String getEdit_tool() {
        return edit_tool;
    }

    public void setEdit_tool(String edit_tool) {
        this.edit_tool = edit_tool;
    }


    @Override
    public String toString() {
        return "IdCardBean{" +
                "words_result=" + words_result +
                ", log_id=" + log_id +
                ", words_result_num=" + words_result_num +
                ", idcard_number_type=" + idcard_number_type +
                ", image_status='" + image_status + '\'' +
                ", direction=" + direction +
                ", risk_type='" + risk_type + '\'' +
                ", edit_tool='" + edit_tool + '\'' +
                ", error_msg='" + error_msg + '\'' +
                ", error_code=" + error_code +
                '}';
    }

    public static class WordsResultBean implements Serializable {
        /**
         * 姓名 : {"words":"张明明","location":{"top":495,"left":667,"width":54,"height":150}}
         * 民族 : {"words":"汉","location":{"top":731,"left":577,"width":43,"height":37}}
         * 住址 : {"words":"陕西省兴平市庄头镇三渠村大孟组231号","location":{"top":470,"left":343,"width":120,"height":461}}
         * 公民身份号码 : {"words":"610481199101253838","location":{"top":660,"left":163,"width":67,"height":618}}
         * 出生 : {"words":"19910125","location":{"top":483,"left":493,"width":58,"height":378}}
         * 性别 : {"words":"男","location":{"top":491,"left":586,"width":48,"height":41}}
         */
        @SerializedName("姓名")
        private NameBean nameBean;
        @SerializedName("民族")
        private NationBean nationBean;
        @SerializedName("住址")
        private AddressBean addressBean;
        @SerializedName("公民身份号码")
        private CitizenshipNumberBean citizenshipNumberBean;
        @SerializedName("出生")
        private BrithBean brithBean;
        @SerializedName("性别")
        private SexBean sexBean;
        @SerializedName("失效日期")
        private ExpirationDateBean expirationdateBean;
        @SerializedName("签发机关")
        private SignOrganizationBean signOrganizationBean;
        @SerializedName("签发日期")
        private SignDateBean signDateBean;

        public NameBean getNameBean() {
            return nameBean;
        }

        public void setNameBean(NameBean nameBean) {
            this.nameBean = nameBean;
        }

        public NationBean getNationBean() {
            return nationBean;
        }

        public void setNationBean(NationBean nationBean) {
            this.nationBean = nationBean;
        }

        public AddressBean getAddressBean() {
            return addressBean;
        }

        public void setAddressBean(AddressBean addressBean) {
            this.addressBean = addressBean;
        }

        public CitizenshipNumberBean getCitizenshipNumberBean() {
            return citizenshipNumberBean;
        }

        public void setCitizenshipNumberBean(CitizenshipNumberBean citizenshipNumberBean) {
            this.citizenshipNumberBean = citizenshipNumberBean;
        }

        public BrithBean getBrithBean() {
            return brithBean;
        }

        public void setBrithBean(BrithBean brithBean) {
            this.brithBean = brithBean;
        }

        public SexBean getSexBean() {
            return sexBean;
        }

        public void setSexBean(SexBean sexBean) {
            this.sexBean = sexBean;
        }

        public ExpirationDateBean getExpirationdateBean() {
            return expirationdateBean;
        }

        public void setExpirationdateBean(ExpirationDateBean expirationdateBean) {
            this.expirationdateBean = expirationdateBean;
        }

        public SignOrganizationBean getSignOrganizationBean() {
            return signOrganizationBean;
        }

        public void setSignOrganizationBean(SignOrganizationBean signOrganizationBean) {
            this.signOrganizationBean = signOrganizationBean;
        }

        public SignDateBean getSignDateBean() {
            return signDateBean;
        }

        public void setSignDateBean(SignDateBean signDateBean) {
            this.signDateBean = signDateBean;
        }

        @Override
        public String toString() {
            return "WordsResultBean{" +
                    "nameBean=" + nameBean +
                    ", nationBean=" + nationBean +
                    ", addressBean=" + addressBean +
                    ", citizenshipNumberBean=" + citizenshipNumberBean +
                    ", brithBean=" + brithBean +
                    ", sexBean=" + sexBean +
                    ", expirationdateBean=" + expirationdateBean +
                    ", signOrganizationBean=" + signOrganizationBean +
                    ", signDateBean=" + signDateBean +
                    '}';
        }

        public static class NameBean implements Serializable {
            /**
             * words : 张明明
             * location : {"top":495,"left":667,"width":54,"height":150}
             */

            private String words;
            private LocationBean location;

            public String getWords() {
                return words;
            }

            public void setWords(String words) {
                this.words = words;
            }

            public LocationBean getLocation() {
                return location;
            }

            public void setLocation(LocationBean location) {
                this.location = location;
            }

            public static class LocationBean implements Serializable {
                /**
                 * top : 495
                 * left : 667
                 * width : 54
                 * height : 150
                 */

                private Integer top;
                private Integer left;
                private Integer width;
                private Integer height;

                public Integer getTop() {
                    return top;
                }

                public void setTop(Integer top) {
                    this.top = top;
                }

                public Integer getLeft() {
                    return left;
                }

                public void setLeft(Integer left) {
                    this.left = left;
                }

                public Integer getWidth() {
                    return width;
                }

                public void setWidth(Integer width) {
                    this.width = width;
                }

                public Integer getHeight() {
                    return height;
                }

                public void setHeight(Integer height) {
                    this.height = height;
                }

                @Override
                public String toString() {
                    return "LocationBean{" +
                            "top=" + top +
                            ", left=" + left +
                            ", width=" + width +
                            ", height=" + height +
                            '}';
                }
            }

            @Override
            public String toString() {
                return "NameBean{" +
                        "words='" + words + '\'' +
                        ", location=" + location +
                        '}';
            }
        }

        public static class NationBean implements Serializable {
            /**
             * words : 汉
             * location : {"top":731,"left":577,"width":43,"height":37}
             */

            private String words;
            private LocationBeanX location;

            public String getWords() {
                return words;
            }

            public void setWords(String words) {
                this.words = words;
            }

            public LocationBeanX getLocation() {
                return location;
            }

            public void setLocation(LocationBeanX location) {
                this.location = location;
            }

            public static class LocationBeanX implements Serializable {
                /**
                 * top : 731
                 * left : 577
                 * width : 43
                 * height : 37
                 */

                private Integer top;
                private Integer left;
                private Integer width;
                private Integer height;

                public Integer getTop() {
                    return top;
                }

                public void setTop(Integer top) {
                    this.top = top;
                }

                public Integer getLeft() {
                    return left;
                }

                public void setLeft(Integer left) {
                    this.left = left;
                }

                public Integer getWidth() {
                    return width;
                }

                public void setWidth(Integer width) {
                    this.width = width;
                }

                public Integer getHeight() {
                    return height;
                }

                public void setHeight(Integer height) {
                    this.height = height;
                }
            }
        }

        public static class AddressBean implements Serializable {
            /**
             * words : 陕西省兴平市庄头镇三渠村大孟组231号
             * location : {"top":470,"left":343,"width":120,"height":461}
             */

            private String words;
            private LocationBeanXX location;

            public String getWords() {
                return words;
            }

            public void setWords(String words) {
                this.words = words;
            }

            public LocationBeanXX getLocation() {
                return location;
            }

            public void setLocation(LocationBeanXX location) {
                this.location = location;
            }

            public static class LocationBeanXX implements Serializable {
                /**
                 * top : 470
                 * left : 343
                 * width : 120
                 * height : 461
                 */

                private Integer top;
                private Integer left;
                private Integer width;
                private Integer height;

                public Integer getTop() {
                    return top;
                }

                public void setTop(Integer top) {
                    this.top = top;
                }

                public Integer getLeft() {
                    return left;
                }

                public void setLeft(Integer left) {
                    this.left = left;
                }

                public Integer getWidth() {
                    return width;
                }

                public void setWidth(Integer width) {
                    this.width = width;
                }

                public Integer getHeight() {
                    return height;
                }

                public void setHeight(Integer height) {
                    this.height = height;
                }
            }
        }

        public static class CitizenshipNumberBean implements Serializable {
            /**
             * words : 610481199101253838
             * location : {"top":660,"left":163,"width":67,"height":618}
             */

            private String words;
            private LocationBeanXXX location;

            public String getWords() {
                return words;
            }

            public void setWords(String words) {
                this.words = words;
            }

            public LocationBeanXXX getLocation() {
                return location;
            }

            public void setLocation(LocationBeanXXX location) {
                this.location = location;
            }

            public static class LocationBeanXXX implements Serializable {
                /**
                 * top : 660
                 * left : 163
                 * width : 67
                 * height : 618
                 */

                private Integer top;
                private Integer left;
                private Integer width;
                private Integer height;

                public Integer getTop() {
                    return top;
                }

                public void setTop(Integer top) {
                    this.top = top;
                }

                public Integer getLeft() {
                    return left;
                }

                public void setLeft(Integer left) {
                    this.left = left;
                }

                public Integer getWidth() {
                    return width;
                }

                public void setWidth(Integer width) {
                    this.width = width;
                }

                public Integer getHeight() {
                    return height;
                }

                public void setHeight(Integer height) {
                    this.height = height;
                }
            }
        }

        public static class BrithBean implements Serializable {
            /**
             * words : 19910125
             * location : {"top":483,"left":493,"width":58,"height":378}
             */

            private String words;
            private LocationBeanXXXX location;

            public String getWords() {
                return words;
            }

            public void setWords(String words) {
                this.words = words;
            }

            public LocationBeanXXXX getLocation() {
                return location;
            }

            public void setLocation(LocationBeanXXXX location) {
                this.location = location;
            }

            public static class LocationBeanXXXX implements Serializable {
                /**
                 * top : 483
                 * left : 493
                 * width : 58
                 * height : 378
                 */

                private Integer top;
                private Integer left;
                private Integer width;
                private Integer height;

                public Integer getTop() {
                    return top;
                }

                public void setTop(Integer top) {
                    this.top = top;
                }

                public Integer getLeft() {
                    return left;
                }

                public void setLeft(Integer left) {
                    this.left = left;
                }

                public Integer getWidth() {
                    return width;
                }

                public void setWidth(Integer width) {
                    this.width = width;
                }

                public Integer getHeight() {
                    return height;
                }

                public void setHeight(Integer height) {
                    this.height = height;
                }
            }
        }

        public static class SexBean implements Serializable {
            /**
             * words : 男
             * location : {"top":491,"left":586,"width":48,"height":41}
             */

            private String words;
            private LocationBeanXXXXX location;

            public String getWords() {
                return words;
            }

            public void setWords(String words) {
                this.words = words;
            }

            public LocationBeanXXXXX getLocation() {
                return location;
            }

            public void setLocation(LocationBeanXXXXX location) {
                this.location = location;
            }

            public static class LocationBeanXXXXX implements Serializable {
                /**
                 * top : 491
                 * left : 586
                 * width : 48
                 * height : 41
                 */

                private Integer top;
                private Integer left;
                private Integer width;
                private Integer height;

                public Integer getTop() {
                    return top;
                }

                public void setTop(Integer top) {
                    this.top = top;
                }

                public Integer getLeft() {
                    return left;
                }

                public void setLeft(Integer left) {
                    this.left = left;
                }

                public Integer getWidth() {
                    return width;
                }

                public void setWidth(Integer width) {
                    this.width = width;
                }

                public Integer getHeight() {
                    return height;
                }

                public void setHeight(Integer height) {
                    this.height = height;
                }
            }
        }

        public static class ExpirationDateBean implements Serializable {
            /**
             * words : 19910125
             * location : {"top":483,"left":493,"width":58,"height":378}
             */

            private String words;
            private LocationBeanXXXX location;

            public String getWords() {
                return words;
            }

            public void setWords(String words) {
                this.words = words;
            }

            public LocationBeanXXXX getLocation() {
                return location;
            }

            public void setLocation(LocationBeanXXXX location) {
                this.location = location;
            }

            public static class LocationBeanXXXX implements Serializable {
                /**
                 * top : 483
                 * left : 493
                 * width : 58
                 * height : 378
                 */

                private Integer top;
                private Integer left;
                private Integer width;
                private Integer height;

                public Integer getTop() {
                    return top;
                }

                public void setTop(Integer top) {
                    this.top = top;
                }

                public Integer getLeft() {
                    return left;
                }

                public void setLeft(Integer left) {
                    this.left = left;
                }

                public Integer getWidth() {
                    return width;
                }

                public void setWidth(Integer width) {
                    this.width = width;
                }

                public Integer getHeight() {
                    return height;
                }

                public void setHeight(Integer height) {
                    this.height = height;
                }
            }
        }

        public static class SignOrganizationBean implements Serializable {
            /**
             * words : 19910125
             * location : {"top":483,"left":493,"width":58,"height":378}
             */

            private String words;
            private LocationBeanXXXX location;

            public String getWords() {
                return words;
            }

            public void setWords(String words) {
                this.words = words;
            }

            public LocationBeanXXXX getLocation() {
                return location;
            }

            public void setLocation(LocationBeanXXXX location) {
                this.location = location;
            }

            public static class LocationBeanXXXX implements Serializable {
                /**
                 * top : 483
                 * left : 493
                 * width : 58
                 * height : 378
                 */

                private Integer top;
                private Integer left;
                private Integer width;
                private Integer height;

                public Integer getTop() {
                    return top;
                }

                public void setTop(Integer top) {
                    this.top = top;
                }

                public Integer getLeft() {
                    return left;
                }

                public void setLeft(Integer left) {
                    this.left = left;
                }

                public Integer getWidth() {
                    return width;
                }

                public void setWidth(Integer width) {
                    this.width = width;
                }

                public Integer getHeight() {
                    return height;
                }

                public void setHeight(Integer height) {
                    this.height = height;
                }
            }
        }

        public static class SignDateBean implements Serializable {
            /**
             * words : 19910125
             * location : {"top":483,"left":493,"width":58,"height":378}
             */

            private String words;
            private LocationBeanXXXX location;

            public String getWords() {
                return words;
            }

            public void setWords(String words) {
                this.words = words;
            }

            public LocationBeanXXXX getLocation() {
                return location;
            }

            public void setLocation(LocationBeanXXXX location) {
                this.location = location;
            }

            public static class LocationBeanXXXX implements Serializable {
                /**
                 * top : 483
                 * left : 493
                 * width : 58
                 * height : 378
                 */

                private Integer top;
                private Integer left;
                private Integer width;
                private Integer height;

                public Integer getTop() {
                    return top;
                }

                public void setTop(Integer top) {
                    this.top = top;
                }

                public Integer getLeft() {
                    return left;
                }

                public void setLeft(Integer left) {
                    this.left = left;
                }

                public Integer getWidth() {
                    return width;
                }

                public void setWidth(Integer width) {
                    this.width = width;
                }

                public Integer getHeight() {
                    return height;
                }

                public void setHeight(Integer height) {
                    this.height = height;
                }
            }
        }
    }
}
