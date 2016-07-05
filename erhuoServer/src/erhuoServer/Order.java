package erhuoServer;

public class Order {
	public int id;
    public String time;
    public String itemName;
    public String place;
    public int item_id;
    public int buyer_id;
    public int owner_id;
    public int status;//1:买家提交订单  2：买家确认交易 3：卖家确认，交易结束
    public String buyer_phone;

    public Order() {}

    public Order(int id, String time, String place, int item_id, int buyer_id,
                  int status, String buyer_phone) {
        this.id = id;
        this.time = time;
        this.place = place;
        this.item_id = item_id;
        this.buyer_id = buyer_id;
        this.status = status;
        this.buyer_phone=buyer_phone;
    }
    //buyerId
   	public void setOwnerId(Integer ownerId)
   	{
   		this.owner_id = ownerId;
   	}
   	public Integer getOwnerId()
   	{
   		return this.owner_id;
   	}
    //time
   	public void setItemName(String itemName)
   	{
   		this.itemName = itemName;
   	}
   	public String getItemName()
   	{
   		return this.itemName;
   	}
 // id的setter和getter方法
 	public void setId(Integer id)
 	{
 		this.id = id;
 	}
 	public Integer getId()
 	{
 		return this.id;
 	}
 	//buyerId
 	public void setBuyerId(Integer buyerId)
 	{
 		this.buyer_id = buyerId;
 	}
 	public Integer getBuyerId()
 	{
 		return this.buyer_id;
 	}
 	//itemId
 	public void setItemId(Integer itemId)
 	{
 		this.item_id = itemId;
 	}
 	public Integer getItemId()
 	{
 		return this.item_id;
 	}
 	//status
 	public void setStatus(Integer status)
 	{
 		this.status = status;
 	}
 	public Integer getStatus()
 	{
 		return this.status;
 	}
 	//time
 	public void setTime(String time)
 	{
 		this.time = time;
 	}
 	public String getTime()
 	{
 		return this.time;
 	}
 	//place
 	public void setPlace(String place)
 	{
 		this.place = place;
 	}
 	public String getPlace()
 	{
 		return this.place;
  	}
 	//buyer_phone
 	public void setBuyer_phone(String buyer_phone)
 	{
 	 	this.buyer_phone = buyer_phone;
 	}
 	public String getBuyer_phone()
 	{
 		return this.buyer_phone;
 	}
}
