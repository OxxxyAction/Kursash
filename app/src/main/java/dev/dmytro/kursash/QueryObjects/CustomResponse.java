package dev.dmytro.kursash.QueryObjects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dmytro on 15.12.2015.
 */
public class CustomResponse {
    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("message send")
    @Expose
    private boolean sendMsg;
    @SerializedName("password reset")
    @Expose
    private boolean resetPass;

    public boolean isResetPass() {
        return resetPass;
    }

    public void setResetPass(boolean resetPass) {
        this.resetPass = resetPass;
    }

    public boolean isSendMsg() {
        return sendMsg;
    }

    public void setSendMsg(boolean sendMsg) {
        this.sendMsg = sendMsg;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
