package br.com.unifavip.cliente_pedidos.specifications.product;

import br.com.unifavip.cliente_pedidos.dto.enums.product.Color;
import br.com.unifavip.cliente_pedidos.models.product.Product;
import br.com.unifavip.cliente_pedidos.models.user.User;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;

@UtilityClass
public class ProductSpecification {
    private static final String FIELD_ID = "id";
    private static final String FIELD_DESCRIPTION = "description";
    private static final String FIELD_STATUS = "status";
    private static final String FIELD_PRODUCT_TYPE = "productType";
    private static final String FIELD_PRICE = "price";
    private static final String FIELD_QUANTITY = "quantity";
    private static final String FIELD_SIZE = "size";
    private static final String FIELD_COLOR = "color";

    // =============== SIMPLE FIELDS ===============

    public static Specification<Product> idEquals(Long id) {
        if (id == null) return null;
        return (root, query, builder) -> builder.equal(root.get(FIELD_ID), id);
    }

    public static Specification<Product> statusEquals(Boolean status) {
        if (status == null) return null;
        return (root, query, builder) -> builder.equal(root.get(FIELD_STATUS), status);
    }

    public static Specification<Product> productTypeEquals(Long typeId) {
        if (typeId == null) return null;
        return (root, query, builder) -> builder.equal(root.get(FIELD_PRODUCT_TYPE).get("id"), typeId);
    }

    public static Specification<Product> colorEquals(Color color) {
        if (color == null) return null;
        return (root, query, builder) -> builder.equal(root.get(FIELD_COLOR), color);
    }

    // =============== DESCRIPTION (LIKE) ===============

    public static Specification<Product> descriptionLike(String expression) {
        if (expression == null) return null;

        String decoded = URLDecoder.decode(expression, StandardCharsets.UTF_8).trim().toLowerCase();
        String noAccents = StringUtils.stripAccents(decoded);

        return (root, query, builder) -> builder.or(
                builder.like(builder.lower(root.get(FIELD_DESCRIPTION)), contains(decoded)),
                builder.like(builder.lower(root.get(FIELD_DESCRIPTION)), builder.literal(decoded)),
                builder.like(builder.lower(root.get(FIELD_DESCRIPTION)), contains(noAccents))
        );
    }

    // =============== RANGE FILTERS ===============

    public static Specification<Product> priceBetween(Double min, Double max) {
        if (min == null && max == null) return null;

        return (root, query, builder) -> {
            if (min != null && max != null) {
                return builder.between(root.get(FIELD_PRICE), min, max);
            } else if (min != null) {
                return builder.greaterThanOrEqualTo(root.get(FIELD_PRICE), min);
            } else {
                return builder.lessThanOrEqualTo(root.get(FIELD_PRICE), max);
            }
        };
    }

    public static Specification<Product> quantityBetween(Integer min, Integer max) {
        if (min == null && max == null) return null;

        return (root, query, builder) -> {
            if (min != null && max != null) {
                return builder.between(root.get(FIELD_QUANTITY), min, max);
            } else if (min != null) {
                return builder.greaterThanOrEqualTo(root.get(FIELD_QUANTITY), min);
            } else {
                return builder.lessThanOrEqualTo(root.get(FIELD_QUANTITY), max);
            }
        };
    }

    public static Specification<Product> sizeBetween(Integer min, Integer max) {
        if (min == null && max == null) return null;

        return (root, query, builder) -> {
            if (min != null && max != null) {
                return builder.between(root.get(FIELD_SIZE), min, max);
            } else if (min != null) {
                return builder.greaterThanOrEqualTo(root.get(FIELD_SIZE), min);
            } else {
                return builder.lessThanOrEqualTo(root.get(FIELD_SIZE), max);
            }
        };
    }

    // =============== UTILS ===============

    private static String contains(String expression) {
        return MessageFormat.format("%{0}%", expression);
    }
}
