package com.zwh.photoframe;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Observable;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

public class FileUtils extends Observable {
	public static final int ONCE_READ = 4 * 1024;
	private String SDPATH;
	//	private String TEMP_PATH;

	/**
	 * 1、判断SD卡是否存在
	 */
	public static boolean hasSdcard() {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	public String getSDPATH() {
		return SDPATH;
	}

	/*	public String getTmpPath() {
		return TEMP_PATH;
	}*/

	public FileUtils() {
		// 得到当前外部存储设备的目录
		// /SDCARD
		SDPATH = Environment.getExternalStorageDirectory() + "/DCIM/";
		//		TEMP_PATH = SDPATH + "temp/";
		/*		Log.e("pafee", "SDPATH:"+SDPATH);
		Log.e("pafee", "createSDDirs:"+TEMP_PATH);
		Log.e("pafee", "createSDDirs:"+createSDDirs("temp/"));*/
	}

	/**
	 * 在SD卡上创建文件
	 * 
	 * @throws IOException
	 */
	public File createSDFile(String fileName) throws IOException {
		File file = new File(SDPATH + fileName);
		try {
			file.createNewFile();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return file;
	}

	/**
	 * 在SD卡上创建目录
	 * 
	 * @param dirName
	 */
	public File createSDDir(String dirName) {
		File dir = new File(SDPATH + dirName);
		dir.mkdir();
		return dir;
	}

	/**
	 * 在SD卡上创建多级目录
	 */
	public File createSDDirs(String dirName) {
		File dir = new File(SDPATH + dirName);
		dir.mkdirs();
		return dir;
	}

	/**
	 * 判断SD卡上的文件夹是否存在
	 */
	public boolean isFileExist(String fileName) {
		File file = new File(SDPATH + fileName);
		return file.exists();
	}

	/**
	 * 将一个InputStream里面的数据写入到SD卡中
	 */
	public File write2SDFromInput(String path, String fileName,
			InputStream input) {
		File file = null;
		OutputStream output = null;
		try {
			createSDDirs(path);
			file = createSDFile(path + fileName);
			output = new FileOutputStream(file);
			int byteRead = 0;
			byte buffer[] = new byte[ONCE_READ];
			while ((byteRead = input.read(buffer, 0, ONCE_READ)) != -1) {
				if (this.countObservers() > 0) {
					this.notifyObservers(ONCE_READ);
				}
				output.write(buffer, 0, byteRead);
			}
			output.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
				input.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	public void delFile(String path, String fileName) {
		// TODO Auto-generated method stub
		File file = new File(SDPATH + path + fileName);
		file.delete();
	}

	public boolean clearImageCache(){
		File file = new File(SDPATH);
		deleteFile(file);
		return true;
	}

	public void deleteFile(File file)  
	{  
		File[] temp = file.listFiles();  
		for(int i=0;i<temp.length;i++)  
		{  
			System.out.println(temp[i].getName());  
			if(temp[i].isDirectory())  
			{  
				if(temp[i].listFiles().length!=0)
					this.deleteFile(temp[i]);  
				this.deleteDir(temp[i]);  
			}else  
			{  
				temp[i].delete();  
			}  
		}  
	}  

	private boolean deleteDir(File file)  
	{  
		boolean result = false;
		if(file.listFiles().length==0){ 
			file.getAbsoluteFile().delete();
			result = true;
		}
		return result;
	} 

	public void renameFile(String oldPath, String newPath) {
		// TODO Auto-generated method stub
		File file = new File(SDPATH + oldPath);
		File f = new File(SDPATH + newPath);
		file.renameTo(f);
	}

	public String getPathInSD(String path, String fileName) {
		return SDPATH + path + fileName;
	}

	@Override
	public boolean hasChanged() {
		// TODO Auto-generated method stub
		// return super.hasChanged();
		return true;
	}

	public static byte[] getBytesFromFile(File f) {
		if (f == null) {
			return null;
		}
		try {
			FileInputStream stream = new FileInputStream(f);
			ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = stream.read(b)) != -1)
				out.write(b, 0, n);
			stream.close();
			out.close();
			return out.toByteArray();
		} catch (IOException e) {
		}
		return null;
	}

	public static File getFileFromBytes(byte[] b, String outputFile) {
		BufferedOutputStream stream = null;
		File file = null;
		try {
			file = new File(outputFile);
			FileOutputStream fstream = new FileOutputStream(file);
			stream = new BufferedOutputStream(fstream);
			stream.write(b);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return file;
	}

	public void inputstreamtofile(InputStream ins, File file) {
		OutputStream os = null;
		try {
			os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				os.close();
				ins.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public String fileToString(String fileName) {
		File file = new File( SDPATH + fileName );
		byte[] by = null;
		try {
			by = getBytesFromFile(file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String img = null;
		try {
			img = new String(by, "ISO-8859-1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			img = null;
			e.printStackTrace();
		}
		return img;
	}

	/** 
	 * 保存文件 
	 * @param bm 
	 * @param fileName 
	 * @throws IOException 
	 */  
	public void saveFile(Bitmap bm, String fileName){  
		try {
			File dirFile = new File(SDPATH);  
			if(!dirFile.exists()){  
				dirFile.mkdir();  
			}  
			File myCaptureFile = new File(SDPATH + fileName);  
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));  
			bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);  

			bos.flush();
			bos.close(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  

	}  

	/**
	 * 保存bitmap为本地文件
	 */
	public void savePhotoBitmap(Bitmap photo,String fileName) {
		String pathfile = SDPATH+fileName;
		Log.e("savePhotoBitmap", " :"+SDPATH+" :"+pathfile);
		int end = fileName.lastIndexOf("/");

		if( end > 0){
			createSDDirs(fileName.substring(0, end));
		}

		File file = new File(pathfile);
		try {
			file.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		photo.compress(Bitmap.CompressFormat.PNG, 60, fOut);
		try {
			fOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}