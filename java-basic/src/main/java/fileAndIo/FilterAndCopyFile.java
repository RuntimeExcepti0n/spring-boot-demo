package fileAndIo;

import constant.FileConstant;
import org.junit.Test;
import util.FileUtil;

import java.io.*;
import java.util.List;

public class FilterAndCopyFile {

    @Test
    public void test() {
        //源文件目录
        String source = "F:\\BaiduNetdiskDownload";
        //目的文件目录
        String target = "F:\\电子书2";
        FileOutputStream fos = null;
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
            List<File> fileList = FileUtil.getFileList(source, FileConstant.FILE_PDF);
            for (File file : fileList) {
                fis = new FileInputStream(file);
                fos = new FileOutputStream(new File(target+"\\"+file.getName()));
                bis = new BufferedInputStream(fis);
                bos = new BufferedOutputStream(fos);
                byte[] bytes = new byte[10];
                int data;
                while ((data = bis.read(bytes)) != -1) {
                    bos.write(bytes, 0, data);
                }
                bos.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Test
    public void  test2(){
        File file = new File("F:\\电子书\\zookeeperStarted.pdf");
        System.out.println(file.canRead());
        String name = file.getName();
        System.out.println(name);
    }
}
