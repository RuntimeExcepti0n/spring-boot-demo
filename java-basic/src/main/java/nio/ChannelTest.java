package nio;

import com.alibaba.druid.sql.visitor.functions.Char;
import org.apache.commons.lang.CharSet;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ChannelTest {
    /**
     * 字节码
     * @throws Exception
     */
    @Test
    public void test5() throws Exception {
        Charset charset = Charset.forName("UTF-8");
        CharsetEncoder charsetEncoder = charset.newEncoder();
        CharsetDecoder charsetDecoder = charset.newDecoder();
        CharBuffer buffer = CharBuffer.allocate(1024);
        buffer.put("你打我试试");
        buffer.flip();
        ByteBuffer encodeBuffer = charsetEncoder.encode(buffer);
        for (int i = 0; i < 12; i++) {
            System.out.println(encodeBuffer.get());
        }
        //解码
        encodeBuffer.flip();
        CharBuffer decodeBuffer = charsetDecoder.decode(encodeBuffer);
        System.out.println(decodeBuffer.toString());
        System.out.println("********************");

        Charset gbk = Charset.forName("GBK");
        CharsetDecoder gbkDecode = gbk.newDecoder();
        encodeBuffer.flip();
        CharBuffer decode = gbkDecode.decode(encodeBuffer);
        System.out.println(decode.toString());

    }


    /**
     * 分散读取和聚集写入
     * @throws IOException
     */
    @Test
    public void test4() throws IOException {
        FileChannel inChannel = FileChannel.open(Paths.get("a.txt"),StandardOpenOption.READ);
        FileChannel outChanel = FileChannel.open(Paths.get("b.txt"),StandardOpenOption.READ,StandardOpenOption.WRITE,StandardOpenOption.CREATE);
        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
        ByteBuffer byteBuffer1 = ByteBuffer.allocate(1024);
        ByteBuffer buffer[] = {byteBuffer,byteBuffer1};

        inChannel.read(buffer);
        for (ByteBuffer buf : buffer) {
           buf.flip();
        }
        System.out.println(new String(buffer[0].array(),0,buffer[0].limit()));
        System.out.println(new String(buffer[1].array(),0,buffer[1].limit()));

        outChanel.write(buffer);
        outChanel.close();
        inChannel.close();
    }


    /**
     * 通道之间数据传毒  （直接缓冲区）
     */
    @Test
    public void test3() throws IOException {
        FileChannel inChannel = FileChannel.open(Paths.get("security3.jpg"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("security4.jpg"),StandardOpenOption.CREATE,StandardOpenOption.WRITE,StandardOpenOption.READ);

        inChannel.transferTo(0,inChannel.size(),outChannel);
        outChannel.close();
        inChannel.close();

    }


    /**
     * 内存映射文件 方式复制
     */
    @Test
    public void test2(){
        try {
            FileChannel inChannel = FileChannel.open(Paths.get("security3.jpg"), StandardOpenOption.READ);
            FileChannel outChannel = FileChannel.open(Paths.get("security2.jpg"),StandardOpenOption.WRITE,StandardOpenOption.READ,StandardOpenOption.CREATE);

            MappedByteBuffer inMappedByteBuffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
            MappedByteBuffer outMappedByteBuffer = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());

            byte[] bytes = new byte[inMappedByteBuffer.limit()];
            inMappedByteBuffer.get(bytes);
            outMappedByteBuffer.put(bytes);


            outChannel.close();
            inChannel.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }

    }


    /**
     * 非直接缓冲区复制文件
     */
    @Test
    public void test()  {

        FileInputStream fis = null;
        FileOutputStream fos = null;
        //①获取通道
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            fis = new FileInputStream("security.jpg");
            fos = new FileOutputStream("security3.jpg");
            inChannel = fis.getChannel();
            outChannel = fos.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

            while (inChannel.read(byteBuffer) != -1){
                byteBuffer.flip();
                outChannel.write(byteBuffer);
                byteBuffer.clear();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(outChannel != null){
                try {
                    outChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(inChannel != null){
                try {
                    inChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
