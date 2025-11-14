package br.com.unifavip.cliente_pedidos.controller.product;

import br.com.unifavip.cliente_pedidos.dto.product.input.FindByFilterProductInputDTO;
import br.com.unifavip.cliente_pedidos.dto.product.input.ProductInputDTO;
import br.com.unifavip.cliente_pedidos.dto.product.input.ProductUpdateInputDTO;
import br.com.unifavip.cliente_pedidos.services.product.ProductService;
import br.com.unifavip.cliente_pedidos.utils.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/product")
public class ProductController {
    private final ProductService productService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PRODUCT_CREATE')")
    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> create(
            @Valid @ModelAttribute ProductInputDTO dto,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        CommonResponse<?> response = productService.create(dto, file);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PRODUCT_UPDATE')")
    @PutMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> update(
            @Valid @ModelAttribute ProductUpdateInputDTO dto,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        CommonResponse<?> response = productService.update(dto, file);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PRODUCT_VIEW')")
    public ResponseEntity<?> findByFilter(
            @ModelAttribute FindByFilterProductInputDTO dto,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable
    ) {
        CommonResponse<?> response = productService.findByFilter(dto, pageable);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PRODUCT_VIEW')")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        CommonResponse<?> response = productService.findById(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PRODUCT_DELETE')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        CommonResponse<?> response = productService.delete(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
