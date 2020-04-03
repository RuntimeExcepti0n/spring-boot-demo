package fileAndIo;

import org.junit.Test;

import java.io.*;

public class fileCopy {

    @Test
    public void test(){
        FileWriter fw = null;
        FileReader fr = null;

        try {
            //获取指定文件的输入流
            File file = new File("a.txt");
            File tagret = new File("F:\\BaiduNetdiskDownload\\电子书分割\\复制\\s.txt");
            fw = new FileWriter(tagret);

            fr = new FileReader(file);
            //声明一个字节数组，每次读取的长度
            char[] bt = new char[5];
            int len;
            while ((len=fr.read(bt)) !=-1){
                fw.write(bt,0,len);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(fw != null)
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(fr != null)
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
