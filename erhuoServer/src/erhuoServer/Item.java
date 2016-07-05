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
	// �޲����Ĺ�����
	public Item()
	{
	}
	// ��ʼ��ȫ�����ԵĹ�����
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

	// id��setter��getter����
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
	// price��setter��getter����
	public void setPrice(Double price)
	{
		this.price = price;
	}
	public Double getPrice()
	{
		return this.price;
	}
	
	// itemName��setter��getter����
	public void setItemName(String itemName)
	{
		this.itemName = itemName;
	}
	public String getItemName()
	{
		return this.itemName;
	}
	
	// imaSrc��setter��getter����
	public void setImgSrc(String imgSrc)
	{
		this.imgSrc = imgSrc;
	}
	public String getImgSrc()
	{
		return this.imgSrc;
	}
		
	// itemDesc��setter��getter����
	public void setItemDesc(String ItemDesc)
	{
		this.itemDesc = ItemDesc;
	}
	public String getItemDesc()
	{
		return this.itemDesc;
	}
}
