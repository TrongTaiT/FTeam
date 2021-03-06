package com.fteam.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.fteam.utilities.FormatUtil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "created_date")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createdDate = new Date();

	@Column(name = "address")
	private String address;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "staff_id")
	private Staff staff;

	@ManyToOne
	@JoinColumn(name = "status_id")
	private Status status;

	@OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
	private List<OrderDetail> orderDetails;
	
	@Transient
	public String getFormatCreateDate() {
		return FormatUtil.dateTimeToString(createdDate);
	}
	
	@Transient
	public String getFormatTotal() {
		return FormatUtil.formatToVietnamCurrency(this.calculateTotal());
	}
	
	public float calculateTotal() {
		float total = 0.0f;

		for (OrderDetail orderDetail : this.orderDetails) {
			total += orderDetail.getQuantity() * orderDetail.getPrice();
		}

		return total;
	}

//	public static void main(String[] args) {
//		System.out.println(new Order().getCreatedDate());
//	}

}
