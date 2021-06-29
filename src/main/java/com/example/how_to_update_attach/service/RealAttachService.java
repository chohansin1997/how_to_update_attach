package com.example.how_to_update_attach.service;

import com.example.how_to_update_attach.domain.attach.dto.request.AttachCreateRequest;
import com.example.how_to_update_attach.domain.attach.service.AttachService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static java.io.File.separator;
import static java.nio.file.Paths.get;
import static java.time.LocalDate.now;
import static java.util.Locale.ROOT;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static org.springframework.util.StringUtils.getFilenameExtension;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RealAttachService {

	private final AttachService attachService;

	private final String PATH_UPLOAD = "upload";
	private final String PATH_DEFAULT = "temp";

	/**
	 * 파일 업로드
	 *
	 * @param target
	 * @param attach
	 * @throws IOException
	 */
	public Map<String, Object> upload(String target, MultipartFile attach) throws IOException {
		HashMap<String, Object> resultMap = new HashMap<>(); // 반환 데이터

		// MultipartFile 에서 데이터 조회
		String filename = ofNullable(attach.getOriginalFilename()).orElse(null); // 파일 이름
		String contentType = ofNullable(attach.getContentType()).orElse(null); // 파일 Content Type
		Long size = of(attach.getSize()).orElse(null); // 파일 용량
																															//소문자로 변환
		String directory = ofNullable(target).orElse(PATH_DEFAULT).toLowerCase(ROOT); // 파일이 업로드 될 디렉토리
		String extension = getFilenameExtension(filename); // 파일 확장자
		UUID uuid = UUID.randomUUID(); // 업로드될 파일 이름이 중복되지 않기 위한 UUID
		String saveName = uuid + "." + extension; // 실제로 저장될 파일 이름
		String date = now().format(DateTimeFormatter.ofPattern("yyyyMMdd")); // 오늘 날짜
		boolean mkdirs = true; // 디렉토리 생성 결과
		File path = get(PATH_UPLOAD, directory, date).toFile(); // 업로드 디렉토리 경로
		// ex. get("folder", "picture","test" .. ).toFile() 이런식이라면 folder\picture\test 에 파일이 저장됨 *무한하게 추가할수 있음

		if (!path.exists()) { // 경로의 디렉토리가 없는지 체크
			mkdirs = path.mkdirs(); // 디렉토리 생성
		}

		if (!mkdirs) { // 디렉토리 생성에 실패했는지 체크
			throw new IOException("Can`t Create Directory");
		}

		File file = get(PATH_UPLOAD, directory, date, saveName).toFile(); // 업로드 파일 경로

		attach.transferTo(file.getAbsoluteFile()); // Parameter 로 넘어온 파일을 설정한 경로에 생성

		String physical = file.getPath().replace(separator, "/"); // 파일이 업로드된 경로

		resultMap.put("filename", filename);
		resultMap.put("physical", physical);
		resultMap.put("contentType", contentType);
		resultMap.put("size", size);

		return resultMap;
	}

	public Long uploadAttach(MultipartFile file) throws IOException {
		return uploadAttach(null, file);
	}

	public Long uploadAttach(String target, MultipartFile file) throws IOException {
		Map<String, Object> upload = upload(target, file);

		return attachService.createAttach(AttachCreateRequest
				.builder()
				.filename((String) upload.get("filename"))
				.path((String) upload.get("physical"))
				.contentType((String) upload.get("contentType"))
				.size((Long) upload.get("size"))
				.build());
	}
}
