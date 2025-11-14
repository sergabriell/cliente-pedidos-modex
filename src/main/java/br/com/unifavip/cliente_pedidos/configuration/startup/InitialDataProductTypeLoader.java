package br.com.unifavip.cliente_pedidos.configuration.startup;

import br.com.unifavip.cliente_pedidos.models.product.ProductType;
import br.com.unifavip.cliente_pedidos.repository.product.ProductTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class InitialDataProductTypeLoader implements ApplicationRunner {
    private final ProductTypeRepository productTypeRepository;

    private static final Map<String, String> PRODUCT_TYPES_MAP = Map.ofEntries(
            Map.entry("CALCADOS", "Calçados"),
            Map.entry("TENIS", "Tênis"),
            Map.entry("SANDALIA", "Sandália"),
            Map.entry("BOTA", "Bota"),
            Map.entry("CHINELO", "Chinelo"),

            Map.entry("CAMISETA", "Camiseta"),
            Map.entry("CAMISA", "Camisa"),
            Map.entry("JAQUETA", "Jaqueta"),
            Map.entry("CALCA", "Calça"),
            Map.entry("SHORTS", "Shorts"),
            Map.entry("SAIA", "Saia"),
            Map.entry("VESTIDO", "Vestido"),
            Map.entry("MOLETOM", "Moletom"),
            Map.entry("SOCIAL", "Social"),

            Map.entry("BONE", "Boné"),
            Map.entry("MEIA", "Meia"),
            Map.entry("CINTO", "Cinto"),
            Map.entry("MOCHILA", "Mochila"),
            Map.entry("OCULOS", "Óculos")
    );

    @Override
    public void run(ApplicationArguments args) {
        PRODUCT_TYPES_MAP.forEach(this::createTypeIfNotExists);
    }

    private void createTypeIfNotExists(String key, String description) {
        productTypeRepository.findByTypeKeyIgnoreCase(key)
                .orElseGet(() -> {
                    ProductType type = ProductType.builder()
                            .typeKey(key)
                            .description(description)
                            .createdDate(LocalDateTime.now())
                            .updatedDate(LocalDateTime.now())
                            .build();

                    return productTypeRepository.save(type);
                });
    }
}
