package co.com.tactusoft.crm.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

public class GlobalSession {
	public static Map<String, HttpSession> httpSessionMap = new HashMap<String, HttpSession>();

}
