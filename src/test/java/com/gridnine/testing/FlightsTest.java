package com.gridnine.testing;

import com.gridnine.testing.filters.FlightFilters;
import com.gridnine.testing.filters.FlightFiltersImpl;
import com.gridnine.testing.flights.Flight;
import com.gridnine.testing.flights.Segment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlightsTest {
    LocalDateTime time = LocalDateTime.now().plusMinutes(1);
    List<Flight> allFlights;
    List<Flight> flightsDepartureUntilCurrentTime;
    List<Flight> flightsArrivalBeforeDeparture;
    List<Flight> flightsTotalTimeExceedsTwoHours;


    private static Flight createFlight(final LocalDateTime... dates) {
        if ((dates.length % 2) != 0) {
            throw new IllegalArgumentException(
                    "you must pass an even number of dates");
        }
        List<Segment> segments = new ArrayList<>(dates.length / 2);
        for (int i = 0; i < (dates.length - 1); i += 2) {
            segments.add(new Segment(dates[i], dates[i + 1]));
        }
        return new Flight(segments);
    }

    @BeforeEach
    void initFlights() {
        allFlights = Arrays.asList(
                createFlight(time, time.plusHours(2)), //Normal flight
                createFlight(time.minusDays(1), time.minusDays(1).minusHours(3)), //A flight that departs before it arrives
                createFlight(time, time.plusHours(5)), //Normal flight
                createFlight(time.plusDays(1), time.plusDays(1).plusHours(3)), //Normal flight
                createFlight(time.minusDays(3).minusHours(2), time.minusDays(3).plusHours(2)), //A flight departing until the current time
                createFlight(time, time.plusHours(2),
                        time.plusHours(3), time.plusHours(5)), //Normal flight
                createFlight(time, time.plusHours(2),
                        time.plusHours(3), time.plusHours(5),
                        time.plusDays(7), time.plusDays(7).plusHours(9)), //A flight with more than two hours ground time
                createFlight(time.minusDays(6), time), //A flight departing until the current time
                createFlight(time.minusDays(2), time.minusDays(2).minusHours(5)), //A flight that departs before it arrives
                createFlight(time, time.plusHours(2),
                        time.plusHours(5), time.plusHours(6)),//A flight with more than two hours ground time
                createFlight(time.minusDays(1), time.minusDays(1).plusHours(5)), //A flight departing until the current time
                createFlight(time, time.plusHours(2),
                        time.plusHours(3), time.plusHours(4),
                        time.plusHours(6), time.plusHours(7)));//A flight with more than two hours ground time

        flightsDepartureUntilCurrentTime = Arrays.asList(
                createFlight(time, time.plusHours(2)), //Normal flight
                createFlight(time, time.plusHours(5)), //Normal flight
                createFlight(time.plusDays(1), time.plusDays(1).plusHours(3)), //Normal flight
                createFlight(time, time.plusHours(2),
                        time.plusHours(3), time.plusHours(5)), //Normal flight
                createFlight(time, time.plusHours(2),
                        time.plusHours(3), time.plusHours(5),
                        time.plusDays(7), time.plusDays(7).plusHours(9)), //A flight with more than two hours ground time
                createFlight(time, time.plusHours(2),
                        time.plusHours(5), time.plusHours(6)),//A flight with more than two hours ground time
                createFlight(time, time.plusHours(2),
                        time.plusHours(3), time.plusHours(4),
                        time.plusHours(6), time.plusHours(7)));//A flight with more than two hours ground time

        flightsArrivalBeforeDeparture = Arrays.asList(
                createFlight(time, time.plusHours(2)), //Normal flight
                createFlight(time, time.plusHours(5)), //Normal flight
                createFlight(time.plusDays(1), time.plusDays(1).plusHours(3)), //Normal flight
                createFlight(time.minusDays(3).minusHours(2), time.minusDays(3).plusHours(2)), //A flight departing until the current time
                createFlight(time, time.plusHours(2),
                        time.plusHours(3), time.plusHours(5)), //Normal flight
                createFlight(time, time.plusHours(2),
                        time.plusHours(3), time.plusHours(5),
                        time.plusDays(7), time.plusDays(7).plusHours(9)), //A flight with more than two hours ground time
                createFlight(time.minusDays(6), time), //A flight departing until the current time
                createFlight(time, time.plusHours(2),
                        time.plusHours(5), time.plusHours(6)),//A flight with more than two hours ground time
                createFlight(time.minusDays(1), time.minusDays(1).plusHours(5)), //A flight departing until the current time
                createFlight(time, time.plusHours(2),
                        time.plusHours(3), time.plusHours(4),
                        time.plusHours(6), time.plusHours(7)));//A flight with more than two hours ground time

        flightsTotalTimeExceedsTwoHours = Arrays.asList(
                createFlight(time, time.plusHours(2)), //Normal flight
                createFlight(time.minusDays(1), time.minusDays(1).minusHours(3)), //A flight that departs before it arrives
                createFlight(time, time.plusHours(5)), //Normal flight
                createFlight(time.plusDays(1), time.plusDays(1).plusHours(3)), //Normal flight
                createFlight(time.minusDays(3).minusHours(2), time.minusDays(3).plusHours(2)), //A flight departing until the current time
                createFlight(time, time.plusHours(2),
                        time.plusHours(3), time.plusHours(5)), //Normal flight
                createFlight(time.minusDays(6), time), //A flight departing until the current time
                createFlight(time.minusDays(2), time.minusDays(2).minusHours(5)), //A flight that departs before it arrives
                createFlight(time.minusDays(1), time.minusDays(1).plusHours(5)));//A flight departing until the current time
    }

    @Test
    void flightsDepartureUntilCurrentTimeTest() {
        FlightFilters flightFilters = new FlightFiltersImpl(allFlights);
        List<Flight> flights = flightFilters.filterDepartureUntilCurrentTime().getFlights();
        assertEquals(flightsDepartureUntilCurrentTime.size(), flights.size());
    }

    @Test
    void flightsArrivalBeforeDepartureTest() {
        FlightFilters flightFilters = new FlightFiltersImpl(allFlights);
        List<Flight> flights = flightFilters.filterArrivalBeforeDeparture().getFlights();
        assertEquals(flightsArrivalBeforeDeparture.size(), flights.size());
    }

    @Test
    void flightsTotalTimeExceedsTwoHoursTest() {
        FlightFilters flightFilters = new FlightFiltersImpl(allFlights);
        List<Flight> flights = flightFilters.filterTotalTimeExceedsTwoHours().getFlights();
        assertEquals(flightsTotalTimeExceedsTwoHours.size(), flights.size());
    }


}
