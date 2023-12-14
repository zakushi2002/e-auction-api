package com.e.auction.api.controller;

import com.e.auction.api.constant.EAuctionConstant;
import com.e.auction.api.view.dto.ApiMessageDto;
import com.e.auction.api.view.dto.ErrorCode;
import com.e.auction.api.view.form.payment.PaymentForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

import com.e.auction.api.config.vnpay.Config;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/payment")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class PaymentController {
    @PostMapping(value = "/pay", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> getPay(@Valid @RequestBody PaymentForm paymentForm) throws UnsupportedEncodingException {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        long amount = paymentForm.getPaymentPrice() * 100;
        /*if (!(Objects.equals(paymentForm.getBankCode(), EAuctionConstant.BANK_CODE_NCB)
                && !Objects.equals(paymentForm.getBankCode(), EAuctionConstant.BANK_CODE_VISA)
                && !Objects.equals(paymentForm.getBankCode(), EAuctionConstant.BANK_CODE_JCB)
                && !Objects.equals(paymentForm.getBankCode(), EAuctionConstant.BANK_CODE_MASTERCARD))) {
            apiMessageDto.setMessage("Bank code is not valid");
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.BANK_ERROR_NOT_FOUND);
            return apiMessageDto;
        }*/
        String bankCode = paymentForm.getBankCode();

        String vnp_TxnRef = paymentForm.getTransactionId().toString();
        String vnp_IpAddr = "127.0.0.1";

        String vnp_TmnCode = Config.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        vnp_Params.put("vnp_BankCode", bankCode);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);

        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;
        apiMessageDto.setData(paymentUrl);
        apiMessageDto.setMessage("Redirect to payment url success");
        return apiMessageDto;
    }

    @GetMapping(value = "/return", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> returnPayment(@RequestParam Map<String, String> params) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        String vnp_Amount = params.get("vnp_Amount");
        String vnp_BankCode = params.get("vnp_BankCode");
        String vnp_BankTranNo = params.get("vnp_BankTranNo");
        String vnp_CardType = params.get("vnp_CardType");
        String vnp_OrderInfo = params.get("vnp_OrderInfo");
        String vnp_PayDate = params.get("vnp_PayDate");
        String vnp_ResponseCode = params.get("vnp_ResponseCode");
        String vnp_TmnCode = params.get("vnp_TmnCode");
        String vnp_TransactionNo = params.get("vnp_TransactionNo");
        String vnp_TxnRef = params.get("vnp_TxnRef");
        String vnp_SecureHashType = params.get("vnp_SecureHashType");
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Amount", vnp_Amount);
        vnp_Params.put("vnp_BankCode", vnp_BankCode);
        vnp_Params.put("vnp_BankTranNo", vnp_BankTranNo);
        vnp_Params.put("vnp_CardType", vnp_CardType);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_PayDate", vnp_PayDate);
        vnp_Params.put("vnp_ResponseCode", vnp_ResponseCode);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_TransactionNo", vnp_TransactionNo);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_SecureHashType", vnp_SecureHashType);
        if (vnp_ResponseCode.equals("00")) {
            apiMessageDto.setMessage("Payment success");
            apiMessageDto.setData(vnp_TxnRef);
            return apiMessageDto;
        } else {
            apiMessageDto.setMessage("Payment fail");
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.PAYMENT_ERROR);
            return apiMessageDto;
        }
    }
}
