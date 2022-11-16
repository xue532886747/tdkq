package com.example.towerdriver.base_authentication.model.bean;

/**
 * @author 53288
 * @description
 * @date 2021/5/27
 */
public class PersonVerifyBean {


    /**
     * error_code : 0
     * error_msg : SUCCESS
     * log_id : 1510175201898
     * timestamp : 1622099352
     * cached : 0
     * result : {"score":95.29251099}
     */

    private Integer error_code;
    private String error_msg;
    private Long log_id;
    private Integer timestamp;
    private Integer cached;
    private ResultBean result;

    public Integer getError_code() {
        return error_code;
    }

    public void setError_code(Integer error_code) {
        this.error_code = error_code;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public Long getLog_id() {
        return log_id;
    }

    public void setLog_id(Long log_id) {
        this.log_id = log_id;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getCached() {
        return cached;
    }

    public void setCached(Integer cached) {
        this.cached = cached;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "PersonVerifyBean{" +
                "error_code=" + error_code +
                ", error_msg='" + error_msg + '\'' +
                ", log_id=" + log_id +
                ", timestamp=" + timestamp +
                ", cached=" + cached +
                ", result=" + result +
                '}';
    }

    public static class ResultBean  {
        /**
         * score : 95.29251099
         */

        private Double score;

        public Double getScore() {
            return score;
        }

        public void setScore(Double score) {
            this.score = score;
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "score=" + score +
                    '}';
        }
    }


}
