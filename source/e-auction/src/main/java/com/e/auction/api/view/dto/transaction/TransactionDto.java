package com.e.auction.api.view.dto.transaction;

import com.e.auction.api.view.dto.InfoAdminDto;
import com.e.auction.api.view.dto.account.AccountDto;
import com.e.auction.api.view.dto.auction.AuctionDto;
import com.e.auction.api.view.dto.paymentmethod.PaymentMethodDto;
import com.e.auction.api.view.dto.shippingcost.ShippingCostDto;
import lombok.Data;

import java.util.Date;
@Data
public class TransactionDto extends InfoAdminDto {
    private AccountDto bidder;
    private AuctionDto auction;
    private String address;
    private ShippingCostDto shippingCost;
    private PaymentMethodDto paymentMethod;
    private Date shippingDate;
    private Integer duration;
    private Date receiveDate;
}
