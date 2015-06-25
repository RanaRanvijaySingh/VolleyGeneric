package com.volleydemo;

public class UserModel {
    private String name;
    private String email;
    private Contact phone;
    public class Contact{
        private String home;
        private String mobile;

        public String getHome() {
            return home;
        }

        public void setHome(String home) {
            this.home = home;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Contact getPhone() {
        return phone;
    }

    public void setPhone(Contact phone) {
        this.phone = phone;
    }
}
