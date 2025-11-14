package br.com.unifavip.cliente_pedidos.utils;

import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

@UtilityClass
public class ValidateFileImgProduct {
    private static final Set<String> ACCEPTED_CONTENT_TYPES = Set.of(
            "image/png",
            "image/jpeg",
            "image/webp"
    );

    private static final Set<String> ACCEPTED_EXTENSIONS = Set.of(
            "png", "jpg", "jpeg", "jfif", "webp"
    );

    // Assinaturas (magic numbers)
    private static final byte[] PNG_MAGIC = {(byte) 0x89, 0x50, 0x4E, 0x47};
    private static final byte[] JPG_MAGIC = {(byte) 0xFF, (byte) 0xD8, (byte) 0xFF};
    private static final byte[] WEBP_MAGIC = {'R', 'I', 'F', 'F'};

    public void validate(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Arquivo de imagem é obrigatório.");
        }

        validateContentType(file);
        validateExtension(file);
        validateMagicNumber(file);
    }

    private void validateContentType(MultipartFile file) {
        String contentType = file.getContentType();
        if (!ACCEPTED_CONTENT_TYPES.contains(contentType)) {
            throw new IllegalArgumentException("Tipo de arquivo inválido. Envie apenas imagens PNG, JPEG ou WEBP.");
        }
    }

    private void validateExtension(MultipartFile file) {
        String fileName = file.getOriginalFilename();

        if (fileName == null || !fileName.contains(".")) {
            throw new IllegalArgumentException("Arquivo sem extensão.");
        }

        String ext = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();

        if (!ACCEPTED_EXTENSIONS.contains(ext)) {
            throw new IllegalArgumentException("Extensão inválida. Permitidas: png, jpg, jpeg, jfif, webp.");
        }
    }


    private void validateMagicNumber(MultipartFile file) {
        try (InputStream is = file.getInputStream()) {

            byte[] header = new byte[12];
            int read = is.read(header);

            if (read < 3) {
                throw new IllegalArgumentException("Arquivo inválido ou corrompido.");
            }

            if (startsWith(header, PNG_MAGIC)) return;
            if (startsWith(header, JPG_MAGIC)) return;
            if (startsWith(header, WEBP_MAGIC)) return;

            throw new IllegalArgumentException("Arquivo não parece ser uma imagem válida.");
        }
        catch (IOException e) {
            throw new IllegalArgumentException("Erro ao validar arquivo de imagem.");
        }
    }


    private boolean startsWith(byte[] file, byte[] magic) {
        if (file.length < magic.length) return false;

        for (int i = 0; i < magic.length; i++) {
            if (file[i] != magic[i]) {
                return false;
            }
        }
        return true;
    }
}
