/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieulm.listener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author MinHiu
 */
public class MyContextServletListener implements ServletContextListener {

    private final String SITE_MAP_FILENAME = "/WEB-INF/siteMap.txt";
    private final String LIST_MEMBER_FUNCTIONS_FILENAME = "/WEB-INF/listMemberFunctions.txt";
    private final String LIST_ADMIN_FUNCTIONS_FILENAME = "/WEB-INF/listAdminFunctions.txt";
    private final String LIST_GUEST_FUNCTIONS_FILENAME = "/WEB-INF/listGuestFunctions.txt";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
	ServletContext context = sce.getServletContext();
	String realPath = context.getRealPath("/");
	Map<String, String> siteMap = readSiteMap(realPath + SITE_MAP_FILENAME);
	if (siteMap != null) {
	    context.setAttribute("SITE_MAP", siteMap);
	}

	List<String> listMemberFunctions = readFunctions(realPath + LIST_MEMBER_FUNCTIONS_FILENAME);
	if (listMemberFunctions != null) {
	    context.setAttribute("MEMBER_FUNCTIONS", listMemberFunctions);
	}

	List<String> listAdminFunctions = readFunctions(realPath + LIST_ADMIN_FUNCTIONS_FILENAME);
	if (listAdminFunctions != null) {
	    context.setAttribute("ADMIN_FUNCTIONS", listAdminFunctions);
	}

	List<String> listGuestFunctions = readFunctions(realPath + LIST_GUEST_FUNCTIONS_FILENAME);
	if (listGuestFunctions != null) {
	    context.setAttribute("GUEST_FUNCTIONS", listGuestFunctions);
	}
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

    private Map<String, String> readSiteMap(String filePath) {
	BufferedReader bf = null;
	FileReader fi = null;

	Map<String, String> siteMap = null;

	try {
	    fi = new FileReader(filePath);
	    bf = new BufferedReader(fi);
	    while (bf.ready()) {
		String line = bf.readLine();
		String[] map = line.split("=");
		if (map.length == 2) {
		    if (siteMap == null) {
			siteMap = new HashMap<>();
		    }

		    siteMap.put(map[0], map[1]);
		}
	    }
	} catch (FileNotFoundException ex) {
	    Logger.getLogger(MyContextServletListener.class.getName()).log(Level.SEVERE, null, ex);
	} catch (IOException ex) {
	    Logger.getLogger(MyContextServletListener.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
	    try {
		if (bf != null) {
		    bf.close();
		}
		if (fi != null) {
		    fi.close();
		}
	    } catch (IOException ex) {
		Logger.getLogger(MyContextServletListener.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
	return siteMap;
    }

    private List<String> readFunctions(String filePath) {
	FileReader fi = null;
	BufferedReader bf = null;

	List<String> listFunctions = null;

	try {
	    fi = new FileReader(filePath);
	    bf = new BufferedReader(fi);
	    while (bf.ready()) {
		if (listFunctions == null) {
		    listFunctions = new ArrayList<>();
		}

		listFunctions.add(bf.readLine());
	    }
	} catch (FileNotFoundException ex) {
	    Logger.getLogger(MyContextServletListener.class.getName()).log(Level.SEVERE, null, ex);
	} catch (IOException ex) {
	    Logger.getLogger(MyContextServletListener.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
	    try {
		if (bf != null) {
		    bf.close();
		}
		if (fi != null) {
		    fi.close();
		}
	    } catch (IOException ex) {
		Logger.getLogger(MyContextServletListener.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}

	return listFunctions;
    }
}
