package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgatePass {
    String email;
    String newPassword;
    String currentPassword;

    public ForgatePass(String email, String newPassword, String currentPassword) {
        this.email = email;
        this.newPassword = newPassword;
        this.currentPassword = currentPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public static class ChangePass {

        @SerializedName("message")
        @Expose
        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }
}
