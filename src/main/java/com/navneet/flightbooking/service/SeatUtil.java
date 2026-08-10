package com.navneet.flightbooking.service;

/**
 * Pure helper for turning a 1-based seat index into a seat number like "1A".
 * Extracted out of BookingService so it can be unit tested directly.
 */
public final class SeatUtil {
  private SeatUtil() {}

  public static String seatNumber(int n) {
    if (n < 1) throw new IllegalArgumentException("Seat index must be >= 1");
    int row = ((n - 1) / 6) + 1;
    char col = (char) ('A' + ((n - 1) % 6));
    return row + String.valueOf(col);
  }
}
