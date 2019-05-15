import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class Test {
	/*
	 * author:lemo
	 * cratetime: 2019/5/13
	 * theme: java端调用api,通过中文名获取经纬度
	 * @parm addr 中文名地址
	 * 步骤：
	 * 1.找到要调用的api接口
	 * 2.向指定url添加参数发送请求
	 * 3.对返回的字符串进行处理
	 */

	public Map<String, BigDecimal> getLatAndLngByAddress(String addr){
        String address = "";
        String lat = "";
        String lng = "";
        try {  
        	
			/* 首先对地址进行编码 */
            address = java.net.URLEncoder.encode(addr,"UTF-8"); 
            
            address=java.net.URLDecoder.decode(address, "UTF-8");
			/*
			 * address=java.net.URLEncoder.encode(address,"UTF-8");
			 * address=java.net.URLDecoder.decode(address, "UTF-8");
			 * address=java.net.URLDecoder.decode(address, "UTF-8");
			 */
            System.out.println("address:"+ address);
        } catch (UnsupportedEncodingException e1) {  
            e1.printStackTrace();  
        } 
		/* ak的值需要到百度地图里面去申请 */
        String url = String.format("http://api.map.baidu.com/geocoder/v2/?"
        +"ak=tS6kwFx4eI54aSZo1G0kFKUentiwM5t0&output=json&address=%s",address);
        URL myURL = null;
        URLConnection httpsConn = null;  
        System.out.println("url:"+url);
        //进行转码
        try {
            myURL = new URL(url);
            System.out.println("myURL:"+myURL);
        } catch (MalformedURLException e) {

        }
        try {
        	//打开和url之间的连接
            httpsConn = (URLConnection) myURL.openConnection();
            System.out.println("httpsConn:"+httpsConn);
            if (httpsConn != null) {
            	
               //字节输入流利用URLConnection的getInputStream去获得服务器端返回的数据
                //InputStreamReader类是从字节流到字符流的桥接器：它使用指定的字符集读取字节并将它们解码为字符。
            	InputStreamReader insr = new InputStreamReader(
                        httpsConn.getInputStream(), "UTF-8");
                //缓冲字符输入流，作用为其他字符输入流添加一些缓冲功能
                BufferedReader br = new BufferedReader(insr);
                String data = null;
                if ((data = br.readLine()) != null) {
                	System.out.println("data:"+data);
                    lat = data.substring(data.indexOf("\"lat\":") 
                    + ("\"lat\":").length(), data.indexOf("},\"precise\""));
                    lng = data.substring(data.indexOf("\"lng\":") 
                    + ("\"lng\":").length(), data.indexOf(",\"lat\""));
                }
                insr.close();
            }
        } catch (IOException e) {

        }
        Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
        map.put("lat", new BigDecimal(lat));
        map.put("lng", new BigDecimal(lng));
        return map;
}
	public static void main(String args[]) {
		Test t=new Test();
		Map<String, BigDecimal>data=t.getLatAndLngByAddress("江苏省扬州市宝应县苏中中路");
		System.out.println("纬度:" +data.get("lat"));
		System.out.println("经度:" +data.get("lng"));
	}
	
}
