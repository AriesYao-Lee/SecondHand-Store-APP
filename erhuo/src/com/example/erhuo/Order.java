package com.example.erhuo;

public class Order {
    public int id;
    public String time;
    public String place;
    public int item_id;
    public int buyer_id;
    public int seller_id;
    public double price;
    public int status;//0:买家提交订单  1：买家确认交易 2：卖家确认，交易结束
    public String buyer_phone;

    public Order() {}

    public Order(int id, String time, String place, int item, int buyer, int seller,
                 double price, int status, String buyer_phone) {
        this.id = id;
        this.time = time;
        this.place = place;
        this.item_id = item;
        this.buyer_id = buyer;
        this.seller_id = seller;
        this.price = price;
        this.status = status;
        this.buyer_phone=buyer_phone;
    }
}
