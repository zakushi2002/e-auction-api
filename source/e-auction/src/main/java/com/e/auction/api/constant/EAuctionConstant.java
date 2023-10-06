package com.e.auction.api.constant;

import com.e.auction.api.utils.ConfigurationService;

public class EAuctionConstant {
    public static final String ROOT_DIRECTORY = ConfigurationService.getInstance().getString("file.upload-dir", "/tmp/upload");

    /**
     * Date format constant
     */
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";

    /**
     * Security Constant for grant type
     */
    public static final String GRANT_TYPE_PASSWORD = "password";

    /**
     * Account kind constant
     */
    public static final Integer ACCOUNT_KIND_ADMIN = 1;

    /**
     * Status constant
     */
    public static final Integer STATUS_ACTIVE = 1;
    public static final Integer STATUS_PENDING = 0;
    public static final Integer STATUS_LOCK = -1;
    public static final Integer STATUS_DELETE = -2;

    /**
     * Privacy constant
     */
    public static final Integer PRIVACY_PUBLIC = 1;
    public static final Integer PRIVACY_PRIVATE = 2;

    /**
     * Category kind constant
     */
    public static final Integer CATEGORY_KIND_HOSPITAL = 1;
    public static final Integer CATEGORY_KIND_HOSPITAL_ROLE = 2;
    public static final Integer CATEGORY_KIND_DEPARTMENT = 3;
    public static final Integer CATEGORY_KIND_ACADEMIC_DEGREE = 4;

    private EAuctionConstant() {
        throw new IllegalStateException("Utility class");
    }
}
