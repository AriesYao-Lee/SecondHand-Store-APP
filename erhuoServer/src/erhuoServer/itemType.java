package erhuoServer;

public class itemType {
	private String id;
	private String kindName;
	private String kindDesc;

	// �޲����Ĺ�����
	public itemType()
	{
	}
	// ��ʼ��ȫ�����ԵĹ�����
	public itemType(String id , String kindName , String kindDesc)
	{
		this.id = id;
		this.kindName = kindName;
		this.kindDesc = kindDesc;
	}

	// id��setter��getter����
	public void setId(String id)
	{
		this.id = id;
	}
	public String getId()
	{
		return this.id;
	}

	// kindName��setter��getter����
	public void setKindName(String kindName)
	{
		this.kindName = kindName;
	}
	public String getKindName()
	{
		return this.kindName;
	}

	// kindDesc��setter��getter����
	public void setKindDesc(String kindDesc)
	{
		this.kindDesc = kindDesc;
	}
	public String getKindDesc()
	{
		return this.kindDesc;
	}
}
