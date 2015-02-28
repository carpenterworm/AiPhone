package net.onefree.aiphone.utils;

import java.io.File;

import android.os.Environment;
import android.os.StatFs;

public class SDCardUtils {

	/**
	 * 外部存储是否可用 (存在且具有读写权限)
	 * 
	 * @return
	 */
	public static boolean isExternalStorageAvailable() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

	/**
	 * 
	 * 获取手机内部可用空间大小
	 * 
	 * @return
	 */

	public static long getAvailableInternalMemorySize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return availableBlocks * blockSize;
	}

	/**
	 * 
	 * 获取手机内部空间大小
	 * 
	 * @return
	 */

	public static long getTotalInternalMemorySize() {
		File path = Environment.getDataDirectory();// Gets the Android data
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize(); // 每个block 占字节数
		long totalBlocks = stat.getBlockCount(); // block总数
		return totalBlocks * blockSize;
	}

	/**
	 * 
	 * 获取手机外部可用空间大小
	 * 
	 * @return
	 */

	public static long getAvailableExternalMemorySize() {

		if (isExternalStorageAvailable()) {
			File path = Environment.getExternalStorageDirectory();// 获取SDCard根目录
			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			long availableBlocks = stat.getAvailableBlocks();
			return availableBlocks * blockSize;
		} else {
			return -1;
		}

	}

	/**
	 * 
	 * 获取手机外部总空间大小
	 * 
	 * @return
	 */

	public static long getTotalExternalMemorySize() {

		if (isExternalStorageAvailable()) {
			File path = Environment.getExternalStorageDirectory(); // 获取SDCard根目录
			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			long totalBlocks = stat.getBlockCount();
			return totalBlocks * blockSize;
		} else {
			return -1;
		}
	}
}
