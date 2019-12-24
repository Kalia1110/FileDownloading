package kcp.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

@WebServlet("/DownloadingFile")
public class DownloadingFile extends HttpServlet {

	private byte[] buffer;

	@SuppressWarnings("resource")
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		ServletOutputStream sos = null;
		ServletContext sc = null;
		String path = null;
		String fileName = null;
		InputStream is = null;
		OutputStream os = null;
		String mimeType = null;
		int bytesRead;
		byte[] buffer = new byte[4096];
		sc = getServletContext();
		path = sc.getInitParameter("download_path");
		fileName = req.getParameter("file");
		File file = new File(path + "/" + fileName);
		long len = file.length();
		res.setContentLengthLong(len);
		mimeType = sc.getMimeType(file.getAbsolutePath());
		res.setContentType(mimeType);
		is = new FileInputStream(file);
		res.setHeader("content-disposition", "attachment;fileName=" + file.getName());
		os = res.getOutputStream();
		IOUtils.copy(is, os);
		/*
		 * while ((bytesRead = is.read(buffer)) != -1) { os.write(buffer, 0, bytesRead);
		 * }
		 */
		is.close();
		os.close();
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		doGet(req, res);
	}

}
