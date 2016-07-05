package com.example.erhuo;

import android.content.Context;
import android.util.Log;
//import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class UploadUtil {
    private static final String TAG = "uploadFile";
    private static final int TIME_OUT = 10 * 1000; // 瓒呮椂鏃堕棿
    private static final String CHARSET = "utf-8"; // 璁剧疆缂栫爜
    /**
     * 涓婁紶鏂囦欢鍒版湇鍔″櫒
     * @param file 闇�瑕佷笂浼犵殑鏂囦欢
     * @param RequestURL 璇锋眰鐨剅ul
     * @return 杩斿洖鍝嶅簲鐨勫唴瀹�
     */
    public static int uploadFile(File file, String RequestURL, Context ctx) {
        int res=0;
        String result = null;
        String BOUNDARY = UUID.randomUUID().toString(); // 杈圭晫鏍囪瘑 闅忔満鐢熸垚
        String PREFIX = "--", LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data"; // 鍐呭绫诲瀷

        try {
            URL url = new URL(RequestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setDoInput(true); // 鍏佽杈撳叆娴�
            conn.setDoOutput(true); // 鍏佽杈撳嚭娴�
            conn.setUseCaches(false); // 涓嶅厑璁镐娇鐢ㄧ紦瀛�
            conn.setRequestMethod("POST"); // 璇锋眰鏂瑰紡
            //conn.setRequestProperty("Charset", CHARSET); // 璁剧疆缂栫爜
            conn.setRequestProperty("connection", "keep-alive");
            //conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="+ BOUNDARY);

            if (file != null) {
                
                DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
                StringBuffer sb = new StringBuffer();
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                //Toast.makeText(ctx,"yes!",Toast.LENGTH_LONG).show();
                /**
                 * 杩欓噷閲嶇偣娉ㄦ剰锛� name閲岄潰鐨勫�间负鏈嶅姟鍣ㄧ闇�瑕乲ey 鍙湁杩欎釜key 鎵嶅彲浠ュ緱鍒板搴旂殑鏂囦欢
                 * filename鏄枃浠剁殑鍚嶅瓧锛屽寘鍚悗缂�鍚�
                 */

                sb.append("Content-Disposition: form-data; name=\"file\"; filename=\""
                        + file.getName() + "\"" + LINE_END);
                sb.append("Content-Type: application/octet-stream; charset="
                        + CHARSET + LINE_END);
                sb.append(LINE_END);
                //dos.write(sb.toString().getBytes());
                InputStream is = new FileInputStream(file);
                byte[] bytes = new byte[1024];
                int len = 0;
                while ((len = is.read(bytes)) != -1) {
                    dos.write(bytes, 0, len);
                }
                is.close();
                //dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
                        .getBytes();
                //dos.write(end_data);
                dos.flush();
                /**
                 * 鑾峰彇鍝嶅簲鐮� 200=鎴愬姛 褰撳搷搴旀垚鍔燂紝鑾峰彇鍝嶅簲鐨勬祦
                 */
                res = conn.getResponseCode();
                //Toast.makeText(ctx,"222",Toast.LENGTH_LONG).show();
                //Log.e(TAG, "response code:" + res);
                if (res == 200) {
                    //Log.e(TAG, "request success");
                    InputStream input = conn.getInputStream();
                    StringBuffer sb1 = new StringBuffer();
                    int ss;
                    while ((ss = input.read()) != -1) {
                        sb1.append((char) ss);
                    }
                    result = sb1.toString();
                    res = (Integer)Integer.parseInt(result);
                    Log.e(TAG, "result : " + res);
                } else {
                    Log.e(TAG, "request error");
                }
                //Toast.makeText(ctx,"no!",Toast.LENGTH_LONG).show();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            //Toast.makeText(ctx,e.toString(),Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            //Toast.makeText(ctx,e.toString(),Toast.LENGTH_LONG).show();
        }
        catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(ctx,e.toString(),Toast.LENGTH_LONG).show();
            Log.e(TAG,e.toString());
        }
        Log.i("real result", res+"");
        return res;
    }
}