package com.example.how_to_update_attach.domain.attach.entity;



import com.example.how_to_update_attach.domain.attach.entity.extension.EntityExtension;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.awt.*;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "TB_ATTACH")
@Getter
@DynamicInsert //null 인 항목 자동으로 빼줌 ,미설정시 기본값 설정해준 애들 기본값이 아닌 null로 들어가게된다
@DynamicUpdate// 항목이 변경되지 않은 애들은 update하지 않는다 바뀐 항목만 update
@NoArgsConstructor(access = PROTECTED)
public class Attach extends EntityExtension {

	@Column(nullable = false)
	private String filename;

	@Column(nullable = false)
	private String path;

	private String contentType;

	private Long size;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menuId")
	private Menu menu;

	public Attach(String filename, String path, String contentType, Long size) {
		this.filename = filename;
		this.path = path;
		this.contentType = contentType;
		this.size = size;
	}

	public static Attach createAttach(String filename, String path, String contentType, Long size) {
		return new Attach(filename, path, contentType, size);
	}

	public void updateMenu(Menu menu) {
		this.menu = menu;
	}
}
