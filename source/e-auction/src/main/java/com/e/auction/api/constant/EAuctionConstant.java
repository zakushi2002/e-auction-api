package com.e.auction.api.constant;

public class EAuctionConstant {
    public static final String REGION_STATIC = "ap-southeast-1";
    public static final String OTP_SUBJECT_EMAIL = "One-Time Password (OTP) - Expire in 5 minutes!";
    public static final long OTP_VALID_DURATION = 300000; // 5 minutes
    public static final Integer OTP_LENGTH = 6;
    public static final Integer ATTEMPT_CODE_START = 1;
    public static final Integer ATTEMPT_CODE_MAX = 3;
    public static final String INVOICE_SUBJECT_EMAIL = "Payment - Toy Bid";

    /**
     * Date format constant
     */
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";

    /**
     * Security Constant for grant type
     */
    public static final String GRANT_TYPE_PASSWORD = "password";
    public static final String GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code";
    public static final String GRANT_TYPE_GOOGLE = "google";

    /**
     * Account kind constant
     */
    public static final Integer ACCOUNT_KIND_ADMIN = 1;
    public static final Integer ACCOUNT_KIND_USER = 2;

    /**
     * Status constant
     */
    public static final Integer STATUS_ACTIVE = 1;
    public static final Integer STATUS_PENDING = 0;
    public static final Integer STATUS_LOCK = -1;
    public static final Integer STATUS_DELETE = -2;
    public static final Integer STATUS_DONE = 2;
    public static final Integer STATUS_TRANSACTION_PENDING = 3;
    public static final Integer STATUS_TRANSACTION_DONE = 4;

    /**
     * Privacy constant
     */
    public static final Integer PRIVACY_PUBLIC = 1;
    public static final Integer PRIVACY_PRIVATE = 2;

    /**
     * Category kind constant
     */
    public static final Integer CATEGORY_KIND_PRODUCT = 1;

    /**
     * Address kind constant
     */
    public static final Integer ADDRESS_KIND_PROVINCE = 1;
    public static final Integer ADDRESS_KIND_DISTRICT = 2;
    public static final Integer ADDRESS_KIND_WARD = 3;

    /**
     * Gender constant
     */
    public static final Integer GENDER_MALE = 1;
    public static final Integer GENDER_FEMALE = 2;

    /**
     * Provider constant
     */
    public static final String PROVIDER_FACEBOOK = "facebook";
    public static final String PROVIDER_GOOGLE = "google";

    /**
     * Sort constant
     */
    public static final Integer SORT_PRICE_ASC = 1;
    public static final Integer SORT_PRICE_DESC = 2;
    public static final Integer SORT_NAME_ASC = 3;
    public static final Integer SORT_NAME_DESC = 4;

    /**
     * Bank code constant
     */
    public static final String BANK_CODE_NCB = "NCB";
    public static final String BANK_CODE_VISA = "VISA";
    public static final String BANK_CODE_MASTERCARD = "MasterCard";
    public static final String BANK_CODE_JCB = "JCB";

    private EAuctionConstant() {
        throw new IllegalStateException("Utility class");
    }
}
