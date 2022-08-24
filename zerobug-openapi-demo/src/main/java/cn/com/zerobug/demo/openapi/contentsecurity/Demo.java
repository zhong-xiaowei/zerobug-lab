package cn.com.zerobug.demo.openapi.contentsecurity;

import cn.hutool.dfa.FoundWord;
import cn.hutool.dfa.WordTree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/8/21
 */
public class Demo {


    public static void main(String[] args) throws IOException {
        URL resource = new Demo().getClass().getResource("/words.txt");
        File file = new File(resource.getFile());
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        WordTree wordTree = new WordTree();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            wordTree.addWord(line);
        }
        List<FoundWord> list = wordTree.matchAllWords("李*dd克&&强总理,毛sd泽d东");
        System.out.println(list);


    }
}
