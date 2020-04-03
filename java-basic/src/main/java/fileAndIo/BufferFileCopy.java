package fileAndIo;

import org.junit.Test;

import java.io.*;

public class BufferFileCopy {


    @Test
    public void test() {
        FileOutputStream  fos = null;
        FileInputStream  fis = null;
        BufferedOutputStream bfos = null;
        BufferedInputStream  bfis = null;

        try {
            File source = new File("security.jpg");
            File target = new File("security2.jpg");

            fis = new FileInputStream(source);
            fos = new FileOutputStream(target);
            bfos = new BufferedOutputStream(fos);
            bfis = new BufferedInputStream(fis);

            byte[] bt = new byte[5];
            int data;
            while ( (data = bfis.read(bt)) != -1){
                bfos.write(bt,0,data);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(bfos != null) {
                try {
                    bfos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(bfis != null) {
                try {
                    bfis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
