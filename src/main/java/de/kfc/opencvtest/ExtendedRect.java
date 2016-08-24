package de.kfc.opencvtest;

import org.opencv.core.Point;
import org.opencv.core.Rect;

import java.util.ArrayList;
import java.util.List;

public class ExtendedRect extends Rect {

	private Point center;
	private double diameter;

	public ExtendedRect(){
		super();
	}

	public ExtendedRect(Rect rect) {
		super(rect.x, rect.y, rect.width, rect.height);
		this.center = calculateCenter(rect);
		this.diameter = calculateDiameter(rect);
	}

	public ExtendedRect(int x, int y, int width, int height) {
		this(new Rect(x,y,width,height));
	}

	public ExtendedRect(Point p1, Point p2) {
		this(new Rect(
				(int) (p1.x < p2.x ? p1.x : p2.x),
				(int) (p1.y < p2.y ? p1.y : p2.y),
				(int) (p1.x > p2.x ? p1.x : p2.x) - (int) (p1.x < p2.x ? p1.x : p2.x),
				(int) (p1.y > p2.y ? p1.y : p2.y) - (int) (p1.y < p2.y ? p1.y : p2.y)
		));
	}

	public Point getCenter() {
		return center;
	}

	public double getDiameter() {
		return diameter;
	}

	private Point calculateCenter(Rect rect) {
		double x = (rect.br().x + rect.tl().x) / 2;
		double y = (rect.br().y + rect.tl().y) / 2;
		return new Point(x, y);
	}

	private double calculateDiameter(Rect rect) {
		return Math.sqrt(Math.pow((rect.tl().x - this.center.x), 2) + Math.pow((rect.tl().y - this.center.y), 2));
	}

	public static List<ExtendedRect> mapToExtendRects(List<Rect> rects) {
		List<ExtendedRect> extendedRects = new ArrayList<ExtendedRect>();
		for (Rect rect : rects) {
			extendedRects.add(new ExtendedRect(rect));
		}

		return extendedRects;
	}
}