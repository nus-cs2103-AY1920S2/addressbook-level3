package com.notably.logic.correction.distance;

/**
 * Wrapper for an algorithm that calculates the distance between two {@link String}s.
 */
public interface EditDistanceCalculator {
  int calculateDistance(String first, String second);
}

