package zippedFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import connector.DBConnector;
import model.ActionType;
import model.InteractionLog;

public class ZipFiles {
	
	public static void zipFiles(){
		String filesPath = "src/data";
		File folder = new File(filesPath);
		File[] sourceFiles = folder.listFiles();
		try{
			 String zipFile = filesPath+"\\zipdemo.zip";
			 byte[] buffer = new byte[1024];
			 
			 FileOutputStream fout = new FileOutputStream(zipFile);
			 ZipOutputStream zout = new ZipOutputStream(fout);
			 
			 for(int i=0; i < sourceFiles.length; i++)
			 {
			 
			 FileInputStream fin = new FileInputStream(filesPath+"\\"+sourceFiles[i].getName());		 
			 zout.putNextEntry(new ZipEntry(filesPath+"\\"+sourceFiles[i].getName()));
			 
			 int length;
			 
			 while((length = fin.read(buffer)) > 0)
			 {
			    zout.write(buffer, 0, length);
			 }
			 
			 zout.closeEntry();
			 fin.close();
		 
		 }
		 
		 zout.finish();
		 zout.close();
		 DBConnector.logZipInteraction(new InteractionLog(LocalDateTime.now(), ActionType.zipping));
		 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
}
