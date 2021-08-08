package com.twan.framework.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "hotel_tbl")
public class Hotel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="hotel_id")
	private long hotelId;
	
	@Column(name = "hotel_name", nullable = false)
	private String hotelName;
	
	@Column(name = "price", nullable = false)
	private float price;
	
	@Column(name = "available", nullable = false)
	private boolean availability;
	
	//Maintain FK in Many side
	@JsonIgnore
	@OneToMany(mappedBy = "hotel",cascade = CascadeType.ALL)
//	@JoinColumn(name="hotel_id")
	private List<Reservation> reservationList;

	public List<Reservation> getReservationList() {
		return reservationList;
	}

	public void setReservationList(List<Reservation> reservationList) {
		this.reservationList = reservationList;
	}
	
	public long getHotelId() {
		return hotelId;
	}

	public void setHotelId(long hotelId) {
		this.hotelId = hotelId;
	}

	public String getHotelName() {
		return hotelName;
	}


	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}


	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public boolean getAvailability() {
		return availability;
	}
	
	public void setAvailability(boolean availability) {
		this.availability = availability;
	}
	
	@Override
    public String toString() {
        return "Hotel [id=" + hotelId + ", hotelName=" + hotelName + ", price=" + price + ", availability=" + availability 
       + "]";
    }
	
}
