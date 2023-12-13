package com.e.auction.api.view.dto;

public class ErrorCode {
    /**
     * General error code
     */
    public static final String GENERAL_ERROR_UNAUTHORIZED = "ERROR-GENERAL-000";

    /**
     * Permission error code
     */
    public static final String PERMISSION_ERROR_NAME_EXIST = "ERROR-PERMISSION-000";
    public static final String PERMISSION_ERROR_CODE_EXIST = "ERROR-PERMISSION-001";
    public static final String PERMISSION_ERROR_NOT_FOUND = "ERROR-PERMISSION-002";

    /**
     * Group error code
     */
    public static final String GROUP_ERROR_NAME_EXIST = "ERROR-GROUP-000";
    public static final String GROUP_ERROR_NOT_FOUND = "ERROR-GROUP-001";

    /**
     * Account error code
     */
    public static final String ACCOUNT_ERROR_EMAIL_EXIST = "ERROR-ACCOUNT-000";
    public static final String ACCOUNT_ERROR_NOT_FOUND = "ERROR-ACCOUNT-001";
    public static final String ACCOUNT_ERROR_PASSWORD_INVALID = "ERROR-ACCOUNT-002";
    public static final String ACCOUNT_ERROR_WRONG_PASSWORD = "ERROR-ACCOUNT-003";

    /**
     * Category error code
     */
    public static final String CATEGORY_ERROR_NOT_FOUND = "ERROR-CATEGORY-000";
    public static final String CATEGORY_ERROR_NAME_EXIST_IN_KIND = "ERROR-CATEGORY-001";

    /**
     * Address error code
     */
    public static final String ADDRESS_ERROR_NOT_FOUND = "ERROR-ADDRESS-000";

    /**
     * User profile error code
     */
    public static final String USER_PROFILE_ERROR_NOT_FOUND = "ERROR-USER-PROFILE-000";

    /**
     * Shipping error code
     */
    public static final String SHIPPING_ERROR_NOT_FOUND = "ERROR-SHIPPING-000";

    /**
     * Product error code
     */
    public static final String PRODUCT_ERROR_NOT_FOUND = "ERROR-PRODUCT-000";
    public static final String PRODUCT_ERROR_NOT_FOUND_OR_NOT_BELONG_TO_SELLER = "ERROR-PRODUCT-001";

    /**
     * Auction error code
     */
    public static final String AUCTION_ERROR_NOT_FOUND = "ERROR-AUCTION-000";
    public static final String AUCTION_ERROR_STATUS_INVALID = "ERROR-AUCTION-003";
    public static final String AUCTION_ERROR_NOT_ADMIN_APPROVE = "ERROR-AUCTION-004";
    public static final String AUCTION_ERROR_NOT_BELONG_TO_SELLER = "ERROR-AUCTION-005";

    /**
     * Bid history error code
     */
    public static final String BID_HISTORY_ERROR_BID_PRICE_INVALID = "ERROR-BID-HISTORY-000";
    public static final String BID_HISTORY_ERROR_NOT_FOUND = "ERROR-BID-HISTORY-001";
    public static final String BID_HISTORY_ERROR_BID_TIME_INVALID = "ERROR-BID-HISTORY-002";
}
