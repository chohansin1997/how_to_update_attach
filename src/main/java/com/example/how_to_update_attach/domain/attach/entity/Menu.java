package com.example.how_to_update_attach.domain.attach.entity;

import com.example.how_to_update_attach.domain.attach.entity.extension.EntityExtension;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "TB_MENU")
@Getter
@DynamicInsert //null 인 항목 자동으로 빼줌 ,미설정시 기본값 설정해준 애들 기본값이 아닌 null로 들어가게된다
@DynamicUpdate// 항목이 변경되지 않은 얘들은 update하지 않는다 바뀐 항목만 update
@NoArgsConstructor(access = PROTECTED)
public class Menu extends EntityExtension {

	@Column(length = 50, nullable = false)
	private String name;

	@Column(nullable = false)
	private Long price;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "restaurantId")
	private Restaurant restaurant;

	@OneToMany(fetch = LAZY, mappedBy = "menu")
	@OrderBy("id asc")
	private List<Attach> pictureList;

	private Menu(String name, Long price) {
		this.name = name;
		this.price = price;
	}

	public static Menu createMenu(String name, Long price) {
		return new Menu(name, price);
	}

	public void updateEntity(String name, Long price) {
		this.name = Optional.ofNullable(name).orElse(this.name);
		this.price = Optional.ofNullable(price).orElse(this.price);
	}

	public void updateRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}


}
