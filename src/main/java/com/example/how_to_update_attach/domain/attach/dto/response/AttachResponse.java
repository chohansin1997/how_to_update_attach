package com.example.how_to_update_attach.domain.attach.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AttachResponse {
	private String filename;

	private String path;

	private String contentType;
}
