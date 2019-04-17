package com.ke.premain;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * @Description
 * @Author guangshunli
 * @Date 2019/4/16 7:59 PM
 **/
public class MyTransformer implements ClassFileTransformer {

    final static String prefix = "\nlong startTime = System.currentTimeMillis();\n";
    final static String postfix = "\nlong endTime = System.currentTimeMillis();\n";

    public MyTransformer() {
    }

    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

        className = className.replace("/", ".");

        if ("com.ke.agent.MyMain".equals(className)) {// 判断加载的class的包路径是不是需要监控的类
            CtClass ctclass = null;
            try {
                ctclass = ClassPool.getDefault().makeClass(new ByteArrayInputStream(classfileBuffer));// 使用全称,用于取得字节码类<使用javassist>
                CtMethod[] ctmethodArray = ctclass.getDeclaredMethods();// 得到这方法实例

                for (CtMethod ctmethod : ctmethodArray) {
                    String methodName = ctmethod.getName();

                    String outputStr = "\nSystem.out.println(\"this method " + methodName
                            + " cost:\" +(endTime - startTime) +\"ms.\");";
                    String newMethodName = methodName + "$old";// 新定义一个方法叫做比如sayHello$old
                    ctmethod.setName(newMethodName);// 将原来的方法名字修改

                    // 创建新的方法，复制原来的方法，名字为原来的名字
                    CtMethod newMethod = CtNewMethod.copy(ctmethod, methodName, ctclass, null);

                    // 构建新的方法体
                    StringBuilder bodyStr = new StringBuilder();
                    bodyStr.append("{");
                    bodyStr.append(prefix);
                    bodyStr.append(newMethodName + "($$);\n");// 调用原有代码，类似于method();($$)表示所有的参数
                    bodyStr.append(postfix);
                    bodyStr.append(outputStr);
                    bodyStr.append("}");

                    newMethod.setBody(bodyStr.toString());// 替换新方法
                    ctclass.addMethod(newMethod);// 增加新方法
                }

                return ctclass.toBytecode();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        return classfileBuffer;
    }
}