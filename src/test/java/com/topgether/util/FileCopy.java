package com.topgether.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileCopy {

	public void findFile(String inPath, final String outPath, final String type) {
		File file = new File(inPath);
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (final File f : files) {
				if (f.isDirectory()) {
					findFile(f.getPath(), outPath, type);
				} else {
					if (f.getName().endsWith(type)) {
						File newFile = new File(outPath + "/" + f.getName());
						if (newFile.exists()) {
							System.out.println("文件已存在" + f.getPath());
						} else {
							writeToFile(f, newFile);
							System.out.println("已复制:" + f.getPath());
						}
					} else {
						System.out.println("---------------------------------------其他文件类型:" + f.getName());
					}
				}
			}
		}
	}

	public void writeToFile(File in, File out) {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream(in);
			fos = new FileOutputStream(out);
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = fis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
			fos.close();
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		FileCopy c = new FileCopy();
		long startTime = System.currentTimeMillis();
		c.findFile("F:/gridgain-fabric/libs", "F:/gridgain-fabric/lib", ".jar");
		long endTime = System.currentTimeMillis();
		System.out.println("系统总耗时:" + (endTime - startTime) + "ms");
	}
}
