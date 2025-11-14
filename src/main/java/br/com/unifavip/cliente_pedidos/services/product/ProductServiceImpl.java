package br.com.unifavip.cliente_pedidos.services.product;

import br.com.unifavip.cliente_pedidos.dto.product.input.FindByFilterProductInputDTO;
import br.com.unifavip.cliente_pedidos.dto.product.input.ProductInputDTO;
import br.com.unifavip.cliente_pedidos.dto.product.input.ProductUpdateInputDTO;
import br.com.unifavip.cliente_pedidos.dto.product.output.ProductOutputDTO;
import br.com.unifavip.cliente_pedidos.dto.product.output.ProductTypeOutputDTO;
import br.com.unifavip.cliente_pedidos.models.product.Product;
import br.com.unifavip.cliente_pedidos.models.product.ProductType;
import br.com.unifavip.cliente_pedidos.repository.product.ProductRepository;
import br.com.unifavip.cliente_pedidos.repository.product.ProductTypeRepository;
import br.com.unifavip.cliente_pedidos.services.file.FileStorageService;
import br.com.unifavip.cliente_pedidos.specifications.product.ProductSpecification;
import br.com.unifavip.cliente_pedidos.utils.CommonResponse;
import br.com.unifavip.cliente_pedidos.utils.ValidateFileImgProduct;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

import static br.com.unifavip.cliente_pedidos.response.product.ProductResponse.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductTypeRepository productTypeRepository;
    private final ModelMapper modelMapper;
    private final FileStorageService fileStorageService;

    @Transactional
    @Override
    public CommonResponse<?> create(ProductInputDTO dto, MultipartFile file) {
        log.info("ProductServiceImpl Create product {}", dto);

        try {
            Product product = modelMapper.map(dto, Product.class);

            if (dto.getProductTypeId() != null) {
                ProductType productType = productTypeRepository.findById(dto.getProductTypeId())
                        .orElseThrow(() -> new EntityNotFoundException("Product type not found!"));
                product.setProductType(productType);
            }

            if (file != null && !file.isEmpty()) {
                ValidateFileImgProduct.validate(file);
                String imageUrl = fileStorageService.storeFile(
                        file, "products/" + product.getProductType().getTypeKey()
                );
                product.setImageUrl(imageUrl);
            }

            Product saved = productRepository.save(product);
            return created(productToOutputDTO(saved));

        } catch (Exception e) {
            return CommonResponse.convertThrowableToCommonResponse(e);
        }
    }

    @Transactional
    @Override
    public CommonResponse<?> update(ProductUpdateInputDTO dto, MultipartFile file) {
        log.info("ProductServiceImpl Update product {}", dto);

        try {
            Product existing = productRepository.findById(dto.getId())
                    .orElseThrow(() ->
                            new EntityNotFoundException("Produto com ID " + dto.getId() + " não encontrado"));

            modelMapper.typeMap(ProductUpdateInputDTO.class, Product.class)
                    .addMappings(mapper -> mapper.skip(Product::setProductType))
                    .addMappings(mapper -> mapper.skip(Product::setImageUrl));

            modelMapper.map(dto, existing);

            if (dto.getProductTypeId() != null) {
                ProductType type = productTypeRepository.findById(dto.getProductTypeId())
                        .orElseThrow(() ->
                                new EntityNotFoundException("Product type not found!"));
                existing.setProductType(type);
            }

            if (file != null && !file.isEmpty()) {
                ValidateFileImgProduct.validate(file);
                if (existing.getImageUrl() != null) {
                    fileStorageService.deleteFile(existing.getImageUrl());
                }

                String folder = "products/" + existing.getProductType().getTypeKey();
                String newImageUrl = fileStorageService.storeFile(file, folder);
                existing.setImageUrl(newImageUrl);
            }

            Product saved = productRepository.save(existing);
            return updated(productToOutputDTO(saved));

        } catch (Exception e) {
            return CommonResponse.convertThrowableToCommonResponse(e);
        }
    }

    @Override
    public CommonResponse<?> findById(Long id) {
        log.info("ProductServiceImpl findById: {}", id);
        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Product not found!"));

            return founded(productToOutputDTO(product));
        } catch (Exception e) {
            return CommonResponse.convertThrowableToCommonResponse(e);
        }
    }

    @Override
    public CommonResponse<?> findByFilter(FindByFilterProductInputDTO dto, Pageable pageable) {
        log.info("ProductServiceImpl findByFilter: {}", dto);

        try {
            Page<Product> page = findByFilterInternal(dto, pageable);

            PageImpl<ProductOutputDTO> outputPage = new PageImpl<>(
                    page.stream()
                            .filter(Objects::nonNull)
                            .map(this::productToOutputDTO)
                            .toList(),
                    page.getPageable(),
                    page.getTotalElements()
            );

            return founded(outputPage);
        } catch (Exception e) {
            return CommonResponse.convertThrowableToCommonResponse(e);
        }
    }

    public Page<Product> findByFilterInternal(FindByFilterProductInputDTO dto, Pageable pageable) {
        log.info("ProductServiceImpl findByFilterInternal: {}", dto);

        Specification<Product> specification = Specification.allOf(
                ProductSpecification.idEquals(dto.getId()),
                ProductSpecification.descriptionLike(dto.getDescription()),
                ProductSpecification.statusEquals(dto.getStatus()),
                ProductSpecification.productTypeEquals(dto.getProductTypeId()),
                ProductSpecification.priceBetween(dto.getPriceMin(), dto.getPriceMax()),
                ProductSpecification.quantityBetween(dto.getQuantityMin(), dto.getQuantityMax()),
                ProductSpecification.sizeBetween(dto.getSizeMin(), dto.getSizeMax()),
                ProductSpecification.colorEquals(dto.getColor())
        );

        return productRepository.findAll(specification, pageable);
    }

    @Override
    public CommonResponse<?> delete(Long id) {
        log.info("ProductServiceImpl delete: {}", id);

        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Product not found!"));

            product.setStatus(false);
            productRepository.save(product);

            return ok(productToOutputDTO(product));
        } catch (Exception e) {
            return CommonResponse.convertThrowableToCommonResponse(e);
        }
    }

    private ProductOutputDTO productToOutputDTO(Product product) {
        log.info("ProductServiceImpl ProductToOutputDTO: {}", product.getDescription());

        ProductOutputDTO productOutput = modelMapper.map(product, ProductOutputDTO.class);

        if (product.getProductType() != null) {
            ProductTypeOutputDTO typeDTO = modelMapper.map(product.getProductType(), ProductTypeOutputDTO.class);
            productOutput.setProductType(typeDTO);
        }

        return productOutput;
    }
}
