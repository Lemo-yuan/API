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
	 * theme: java�˵���api,ͨ����������ȡ��γ��
	 * @parm addr ��������ַ
	 * ���裺
	 * 1.�ҵ�Ҫ���õ�api�ӿ�
	 * 2.��ָ��url��Ӳ�����������
	 * 3.�Է��ص��ַ������д���
	 */

	public Map<String, BigDecimal> getLatAndLngByAddress(String addr){
        String address = "";
        String lat = "";
        String lng = "";
        try {  
        	
			/* ���ȶԵ�ַ���б��� */
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
		/* ak��ֵ��Ҫ���ٶȵ�ͼ����ȥ���� */
        String url = String.format("http://api.map.baidu.com/geocoder/v2/?"
        +"ak=tS6kwFx4eI54aSZo1G0kFKUentiwM5t0&output=json&address=%s",address);
        URL myURL = null;
        URLConnection httpsConn = null;  
        System.out.println("url:"+url);
        //����ת��
        try {
            myURL = new URL(url);
            System.out.println("myURL:"+myURL);
        } catch (MalformedURLException e) {

        }
        try {
        	//�򿪺�url֮�������
            httpsConn = (URLConnection) myURL.openConnection();
            System.out.println("httpsConn:"+httpsConn);
            if (httpsConn != null) {
            	
               //�ֽ�����������URLConnection��getInputStreamȥ��÷������˷��ص�����
                //InputStreamReader���Ǵ��ֽ������ַ������Ž�������ʹ��ָ�����ַ�����ȡ�ֽڲ������ǽ���Ϊ�ַ���
            	InputStreamReader insr = new InputStreamReader(
                        httpsConn.getInputStream(), "UTF-8");
                //�����ַ�������������Ϊ�����ַ����������һЩ���幦��
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
		Map<String, BigDecimal>data=t.getLatAndLngByAddress("����ʡ�����б�Ӧ��������·");
		System.out.println("γ��:" +data.get("lat"));
		System.out.println("����:" +data.get("lng"));
	}
	
}
