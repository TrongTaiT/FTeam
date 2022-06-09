package com.fteam.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "product_name", length = 64, nullable = false)
	private String productName;

	@Column(length = 4096, nullable = false)
	private String description;

	@Column(nullable = false)
	private Float price;

	@Column(name = "day_of_manufacture", nullable = false)
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dayOfManufacture;

	@Column(name = "average_rating")
	private Float averageRating;

	@Column(name = "main_image", nullable = false)
	private String mainImage;

	@Column(name = "in_stock")
	private Boolean inStock;

	@Column(nullable = false)
	private Integer insurance;
	
	@Column(name = "discount_percent")
	private Float discountPercent;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@ManyToOne
	@JoinColumn(name = "brand_id")
	private Brand brand;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProductImage> productImages = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "shell_material_id")
	private ShellMaterial shellMaterial;

	@ManyToOne
	@JoinColumn(name = "strap_material_id")
	private StrapMaterial strapMaterial;

	@ManyToOne
	@JoinColumn(name = "watch_face_id")
	private WatchFace watchFace;

	@ManyToOne
	@JoinColumn(name = "face_shape_id")
	private FaceShape faceShape;

	@ManyToOne
	@JoinColumn(name = "style_id")
	private Style style;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProductDetail> productDetails = new ArrayList<>();

	@OneToMany(mappedBy = "product")
	private List<Rating> ratings;

	@Transient
	public String getMainImagePath() {
		return "/images/products/" + this.id + "/" + this.mainImage;
	}
	
	@Transient
	public Float getRealPrice() {
		return this.price - (this.price * this.discountPercent / 100);
	}

	@Transient
	public String getOldPrice() {
		return FormatUtil.formatToVietnamCurrency(this.price);
	}

	@Transient
	public String getNewPrice() {
		return FormatUtil.formatToVietnamCurrency(this.getRealPrice());
	}
	
	@Transient
	public String getDiscountAmount() {
		return FormatUtil.formatToVietnamCurrency(this.price - this.getRealPrice());
	}
	
	@Transient
	public String getFormatDayOfManufacture() {
		return FormatUtil.dateToString(this.dayOfManufacture);
	}
	
	@Transient
	public String getPriceInVNFormat() {
		return FormatUtil.formatToVietnamCurrency(this.price);
	}
	
	@Transient
	public String getPriceUsedInform() {
		return FormatUtil.toStringNumber(this.price);
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", productName=" + productName + ", price=" + price + "]";
	}

}
