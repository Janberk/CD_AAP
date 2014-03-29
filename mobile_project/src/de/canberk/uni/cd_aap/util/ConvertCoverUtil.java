package de.canberk.uni.cd_aap.util;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;

public class ConvertCoverUtil {

	// convert from bitmap to byte array
	public static byte[] getByteArray(Bitmap bitmap) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 0, stream);
		
		return stream.toByteArray();
	}

	// convert from byte array to bitmap
	public static Bitmap getBitmap(byte[] image) {
		return BitmapFactory.decodeByteArray(image, 0, image.length);
	}

}