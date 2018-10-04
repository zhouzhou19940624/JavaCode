package com.easycore.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

public class HdfsUtils {
	// 文件系统
	public static FileSystem hdfs;

	// 文件系统
	static {
		try {
			String HDFS_URL = ConfigUtils.getVal("config/hdfs.properties", "hdfs.url");
			hdfs = FileSystem.get(URI.create(HDFS_URL), new Configuration(), "root");
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	// 创建路径
	public static boolean createPath(String filePath) throws IOException, InterruptedException {
		Path path = new Path(filePath);
		return hdfs.mkdirs(path);
	}

	// 删除路径
	public static boolean deletePath(String filePath) throws IOException, InterruptedException {
		Path deletePath = new Path(filePath);
		return hdfs.delete(deletePath, true);
	}

	// 重命名
	public static boolean reName(String oldPathString, String newPathString) throws IOException, InterruptedException {
		Path oldPath = new Path(oldPathString);
		Path newPath = new Path(newPathString);
		return hdfs.rename(oldPath, newPath);
	}

	// hdfs->
	public static void copyFileFromHDFS(String HDFSPathString, String LocalPathString)
			throws IOException, InterruptedException {
		InputStream in = hdfs.open(new Path(HDFSPathString));
		OutputStream out = new FileOutputStream(LocalPathString);
		IOUtils.copyBytes(in, out, 4096, true);
		System.out.println("hdfs->拷贝完成");
	}

	// ->hdfs
	public static void copyFileToHDFS(String LocalPathString, String HDFSPathString) throws Exception {
		InputStream in = new FileInputStream(new File(LocalPathString));
		OutputStream out = hdfs.create(new Path(HDFSPathString));
		IOUtils.copyBytes(in, out, 4096, true);
		System.out.println("->hdfs拷贝完成");
		hdfs.close();
	}

	// 内容列表
	public static List<String> listHdfsPath(String listPath) throws FileNotFoundException, IOException {
		List<String> list = new ArrayList<>();
		FileStatus[] files = hdfs.listStatus(new Path(listPath));
		for (FileStatus f : files) {
			String str = f.getPath().toUri().getPath();
			list.add(str);
			System.out.println(str);
		}
		return list;
	}

	//上传到hadoop里面
	public static void copyLocalFileToHDFS(String file,String disk) throws IOException{
		Path srcPath = new Path(file);
		Path dstPath = new Path(disk);
		hdfs.copyFromLocalFile(srcPath, dstPath);
	}
}