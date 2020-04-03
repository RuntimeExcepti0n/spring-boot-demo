package tcp;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPTest01 {
    /**
     * 客户端
     */
    @Test
    public void test() throws UnknownHostException {

        InetAddress inetAddress = InetAddress.getByName("127.0.0.1");
        try (Socket socket = new Socket(inetAddress,8888);
             OutputStream os = socket.getOutputStream();
        ) {
            os.write("我是客户端".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 我是服务端
     */
    @Test
    public void test02() throws UnknownHostException {
        try (ServerSocket serverSocket = new ServerSocket(8888);
             Socket socket = serverSocket.accept();
             InputStream inputStream =  socket.getInputStream();
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ) {
            byte[] bf = new byte[5];
            int len;
            while ( (len = inputStream.read(bf)) != -1){
                byteArrayOutputStream.write(bf,0,len);
            }
            System.out.println(byteArrayOutputStream.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
