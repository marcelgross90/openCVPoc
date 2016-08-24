package de.kfc.opencvtest;

import org.opencv.core.Point;
import org.opencv.core.Rect;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marcelgross on 22.08.16.
 */
public class Tester {

	/**
	 * Expacted result [{2,2,6,6},{10,2,2,2}]
	 */

	public static void main(String[] args) {
		List<Rect> input = new ArrayList<Rect>();
		input.add(new Rect(2,2,4,4));
		input.add(new Rect(4,3,4,5));
		input.add(new Rect(10,2,2,2));

		List<ExtendedRect> extendedRects = combine(input, 4);

		System.out.println(extendedRects.size());
		System.out.println(extendedRects.toString());


		List<ExtendedRect> overlapped = combineOverlapped(ExtendedRect.mapToExtendRects(input));
		System.out.println(overlapped.size());
		System.out.println(overlapped.toString());

	}


	private static List<ExtendedRect> combineOverlapped(List<ExtendedRect> allRects) {
		for (int i = 0; i < allRects.size(); i++) {
			for (int j = 0; j < allRects.size(); j++) {
				if (combineBecauseOfOverlap(allRects.get(i), allRects.get(j))) {
					allRects.set(i, combineRects(allRects.get(i), allRects.get(j)));
					allRects.remove(j);
					i = 0;
					break;
				}
			}
		}
		return allRects;
	}


	private static boolean combineBecauseOfOverlap(ExtendedRect a, ExtendedRect b){
		boolean notSame = a.hashCode() != b.hashCode();
		return a.x < b.x + b.width && a.x + a.width > b.x && a.y < b.y + b.height && a.y + a.height > b.y && notSame;
	}

	private static List<ExtendedRect> combine(List<Rect> rects, double maxAcceptedDistance) {
		List<ExtendedRect> allRects = ExtendedRect.mapToExtendRects(rects);

		for (int i = 0; i < allRects.size(); i++) {
			for (int j = 0; j < allRects.size(); j++) {
				if (mustCombine(allRects.get(i), allRects.get(j), maxAcceptedDistance)) {
					allRects.set(i, combineRects(allRects.get(i), allRects.get(j)));
					allRects.remove(j);
					i = 0;
					break;
				}
			}
		}

		return allRects;
	}

	private static double calculateDistance(Point a, Point b) {
		double result =  Math.sqrt(Math.pow((a.x - b.x), 2) + Math.pow((a.y - b.y), 2));
		return result;
	}

	private static boolean mustCombine(ExtendedRect a, ExtendedRect b, double maxAcceptedDistance) {
		double distanceBetweenCenters = calculateDistance(a.getCenter(), b.getCenter());
		return distanceBetweenCenters <= maxAcceptedDistance && distanceBetweenCenters != 0;
	}

	private static ExtendedRect combineRects(Rect a, Rect b) {
		return new ExtendedRect(
				new Point(
						Math.max(a.br().x, b.br().x),
						Math.max(a.br().y, b.br().y)),
				new Point(
						Math.min(a.tl().x, b.tl().x),
						Math.min(a.tl().y, b.tl().y)));
	}

