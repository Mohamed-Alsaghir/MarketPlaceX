package net.aldarraji.marketplacex;

import com.fasterxml.jackson.annotation.JsonProperty;
public class OrderItem {
    @JsonProperty("product_id")
    private int productId;
    @JsonProperty("buyer_id")
    private int buyerId;
        @JsonProperty("seller_id")
    private int sellerId;

    // Getters and Setters
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }
}

