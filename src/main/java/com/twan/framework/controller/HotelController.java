package com.twan.framework.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twan.framework.entity.Hotel;
import com.twan.framework.exception.ResourceNotFoundException;
import com.twan.framework.repository.HotelRepository;


@RestController
public class HotelController {
	@Autowired
	private HotelRepository hotelRepository;
	
	@RequestMapping("/getListOfHotels")
	public Map<String, Object> getListOfHotels() {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("hotels_list", hotelRepository.findAll());
	
		return result;
	}
	
	@RequestMapping("/getHotel/{id}")
	public ResponseEntity<Hotel> getHotelByID (@PathVariable(value="id") Long hotelId) throws ResourceNotFoundException {
		Hotel hotel = hotelRepository.findById(hotelId)
				.orElseThrow(() -> new ResourceNotFoundException("Hotel not found by ID:"+hotelId)) ;
		//Map<String, Object> result = new HashMap<String, Object>();
		//result.put("all_hotels",  ResponseEntity.ok().body(hotel));
		
		return ResponseEntity.ok().body(hotel);
	}
	
	@RequestMapping(value="/createHotel", method=RequestMethod.POST,consumes="application/json")
	public Hotel createHotel(@RequestBody Hotel hotel) {
		return hotelRepository.save(hotel);
	}
	
	@RequestMapping(value="/updateHotel/{id}", method=RequestMethod.PUT,consumes="application/json")
	public ResponseEntity<Hotel> updateHotel(@PathVariable(value="id") Long hotelId, @RequestBody Hotel hotelUpdates
			) throws ResourceNotFoundException {
		Hotel hotel = hotelRepository.findById(hotelId)
				.orElseThrow(() -> new ResourceNotFoundException("Hotel not found by ID:"+hotelId)) ;
		
		hotel.setAvailability(hotelUpdates.getAvailability());
		hotel.setPrice(hotelUpdates.getPrice());
		
		hotelRepository.save(hotel);
		
		return ResponseEntity.ok().body(hotel);
	}
	
	@RequestMapping(value="/deleteHotel/{id}", method=RequestMethod.DELETE)
	public Map<String, Boolean> deleteHotel(@PathVariable(value="id") Long hotelId
			) throws ResourceNotFoundException {
		Hotel hotel = hotelRepository.findById(hotelId)
				.orElseThrow(() -> new ResourceNotFoundException("Hotel not found by ID:"+hotelId)) ;
		
		hotelRepository.delete(hotel);
		Map<String, Boolean> response = new HashMap<>();
		response.put("delete", Boolean.TRUE);
		return response;
	}
	
	/*
	 * @RequestMapping(value="/reservationConfirmation",
	 * method=RequestMethod.POST,consumes="application/json") public String
	 * reservationConfirmation(@RequestBody Reservation reservation) { return
	 * "Confirmation Number :"+ reservation.getReservationID(); }
	 */
	
	
	
	/*
	 *hardcoded part, no use anymore
	 * public List<Hotel> getHotelList() { List<Hotel> hotelsList = new
	 * ArrayList<Hotel>(); List<String> hotelsNames =
	 * Arrays.asList("Holiday Inn","Hilton", "Four Seasons", "Marriot"); Random
	 * random = new Random();
	 * 
	 * for(int i=0; i<hotelsNames.size(); i++) { float randomPrice =
	 * random.nextInt(30000 - 5000 + 100) / 100 + 50;
	 * 
	 * Hotel hotel = new Hotel(i+1, hotelsNames.get(i), randomPrice, true);
	 * 
	 * hotelsList.add(hotel); hotel = null; }
	 * 
	 * return hotelsList; }
	 */
	
	
	/*
	 * @GetMapping("/getBookings") public Hotel getBookings(@RequestParam(value =
	 * "name", defaultValue = "CozyHotel") String name) { return new
	 * Hotel(counter.incrementAndGet(),name); }
	 */
}