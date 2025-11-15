package br.com.unifavip.cliente_pedidos.specifications.order;

import br.com.unifavip.cliente_pedidos.dto.enums.order.OrderStatus;
import br.com.unifavip.cliente_pedidos.dto.enums.order.PaymentType;
import br.com.unifavip.cliente_pedidos.models.order.Order;
import br.com.unifavip.cliente_pedidos.models.order.OrderItem;
import br.com.unifavip.cliente_pedidos.models.product.Product;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;

@UtilityClass
public class OrderSpecification {
    private static final String FIELD_ID = "id";
    private static final String FIELD_CLIENT = "client";
    private static final String FIELD_CREATED_BY = "createdBy";
    private static final String FIELD_STATUS = "status";
    private static final String FIELD_PAYMENT_TYPE = "paymentType";

    private static final String FIELD_ITEMS = "items";
    private static final String FIELD_PRODUCT = "product";
    private static final String FIELD_DESCRIPTION = "description";
    private static final String FIELD_TOTAL_PRICE = "totalPrice";

    public static Specification<Order> idEquals(Long expression) {
        if (expression == null) return null;
        return (root, query, builder) ->
                builder.equal(root.get(FIELD_ID), expression);
    }

    public static Specification<Order> clientIdEquals(Long clientId) {
        if (clientId == null) return null;

        return (root, query, builder) ->
                builder.equal(root.get(FIELD_CLIENT).get("id"), clientId);
    }

    public static Specification<Order> createdByUserIdEquals(Long userId) {
        if (userId == null) return null;

        return (root, query, builder) ->
                builder.equal(root.get(FIELD_CREATED_BY).get("id"), userId);
    }

    public static Specification<Order> statusEquals(OrderStatus status) {
        if (status == null) return null;

        return (root, query, builder) ->
                builder.equal(root.get(FIELD_STATUS), status);
    }

    public static Specification<Order> paymentTypeEquals(PaymentType paymentType) {
        if (paymentType == null) return null;

        return (root, query, builder) ->
                builder.equal(root.get(FIELD_PAYMENT_TYPE), paymentType);
    }

    public static Specification<Order> productIdEquals(Long productId) {
        if (productId == null) return null;

        return (root, query, builder) -> {
            Join<Order, OrderItem> itemJoin = root.join(FIELD_ITEMS, JoinType.LEFT);
            return builder.equal(itemJoin.get(FIELD_PRODUCT).get("id"), productId);
        };
    }

    public static Specification<Order> productDescriptionLike(String expression) {
        if (expression == null || expression.isEmpty()) return null;

        String decoded = URLDecoder.decode(expression, StandardCharsets.UTF_8);
        String exp = decoded.trim().toLowerCase();
        String noAccent = StringUtils.stripAccents(exp);

        return (root, query, builder) -> {
            Join<Order, OrderItem> itemsJoin = root.join(FIELD_ITEMS, JoinType.LEFT);
            Join<OrderItem, Product> productJoin = itemsJoin.join(FIELD_PRODUCT, JoinType.LEFT);

            return builder.or(
                    builder.like(builder.lower(productJoin.get(FIELD_DESCRIPTION)), contains(exp)),
                    builder.like(builder.lower(productJoin.get(FIELD_DESCRIPTION)), builder.literal(exp)),
                    builder.like(builder.lower(productJoin.get(FIELD_DESCRIPTION)), contains(noAccent))
            );
        };
    }

    public static Specification<Order> priceAtPurchaseMin(Double value) {
        if (value == null) return null;

        return (root, query, builder) -> {
            return builder.greaterThanOrEqualTo(root.get(FIELD_TOTAL_PRICE), value);
        };
    }

    public static Specification<Order> priceAtPurchaseMax(Double value) {
        if (value == null) return null;

        return (root, query, builder) -> {
            return builder.lessThanOrEqualTo(root.get(FIELD_TOTAL_PRICE), value);
        };
    }

    private static String contains(String expression) {
        if (expression == null) return null;
        return MessageFormat.format("%{0}%", expression);
    }
}
