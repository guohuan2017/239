package com.gyx.sp.controller.iwant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.springframework.http.HttpHeaders;

class Eu {
	static String tokenString = "memor";
	static String tokenString2 = "谢思娜";

	static String contentTokenString = "发私信";
	static File file = new File("log.txt");
	static int size = 25;
	static AtomicInteger j = new AtomicInteger(150773);
	static int yeshu = 151273;

	/**
	 * @author gyx
	 *
	 */

	static class MyThread extends Thread {
		static int aa = 2;
		private static ThreadLocal<Temp> temp = new ThreadLocal<Temp>();
		FileChannel fileChannel1 = null;

		public MyThread(int i, final FileChannel fileChannel) {
//			aa = i;
			fileChannel1 = fileChannel;
		}

		class Temp {
			int bb;
			String str1;
			int index11;
			int index1;
			int index2;
			String string4;
			String hrefString;

			public int getBb() {
				return bb;
			}

			public Temp setBb(int bb) {
				this.bb = bb;
				return this;
			}

			public String getStr1() {
				return str1;
			}

			public Temp setStr1(String str1) {
				this.str1 = str1;
				return this;
			}

			public int getIndex11() {
				return index11;
			}

			public Temp setIndex11(int index11) {
				this.index11 = index11;
				return this;
			}

			public int getIndex1() {
				return index1;
			}

			public Temp setIndex1(int index1) {
				this.index1 = index1;
				return this;
			}

			public int getIndex2() {
				return index2;
			}

			public Temp setIndex2(int index2) {
				this.index2 = index2;
				return this;
			}

			public String getString4() {
				return string4;
			}

			public Temp setString4(String string4) {
				this.string4 = string4;
				return this;
			}

			public String getHrefString() {
				return hrefString;
			}

			public Temp setHrefString(String hrefString) {
				this.hrefString = hrefString;
				return this;
			}

			public Temp() {
				super();
				// TODO Auto-generated constructor stub
			}
		}

