package com.driver;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@org.springframework.stereotype.Repository
public class Repository {
    HashMap<String, Airport> airportDB = new HashMap<>();
    HashMap<Integer, Flight> flightDB = new HashMap<>();
    HashMap<Integer, List<Integer>> passengerInTheFlightDB = new HashMap<>();
    HashMap<Integer , Passenger> passengerDB = new HashMap<>();

    public void AddAirport(@RequestBody Airport airport) {
        if (airportDB.containsValue(airport)) {
            System.out.println("Airport Already Present");
        } else {
            airportDB.put(airport.getAirportName(), airport);
        }
    }

    public String getLargestAirport() {
        int size = 0;
        String ans = "";
        for (Airport airport : airportDB.values()) {
            if (airport.getNoOfTerminals() > size) {
                size = airport.getNoOfTerminals();
                ans = airport.getAirportName();
            } else if (airport.getNoOfTerminals() == size) {
                if (airport.getAirportName().compareTo(ans) < 0) {
                    ans = airport.getAirportName();
                }
            }
        }
        return ans;
    }

    public double getShortestDis(City fromCity, City toCity) {
        double ans = 1000000000;
        for (Flight flight : flightDB.values()) {
            if (flight.getFromCity().equals(fromCity) && flight.getToCity().equals(toCity)) {
                ans = Math.min(ans, flight.getDuration());
            }
        }
        return ans;
    }


    public int getNumberOfPeople(Date date, String airportName) {
        Airport airport = airportDB.get(airportName);
        if (Objects.isNull(airport)) {
            return 0;
        }
        City city = airport.getCity();
        int cnt = 0;
        for (Flight flight : flightDB.values()) {
            if (date.equals(flight.getFlightDate())) {
                if (flight.getToCity().equals(city) || flight.getFromCity().equals(city)) {
                    int id = flight.getFlightId();
                    cnt = cnt + passengerInTheFlightDB.get(id).size();
                }
            }
        }
        return cnt;
    }

    public int pricing(int flightid) {
        int allbokings = passengerInTheFlightDB.get(flightid).size();
        return allbokings * 50 + 3000;
    }

    public String booking(Integer flightId, Integer PassengerId) {
        if (Objects.nonNull(passengerInTheFlightDB.get(flightId)) && passengerInTheFlightDB.get(flightId).size() < flightDB.get(flightId).getMaxCapacity()) {
            List<Integer> list = passengerInTheFlightDB.get(flightId);
            if (list.contains(PassengerId)) {
                return "FAILURE";
            }
            list.add(PassengerId);
            passengerInTheFlightDB.put(flightId, list);
            return "SUCCESS";
        } else if (Objects.isNull(passengerInTheFlightDB.get(flightId))) {
            passengerInTheFlightDB.put(flightId, new ArrayList<>());
            List<Integer> list = passengerInTheFlightDB.get(flightId);
            if (list.contains(PassengerId)) {
                return "FAILURE";
            }
            list.add(PassengerId);
            passengerInTheFlightDB.put(flightId, list);
            return "SUCCESS";

        }
        return "FAILURE";
    }

    public String cancel(Integer flightID, Integer passengerId) {
        List<Integer> list = passengerInTheFlightDB.get(flightID);
        if (list == null) {
            return "FAILURE";
        }
        if (list.contains(passengerId)) {
            list.remove(passengerId);
            return "SUCCESS";
        }
        return "FAILURE";
    }

    public int count(Integer passengerId){
        HashMap<Integer,List<Integer>> passengerToFlightDb = new HashMap<>();
         int cntt = 0;
        for(Map.Entry<Integer,List<Integer>> entry: passengerInTheFlightDB.entrySet()){

            List<Integer> passengers  = entry.getValue();
            for(Integer passenger : passengers){
                if(passenger==passengerId){
                    cntt++;
                }
            }
        }
        return cntt;
    }
    public String addFlight(Flight flight){
        flightDB.put(flight.getFlightId(),flight);
        return "SUCCESS";
    }
    public String getName(Integer flightId){
        if(flightDB.containsKey(flightId)){
            City city = flightDB.get(flightId).getFromCity();
            for(Airport airport:airportDB.values()){
                if(airport.getCity().equals(city)){
                    return airport.getAirportName();
                }
            }
        }
        return null;
    }

    public int calculate(Integer flightId){
        int noOfPeopleBooked = passengerInTheFlightDB.get(flightId).size();
        int variableFare = (noOfPeopleBooked*(noOfPeopleBooked))*25;
        int fixedFare = 3000*noOfPeopleBooked;
        int totalFare = variableFare + fixedFare;

        return totalFare;
    }
    public String addPassenger(Passenger passenger){
      passengerDB.put(passenger.getPassengerId(), passenger);
      return "SUCCESS";
    }
}