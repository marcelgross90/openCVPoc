package de.kfc.opencvtest;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by marcelgross on 17.08.16.
 */
public class Contour {

	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		//String pathToImage = Contour.class.getClass().getResource("/Zahnpasta.png").getPath();
		//String pathToImage = Contour.class.getClass().getResource("/LKG-Emotions-1.png").getPath();
		//String pathToImage = Contour.class.getClass().getResource("/Insight-big.png").getPath();
		//String pathToImage = Contour.class.getClass().getResource("/pap.png").getPath();
		//String pathToImage = Contour.class.getClass().getResource("/lind.png").getPath();
		//String pathToImage = Contour.class.getClass().getResource("/Came.png").getPath();
		//String pathToImage = Contour.class.getClass().getResource("/lande.png").getPath();
		String pathToImage = Contour.class.getClass().getResource("/card.png").getPath();

		new DetectContour(pathToImage).run();
	}
}

class DetectContour {

	private final List<MatOfPoint> contours;
	private final Mat hierarchyMat;

	private Mat orgImg;

	public DetectContour(String pathToImage){
		this.contours = new ArrayList<MatOfPoint>();
		this.hierarchyMat = new Mat();
		this.orgImg = Imgcodecs.imread(pathToImage, Imgcodecs.IMREAD_UNCHANGED);
	}

	public void run() {
		Mat image = new Mat();
		Imgproc.cvtColor(orgImg, image, Imgproc.COLOR_RGB2GRAY);
		Scalar color = new Scalar(0, 255, 0);

		Imgproc.threshold(image, image, 50, 100, Imgproc.THRESH_OTSU);
		Imgcodecs.imwrite("result_contour_threshold.png", image);
		Imgproc.findContours(image.clone(), contours, hierarchyMat, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_NONE);

		if (contours.size() < 1) {
			System.out.println("no contours");
			return;
		}

		Imgproc.cvtColor(image, image, Imgproc.COLOR_GRAY2BGR);

		printClustered(contours, orgImg.clone(), color);

		//for test propose
		printAll(contours, orgImg.clone(), color);
	}

	private void printClustered(List<MatOfPoint> contours, Mat orgImg, Scalar color) {
		//size which avoid that a frame around the whole image is printed
//todo discuss to let max. area size in percent of org. image choose by user
		double maxAcceptedSize = ((orgImg.size().height * orgImg.size().width) * 99) / 100;
		//distance between the middlePoints of Rects, as higher it is as more will be combined
//todo discuss to let distance in percent of org. image choose by user
		double maxAcceptedDistance = (5 * orgImg.size().width) / 100;

		List<Rect> rects = new ArrayList<Rect>();
		for (MatOfPoint contour : contours) {

			rects.add(Imgproc.boundingRect(contour));
		}
		rects.sort(new Comparator<Rect>() {
			public int compare(Rect o1, Rect o2) {
				return o1.area() < o2.area() ? +1 : o1.area() > o2.area() ? -1 : 0;
			}
		});

		for (Rect rect : combine(rects, maxAcceptedDistance, maxAcceptedSize)) {
//todo discuss to let min. area size choose by user
			if (rect.area() > 10) {
				Imgproc.rectangle(orgImg, rect.tl(), rect.br(), color);
			}
		}

		Imgcodecs.imwrite("result_contour_clustered.png", orgImg);

	}

	private List<ExtendedRect> combine(List<Rect> rects, double maxAcceptedDistance, double maxAcceptedArea) {
		List<ExtendedRect> allRects = ExtendedRect.mapToExtendRects(rects);
		for (int i = 0; i < allRects.size(); i++) {
			if (allRects.get(i).area() > maxAcceptedArea) {
				allRects.remove(i);
				i = 0;
			} else {
				for (int j = 0; j < allRects.size(); j++) {
					if (mustCombine(allRects.get(i), allRects.get(j), maxAcceptedDistance)) {
						allRects.set(i, combineRects(allRects.get(i), allRects.get(j)));
						allRects.remove(j);
						i = 0;
						break;
					}
				}
			}
		}
		return allRects;
	}

	private double calculateDistance(Point a, Point b) {
		double result = Math.sqrt(Math.pow((a.x - b.x), 2) + Math.pow((a.y - b.y), 2));
		return result;
	}

	private boolean mustCombine(ExtendedRect a, ExtendedRect b, double maxAcceptedDistance) {
		return combineBecauseOfDistance(a, b, maxAcceptedDistance) || combineBecauseOfOverlap(a, b);
	}

	private boolean combineBecauseOfDistance(ExtendedRect a, ExtendedRect b, double maxAcceptedDistance) {
		double distanceBetweenCenters = calculateDistance(a.getCenter(), b.getCenter());
		return distanceBetweenCenters <= maxAcceptedDistance && distanceBetweenCenters != 0;
	}

	private boolean combineBecauseOfOverlap(ExtendedRect a, ExtendedRect b) {
		boolean notSame = a.hashCode() != b.hashCode();
		return a.x < b.x + b.width && a.x + a.width > b.x && a.y < b.y + b.height && a.y + a.height > b.y && notSame;
	}

	private ExtendedRect combineRects(Rect a, Rect b) {
		return new ExtendedRect(new Point(Math.max(a.br().x, b.br().x), Math.max(a.br().y, b.br().y)),
				new Point(Math.min(a.tl().x, b.tl().x), Math.min(a.tl().y, b.tl().y)));
	}

	/**
	 * Just for testing propose
	 */
	private void printAll(List<MatOfPoint> contours, Mat orgImg, Scalar color) {
		for (MatOfPoint contour : contours) {

			Rect bounds = Imgproc.boundingRect(contour);
			Imgproc.rectangle(orgImg, bounds.tl(), bounds.br(), color);

		}

		Imgcodecs.imwrite("result_contour_all.png", orgImg);
	}
}