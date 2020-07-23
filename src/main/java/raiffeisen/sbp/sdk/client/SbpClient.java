package raiffeisen.sbp.sdk.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import raiffeisen.sbp.sdk.json.JsonBuilder;
import raiffeisen.sbp.sdk.model.Response;
import raiffeisen.sbp.sdk.model.in.PaymentInfo;
import raiffeisen.sbp.sdk.model.in.QRUrl;
import raiffeisen.sbp.sdk.model.in.RefundStatus;
import raiffeisen.sbp.sdk.model.out.QRId;
import raiffeisen.sbp.sdk.model.out.QRInfo;
import raiffeisen.sbp.sdk.model.out.RefundInfo;

import java.io.IOException;

public class SbpClient {
    public static String TEST_DOMAIN = "https://test.ecom.raiffeisen.ru";
    public static String PRODUCTION_DOMAIN = "https://e-commerce.raiffeisen.ru";

    private static final String REGISTER_PATH = "/api/sbp/v1/qr/register";
    private static final String QR_INFO_PATH = "/api/sbp/v1/qr/?/info";
    private static final String PAYMENT_INFO_PATH = "/api/sbp/v1/qr/?/payment-info";
    private static final String REFUND_PATH = "/api/sbp/v1/refund";
    private static final String REFUND_INFO_PATH = "/api/sbp/v1/refund/?";

    private final String domain;

    private final String secretKey;

    public SbpClient(String domain, String secretKey) {
        this.domain = domain;
        this.secretKey = secretKey;
    }

    public QRUrl registerQR(final QRInfo qr) throws IOException {
        Response tempResponse = PostRequester.request(domain + REGISTER_PATH, JsonBuilder.fromObject(qr), null);
        return new QRUrl(tempResponse.getBody());
    }

    public RefundStatus refundPayment(final RefundInfo refund) throws IOException {
        Response tempResponse = PostRequester.request(domain + REFUND_PATH, JsonBuilder.fromObject(refund), secretKey);
        return new RefundStatus(tempResponse.getBody());
    }

    public QRUrl getQRInfo(final QRId qrId) throws IOException {
        Response tempResponse = GetRequester.request(domain + QR_INFO_PATH, qrId, secretKey);
        return new QRUrl(tempResponse.getBody());
    }

    public PaymentInfo getPaymentInfo(final QRId qrId) throws IOException, JsonProcessingException {
        Response tempResponse = GetRequester.request(domain + PAYMENT_INFO_PATH, qrId, secretKey);
        return new PaymentInfo(tempResponse.getBody());
    }

    public RefundStatus getRefundInfo(final String refundId) throws IOException {
        Response tempResponse = GetRequester.request(domain + REFUND_INFO_PATH, refundId, secretKey);
        return new RefundStatus(tempResponse.getBody());
    }

}
