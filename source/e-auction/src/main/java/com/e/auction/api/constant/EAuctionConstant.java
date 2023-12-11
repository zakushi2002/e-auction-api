package com.e.auction.api.constant;

public class EAuctionConstant {
    public static final String REGION_STATIC = "ap-southeast-1";

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

    private EAuctionConstant() {
        throw new IllegalStateException("Utility class");
    }
}