	private List<ExtendedRect> initRects() {
		List<ExtendedRect> result = new ArrayList<ExtendedRect>();
		result.add(new ExtendedRect(296, 380, 31,30));
		result.add(new ExtendedRect(531, 206, 31,30));
		result.add(new ExtendedRect(296, 206, 31,30));
		result.add(new ExtendedRect(767, 380, 30,30));
		result.add(new ExtendedRect(61, 380, 30,30));
		result.add(new ExtendedRect(767, 206, 30,30));
		result.add(new ExtendedRect(61, 206, 30,30));
		result.add(new ExtendedRect(313, 423, 22,21));
		result.add(new ExtendedRect(313, 249, 22,21));
		result.add(new ExtendedRect(784, 423, 21,21));
		result.add(new ExtendedRect(549, 423, 21,21));
		result.add(new ExtendedRect(78, 423, 21,21));
		result.add(new ExtendedRect(784, 249, 21,21));
		result.add(new ExtendedRect(549, 249, 21,21));
		result.add(new ExtendedRect(78, 249, 21,21));
		result.add(new ExtendedRect(314, 475, 20,17));
		result.add(new ExtendedRect(314, 301, 20,17));
		result.add(new ExtendedRect(133, 429, 20,11));
		result.add(new ExtendedRect(133, 255, 20,11));
		result.add(new ExtendedRect(784, 304, 21,17));
		result.add(new ExtendedRect(836, 478, 17,11));
		result.add(new ExtendedRect(833, 304, 20,11));
		result.add(new ExtendedRect(385, 479, 18,10));
		result.add(new ExtendedRect(384, 454, 20,11));
		result.add(new ExtendedRect(385, 305, 18,10));
		result.add(new ExtendedRect(384, 280, 20,11));
		result.add(new ExtendedRect(124, 478, 23,14));
		result.add(new ExtendedRect(867, 429, 16,11));
		result.add(new ExtendedRect(124, 304, 23,14));
		result.add(new ExtendedRect(867, 255, 16,11));
		result.add(new ExtendedRect(531, 380, 31,30));
		result.add(new ExtendedRect(869, 454, 15,11));
		result.add(new ExtendedRect(163, 454, 15,11));
		result.add(new ExtendedRect(622, 304, 20,11));
		result.add(new ExtendedRect(869, 280, 15,11));
		result.add(new ExtendedRect(163, 280, 15,11));
		result.add(new ExtendedRect(784, 478, 21,17));
		result.add(new ExtendedRect(549, 302, 21,16));
		result.add(new ExtendedRect(591, 478, 18,14));
		result.add(new ExtendedRect(581, 478, 11,11));
		result.add(new ExtendedRect(362, 478, 21,11));
		result.add(new ExtendedRect(581, 304, 21,14));
		result.add(new ExtendedRect(359, 304, 24,14));
		result.add(new ExtendedRect(622, 478, 20,11));
		result.add(new ExtendedRect(549, 476, 21,16));
		result.add(new ExtendedRect(881, 481, 21,8));
		result.add(new ExtendedRect(881, 307, 21,8));
		result.add(new ExtendedRect(855, 478, 19,11));
		result.add(new ExtendedRect(608, 478, 16,11));
		result.add(new ExtendedRect(111, 478, 14,11));
		result.add(new ExtendedRect(855, 454, 15,11));
		result.add(new ExtendedRect(149, 454, 15,11));
		result.add(new ExtendedRect(618, 429, 20,11));
		result.add(new ExtendedRect(581, 429, 20,11));
		result.add(new ExtendedRect(383, 429, 20,11));
		result.add(new ExtendedRect(152, 429, 22,11));
		result.add(new ExtendedRect(855, 304, 19,11));
		result.add(new ExtendedRect(601, 304, 17,11));
		result.add(new ExtendedRect(111, 304, 14,11));
		result.add(new ExtendedRect(855, 280, 15,11));
		result.add(new ExtendedRect(149, 280, 15,11));
		result.add(new ExtendedRect(618, 255, 20,11));
		result.add(new ExtendedRect(581, 255, 20,11));
		result.add(new ExtendedRect(383, 255, 20,11));
		result.add(new ExtendedRect(152, 255, 22,11));
		result.add(new ExtendedRect(369, 454, 16,11));
		result.add(new ExtendedRect(858, 429, 10,11));
		result.add(new ExtendedRect(817, 429, 19,11));
		result.add(new ExtendedRect(603, 429, 16,11));
		result.add(new ExtendedRect(111, 429, 19,11));
		result.add(new ExtendedRect(858, 255, 10,11));
		result.add(new ExtendedRect(817, 255, 19,11));
		result.add(new ExtendedRect(603, 255, 16,11));
		result.add(new ExtendedRect(111, 255, 19,11));
		result.add(new ExtendedRect(151, 478, 20,11));
		result.add(new ExtendedRect(151, 304, 20,11));
		result.add(new ExtendedRect(647, 432, 17,8));
		result.add(new ExtendedRect(647, 258, 17,8));
		result.add(new ExtendedRect(624, 279, 22,12));
		result.add(new ExtendedRect(840, 454, 16,11));
		result.add(new ExtendedRect(134, 454, 16,11));
		result.add(new ExtendedRect(368, 429, 16,11));
		result.add(new ExtendedRect(368, 255, 16,11));
		result.add(new ExtendedRect(641, 481, 22,8));
		result.add(new ExtendedRect(175, 481, 21,8));
		result.add(new ExtendedRect(346, 478, 21,14));
		result.add(new ExtendedRect(402, 454, 18,11));
		result.add(new ExtendedRect(636, 429, 12,11));
		result.add(new ExtendedRect(401, 429, 20,11));
		result.add(new ExtendedRect(641, 307, 22,8));
		result.add(new ExtendedRect(175, 307, 21,8));
		result.add(new ExtendedRect(346, 304, 14,11));
		result.add(new ExtendedRect(402, 280, 18,11));
		result.add(new ExtendedRect(636, 255, 12,11));
		result.add(new ExtendedRect(401, 255, 20,11));
		result.add(new ExtendedRect(624, 453, 21,12));
		result.add(new ExtendedRect(838, 429, 21,11));
		result.add(new ExtendedRect(443, 392, 19,7));
		result.add(new ExtendedRect(208, 392, 19,7));
		result.add(new ExtendedRect(838, 255, 21,11));
		result.add(new ExtendedRect(443, 218, 19,7));
		result.add(new ExtendedRect(208, 218, 19,7));
		result.add(new ExtendedRect(817, 478, 20,14));
		result.add(new ExtendedRect(78, 478, 21,17));
		result.add(new ExtendedRect(817, 453, 20,12));
		result.add(new ExtendedRect(581, 454, 17,11));
		result.add(new ExtendedRect(346, 454, 20,11));
		result.add(new ExtendedRect(111, 453, 20,12));
		result.add(new ExtendedRect(346, 430, 20,10));
		result.add(new ExtendedRect(817, 304, 20,14));
		result.add(new ExtendedRect(78, 304, 21,17));
		result.add(new ExtendedRect(840, 280, 16,11));
		result.add(new ExtendedRect(134, 280, 16,11));
		result.add(new ExtendedRect(817, 279, 20,12));
		result.add(new ExtendedRect(581, 280, 17,11));
		result.add(new ExtendedRect(346, 280, 20,11));
		result.add(new ExtendedRect(111, 279, 20,12));
		result.add(new ExtendedRect(346, 256, 20,10));
		result.add(new ExtendedRect(914, 392, 19,7));
		result.add(new ExtendedRect(686, 392, 19,7));
		result.add(new ExtendedRect(914, 218, 19,7));
		result.add(new ExtendedRect(686, 218, 19,7));
		result.add(new ExtendedRect(604, 454, 17,11));
		result.add(new ExtendedRect(617, 390, 19,11));
		result.add(new ExtendedRect(371, 392, 20,9));
		result.add(new ExtendedRect(604, 280, 17,11));
		result.add(new ExtendedRect(617, 216, 19,11));
		result.add(new ExtendedRect(371, 218, 20,9));
		result.add(new ExtendedRect(369, 280, 16,11));
		result.add(new ExtendedRect(655, 457, 17,8));
		result.add(new ExtendedRect(419, 456, 18,9));
		result.add(new ExtendedRect(818, 391, 21,10));
		result.add(new ExtendedRect(655, 283, 17,8));
		result.add(new ExtendedRect(419, 282, 18,9));
		result.add(new ExtendedRect(818, 217, 21,10));
		result.add(new ExtendedRect(581, 390, 17,11));
		result.add(new ExtendedRect(348, 390, 20,11));
		result.add(new ExtendedRect(581, 216, 17,11));
		result.add(new ExtendedRect(348, 216, 20,11));
		result.add(new ExtendedRect(406, 481, 21,8));
		result.add(new ExtendedRect(890, 456, 17,9));
		result.add(new ExtendedRect(645, 454, 11,10));
		result.add(new ExtendedRect(178, 456, 17,9));
		result.add(new ExtendedRect(784, 449, 21,20));
		result.add(new ExtendedRect(889, 432, 17,8));
		result.add(new ExtendedRect(882, 433, 8,7));
		result.add(new ExtendedRect(663, 433, 13,7));
		result.add(new ExtendedRect(183, 432, 17,8));
		result.add(new ExtendedRect(173, 429, 11,11));
		result.add(new ExtendedRect(854, 391, 22,9));
		result.add(new ExtendedRect(597, 392, 22,9));
		result.add(new ExtendedRect(143, 392, 20,8));
		result.add(new ExtendedRect(841, 392, 14,9));
		result.add(new ExtendedRect(666, 392, 21,7));
		result.add(new ExtendedRect(564, 390, 17,9));
		result.add(new ExtendedRect(391, 390, 19,10));
		result.add(new ExtendedRect(126, 391, 17,10));
		result.add(new ExtendedRect(108, 390, 19,11));
		result.add(new ExtendedRect(895, 391, 20,10));
		result.add(new ExtendedRect(189, 391, 20,10));
		result.add(new ExtendedRect(406, 307, 21,8));
		result.add(new ExtendedRect(890, 282, 17,9));
		result.add(new ExtendedRect(645, 280, 11,10));
		result.add(new ExtendedRect(178, 282, 17,9));
		result.add(new ExtendedRect(784, 275, 21,20));
		result.add(new ExtendedRect(889, 258, 17,8));
		result.add(new ExtendedRect(882, 259, 8,7));
		result.add(new ExtendedRect(663, 259, 13,7));
		result.add(new ExtendedRect(183, 258, 17,8));
		result.add(new ExtendedRect(173, 255, 11,11));
		result.add(new ExtendedRect(854, 217, 22,9));
		result.add(new ExtendedRect(143, 218, 20,8));
		result.add(new ExtendedRect(841, 218, 14,9));
		result.add(new ExtendedRect(666, 218, 21,7));
		result.add(new ExtendedRect(564, 216, 17,9));
		result.add(new ExtendedRect(391, 216, 19,10));
		result.add(new ExtendedRect(126, 217, 17,10));
		result.add(new ExtendedRect(108, 216, 19,11));
		result.add(new ExtendedRect(895, 217, 20,10));
		result.add(new ExtendedRect(884, 457, 7,7));
		result.add(new ExtendedRect(194, 457, 12,8));
		result.add(new ExtendedRect(313, 449, 22,20));
		result.add(new ExtendedRect(78, 449, 21,20));
		result.add(new ExtendedRect(418, 432, 17,8));
		result.add(new ExtendedRect(653, 392, 14,9));
		result.add(new ExtendedRect(329, 392, 19,7));
		result.add(new ExtendedRect(93, 392, 17,7));
		result.add(new ExtendedRect(884, 283, 7,7));
		result.add(new ExtendedRect(194, 283, 12,8));
		result.add(new ExtendedRect(313, 275, 22,20));
		result.add(new ExtendedRect(78, 275, 21,20));
		result.add(new ExtendedRect(418, 258, 17,8));
		result.add(new ExtendedRect(597, 218, 22,9));
		result.add(new ExtendedRect(653, 218, 14,9));
		result.add(new ExtendedRect(329, 218, 19,7));
		result.add(new ExtendedRect(93, 218, 17,7));
		result.add(new ExtendedRect(189, 217, 20,10));
		result.add(new ExtendedRect(549, 449, 21,20));
		result.add(new ExtendedRect(549, 275, 21,20));
		result.add(new ExtendedRect(873, 478, 9,11));
		result.add(new ExtendedRect(170, 481, 6,8));
		result.add(new ExtendedRect(906, 457, 6,7));
		result.add(new ExtendedRect(671, 457, 6,7));
		result.add(new ExtendedRect(436, 457, 6,7));
		result.add(new ExtendedRect(905, 433, 6,7));
		result.add(new ExtendedRect(434, 433, 6,7));
		result.add(new ExtendedRect(199, 433, 6,7));
		result.add(new ExtendedRect(876, 392, 20,7));
		result.add(new ExtendedRect(163, 392, 17,7));
		result.add(new ExtendedRect(424, 391, 20,10));
		result.add(new ExtendedRect(873, 304, 9,11));
		result.add(new ExtendedRect(170, 307, 6,8));
		result.add(new ExtendedRect(906, 283, 6,7));
		result.add(new ExtendedRect(671, 283, 6,7));
		result.add(new ExtendedRect(436, 283, 6,7));
		result.add(new ExtendedRect(905, 259, 6,7));
		result.add(new ExtendedRect(434, 259, 6,7));
		result.add(new ExtendedRect(199, 259, 6,7));
		result.add(new ExtendedRect(876, 218, 20,7));
		result.add(new ExtendedRect(163, 218, 17,7));
		result.add(new ExtendedRect(424, 217, 20,10));
		result.add(new ExtendedRect(662, 481, 5,8));
		result.add(new ExtendedRect(427, 481, 5,8));
		result.add(new ExtendedRect(402, 478, 6,11));
		result.add(new ExtendedRect(598, 454, 4,10));
		result.add(new ExtendedRect(932, 392, 8,7));
		result.add(new ExtendedRect(662, 307, 5,8));
		result.add(new ExtendedRect(427, 307, 5,8));
		result.add(new ExtendedRect(402, 304, 6,11));
		result.add(new ExtendedRect(598, 280, 4,10));
		result.add(new ExtendedRect(932, 218, 8,7));
		result.add(new ExtendedRect(462, 392, 7,7));
		result.add(new ExtendedRect(226, 392, 8,7));
		result.add(new ExtendedRect(182, 392, 8,7));
		result.add(new ExtendedRect(799, 390, 20,9));
		result.add(new ExtendedRect(408, 392, 17,7));
		result.add(new ExtendedRect(462, 218, 7,7));
		result.add(new ExtendedRect(226, 218, 8,7));
		result.add(new ExtendedRect(182, 218, 8,7));
		result.add(new ExtendedRect(799, 216, 20,9));
		result.add(new ExtendedRect(408, 218, 17,7));
		result.add(new ExtendedRect(635, 392, 15,7));
		result.add(new ExtendedRect(635, 218, 15,7));
		result.add(new ExtendedRect(149, 485, 5,4));
		result.add(new ExtendedRect(149, 311, 5,4));
		result.add(new ExtendedRect(620, 310, 4,5));
		result.add(new ExtendedRect(620, 459, 5,3));
		result.add(new ExtendedRect(620, 285, 5,3));
		result.add(new ExtendedRect(313, 490, 7,6));
		result.add(new ExtendedRect(313, 316, 7,6));
		result.add(new ExtendedRect(784, 493, 5,3));
		result.add(new ExtendedRect(78, 493, 5,3));
		result.add(new ExtendedRect(549, 491, 6,4));
		result.add(new ExtendedRect(792, 475, 3,3));
		result.add(new ExtendedRect(86, 475, 3,3));
		result.add(new ExtendedRect(784, 319, 5,3));
		result.add(new ExtendedRect(78, 319, 5,3));
		result.add(new ExtendedRect(549, 317, 6,4));
		result.add(new ExtendedRect(792, 301, 3,3));
		result.add(new ExtendedRect(86, 301, 3,3));
		result.add(new ExtendedRect(885, 435, 2,3));
		return result;
	}

}
