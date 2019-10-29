package br.com.agibank.importfiles.utils;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class DataHolder {

	private static Map<String, WeakReference<Object>> data = new HashMap<String, WeakReference<Object>>();

	public static void save(String id, Object object) {

		data.put(id, new WeakReference<Object>(object));

	}

	public static Object retrieve(String id) {
		WeakReference<Object> objectWeakReference = data.get(id);

		return objectWeakReference.get();
	}
	
}
