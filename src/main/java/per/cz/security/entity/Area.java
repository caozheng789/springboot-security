package per.cz.security.entity;

/**
 * 七牛云地区
 * Created by Administrator on 2020/5/25.
 */
public enum  Area {
	REGION0("华东","Region.region0()"),
	REGION1("华北","Region.region1()"),
	REGION2("华南","Region.region2()"),
	REGIONNA0("北美","Region.regionna0()"),
	REGIONAS0("东南亚","Region.regionas0()");

	private String name;
	private String flag;

	public static String getflag(String index) {
		for (Area c : values()) {
			if (c.getName().equals(index)) {
				return c.getFlag();
			}
		}
		return null;
	}

	Area(String name, String flag) {
		this.name = name;
		this.flag = flag;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public static void main(String[] args) {
		String name = "东南亚";
		String flag = Area.getflag(name);
		System.out.println(flag);
	}

	@Override
	public String toString() {
		return "Area{" +
				"name='" + name + '\'' +
				", flag='" + flag + '\'' +
				'}';
	}
}


