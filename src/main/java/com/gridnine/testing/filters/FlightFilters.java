package com.gridnine.testing.filters;

import com.gridnine.testing.flights.Flight;

import java.util.List;

public interface FlightFilters {

  List<Flight> getFlights();

  //Departure until the current time
  FlightFilters filterDepartureUntilCurrentTime();

  //There are segments with arrival date earlier than departure date
  FlightFilters filterArrivalBeforeDeparture();

  //The total time spent on the ground exceeds two hours
  FlightFilters filterTotalTimeExceedsTwoHours();
}
