package com.notably.logic.correction.distance;

/**
 * Wrapper for algorithms that calculate distances between two {@link String}s.
 */
public interface EditDistanceCalculator {
  int calculateDistance(String first, String second);
}

