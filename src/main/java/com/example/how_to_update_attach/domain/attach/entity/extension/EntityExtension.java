package com.example.how_to_update_attach.domain.attach.entity.extension;

import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@MappedSuperclass
public class EntityExtension {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	Long id;

	@ColumnDefault("true")
	protected Boolean useFlag;

	@ColumnDefault("false")
	protected Boolean delFlag;

	@ColumnDefault("CURRENT_TIMESTAMP")
	protected LocalDateTime createDt;

	@ColumnDefault("CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	protected LocalDateTime updateDt;


	public void remove() {
		useFlag = false;
		delFlag = true;
	}
}
