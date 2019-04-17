package com.ke.premain;

import java.lang.instrument.Instrumentation;

/**
 * @Description
 * @Author guangshunli
 * @Date 2019/4/16 6:19 PM
 **/
public class MyPremain {

    public static void premain(String ops) {
        System.out.println( "-----MyPremain.premain() 执行-----");
    }

    public static void premain(String agentOps, Instrumentation inst) {
        System.out.println("=========premain方法执行========");
        System.out.println(agentOps);
        // 添加Transformer
        inst.addTransformer(new MyTransformer());
    }

    public static void main(String[] args) {
        System.out.println( "-----MyPremain.main() 执行-----");
    }


}
