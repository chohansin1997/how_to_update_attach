package com.example.how_to_update_attach.domain.attach.service;

import com.example.how_to_update_attach.domain.attach.entity.Attach;
import com.example.how_to_update_attach.domain.attach.dto.request.AttachCreateRequest;
import com.example.how_to_update_attach.domain.attach.repository.AttachRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.awt.*;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttachService {


	private final AttachRepository attachRepository;

	@Transactional
	public Long createAttach(AttachCreateRequest dto) {
		return attachRepository.save(Attach.createAttach(dto.getFilename(), dto.getPath(),
				dto.getContentType(),dto.getSize())).getId();
	}

	public Attach getAttach(Long id) {

		return attachRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}

	public List<Attach> getAttach(Menu menu) {

		return attachRepository.findByMenu(menu);
	}
}
