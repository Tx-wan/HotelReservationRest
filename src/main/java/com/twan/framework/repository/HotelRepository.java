package com.twan.framework.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.twan.framework.entity.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
	
	@Query(nativeQuery = true, value = "select htl.hotel_id from hotel_tbl htl where htl.hotel_name = ?1")
	public long findIdByName(String name);
}
