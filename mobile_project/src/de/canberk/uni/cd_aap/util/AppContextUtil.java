package de.canberk.uni.cd_aap.util;

import android.content.Context;

public class AppContextUtil {

	private static Context context;
	
	public static Context getContext() {
		return context;
	}

	public static void setContext(Context appContext) {
		if (context == null)
			context = appContext;
	}	

}