package com.twan.framework.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twan.framework.convertor.GenderConvertor;
import com.twan.framework.entity.Gender;
import com.twan.framework.entity.Guest;
import com.twan.framework.entity.Hotel;
import com.twan.framework.entity.Reservation;
import com.twan.framework.exception.DateInputException;
import com.twan.framework.exception.HotelNotAvailableException;
import com.twan.framework.exception.ResourceNotFoundException;
import com.twan.framework.repository.GuestRepository;
import com.twan.framework.repository.HotelRepository;
import com.twan.framework.repository.ReservationRepository;

@RestController
public class ReservationController {
	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired
	private HotelRepository hotelRepository;
	
	@Autowired
	private GuestRepository guestRepository;
	
	@GetMapping("/getListOfReservations")
	public List<Reservation> getListOfReservations() {
		Map<String, Object> allReservation = new HashMap<String, Object>();
		
		List<Reservation> resList = reservationRepository.findAll();
		
		return reservationRepository.findAll();
	}
	
	@RequestMapping(value="/reservationConfirmation", method=RequestMethod.POST,consumes="application/json")
	public String createReservation(@RequestBody String newReservationJSON) 
			throws JacksonException , ResourceNotFoundException, HotelNotAvailableException, ParseException, DateInputException{		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(newReservationJSON);
		
		Reservation reservation = new Reservation();
		Guest guest = new Guest();
		
		String hotelName =  node.get("hotel_name").toString().replace("\"", "");

		Date checkInDate =  new SimpleDateFormat("yyyy-MM-dd").parse(node.get("checkin").toString().replace("\"", ""));
		Date checkOutDate = new SimpleDateFormat("yyyy-MM-dd").parse(node.get("checkout").toString().replace("\"", ""));
		
		if (checkOutDate.compareTo(checkInDate) < 0 ) {
			throw new DateInputException("invalid date input: check-out date is before check-in date");
		}
		
		long hotelId = hotelRepository.findIdByName(hotelName);
		
		Hotel hotel = hotelRepository.findById(hotelId)
				.orElseThrow(() -> new ResourceNotFoundException("Hotel not found by Name:"+hotelName+". Invalid Reservation"));
		
		if(!hotel.getAvailability()) {
			throw new HotelNotAvailableException("Hotel " + hotelName + " is not available now. Reservation Failed");
		}
		
		reservation.setCheckInDate(checkInDate);
		reservation.setCheckOutDate(checkOutDate);
		reservation.setHotel(hotel);
		
		Reservation newReservation = reservationRepository.save(reservation);
	  
		System.out.println("Rid"+newReservation.getReservationId());
	  
		JsonNode gNode = node.get("guests_list");
	  
		for (int i=0;i< gNode.size(); i ++) { 
			String guestFirstName = gNode.get(i).get("guest_firstname").toString().replace("\"", ""); 
			String guestLasttName = gNode.get(i).get("guest_lastname").toString().replace("\"", ""); 
			int guestAge = Integer.parseInt(gNode.get(i).get("age").toString().replace("\"", ""));
			Gender guestGender = GenderConvertor.Convertor(gNode.get(i).get("gender").toString().replace("\"", ""));
	  
			guest.setAge(guestAge); guest.setFirstName(guestFirstName);
			guest.setLastName(guestLasttName); guest.setGender(guestGender);
			guest.setReservation(newReservation);
	  
			guestRepository.save(guest);
	  
			guest = null; guest = new Guest(); 
		}
	  
		return "confirmation_number :" + newReservation.getReservationId();
		 
	}

}
