package com.google.operations;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.FileList;
import com.google.configure.ConfigureAccess;

public class DriveFile {
	static Drive service;
	static{
		try {
			service = ConfigureAccess.setup();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void getFile() throws GeneralSecurityException, IOException {
		try {
			String outFile = "C:/Users/shivs2/Downloads/output/new2.txt";
			OutputStream out = new FileOutputStream(outFile);
			service.files().get("1zcDN3BIWpL8N4SFn_eSPiDWEL5hdRXrI").executeMediaAndDownloadTo(out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void listFiles(){
		FileList result;
		try {
			result = service.files().list()
			        .setPageSize(10)
			        .setFields("nextPageToken, files(id, name)")
			        .execute();
		
        List<com.google.api.services.drive.model.File> files = result.getFiles();
        if (files == null || files.isEmpty()) {
            System.out.println("No files found.");
        } else {
            System.out.println("Files:");
            for (com.google.api.services.drive.model.File file : files) {
                System.out.printf("%s (%s)\n", file.getName(), file.getId());
            }
        }
        }
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void createFile(){
		String filePath = "C:/Users/shivs2/Downloads/output/new3.txt";
		File writeFile = new File(filePath);
		String content = "Hello world";
		List<String> parents = new ArrayList<String>();
		parents.add("1Qki4olt-wrq-8dUgdFtnSELBV7LesTd5");
		FileOutputStream fo;
		try{
			fo = new FileOutputStream(writeFile);
			fo.write(content.getBytes());
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		FileContent mediaContent = new FileContent("plain/text",writeFile);
		com.google.api.services.drive.model.File fileMetadata = new com.google.api.services.drive.model.File();
		fileMetadata.setName("newFileSDKinside1.txt");
		//setmimetype for folder
		//fileMetadata.setMimeType("application/vnd.google-apps.folder");
		fileMetadata.setParents(parents);
		
		
		com.google.api.services.drive.model.File file;
		try{
			file = service.files().create(fileMetadata, mediaContent).execute();
			//file = service.files().create(fileMetadata).execute();
			System.out.println("File ID: " + file.getId());
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}
	
	
	public static void deleteFile(){
		try {
			service.files().delete("1fAR0jDOb_x9DuLv8Vp_RsYYuuXnD7WXe").execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
		public static void main(String[] args){
		
		
		try {
			listFiles();
			getFile();
			createFile();
			deleteFile();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
