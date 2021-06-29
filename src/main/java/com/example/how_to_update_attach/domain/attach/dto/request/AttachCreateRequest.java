package com.example.how_to_update_attach.domain.attach.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttachCreateRequest {

	@NotNull
	@Length(min = 1, max = 30, message = "첨부파일 이름은 1~30글자 이내로 적어주세요")
	private String filename;

	@NotNull
	private String path;

	@NotNull
	private String contentType;

	@NotNull
	private Long size;
}
