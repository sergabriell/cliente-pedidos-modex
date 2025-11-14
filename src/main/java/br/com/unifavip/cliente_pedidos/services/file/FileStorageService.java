package br.com.unifavip.cliente_pedidos.services.file;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    String storeFile(MultipartFile file, String folder);

    void deleteFile(String url);
}
