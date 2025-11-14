package br.com.unifavip.cliente_pedidos.services.file;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {
    @Value("${app.upload.base-url}")
    private String baseUrl;

    @Value("${app.upload.local-path}")
    private String uploadPath;

    @Override
    public String storeFile(MultipartFile file, String folder) {

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("Nenhum arquivo enviado");
        }

        try {
            Path directory = Paths.get(uploadPath, folder);
            Files.createDirectories(directory);

            String originalName = Objects.requireNonNull(file.getOriginalFilename());
            String extension = getExtension(originalName);

            String fileName = UUID.randomUUID() + extension;

            Path destination = directory.resolve(fileName).normalize();

            if (!destination.startsWith(directory)) {
                throw new RuntimeException("Tentativa de path traversal detectada");
            }

            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

            return baseUrl + folder + "/" + fileName;

        } catch (IOException e) {
            log.error("Erro ao armazenar arquivo", e);
            throw new RuntimeException("Não foi possível salvar o arquivo");
        }
    }

    @Override
    public void deleteFile(String url) {
        try {
            Path filePath = resolveUrlToFilePath(url);

            if (Files.exists(filePath)) {
                Files.delete(filePath);
                log.info("Arquivo deletado: {}", filePath);
            } else {
                log.warn("Tentativa de deletar arquivo inexistente: {}", filePath);
            }
        } catch (Exception e) {
            log.error("Erro ao deletar arquivo: {}", url, e);
            throw new RuntimeException("Erro ao deletar arquivo");
        }
    }

    private Path resolveUrlToFilePath(String url) {
        if (url == null || url.isBlank()) {
            throw new IllegalArgumentException("URL inválida para remoção do arquivo");
        }
        String uploadsPrefix = "/uploads/";
        int index = url.indexOf(uploadsPrefix);

        if (index == -1) {
            throw new IllegalArgumentException("URL não contém o caminho /uploads/");
        }

        String relativePath = url.substring(index + uploadsPrefix.length());

        return Paths.get(uploadPath).resolve(relativePath).normalize().toAbsolutePath();
    }

    private String getExtension(String filename) {
        if (!filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf("."));
    }
}
