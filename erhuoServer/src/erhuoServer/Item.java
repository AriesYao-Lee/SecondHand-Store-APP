package erhuoServer;

public class Item {
	private Integer id;
	private String itemName;
	private String itemDesc;
	private Double price;
	private Integer sellerId;
	private Integer status;
	private Integer typeId;
	private String  imgSrc;
	// 无参数的构造器
	public Item()
	{
	}
	// 初始化全部属性的构造器
	public Item(Integer id , String itemName , String itemDesc, String imgSrc,
			Double price, Integer sellerId, Integer status,Integer typeId)
	{
		this.id = id;
		this.itemName = itemName;
		this.itemDesc = itemDesc;
		this.imgSrc = imgSrc;
		this.price = price;
		this.sellerId = sellerId;
		this.status = status;
		this.typeId = typeId;
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
	//sellerId
	public void setSellerId(Integer sellerId)
	{
		this.sellerId = sellerId;
	}
	public Integer getSellerId()
	{
		return this.sellerId;
	}
	//typeId
	public void setTypeId(Integer typeId)
	{
		this.typeId = typeId;
	}
	public Integer getTypeId()
	{
		return this.typeId;
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
	// price的setter和getter方法
	public void setPrice(Double price)
	{
		this.price = price;
	}
	public Double getPrice()
	{
		return this.price;
	}
	
	// itemName的setter和getter方法
	public void setItemName(String itemName)
	{
		this.itemName = itemName;
	}
	public String getItemName()
	{
		return this.itemName;
	}
	
	// imaSrc的setter和getter方法
	public void setImgSrc(String imgSrc)
	{
		this.imgSrc = imgSrc;
	}
	public String getImgSrc()
	{
		return this.imgSrc;
	}
		
	// itemDesc的setter和getter方法
	public void setItemDesc(String ItemDesc)
	{
		this.itemDesc = ItemDesc;
	}
	public String getItemDesc()
	{
		return this.itemDesc;
	}
}
