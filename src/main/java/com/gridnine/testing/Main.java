package com.gridnine.testing;

import com.gridnine.testing.filters.FlightFiltersImpl;
import com.gridnine.testing.flights.Flight;
import com.gridnine.testing.flights.FlightBuilder;

import java.util.List;

public class Main {

    public static void main(String[] args) {
	    List<Flight> flights = FlightBuilder.createFlights();

        System.out.println("All Flights");
	    for (Flight f : flights)
            System.out.println(f.toString());

        System.out.println("\nRule 1");
        System.out.println("Departure until the current time");
        List<Flight> flightsDepartureUntilCurrentTime = new FlightFiltersImpl(flights).filterDepartureUntilCurrentTime().getFlights();
        for (Flight f : flightsDepartureUntilCurrentTime)
            System.out.println(f.toString());


        System.out.println("\nRule 2");
        System.out.println("There are segments with arrival date earlier than departure date");
        List<Flight> flightsArrivalBeforeDeparture = new FlightFiltersImpl(flights).filterArrivalBeforeDeparture().getFlights();
        for (Flight f : flightsArrivalBeforeDeparture)
            System.out.println(f.toString());

        System.out.println("\nRule 3");
        System.out.println("The total time spent on the ground exceeds two hours");
        List<Flight> flightsTotalTimeExceedsTwoHours = new FlightFiltersImpl(flights).filterTotalTimeExceedsTwoHours().getFlights();
        for (Flight f : flightsTotalTimeExceedsTwoHours)
            System.out.println(f.toString());
    }
}
