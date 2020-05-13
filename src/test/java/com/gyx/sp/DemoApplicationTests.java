package com.gyx.sp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {
	static String tokenString ="谢思娜</a>";
	
	static class MyThread extends Thread {
		int aa =1;
		int bb =2;
		public MyThread(int i,int j) {
			aa=i;
			bb=j;
		}
			@Override
			public void run() {				
				String str1 = doPost("http://www.worlduc.com/space_newuser_more.aspx?EnterpriseType=" + String.valueOf(aa)
						+ "&Page=" + String.valueOf(bb), "");

				int index1 = str1.indexOf(tokenString);
				while (index1 != -1) {
					String string4 = str1.substring(0, index1 + 7);

					String hrefString = string4.substring(string4.lastIndexOf("<a href=\""));

					System.out.println(hrefString);

					index1 = str1.indexOf(tokenString, index1 + 4);
				}
			}
		}
	

	@Test
   public static void main(String[] args) {
//		int a =1;
		int b =2;
//			EnumTest.a.hello();
//			System.out.println("谢思娜aa".indexOf("a", 4));
		ExecutorService cachedThreadPool = Executors.newFixedThreadPool(100);
	
		
		for (int i = 1; i < 2; i++) {
			String str = doPost("http://www.worlduc.com/space_newuser_more.aspx?EnterpriseType=" + String.valueOf(i),
					"");
			String string = str.substring(str.indexOf("当前第1页，共"));
			String string2 = string.substring(7, string.indexOf("页", 6));
			b = Integer.valueOf(string2);
			int index = str.indexOf(tokenString);
			while (index != -1) {
				String string4 = str.substring(0, index + 7);

				String hrefString = string4.substring(string4.lastIndexOf("<a href=\""));

				System.out.println(hrefString);

				index = str.indexOf(tokenString, index + 4);
			}
			
			int j = 1;
			while (j++ < b) {
				System.out.println(j);
				cachedThreadPool.execute(new MyThread(i,j));
			}
		}
	}
//@org.junit.Test
	public static String doPost(String httpUrl, String param) {

		HttpURLConnection connection = null;
		InputStream is = null;
		OutputStream os = null;
		BufferedReader br = null;
		String result = null;
		try {
			URL url = new URL(httpUrl);
			// 通过远程url连接对象打开连接
			connection = (HttpURLConnection) url.openConnection();
			// 设置连接请求方式
			connection.setRequestMethod("POST");
			// 设置连接主机服务器超时时间：15000毫秒
			connection.setConnectTimeout(2147483647);
			// 设置读取主机服务器返回数据超时时间：60000毫秒
			connection.setReadTimeout(2147483647);

			// 默认值为：false，当向远程服务器传送数据/写数据时，需要设置为true
			connection.setDoOutput(true);
			// 默认值为：true，当前向远程服务读取数据时，设置为true，该参数可有可无
			connection.setDoInput(true);
			// 设置传入参数的格式:请求参数应该是 name1=value1&name2=value2 的形式。
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			// 设置鉴权信息：Authorization: Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0
			connection.setRequestProperty("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");
			// 通过连接对象获取一个输出流
			os = connection.getOutputStream();
			// 通过输出流对象将参数写出去/传输出去,它是通过字节数组写出的
			os.write(param.getBytes());
			// 通过连接对象获取一个输入流，向远程读取
			if (connection.getResponseCode() == 200) {

				is = connection.getInputStream();
				// 对输入流对象进行包装:charset根据工作项目组的要求来设置
				br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

				StringBuffer sbf = new StringBuffer();
				String temp = null;
				// 循环遍历一行一行读取数据
				while ((temp = br.readLine()) != null) {
					sbf.append(temp);
					sbf.append("\r\n");
				}
				result = sbf.toString();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭资源
			if (null != br) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != os) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != is) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			// 断开与远程地址url的连接
			connection.disconnect();
		}
		return result;
	}
}
