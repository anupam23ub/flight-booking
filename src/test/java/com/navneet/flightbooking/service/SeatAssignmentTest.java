package com.navneet.flightbooking.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SeatAssignmentTest {

  @Test
  void firstSeatIsRow1SeatA() {
    assertEquals("1A", SeatUtil.seatNumber(1));
  }

  @Test
  void sixthSeatIsRow1SeatF() {
    assertEquals("1F", SeatUtil.seatNumber(6));
  }

  @Test
  void seventhSeatRollsOverToRow2SeatA() {
    assertEquals("2A", SeatUtil.seatNumber(7));
  }

  @Test
  void twelfthSeatIsRow2SeatF() {
    assertEquals("2F", SeatUtil.seatNumber(12));
  }

  @Test
  void rejectsNonPositiveIndex() {
    assertThrows(IllegalArgumentException.class, () -> SeatUtil.seatNumber(0));
  }
}
