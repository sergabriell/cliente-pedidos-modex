package br.com.unifavip.cliente_pedidos.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpStatusConstants {
    /**
     * HTTP Status-Code 200: OK.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class HttpOK {
        public static final int CODE = 200;
        public static String DESCRIPTION = "HTTP Status-Code 200: OK";
    }

    /**
     * HTTP Status-Code 201: Created.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class HTTP_CREATED {
        public static final int CODE = 201;
        public static final String DESCRIPTION = "HTTP Status-Code 201: Created.";
    }

    /**
     * HTTP Status-Code 202: Accepted.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class HTTP_ACCEPTED {
        public static final int CODE = 202;
        public static final String DESCRIPTION = "HTTP Status-Code 202: Accepted.";
    }

    /**
     * HTTP Status-Code 203: Non-Authoritative Information.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class HTTP_NOT_AUTHORITATIVE {
        public static final int CODE = 203;
        public static final String DESCRIPTION = "HTTP Status-Code 203: Non-Authoritative Information.";
    }

    /**
     * HTTP Status-Code 204: No Content.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class HTTP_NO_CONTENT {
        public static final int CODE = 204;
        public static final String DESCRIPTION = "HTTP Status-Code 204: No Content.";
    }

    /**
     * HTTP Status-Code 205: Reset Content.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class HTTP_RESET {
        public static final int CODE = 205;
        public static final String DESCRIPTION = "HTTP Status-Code 205: Reset Content.";
    }

    /**
     * HTTP Status-Code 206: Partial Content.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class HTTP_PARTIAL {
        public static final int CODE = 206;
        public static final String DESCRIPTION = "HTTP Status-Code 206: Partial Content.";

        /* 3XX: relocation/redirect */
    }

    /**
     * HTTP Status-Code 300: Multiple Choices.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class HTTP_MULT_CHOICE {
        public static final int CODE = 300;
        public static final String DESCRIPTION = "HTTP Status-Code 300: Multiple Choices.";
    }

    /**
     * HTTP Status-Code 301: Moved Permanently.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class HTTP_MOVED_PERM {
        public static final int CODE = 301;
        public static final String DESCRIPTION = "HTTP Status-Code 301: Moved Permanently.";
    }

    /**
     * HTTP Status-Code 302: Temporary Redirect.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class HTTP_MOVED_TEMP {
        public static final int CODE = 302;
        public static final String DESCRIPTION = "HTTP Status-Code 302: Temporary Redirect.";
    }

    /**
     * HTTP Status-Code 303: See Other.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class HTTP_SEE_OTHER {
        public static final int CODE = 303;
        public static final String DESCRIPTION = "HTTP Status-Code 303: See Other.";
    }

    /**
     * HTTP Status-Code 304: Not Modified.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class HTTP_NOT_MODIFIED {
        public static final int CODE = 304;
        public static final String DESCRIPTION = "HTTP Status-Code 304: Not Modified.";
    }

    /**
     * HTTP Status-Code 305: Use Proxy.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class HTTP_USE_PROXY {
        public static final int CODE = 305;
        public static final String DESCRIPTION = "HTTP Status-Code 305: Use Proxy.";
    }

    /**
     * HTTP Status-Code 400: Bad Request.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class HttpBadRequest {
        public static final int CODE = 400;
        public static final String DESCRIPTION = "HTTP Status-Code 400: Bad Request.";
    }

    /**
     * HTTP Status-Code 401: Unauthorized.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class HTTP_UNAUTHORIZED {
        public static final int CODE = 401;
        public static final String DESCRIPTION = "HTTP Status-Code 401: Unauthorized.";
    }

    /**
     * HTTP Status-Code 402: Payment Required.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class HTTP_PAYMENT_REQUIRED {
        public static final int CODE = 402;
        public static final String DESCRIPTION = "HTTP Status-Code 402: Payment Required.";
    }

    /**
     * HTTP Status-Code 403: Forbidden.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class HTTP_FORBIDDEN {
        public static final int CODE = 403;
        public static final String DESCRIPTION = "HTTP Status-Code 403: Forbidden.";
    }

    /**
     * HTTP Status-Code 404: Not Found.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class HTTP_NOT_FOUND {
        public static final int CODE = 404;
        public static final String DESCRIPTION = "HTTP Status-Code 404: Not Found.";
    }

    /**
     * HTTP Status-Code 405: Method Not Allowed.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class HTTP_BAD_METHOD {
        public static final int CODE = 405;
        public static final String DESCRIPTION = "HTTP Status-Code 405: Method Not Allowed.";
    }

    /**
     * HTTP Status-Code 406: Not Acceptable.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class HTTP_NOT_ACCEPTABLE {
        public static final int CODE = 406;
        public static final String DESCRIPTION = "HTTP Status-Code 406: Not Acceptable.";
    }

    /**
     * HTTP Status-Code 407: Proxy Authentication Required.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class HTTP_PROXY_AUTH {
        public static final int CODE = 407;
        public static final String DESCRIPTION = "HTTP Status-Code 407: Proxy Authentication Required.";
    }

    /**
     * HTTP Status-Code 408: Request Time-Out.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class HTTP_CLIENT_TIMEOUT {
        public static final int CODE = 408;
        public static final String DESCRIPTION = "HTTP Status-Code 408: Request Time-Out.";
    }

    /**
     * HTTP Status-Code 409: Conflict.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class HTTP_CONFLICT {
        public static final int CODE = 409;
        public static final String DESCRIPTION = "HTTP Status-Code 409: Conflict.";
    }

    /**
     * HTTP Status-Code 410: Gone.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class HTTP_GONE {
        public static final int CODE = 410;
        public static final String DESCRIPTION = "HTTP Status-Code 410: Gone.";
    }

    /**
     * HTTP Status-Code 411: Length Required.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class HTTP_LENGTH_REQUIRED {
        public static final int CODE = 411;
        public static final String DESCRIPTION = "HTTP Status-Code 411: Length Required.";
    }

    /**
     * HTTP Status-Code 412: Precondition Failed.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class HTTP_PRECON_FAILED {
        public static final int CODE = 412;
        public static final String DESCRIPTION = "HTTP Status-Code 412: Precondition Failed.";
    }

    /**
     * HTTP Status-Code 413: Request Entity Too Large.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class HTTP_ENTITY_TOO_LARGE {
        public static final int CODE = 413;
        public static final String DESCRIPTION = "HTTP Status-Code 413: Request Entity Too Large.";
    }

    /**
     * HTTP Status-Code 414: Request-URI Too Large.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class HTTP_REQ_TOO_LONG {
        public static final int CODE = 414;
        public static final String DESCRIPTION = "HTTP Status-Code 414: Request-URI Too Large.";
    }

    /**
     * HTTP Status-Code 415: Unsupported Media Type.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class HTTP_UNSUPPORTED_TYPE {
        public static final int CODE = 415;
        public static final String DESCRIPTION = "HTTP Status-Code 415: Unsupported Media Type.";

        /* 5XX: server error */
    }

    /**
     * HTTP Status-Code 500: internal Server Error.
     *
     * @deprecated it is misplaced and shouldn't have existed.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class HTTP_SERVER_ERROR {
        @Deprecated
        public static final int CODE = 500;
        public static final String DESCRIPTION = "HTTP Status-Code 500: internal Server Error.";
    }

    /**
     * HTTP Status-Code 500: internal Server Error.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class HTTP_INTERNAL_ERROR {
        public static final int CODE = 500;
        public static final String DESCRIPTION = "HTTP Status-Code 500: internal Server Error.";
    }

    /**
     * HTTP Status-Code 501: Not Implemented.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class HTTP_NOT_IMPLEMENTED {
        public static final int CODE = 501;
        public static final String DESCRIPTION = "HTTP Status-Code 501: Not Implemented.";
    }

    /**
     * HTTP Status-Code 502: Bad Gateway.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class HTTP_BAD_GATEWAY {
        public static final int CODE = 502;
        public static final String DESCRIPTION = "HTTP Status-Code 502: Bad Gateway.";
    }

    /**
     * HTTP Status-Code 503: Service Unavailable.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class HTTP_UNAVAILABLE {
        public static final int CODE = 503;
        public static final String DESCRIPTION = "HTTP Status-Code 503: Service Unavailable.";
    }

    /**
     * HTTP Status-Code 504: Gateway Timeout.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class HTTP_GATEWAY_TIMEOUT {
        public static final int CODE = 504;
        public static final String DESCRIPTION = "HTTP Status-Code 504: Gateway Timeout.";
    }

    /**
     * HTTP Status-Code 505: HTTP Version Not Supported.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class HTTP_VERSION {
        public static final int CODE = 505;
        public static final String DESCRIPTION = "HTTP Status-Code 505: HTTP Version Not Supported.";
    }
}
