package com.example.how_to_update_attach.domain.attach.entity;

import com.example.how_to_update_attach.domain.attach.entity.extension.EntityExtension;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Optional;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "TB_RESTAURANT")
@Getter
@DynamicInsert //null 인 항목 자동으로 빼줌 ,미설정시 기본값 설정해준 애들 기본값이 아닌 null로 들어가게된다
@DynamicUpdate// 항목이 변경되지 않은 애들은 update하지 않는다 바뀐 항목만 update
@NoArgsConstructor(access = PROTECTED)
public class Restaurant extends EntityExtension {

	@Column(length = 50, nullable = false)
	private String name;

	@OneToMany(fetch = LAZY, mappedBy = "restaurant")
	private List<Menu> menuList;

	private Restaurant(String name) {
		this.name = name;
	}

	public static Restaurant createEntity(String name) {
		return new Restaurant(name);
	}

	public void updateEntity(String name) {
		this.name = Optional.ofNullable(name).orElse(this.name);
	}
}
