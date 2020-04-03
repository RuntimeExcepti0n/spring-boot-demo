package util;

import com.google.common.collect.Lists;
import org.junit.Test;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    private static List<File> list = new ArrayList<>();

    /**
     *
     * @param path  筛选文件的目录
     * @param filter 需要筛选的文件后缀名
     * @return  筛选后的文件
     */
    public static List<File> getFileList(String path,String filter){
        List<File> resultFiles = Lists.newArrayList();
        File file = new File(path);
        List<File> files = searchFile(file);
        for (int i = 0; i <files.size() ; i++)
        {
            File tempFile = files.get(i);
            String fileName = tempFile.getName();
            if(fileName.contains(".")){
                String fileType = fileName.substring(fileName.lastIndexOf('.'));
                if(filter.equals(fileType)){
                    resultFiles.add(tempFile);
                }
            }

        }
        return resultFiles;
    }

    @Test
    public void tset(){
        String a = "sdfsdfs.jpg";
        System.out.println(a.substring(a.lastIndexOf('.')));
        File file = new File("F:\\BaiduNetdiskDownload\\搜云库\\面试题\\Java核心知识 面试宝典.pdf");
        System.out.println(file.getParent().contains("搜云库"));
        System.out.println(file.getParentFile());
    }

    private static List<File> searchFile(File file){

        File[] files = file.listFiles();
        for (int i = 0; i <files.length ; i++) {
            File file1 = files[i];
            if(file1.isFile()){
                list.add(file1);
            }else {
                searchFile(file1);
            }
        }
        return list;
    }
}
