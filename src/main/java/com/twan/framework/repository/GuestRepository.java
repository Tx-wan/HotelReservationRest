package com.twan.framework.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.twan.framework.entity.Guest;

@Repository
public interface  GuestRepository extends JpaRepository<Guest, Long>{

	@Query(nativeQuery = true, value = "select gtl.guest_id from guest_tbl gtl where gtl.first_name = ?1 and gtl.last_name = ?2")
	public long findIdByName(String firstname, String lastname);
	
	@Query(nativeQuery = true, value = "select gtbl.first_name, gtbl.last_name, gtbl.gender, gtbl.age, hrtbl.hotel_name, hrtbl.check_in_date, hrtbl.check_out_date from guest_tbl gtbl join\r\n"
			+ "(select htbl.hotel_name, rtbl.reservation_id, rtbl.check_in_date, rtbl.check_out_date from hotel_tbl htbl join reservation_tbl rtbl on htbl.hotel_id = rtbl.hotel_id) as hrtbl\r\n"
			+ "on gtbl.reservation_id = hrtbl.reservation_id")
	public List<Map<String, Object>> findAllGuests();
	
	@Query(nativeQuery = true, value = "select gtbl.first_name, gtbl.last_name, gtbl.gender, gtbl.age, hrtbl.hotel_name from guest_tbl gtbl join\r\n"
			+ "(select htbl.hotel_name, rtbl.reservation_id, rtbl.check_in_date, rtbl.check_out_date from hotel_tbl htbl join reservation_tbl rtbl on htbl.hotel_id = rtbl.hotel_id where rtbl.reservation_id= ?1) as hrtbl\r\n"
			+ "on gtbl.reservation_id = hrtbl.reservation_id")
	public List<Map<String, Object>> findGuestsByReservationId(Integer reservationId) throws IllegalArgumentException;
}
