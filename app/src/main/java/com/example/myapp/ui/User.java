package com.example.myapp.ui;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser {
    private String nickname;
    private String sex;
    private String name;
    private String pic;
    private int age;

    
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname=nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setRName(String name) {
        this.name = name;
    }

    public String getRName() {
        return name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPic() {
        return pic;
    }
}
