package erhuoServer;

public class itemType {
	private String id;
	private String kindName;
	private String kindDesc;

	// 无参数的构造器
	public itemType()
	{
	}
	// 初始化全部属性的构造器
	public itemType(String id , String kindName , String kindDesc)
	{
		this.id = id;
		this.kindName = kindName;
		this.kindDesc = kindDesc;
	}

	// id的setter和getter方法
	public void setId(String id)
	{
		this.id = id;
	}
	public String getId()
	{
		return this.id;
	}

	// kindName的setter和getter方法
	public void setKindName(String kindName)
	{
		this.kindName = kindName;
	}
	public String getKindName()
	{
		return this.kindName;
	}

	// kindDesc的setter和getter方法
	public void setKindDesc(String kindDesc)
	{
		this.kindDesc = kindDesc;
	}
	public String getKindDesc()
	{
		return this.kindDesc;
	}
}
