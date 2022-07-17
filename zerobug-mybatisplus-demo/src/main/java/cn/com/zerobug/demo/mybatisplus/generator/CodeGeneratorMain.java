package cn.com.zerobug.demo.mybatisplus.generator;


/**
 * @author zhongxiaowei
 * @date 2022/3/12
 */
public class CodeGeneratorMain {

    public static void main(String[] args) {
        String url = "jdbc:mysql://www.zyc.cool:22002/nuclear_boot?useUnicode=true&characterEncoding=utf8&useSSL=false";
        String username = "";
        String password = "";
        String packageName = "";
        CodeGenerator.execute(url, username, password, packageName);
    }

}
