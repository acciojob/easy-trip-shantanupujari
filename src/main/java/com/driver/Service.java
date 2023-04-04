package com.driver;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;

@org.springframework.stereotype.Service
public class Service {
    Repository repository = new Repository();

    public void AddAirport(@RequestBody Airport airport) {
        repository.AddAirport(airport);
    }

    public String getLargestAirport() {
        String s = repository.getLargestAirport();
        return s;
    }

    public double getShortestDis(City fromCity, City toCity) {
        double d = repository.getShortestDis(fromCity, toCity);
        return d;
    }

    public int getNumberOfPeople(Date date, String airportName) {
        int ans = repository.getNumberOfPeople(date, airportName);
        return ans;
    }

    public int  pricing(int flightId){
        int ans  = repository.pricing(flightId);
        return ans;
    }

    public String booking(Integer flightID , Integer passengerId){
        return repository.booking(flightID,passengerId);
    }

    public String cancel(Integer flightID , Integer passengerId){
       return repository.cancel(flightID,passengerId);
    }

    public int count(Integer passengerId){
        return repository.count(passengerId);
    }

    public  String addFlight(Flight flight){
        return repository.addFlight(flight);
    }

    public  String getName(Integer flightid){
        return repository.getName(flightid);
    }

    public int calculate(Integer flightId){
    return repository.calculate(flightId);
    }
    public  String addPassenger(Passenger passenger){

        return repository.addPassenger(passenger);
    }
}
