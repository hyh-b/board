package com.example.board.service.posts;

import com.example.board.domain.posts.FilesRepository;
import com.example.board.domain.posts.Posts;
import com.example.board.web.Dto.FilesSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilesService {
    private final FilesRepository filesRepository;

    public List<com.example.board.domain.posts.Files> loadFiles(Long pSeq){
        return filesRepository.findByPseq(pSeq);
    }

    @Transactional
    public List<com.example.board.domain.posts.Files> processAndStoreFiles(MultipartFile[] files, Posts post) {
        return Arrays.stream(files)
                .map(file -> {
                    try {
                        return createFileSaveRequestDto(file, post);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(this::storeFile)
                .collect(Collectors.toList());
    }

    private FilesSaveRequestDto createFileSaveRequestDto(MultipartFile file, Posts post) throws IOException{
        String originalFileName = file.getOriginalFilename(); // 파일의 원본 이름
        String directoryPath = "C:\\java\\board\\src\\main\\resources\\static\\image";// 파일이 저장될 디렉토리 경로
        Path filePath = Paths.get(directoryPath, originalFileName); // 파일 저장 경로 및 파일명

        Files.createDirectories(filePath.getParent());
        // 파일을 서버에 저장하는 로직 추가 필요 (예: file.transferTo(new File(filePath)))
        file.transferTo(filePath.toFile());

        // FilesSaveRequestDto 객체 생성 및 반환
        return FilesSaveRequestDto.builder()
                .posts(post)
                .name(originalFileName)
                .path(filePath.toString())
                .build();
    }

    private com.example.board.domain.posts.Files storeFile(FilesSaveRequestDto fileDto) {
        // FilesSaveRequestDto를 Files 엔티티로 변환하고 저장
        return filesRepository.save(fileDto.toEntity());
    }

    public void deleteFile(Long seq){
        com.example.board.domain.posts.Files file = filesRepository.findById(seq)
                .orElseThrow(()-> new IllegalArgumentException("해당 파일이 없습니다 seq: "+seq));

        Path filePath = Paths.get(file.getPath());
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException("파일 삭제 실패: " + filePath, e);
        }
        filesRepository.delete(file);
    }
}
