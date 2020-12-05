package scanning;

public class Token {
	String name;
	String code;
	int addr;
	public Token(String name, String code) {
		super();
		this.name = name;
		this.code = code;
	}
	
	public Token(String name, String code, int addr) {
		super();
		this.name = name;
		this.code = code;
		this.addr = addr;
	}

	public void setAddr(int rcv) {
		this.addr=rcv;
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

	public int getAddr() {
		return addr;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	//
}
