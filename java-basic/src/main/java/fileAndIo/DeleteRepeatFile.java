package fileAndIo;

import constant.FileConstant;
import org.junit.Test;
import util.FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 删除目录下重复的文件
 */
public class DeleteRepeatFile {

    static  List<File> list = new ArrayList<>();


    @Test
    public void test2(){
        //源文件目录
        String source = "F:\\BaiduNetdiskDownload";
        int i = 0;
        List<File> fileList = FileUtil.getFileList(source, FileConstant.FILE_PDF);
        for (File file : fileList) {
            if(!file.getParent().contains("搜云库")){
                file.delete();
                i++;
            }
        }
        System.out.println(i);
    }

    @Test
    public void test1(){
        File file1 = new File("F:\\BaiduNetdiskDownload\\电子书分割\\Kafka");
        List<File> files = searchFile(file1);
        System.out.println(files);
        for (int i = 0; i < files.size()-1; i++) {
          if(isSameFile(files.get(i).toString(),files.get(i+1).toString())){
              files.get(i).delete();
          }
        }
        System.out.println("d");
    }

    public static List<File> searchFile(File file1){

        File[] files = file1.listFiles();
        for (int i = 0; i <files.length ; i++) {
            File file = files[i];
            if(file.isFile()){
                list.add(file);
            }else {
                searchFile(file);
            }
        }
        return list;
    }

    /**
     * 判断两个文件的内容是否相同，文件名要用绝对路径
     * @param fileName1 ：文件1的绝对路径
     * @param fileName2 ：文件2的绝对路径
     * @return 相同返回true，不相同返回false
     */
    public static boolean isSameFile(String fileName1,String fileName2){
        FileInputStream fis1 = null;
        FileInputStream fis2 = null;
        try {
            fis1 = new FileInputStream(fileName1);
            fis2 = new FileInputStream(fileName2);

            int len1 = fis1.available();//返回总的字节数
            int len2 = fis2.available();

            if (len1 == len2) {//长度相同，则比较具体内容
                //建立两个字节缓冲区
                byte[] data1 = new byte[len1];
                byte[] data2 = new byte[len2];

                //分别将两个文件的内容读入缓冲区
                fis1.read(data1);
                fis2.read(data2);

                //依次比较文件中的每一个字节
                for (int i=0; i<len1; i++) {
                    //只要有一个字节不同，两个文件就不一样
                    if (data1[i] != data2[i]) {
                        System.out.println("文件内容不一样");
                        return false;
                    }
                }
                System.out.println("两个文件完全相同");
                return true;
            } else {
                //长度不一样，文件肯定不同
                return false;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {//关闭文件流，防止内存泄漏
            if (fis1 != null) {
                try {
                    fis1.close();
                } catch (IOException e) {
                    //忽略
                    e.printStackTrace();
                }
            }
            if (fis2 != null) {
                try {
                    fis2.close();
                } catch (IOException e) {
                    //忽略
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

}
