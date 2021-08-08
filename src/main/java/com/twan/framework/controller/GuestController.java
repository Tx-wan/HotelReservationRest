package com.twan.framework.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twan.framework.convertor.DateConvertor;
import com.twan.framework.convertor.GenderConvertor;
import com.twan.framework.exception.ResourceNotFoundException;
import com.twan.framework.repository.GuestRepository;

@RestController
public class GuestController {
	@Autowired
	private GuestRepository guestRepository;
	
	@RequestMapping("/getListOfGuests")
	public Map<String, Object> getAllGuest() throws ParseException {
		Map<String, Object> allGuest = new HashMap<String, Object>();
		
		List<Map<String, Object>> res = guestRepository.findAllGuests();
		List<Map<String, Object>> guestsList = new LinkedList<Map<String, Object>>();
		
		DateConvertor dc = new DateConvertor();
		
		Map<String, Object> guest;
		
		for(Map<String, Object> g : res) {
			guest = null;
			guest = new HashMap<String, Object>();
			
			guest.putAll(g);
			
			guest.replace("gender", GenderConvertor.ConvertorFromInteger(g.get("gender").toString()));
			guest.replace("check_in_date", dc.Convert(g.get("check_in_date")));
			guest.replace("check_out_date", dc.Convert(g.get("check_out_date")));
			
			guestsList.add(guest);
		}
		
		allGuest.put("all_Guests", guestsList);
		
		return allGuest;
	}
	
	@RequestMapping("/getGuestsByReservationId/{id}")
	public Map<String, Object> getHotelByID (@PathVariable(value="id") Integer reservationId) throws ResourceNotFoundException{
		List<Map<String, Object>> guests;
		Map<String, Object> allGuest = new HashMap<String, Object>();

		guests = guestRepository.findGuestsByReservationId(reservationId);
		
		if(guests.size() <1) {
			throw new ResourceNotFoundException("No guest found by reservationId:+"+reservationId);
		}

		allGuest.put("Guests", guests);
		
		return  allGuest;
	}
	
}