		@Override
		public void run() {
			temp.set(new Temp());
			while (j.get() <= yeshu) {
				synchronized (tokenString2) {
					temp.set(temp.get().setBb(j.getAndIncrement()));
				}
				if (temp.get().getBb() <= yeshu && temp.get().getBb() > 0) {
					temp.set(temp.get()
							.setStr1((doPost("http://www.worlduc.com/space_newuser_more.aspx?EnterpriseType="
									+ String.valueOf(aa) + "&Page=" + String.valueOf(temp.get().getBb()), "")
											.toLowerCase())));
					// 判断内容页面是否加载成功
					temp.set(temp.get().setIndex11(Integer.valueOf(temp.get().getStr1().indexOf(contentTokenString))));
					while (temp.get().getIndex11() == -1) {
						try {
							synchronized (tokenString) {
							fileChannel1.write(ByteBuffer.wrap((System.currentTimeMillis() + " wait " + String.valueOf(aa) + " "
									+ String.valueOf(temp.get().getBb()) + "\r\n").toString().getBytes("UTF-8")));
							}
							Thread.sleep(1000);
						} catch (InterruptedException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						temp.set(temp.get()
								.setStr1((doPost(
										"http://www.worlduc.com/space_newuser_more.aspx?EnterpriseType="
												+ String.valueOf(aa) + "&Page=" + String.valueOf(temp.get().getBb()),
										"").toLowerCase())));
						// 判断内容页面是否加载成功
						temp.set(temp.get()
								.setIndex11(Integer.valueOf(temp.get().getStr1().indexOf(contentTokenString))));
					}
					synchronized (tokenString) {
						try {
							fileChannel1.write(ByteBuffer.wrap((String.valueOf(System.currentTimeMillis()) + " " + String.valueOf(aa) + " "
									+ String.valueOf(temp.get().getBb()) + "\r\n").toString().getBytes("UTF-8")));
						} catch (Exception e) { // TODO: handle exception

						}
					}

					temp.set(temp.get().setIndex1(temp.get().getStr1().indexOf(tokenString)));
					temp.set(temp.get().setIndex2(temp.get().getStr1().indexOf(tokenString2)));
					while (temp.get().getIndex1() != -1) {
						temp.set(temp.get().setString4(temp.get().getStr1().substring(0, temp.get().getIndex1() + 7)));

						temp.set(temp.get().setHrefString(
								temp.get().getString4().substring(temp.get().getString4().lastIndexOf("<a href=\""))));
						synchronized (tokenString) {
							try {
								fileChannel1.write(ByteBuffer.wrap((String.valueOf(System.currentTimeMillis()) + " " + String.valueOf(aa) + " "
										+ String.valueOf(temp.get().getBb()) + " " + temp.get().getHrefString() + "\r\n").toString().getBytes("UTF-8")));
							} catch (Exception e) { // TODO: handle exception

							}
						}
						temp.set(temp.get()
								.setIndex1(temp.get().getStr1().indexOf(tokenString, temp.get().getIndex1() + 4)));
					}
					while (temp.get().getIndex2() != -1) {
						temp.set(temp.get().setString4(temp.get().getStr1().substring(0, temp.get().getIndex2() + 7)));

						temp.set(temp.get().setHrefString(
								temp.get().getString4().substring(temp.get().getString4().lastIndexOf("<a href=\""))));
						synchronized (tokenString) {
						try {
							fileChannel1.write(ByteBuffer.wrap((String.valueOf(System.currentTimeMillis()) + " " + String.valueOf(aa) + " "
									+ String.valueOf(temp.get().getBb()) + " " + temp.get().getHrefString() + "\r\n").toString().getBytes("UTF-8")));

						} catch (Exception e) { // TODO: handle exception

						}
						temp.set(temp.get()
								.setIndex2(temp.get().getStr1().indexOf(tokenString2, temp.get().getIndex2() + 4)));
					}
				}
			}
		}
	}
	}

//	@Test
//	String[] args
	public static void main() throws Exception {
//	   System.out.println("ABC".toLowerCase());
//		int a =1;
//		int b = 2;
//			EnumTest.a.hello();
//			System.out.println("谢思娜aa".indexOf("a", 4));
		ThreadPoolExecutor cachedThreadPool = new ThreadPoolExecutor(size, size, Long.MAX_VALUE, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>(32));

		FileOutputStream fileOutputStream = new FileOutputStream(file,true);
		fileOutputStream.flush();
		final FileChannel fileChannel = fileOutputStream.getChannel();
//		int i = 2;
//			152540
//		int k = 150773;
//		while (k <= 152539) {
		for (int i = 0; i < size; i++) {
			cachedThreadPool.execute(new MyThread(i, fileChannel), Long.MAX_VALUE, TimeUnit.MILLISECONDS);
		}

//		}

		/*
		 * for (int i = 2; i < 3; i++) {
		 * 
		 * fileChannel.write(ByteBuffer.wrap(
		 * (String.valueOf(System.currentTimeMillis()) + " " + (String.valueOf(i) +
		 * "\r\n")).getBytes())); String str = doPost(
		 * "http://www.worlduc.com/space_newuser_more.aspx?EnterpriseType=" +
		 * String.valueOf(i), ""); // 循环加载第一页 int index = str.indexOf("当前第1页，共"); while
		 * (index == -1) { Thread.sleep(5000); str =
		 * doPost("http://www.worlduc.com/space_newuser_more.aspx?EnterpriseType=" +
		 * String.valueOf(i), ""); index = str.indexOf("当前第1页，共"); }
		 * fileChannel.write(ByteBuffer.wrap(
		 * (String.valueOf(System.currentTimeMillis()) + " " + (String.valueOf(1) +
		 * "\r\n")).getBytes())); String string = str.substring(index); String string2 =
		 * string.substring(7, string.indexOf("页", 6)); b = Integer.valueOf(string2);
		 * int index1 = str.indexOf(tokenString); int index2 =
		 * str.indexOf(tokenString2); // 在第一页后续内容中循环查找 while (index1 != -1) { String
		 * string4 = str.substring(0, index1 + 7);
		 * 
		 * String hrefString = string4.substring(string4.lastIndexOf("<a href=\""));
		 * 
		 * fileChannel.write(ByteBuffer
		 * .wrap((String.valueOf(System.currentTimeMillis()) + " " + hrefString +
		 * "\r\n").getBytes()));
		 * 
		 * index1 = str.indexOf(tokenString, index1 + 4); } while (index2 != -1) {
		 * String string4 = str.substring(0, index2 + 7);
		 * 
		 * String hrefString = string4.substring(string4.lastIndexOf("<a href=\""));
		 * 
		 * fileChannel.write(ByteBuffer
		 * .wrap((String.valueOf(System.currentTimeMillis()) + " " + hrefString +
		 * "\r\n").getBytes()));
		 * 
		 * index2 = str.indexOf(tokenString2, index2 + 4); }
		 * 
		 * int j = 1; while (j++ < b) {
		 * 
		 * cachedThreadPool.execute(new MyThread(i, j, fileChannel)); } }
		 */
	}

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
			connection.addRequestProperty("ProtocolVersion", "http-1.0");

			connection.addRequestProperty(HttpHeaders.CONNECTION, "close");

			// 默认值为：false，当向远程服务器传送数据/写数据时，需要设置为true
			connection.setDoOutput(true);
			// 默认值为：true，当前向远程服务读取数据时，设置为true，该参数可有可无
			connection.setDoInput(true);
			// 设置传入参数的格式:请求参数应该是 name1=value1&name2=value2 的形式。
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			// 设置鉴权信息：Authorization: Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0
//			connection.setRequestProperty("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");
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
