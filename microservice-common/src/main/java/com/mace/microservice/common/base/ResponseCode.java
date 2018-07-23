package com.mace.microservice.common.base;

/**
 * description: 响应码
 * <br />
 * Created by mace on 15:02 2018/6/26.
 */
public class ResponseCode {

    public static final String SUCCESS = "success";
    public static final String EXCEPTION = "exception";

    public enum Base{

        SUCCESS("success","响应成功");

        private final String resultCode;
        private String message;

        Base(String resultCode, String message){
            this.resultCode = resultCode;
            this.message = message;
        }

        public String getResultCode() {
            return resultCode;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Base{");
            sb.append("resultCode='").append(resultCode).append('\'');
            sb.append(", message='").append(message).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

    public enum User{

        /*
         * 昵称重复
         */
        NAME_DUPLICATE("name.duplicate","昵称重复: {1} ,请换个昵称!");

        User(String resultCode, String message){
            this.resultCode = resultCode;
            this.message = message;
        }

        private final String resultCode;
        private final String message;

        public String getResultCode() {
            return resultCode;
        }

        public String getMessage() {
            return message;
        }
    }

    /**
     * description: 重置 Base.SUCCESS 的响应消息
     * <br /><br />
     * create by mace on 2018/6/26 16:02.
     * @param newMessage
     * @return: com.mace.common.RestPackResponseCode.Base
     */
    public static Base resetMessage(String newMessage){
        Base success = Base.SUCCESS;
        success.setMessage(newMessage);
        return success;
    }
}
