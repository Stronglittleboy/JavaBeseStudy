package com.hlw.java_base.designpattern.singleton;

public class EnumSingleton {
    private EnumSingleton(){
    }
    public static enum SingletonEnum {
        SINGLETON;
        private EnumSingleton instance = null;
        private SingletonEnum(){
            instance = new EnumSingleton();
        }
        public EnumSingleton getInstance(){
            return instance;
        }
    }
}
