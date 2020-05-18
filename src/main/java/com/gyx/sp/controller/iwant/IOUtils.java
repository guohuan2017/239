package com.gyx.sp.controller.iwant;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public enum IOUtils {
	instance;

	private static boolean isRunning = true;

	private static long contentLength = 0;
	private static String encodeType = "utf-8";

	public IOUtils setContentLength(long contentLength) {
		this.contentLength = contentLength;
		return this;
	}

	public IOUtils setEncodeType(String encodeType) {
		this.encodeType = encodeType;
		return this;

	}

	public static void close(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void stop() {
		isRunning = false;
	}

	public static void read(boolean isLine, InputStream inputStream, IOListener ioListener) {
		if (isLine) {
			readLine2String(inputStream, ioListener);
		} else {
			read2String(inputStream, ioListener);

		}
	}

	/**
	 * @param ioListener
	 */

	public static void read2String(String pathFile, IOListener ioListener) {
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(pathFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			ioListener.onFail(e.getMessage());
			return;
		}
		read2String(fileInputStream, ioListener);
	}

	public static void read2String(InputStream inputStream, IOListener ioListener) {

		if (!(inputStream instanceof BufferedInputStream)) {
			inputStream = new BufferedInputStream(inputStream);
		}
		BufferedReader bufferedReader = null;
		InputStreamReader inputStreamReader = null;
		try {
			inputStreamReader = new InputStreamReader(inputStream, encodeType);
			bufferedReader = new BufferedReader(inputStreamReader);

			StringBuilder sb = new StringBuilder();

			char[] buf = new char[1024];
			int len = 0;
			long current = 0;

			while (isRunning && (len = bufferedReader.read(buf)) != -1) {
				sb.append(buf, 0, len);
				current += len;
				ioListener.onLoading("", current, contentLength);
			}

			// 中断
			if (len != -1) {
				ioListener.onInterrupted();
			} else {

				ioListener.onCompleted(sb.toString());

			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			ioListener.onFail(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			ioListener.onFail(e.getMessage());

		} finally {
			close(bufferedReader);
			close(inputStreamReader);
			close(inputStream);
		}
	}

	/**
	 * 同步读取，共享锁，但无法同时进行写操作
	 *
	 * @param ioListener
	 */
	public static void read2StrSync(String pathFile, IOListener ioListener) {
		FileInputStream fileInputStream = null;
		FileChannel fileChannel = null;
		FileLock fileLock = null;// 文件锁

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {

			/**
			 * 读文件
			 */

			fileInputStream = new FileInputStream(pathFile);
			fileChannel = fileInputStream.getChannel();
			while (true) {
				try {
					fileLock = fileChannel.tryLock(0, Long.MAX_VALUE, true);// 共享锁
					break;
				} catch (Exception e) {
					System.out.println("有其他线程正在操作该文件，当前线程" + Thread.currentThread().getName());
				}
			}
			if (fileLock != null) {

				ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
				int len = 0;
				long current = 0;
				while (isRunning && (len = fileChannel.read(byteBuffer)) != -1) {
					// 0,byteBuffer.position()，必须写这个，否则GG，读取文件错乱
					byteArrayOutputStream.write(byteBuffer.array(), 0, byteBuffer.position());
					current += len;
					ioListener.onLoading("", current, fileChannel.size());
					byteBuffer.clear();
				}
				if (fileLock != null && fileLock.isValid()) {
//                LogUtils.log("release-read-lock");
					fileLock.release();
				}
				close(fileChannel);
				close(fileInputStream);
				// 中断
				if (len != -1) {
					ioListener.onInterrupted();
				} else {
					ioListener.onCompleted(byteArrayOutputStream.toString("utf-8"));

				}
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			ioListener.onFail(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			ioListener.onFail(e.getMessage());

		} finally {

		}
	}

	/**
	 * @param ioListener
	 */

	public static void readLine2String(String pathFile, IOListener ioListener) {
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(pathFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			ioListener.onFail(e.getMessage());
			return;
		}
		readLine2String(fileInputStream, ioListener);
	}

	/**
	 * 一行一行地读
	 *
	 * @param inputStream
	 * @param ioListener
	 */
	public static void readLine2String(InputStream inputStream, IOListener ioListener) {

		BufferedReader bufferedReader = null;
		InputStreamReader inputStreamReader = null;
		try {
			inputStreamReader = new InputStreamReader(inputStream, encodeType);
			bufferedReader = new BufferedReader(inputStreamReader);

			StringBuilder sb = new StringBuilder();
			long current = 0;

			String str;
			while (isRunning && (str = bufferedReader.readLine()) != null) {
				sb.append(str);
				current += str.length();

				ioListener.onLoading(str, current, contentLength);

			}

			// 中断
			if ((str = bufferedReader.readLine()) != null) {
				ioListener.onInterrupted();
			} else {

				ioListener.onCompleted(sb.toString());

			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			ioListener.onFail(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			ioListener.onFail(e.getMessage());

		} finally {
			close(bufferedReader);
			close(inputStreamReader);
			close(inputStream);
		}
	}

	public static void readL2StrNoBuffer(String pathFile, IOListener ioListener) {
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(pathFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			ioListener.onFail(e.getMessage());
			return;
		}
		readL2StrNoBuffer(fileInputStream, ioListener);
	}

	/**
	 * 一行一行地读，不拼接
	 *
	 * @param inputStream
	 * @param ioListener
	 */
	public static void readL2StrNoBuffer(InputStream inputStream, IOListener ioListener) {

		BufferedReader bufferedReader = null;
		InputStreamReader inputStreamReader = null;
		try {
			inputStreamReader = new InputStreamReader(inputStream, encodeType);
			bufferedReader = new BufferedReader(inputStreamReader);

			long current = 0;

			String str;
			while (isRunning && (str = bufferedReader.readLine()) != null) {
				current += str.length();

				ioListener.onLoading(str, current, contentLength);

			}

			// 中断
			if ((str = bufferedReader.readLine()) != null) {
				ioListener.onInterrupted();
			} else {

				ioListener.onCompleted("");

			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			ioListener.onFail(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			ioListener.onFail(e.getMessage());

		} finally {
			close(bufferedReader);
			close(inputStreamReader);
			close(inputStream);
		}
	}

	public static void readL_N2String(String pathFile, IOListener ioListener) {
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(pathFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			ioListener.onFail(e.getMessage());
			return;
		}
		readL_N2String(fileInputStream, ioListener);
	}

	/**
	 * 一行一行地读，\n拼接
	 *
	 * @param inputStream
	 * @param ioListener
	 */
	public static void readL_N2String(InputStream inputStream, IOListener ioListener) {

		BufferedReader bufferedReader = null;
		InputStreamReader inputStreamReader = null;
		try {
			inputStreamReader = new InputStreamReader(inputStream, encodeType);
			bufferedReader = new BufferedReader(inputStreamReader);

			StringBuilder sb = new StringBuilder();
			long current = 0;

			String str;
			while (isRunning && (str = bufferedReader.readLine()) != null) {
				sb.append(str);
				sb.append("\n");
				current += str.length();

				ioListener.onLoading(str, current, contentLength);

			}

			// 中断
			if ((str = bufferedReader.readLine()) != null) {
				ioListener.onInterrupted();
			} else {

				ioListener.onCompleted(sb.toString());

			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			ioListener.onFail(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			ioListener.onFail(e.getMessage());

		} finally {
			close(bufferedReader);
			close(inputStreamReader);
			close(inputStream);
		}
	}

	/**
	 * 读取到文件
	 *
	 * @param inputStream
	 * @param outputStream
	 * @param ioListener
	 */
	public static void read2File(InputStream inputStream, OutputStream outputStream, IOListener ioListener) {

		try {

			byte[] buffer = new byte[1024];
			int len = 0;
			long current = 0;

			while (isRunning && (len = inputStream.read(buffer)) != -1) {

				outputStream.write(buffer, 0, len);
				current += len;
				ioListener.onLoading(new String(buffer), current, contentLength);
			}

			outputStream.flush();
			// 中断
			if (len != -1) {
				ioListener.onInterrupted();
			} else {

				ioListener.onCompleted(null);

			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close(outputStream);
			close(inputStream);
		}
	}

	/**
	 * 将str写入文件
	 */
	public static void writeStr2File(String str, String pathFile, IOListener ioListener) {
		BufferedWriter bufferedWriter = null;
		OutputStreamWriter outputStreamWriter = null;
		OutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(pathFile);
			outputStreamWriter = new OutputStreamWriter(outputStream);
			bufferedWriter = new BufferedWriter(outputStreamWriter);
			bufferedWriter.write(str);
			ioListener.onCompleted("");
		} catch (IOException e) {
			e.printStackTrace();
			ioListener.onFail(e.getMessage());
		} finally {
			close(bufferedWriter);
			close(outputStreamWriter);
			close(outputStream);
		}
	}

	/**
	 * 将str写入文件,同步操作,独占锁
	 */
	public static void writeStr2ReplaceFileSync(String str, FileChannel fileChannel, IOListener ioListener) {
//		File file;
//		file = new File(pathFile);
//		FileOutputStream fileOutputStream = null;
//		FileChannel fileChannel = null;
		FileLock fileLock = null;// 文件锁
		try {

			/**
			 * 写文件
			 */
//			fileOutputStream = new FileOutputStream(file);
//			fileChannel = fileOutputStream.getChannel();
			while (true) {
				try {
					fileLock = fileChannel.tryLock();// 独占锁
					break;
				} catch (Exception e) {
					System.out.println("有其他线程正在操作该文件，当前线程" + Thread.currentThread().getName());
				}
			}
			if (fileLock != null) {
				int len = 0;
//				long current = file.length();
				if (isRunning) {
					fileChannel.write(ByteBuffer.wrap(str.getBytes()));
//					current += len;
//                    LogUtils.log("当前线程" + Thread.currentThread().getName());
					ioListener.onLoading(str, 0, str.length());
				} else {
					// 中断
					ioListener.onInterrupted();
				}
				if (fileLock != null && fileLock.isValid()) {
//                    LogUtils.log("release-write-lock");
					fileLock.release();
				}
//				close(fileChannel);
//				close(fileOutputStream);
//				if (file.length() == str.getBytes().length) {
//					ioListener.onCompleted(file);
//				}
			}

		} catch (IOException e) {
			e.printStackTrace();
			ioListener.onFail(e.getMessage());
		} finally {
		}
	}

}
