package br.com.unifavip.cliente_pedidos.specifications.user;

import br.com.unifavip.cliente_pedidos.models.user.User;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;

@UtilityClass
public class UserSpecification {
    private static final String FIELD_ID = "id";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_EMAIL = "email";
    private static final String FIELD_STATUS = "status";
    private static final String USER_GROUP = "userGroup";

    public static Specification<User> idEquals(Long expression) {
        if (expression == null) {
            return null;
        }
        return (root, query, builder) -> builder.equal(root.get(FIELD_ID), expression);
    }

    public static Specification<User> userGroupIdEquals(Long userGroupId) {
        if (userGroupId == null) {
            return null;
        }
        return (root, query, builder) -> builder.equal(root.get(USER_GROUP).get("id"), userGroupId);
    }


    public static Specification<User> nameLike(String expression) {
        if (expression == null) {
            return null;
        }
        String expressionDecoded = URLDecoder.decode(expression, StandardCharsets.UTF_8);
        return (root, query, builder) -> builder.or(
                builder.like(builder.lower(root.get(FIELD_NAME)), contains(expressionDecoded.trim().toLowerCase())),
                builder.like(builder.lower(root.get(FIELD_NAME)), builder.literal(expressionDecoded.trim().toLowerCase())),
                builder.like(builder.lower(root.get(FIELD_NAME)),
                        contains(StringUtils.stripAccents(expressionDecoded.trim().toLowerCase()))));
    }

    public static Specification<User> emailLike(String expression) {
        if (expression == null) {
            return null;
        }
        String expressionDecoded = URLDecoder.decode(expression, StandardCharsets.UTF_8);
        return (root, query, builder) -> builder.or(
                builder.like(builder.lower(root.get(FIELD_EMAIL)), contains(expressionDecoded.trim().toLowerCase())),
                builder.like(builder.lower(root.get(FIELD_EMAIL)), builder.literal(expressionDecoded.trim().toLowerCase())),
                builder.like(builder.lower(root.get(FIELD_EMAIL)),
                        contains(StringUtils.stripAccents(expressionDecoded.trim().toLowerCase()))));
    }

    public static Specification<User> statusEquals(Boolean status) {
        if (status == null) {
            return null;
        }
        return (root, query, builder) -> builder.equal(root.get(FIELD_STATUS), status);
    }

    private static String contains(String expression) {
        if (expression == null) {
            return null;
        }
        return MessageFormat.format("%{0}%", expression);
    }
}
