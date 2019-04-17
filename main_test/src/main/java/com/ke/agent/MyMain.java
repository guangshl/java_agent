package com.ke.agent;

/**
 * @Description
 * @Author guangshunli
 * @Date 2019/4/16 5:11 PM
 **/
public class MyMain {

    public static void main(String[] args) {
        System.out.println("----Mymain.main() 执行 begin---");
        sayHello();
        sayHello2("hello2");

        System.out.println("----Mymain.main() 执行 end---");
    }

    public static void sayHello() {
        try {
            Thread.sleep(20);
            System.out.println("hello world!!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void sayHello2(String hello) {
        try {
            Thread.sleep(10);
            System.out.println(hello);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
