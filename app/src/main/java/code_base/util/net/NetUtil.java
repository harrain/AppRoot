package code_base.util.net;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by net on 2018/6/25.
 */

public class NetUtil {

    public static String getPageContent(String url_str){
        try {

            //建立连接
            URL url = new URL(url_str);
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
            httpUrlConn.setDoInput(true);
            // 设置通用的请求属性
            httpUrlConn.setRequestProperty("accept", "*/*");
            httpUrlConn.setRequestProperty("connection", "Keep-Alive");
            httpUrlConn.setRequestProperty("user-agent",
                    "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.155 Safari/537.36");
            //获取输入流
            InputStream input = httpUrlConn.getInputStream();
            //将字节输入流转换为字符输入流
            InputStreamReader read = new InputStreamReader(input, "utf-8");
            //为字符输入流添加缓冲
            BufferedReader br = new BufferedReader(read);

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                //这里是对链接进行处理

                sb.append(line);
            }
            input.close();
            br.close();

            return sb.toString();
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }


    public static String getBaseUrl(String redirectUrl, String tu) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(redirectUrl).openConnection();
        conn.setInstanceFollowRedirects(false);
        // conn.setConnectTimeout(8000);
        /**
         * tu为包含location的最终url
         */
        // String tu =
        // "https://s.click.taobao.com/t_js?tu=https%3A%2F%2Fs.click.taobao.com%2Ft%3Fe%3Dm%253D2%2526s%253DshhycMgyX5YcQipKwQzePOeEDrYVVa64K7Vc7tFgwiHjf2vlNIV67gtlG6lwPBS0UkCu4LW4fHWggE%252B81xYdaYgwvAJKzKRhR0QUH89hs6odoD3G8jExAxAqvUhwaPOiypAbhWWbtazmAClNJkpsCzHrKHhQWCLYttvJ9Z4G1Zg%253D%26pvid%3D10_183.135.6.227_21727_1512373605774%26sc%3Dc2Z10Xw%26ref%3D%26et%3DXDdI5IoTDHiv%252FPC6VebP6npa%252BXvuZjy1";
        /**
         * 设置请求行
         */
//        conn.setRequestProperty("Referer", tu);
        // System.out.println(conn.getURL());
        return getRedirectUrl(conn.getHeaderField("Location"));

    }

    public static String getRedirectUrl(String needRedirectUrl) throws Exception {

        HttpURLConnection conn = (HttpURLConnection) new URL(needRedirectUrl).openConnection();
        conn.setInstanceFollowRedirects(false);
        // conn.setConnectTimeout(8000);
        /**
         * 递归找到最终的url(包含location)
         */
        if (conn.getHeaderField("location") == null)
            return needRedirectUrl;
        else
            return getRedirectUrl(conn.getHeaderField("location"));
    }

    /**
     * 不递归获取跳转链接
     *
     *
     *
     *
     * @param path
     * @return
     * @throws Exception
     */

    public static String getRUrl(String path) throws Exception {

        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.getResponseCode();
        conn.setInstanceFollowRedirects(true);
        String realUrl = conn.getURL().toString();
        conn.disconnect();
        // System.out.println(realUrl);
        return realUrl;
    }

    public static byte[] downloadBytes(String path){
        try {

            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream is = conn.getInputStream();

            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = is.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            is.close();
            byte[] data = bos.toByteArray();
            bos.close();
            return data;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


}
