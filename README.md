# java_agent
java 探针使用

main_test 采用 mvn插件 打可执行jar包 main_test.jar
pre_main_test 用 idea buid 打包 pre_main_test.jar， 打包时注意指定 META-INF/MANIFEST.MF 文件位置 到模块 pre_main_test 根目录 （可能是idea的bug）


运行命令：
java -javaagent:pre_main_test.jar -jar main_test.jar

注意 需要将 javassist-3.12.1.GA.jar 与上面的jar包放在同一目录下 （或者将该jar包与pre_main_test.jar 包打成一个jar（操作失败）），否则运行时找不到引用的 javassist中的类

