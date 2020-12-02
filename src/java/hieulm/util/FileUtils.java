/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieulm.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.http.Part;

/**
 *
 * @author MinHiu
 */
public class FileUtils {

    public static void copyFileToServer(Part imageSelected, String filePath) throws IOException {
	InputStream is = null;
	OutputStream os = null;

	try {
	    File uploadImage = new File(filePath);

	    is = imageSelected.getInputStream();
	    os = new FileOutputStream(uploadImage);

	    byte[] buffer = new byte[4096];
	    int bytesRead = -1;
	    while ((bytesRead = is.read(buffer)) != -1) {
		os.write(buffer, 0, bytesRead);
	    }
	} finally {
	    if (is != null) {
		is.close();
	    }
	    if (os != null) {
		os.close();
	    }
	}
    }
}
