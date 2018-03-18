package com.coralc.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Base64;

public class PhotoUtil {
	/*
	 * Créer un bitmap ajusté à la taille voulue et dans le sens portrait
	 */
	public static Bitmap createAdjustedBitmap(String pictPath, int targetW, int targetH) {
		/* Get the size of the image */
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		bmOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(pictPath, bmOptions);
		int photoW = bmOptions.outWidth;
		int photoH = bmOptions.outHeight;

		/* Figure out which way needs to be reduced less */
		int scaleFactor = 1;
		if ((targetW > 0) || (targetH > 0)) {
			scaleFactor = Math.min(photoW / targetW, photoH / targetH);
		}

		/* Set bitmap options to scale the image decode target */
		bmOptions.inJustDecodeBounds = false;
		bmOptions.inSampleSize = scaleFactor;
		bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
		Bitmap bm = BitmapFactory.decodeFile(pictPath, bmOptions);

		/* check orientation */
		int degree = PhotoUtil.getDegree(pictPath);

		if (degree != 0) {
			// rotate the bitmap
			Matrix mtx = new Matrix();
			mtx.postRotate(degree);
			return Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), mtx, true);
		}
		return bm;
	}

	/*
	 * retourne les données d'un fichier image en une chaine base encodée en
	 * base64
	 */
	public static String getImageToBase64(File file) throws IOException {
		RandomAccessFile f = null;
		// Open file
		try {
			f = new RandomAccessFile(file, "r");

			// Get and check length
			long longlength = f.length();
			int length = (int) longlength;
			if (length != longlength)
				throw new IOException("File size >= 2 GB");
			// Read file and return data
			byte[] data = new byte[length];
			f.readFully(data);

			return Base64.encodeToString(data, Base64.DEFAULT);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} finally {
			if (f != null) {
				f.close();
			}
		}
	}

	/*
	 * retourne les données d'une image à la taille spécifiée et encodée en png
	 */
	public static byte[] getImageIconPngBytes(String imagePath, int width, int height) {
		byte[] data = null;
		Bitmap bmp = PhotoUtil.createAdjustedBitmap(imagePath, width, height);
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try {
			bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
			data = stream.toByteArray();
		} finally {
			try {
				stream.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		return data;
	}

	/*
	 * retourne un Bitmap à partir de données brutes
	 */
	public static Bitmap getBitmapFromPngBytes(byte[] data) {
		if (data == null)
			return null;
		// BitmapFactory.Options opt = new BitmapFactory.Options();
		return BitmapFactory.decodeByteArray(data, 0, data.length);
	}

	/*
	 * retourne le degree en fonction de l'attribut
	 * ExifInterface.TAG_ORIENTATION de l'image
	 */
	public static int getDegree(String pictPath) {
		ExifInterface exif;
		int degree = 0;
		try {
			exif = new ExifInterface(pictPath);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
		if (exifOrientation == 6)
			degree = 90;
		else if (exifOrientation == 3)
			degree = 180;
		else if (exifOrientation == 8)
			degree = 270;
		return degree;
	}
}
