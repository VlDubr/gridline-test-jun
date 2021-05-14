package com.gridnine.testing.filters;

import com.gridnine.testing.flights.Flight;
import com.gridnine.testing.flights.Segment;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FlightFiltersImpl implements FlightFilters{

    LocalDateTime now;
    private final List<Flight> flights;

    public FlightFiltersImpl(List<Flight> flights) {
        this.flights = new ArrayList<>(flights);
        now = LocalDateTime.now();
    }

    @Override
    public List<Flight> getFlights() {
        return flights;
    }

    @Override
    public FlightFilters filterDepartureUntilCurrentTime() {
        for (int i = 0; i < flights.size(); i++) {
            List<Segment> segments = flights.get(i).getSegments();
            for (Segment segment : segments) {
                if (segment.getDepartureDate().isBefore(now)) {
                    flights.remove(i);
                    i--;
                    break;
                }
            }
        }
        return this;
    }

    @Override
    public FlightFilters filterArrivalBeforeDeparture() {

        for (int i = 0; i < flights.size(); i++) {
            List<Segment> segments = flights.get(i).getSegments();
            for (Segment s : segments) {
                if (s.getDepartureDate().isAfter(s.getArrivalDate())) {
                    flights.remove(i);
                    i--;
                    break;
                }
            }
        }
        return this;
    }

    @Override
    public FlightFilters filterTotalTimeExceedsTwoHours() {
        for (int i = 0; i < flights.size(); i++) {
            boolean flag = true;
            List<Segment> segments = flights.get(i).getSegments();
            if (segments.size() > 1) {
                Duration duration = Duration.ZERO;

                for (int j = 0; j < segments.size(); j++) {
                    LocalDateTime arrTime = segments.get(j).getArrivalDate();
                    LocalDateTime depTime;
                    if (j + 1 < segments.size()) {
                        depTime = segments.get(j + 1).getDepartureDate();
                    } else
                        depTime = segments.get(j).getArrivalDate();
                    duration = duration.plus(Duration.between(arrTime, depTime));
                }
                if (duration.toHours() > 2) {
                    flights.remove(i);
                    i--;
                }
            }
        }
        return this;
    }

}
