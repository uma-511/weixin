package com.jfinal.weixin.sdk.kit;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.jfinal.kit.StrKit;
import com.jfinal.weixin.sdk.api.MediaFile;
import com.jfinal.weixin.sdk.utils.IOUtils;

/**
 * 对HttpKit添加功能
 * @author L.cm
 *
 */
public class HttpKitExt {
	private static final String DEFAULT_CHARSET = "UTF-8";
	private static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36";
	
	/**
	 * 上传临时素材，本段代码来自老版本（____′↘夏悸 / wechat），致敬！
	 * @param url 图片上传地址
	 * @param file 需要上传的文件
	 * @return ApiResult
	 * @throws IOException
	 */
	public static String uploadMedia(String url, File file, String params) throws IOException {
		URL urlGet = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) urlGet.openConnection();
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setUseCaches(false);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("connection", "Keep-Alive");
		conn.setRequestProperty("user-agent", DEFAULT_USER_AGENT);  
		conn.setRequestProperty("Charsert", "UTF-8");
		// 定义数据分隔线
		String BOUNDARY = "----WebKitFormBoundaryiDGnV9zdZA1eM1yL"; 
		conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
		
		OutputStream out = new DataOutputStream(conn.getOutputStream());
		// 定义最后数据分隔线
		StringBuilder mediaData = new StringBuilder();
		mediaData.append("--").append(BOUNDARY).append("\r\n");
		mediaData.append("Content-Disposition: form-data;name=\"media\";filename=\""+ file.getName() + "\"\r\n");
		mediaData.append("Content-Type:application/octet-stream\r\n\r\n");
		byte[] mediaDatas = mediaData.toString().getBytes();
		out.write(mediaDatas);
		DataInputStream fs = new DataInputStream(new FileInputStream(file));
		int bytes = 0;  
		byte[] bufferOut = new byte[1024];
		while ((bytes = fs.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		IOUtils.closeQuietly(fs);
		// 多个文件时，二个文件之间加入这个
		out.write("\r\n".getBytes());
		if (StrKit.notBlank(params)) {
			StringBuilder paramData = new StringBuilder();
			paramData.append("--").append(BOUNDARY).append("\r\n");
			paramData.append("Content-Disposition: form-data;name=\"description\";");
			byte[] paramDatas = paramData.toString().getBytes();
			out.write(paramDatas);
			out.write(params.getBytes(DEFAULT_CHARSET));
		}
		byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
		out.write(end_data);
		out.flush();
		IOUtils.closeQuietly(out);
		
		// 定义BufferedReader输入流来读取URL的响应  
		InputStream in = conn.getInputStream();
		BufferedReader read = new BufferedReader(new InputStreamReader(in, DEFAULT_CHARSET));
		String valueString = null;
		StringBuffer bufferRes = null;
		bufferRes = new StringBuffer();
		while ((valueString = read.readLine()) != null){
			bufferRes.append(valueString);
		}
		IOUtils.closeQuietly(in);
		// 关闭连接
		if (conn != null) {
			conn.disconnect();
		}
		return bufferRes.toString();
	}
	

	/**
	 * 获取永久素材
	 * @param url 素材地址
	 * @return params post参数
	 * @return InputStream 流，考虑到这里可能返回json或file
	 * @throws IOException
	 */
	public static InputStream downloadMaterial(String url, String params) throws IOException {
		URL _url = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) _url.openConnection();
		// 连接超时
		conn.setConnectTimeout(25000);
		// 读取超时 --服务器响应比较慢，增大时间
		conn.setReadTimeout(25000);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "Keep-Alive");
		conn.setRequestProperty("User-Agent", DEFAULT_USER_AGENT);
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.connect();
		if (StrKit.notBlank(params)) {
			OutputStream out = conn.getOutputStream();
			out.write(params.getBytes(DEFAULT_CHARSET));
			out.flush();
			IOUtils.closeQuietly(out);
		}
		InputStream input = conn.getInputStream();
//		// 关闭连接
//		if (conn != null) {
//			conn.disconnect();
//		}
		return input;
	}
	
	/**
	 * 下载素材，本段代码来自老版本（____′↘夏悸 / wechat），致敬！
	 * @param url 素材地址
	 * @return MediaFile
	 * @throws IOException
	 */
	public static MediaFile download(String url) throws IOException {
		MediaFile mediaFile = new MediaFile();
		URL _url = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) _url.openConnection();
		// 连接超时
		conn.setConnectTimeout(25000);
		// 读取超时 --服务器响应比较慢，增大时间
		conn.setReadTimeout(25000);
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		conn.setRequestProperty("User-Agent", DEFAULT_USER_AGENT);
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.connect();
		
		if(conn.getContentType().equalsIgnoreCase("text/plain")){
			// 定义BufferedReader输入流来读取URL的响应  
			InputStream in = conn.getInputStream();
			BufferedReader read = new BufferedReader(new InputStreamReader(in, DEFAULT_CHARSET));
			String valueString = null;
			StringBuffer bufferRes = new StringBuffer();
			while ((valueString = read.readLine()) != null){
				bufferRes.append(valueString);
			}
			read.close();
			IOUtils.closeQuietly(in);
			mediaFile.setError(bufferRes.toString());
		}else{
			BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());  
			String ds = conn.getHeaderField("Content-disposition");
			String fullName = ds.substring(ds.indexOf("filename=\"") + 10, ds.length() - 1);
			String relName = fullName.substring(0, fullName.lastIndexOf("."));
			String suffix = fullName.substring(relName.length()+1);
			
			mediaFile.setFullName(fullName);
			mediaFile.setFileName(relName);
			mediaFile.setSuffix(suffix);
			mediaFile.setContentLength(conn.getHeaderField("Content-Length"));
			mediaFile.setContentType(conn.getHeaderField("Content-Type"));
			mediaFile.setFileStream(bis);
		}
		// 关闭连接
		if (conn != null) {
			conn.disconnect();
		}
		return mediaFile;
	}
}
