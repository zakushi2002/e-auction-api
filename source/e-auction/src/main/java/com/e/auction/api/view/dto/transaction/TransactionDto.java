package com.e.auction.api.view.dto.transaction;

import com.e.auction.api.view.dto.InfoAdminDto;
import com.e.auction.api.view.dto.account.AccountDto;
import com.e.auction.api.view.dto.paymentmethod.PaymentMethodDto;
import com.e.auction.api.view.dto.shippingcost.ShippingCostDto;
import java.util.Date;

public class TransactionDto extends InfoAdminDto {
    private AccountDto bidder;
    private AccountDto auction;
    private String address;
    private ShippingCostDto shippingCost;
    private PaymentMethodDto paymentMethod;
    private Date shippingDate;
    private Integer duration;
    private Date receiveDate;
}
