package com.example.how_to_update_attach.controller;

import com.example.how_to_update_attach.service.RealAttachService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class RealAttachController {

	private final RealAttachService service;

	@GetMapping(value = "/uploadPage")
	public String attachUploadPage() {

		return "/test";
	}

	@PostMapping(value = "/uploadFile")
	public String uploadFile(MultipartFile file) throws IOException {

		Long attachId = service.uploadAttach(file);

		return "/test";
	}

}
