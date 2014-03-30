package de.canberk.uni.cd_aap.util;

import java.io.ByteArrayOutputStream;

import de.canberk.uni.cd_aap.R;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;

public class CoverUtil {

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
	
	// create a default item cover placeholder
	public static Bitmap createDefaultCover() {
		Bitmap bitmap = null;
		
		try {
			bitmap = Bitmap.createBitmap(48, 72, Bitmap.Config.ARGB_8888);
			bitmap = BitmapFactory.decodeResource(AppContextUtil.getContext()
					.getResources(), R.drawable.cover_placeholder);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		return bitmap;
	}

}