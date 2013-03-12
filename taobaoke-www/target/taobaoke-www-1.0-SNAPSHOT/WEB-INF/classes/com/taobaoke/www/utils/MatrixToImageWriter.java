package com.taobaoke.www.utils;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public final class MatrixToImageWriter {

	private static final int CONTENTCOLOR = 0xFF123456;
	private static final int POTCOLOR = 0xFF000000;
	private static String format = "jpg";
	private static String iconPath = "icon.jpg";

	public static void toBufferendColorQRImage(String text, int size,
			Map<EncodeHintType, ?> hints, File file) throws IOException {
		try {

			// BitMatrix bitMatrixPot = new QRCodeWriter().encode(text,
			// BarcodeFormat.QR_CODE, size, size, hints, true);

			BitMatrix bitMatrixContent = new QRCodeWriter().encode(text,
					BarcodeFormat.QR_CODE, size, size, hints);
			BufferedImage image = toBufferedColorImage(bitMatrixContent);
			if (!ImageIO.write(image, format, file)) {
				throw new IOException("Could not write an image of format "
						+ file);
			}
		} catch (WriterException e) {
			e.printStackTrace();
		}
	}

	public static void toBufferendColorQRImage(String text, int size,
			Map<EncodeHintType, ?> hints, OutputStream stream)
			throws IOException {
		try {
			// BitMatrix bitMatrixPot = new QRCodeWriter().encode(text,
			// BarcodeFormat.QR_CODE, size, size, hints, true);
			BitMatrix bitMatrixContent = new QRCodeWriter().encode(text,
					BarcodeFormat.QR_CODE, size, size, hints);
			BufferedImage image = toBufferedColorImage(bitMatrixContent);
			if (!ImageIO.write(image, format, stream)) {
				throw new IOException("Could not write stream ");
			}
		} catch (WriterException e) {
			e.printStackTrace();
		}
	}

	public static boolean isExistIcon() {
		URL fileUrl = Thread.currentThread().getContextClassLoader()
				.getResource(iconPath);

		try {
			File file = new File(fileUrl.toURI());
			return file.exists();
		} catch (URISyntaxException e) {

			e.printStackTrace();
		}
		return false;
	}

	public static BufferedImage zoomIconToSize(int size) throws Exception {
		URL fileUrl = Thread.currentThread().getContextClassLoader()
				.getResource(iconPath);
		System.out.println("fileUrl=" + fileUrl);
		File file = new File(fileUrl.toURI());
		BufferedImage originalImage = null;
		try {
			originalImage = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (originalImage == null)
			return null;
		BufferedImage newImage = new BufferedImage(size, size,
				originalImage.getType());
		Graphics g = newImage.getGraphics();
		g.drawImage(originalImage, 0, 0, size, size, null);
		g.dispose();
		return newImage;
	}

	public static BufferedImage toBufferedColorImage(BitMatrix matrixContent) {
		int width = matrixContent.getWidth();
		int height = matrixContent.getHeight();
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		boolean isExistIcon = isExistIcon();
		BufferedImage nBufferedImage = null;
		int halfSize = (int) (width * 0.1);
		// if(halfSize>36)
		// {
		// halfSize=36;
		// }
		int iconPadding = 10;
		if (isExistIcon) {
			iconPadding = (int) (halfSize * 0.1);
			if (iconPadding > 8)// 空白区域最大为8
				iconPadding = 8;
			halfSize = halfSize - iconPadding;
			try {
				nBufferedImage = zoomIconToSize(halfSize * 2);
				if (nBufferedImage == null) {
					isExistIcon = false;
				}
			} catch (Exception e) {
				isExistIcon = false;
				e.printStackTrace();
			}
		}
		int ceter = (int) (width * 0.5);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (isExistIcon && Math.abs(x - ceter) < halfSize + iconPadding
						&& Math.abs(y - ceter) < halfSize + iconPadding) {

					if (Math.abs(x - ceter) < halfSize
							&& Math.abs(y - ceter) < halfSize) {
						int nx, ny;
						nx = x - ceter + halfSize - 1;
						ny = y - ceter + halfSize - 1;
						image.setRGB(x, y, nBufferedImage.getRGB(nx, ny));
					} else {
						image.setRGB(x, y, 0xFFFFFFFF);
					}
				} else {
					image.setRGB(x, y, matrixContent.get(x, y) ? POTCOLOR
							: 0xFFFFFFFF);

				}

			}
		}
		nBufferedImage = null;
		return image;
	}

	public static BufferedImage toBufferedColorImage(BitMatrix matrixPot,
			BitMatrix matrixContent) {
		int width = matrixContent.getWidth();
		int height = matrixContent.getHeight();
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		boolean isExistIcon = isExistIcon();
		BufferedImage nBufferedImage = null;
		int halfSize = (int) (width * 0.1);
		// if(halfSize>36)
		// {
		// halfSize=36;
		// }
		int iconPadding = 10;
		if (isExistIcon) {
			iconPadding = (int) (halfSize * 0.1);
			if (iconPadding > 8)// 空白区域最大为8
				iconPadding = 8;
			halfSize = halfSize - iconPadding;
			try {
				nBufferedImage = zoomIconToSize(halfSize * 2);
				if (nBufferedImage == null) {
					isExistIcon = false;
				}
			} catch (Exception e) {
				isExistIcon = false;
				e.printStackTrace();
			}
		}
		int ceter = (int) (width * 0.5);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (isExistIcon && Math.abs(x - ceter) < halfSize + iconPadding
						&& Math.abs(y - ceter) < halfSize + iconPadding) {

					if (Math.abs(x - ceter) < halfSize
							&& Math.abs(y - ceter) < halfSize) {
						int nx, ny;
						nx = x - ceter + halfSize - 1;
						ny = y - ceter + halfSize - 1;
						image.setRGB(x, y, nBufferedImage.getRGB(nx, ny));
					} else {
						image.setRGB(x, y, 0xFFFFFFFF);
					}
				} else {
					image.setRGB(
							x,
							y,
							matrixContent.get(x, y) ? (matrixPot.get(x, y) ? POTCOLOR
									: CONTENTCOLOR)
									: 0xFFFFFFFF);

				}

			}
		}
		nBufferedImage = null;
		return image;
	}

	public static void writeColorQRToStream(BitMatrix matrix, String format,
			OutputStream stream) throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, stream)) {
			throw new IOException("Could not write an image of format "
					+ format);
		}
	}

	public static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFF);
			}
		}
		return image;
	}

	public static void writeToFile(String text, int size, File file,
			Map<EncodeHintType, ?> hints) throws IOException {

		try {
			BitMatrix matrix = new QRCodeWriter().encode(text,
					BarcodeFormat.QR_CODE, size, size, hints);
			BufferedImage image = toBufferedImage(matrix);
			if (!ImageIO.write(image, format, file)) {
				throw new IOException("Could not write an image of format "
						+ format + " to " + file);
			}

		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void writeToStream(String text, int size,
			Map<EncodeHintType, ?> hints, OutputStream stream)
			throws IOException {

		try {
			BitMatrix matrix = new QRCodeWriter().encode(text,
					BarcodeFormat.QR_CODE, size, size, hints);
			BufferedImage image = toBufferedImage(matrix);
			if (!ImageIO.write(image, format, stream)) {
				throw new IOException("Could not write an image of format "
						+ format);
			}
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}