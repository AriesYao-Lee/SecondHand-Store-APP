package erhuoServer;

public class UserInfo {
	private String username = "";
	private String password = "";
	private String phoneNum = "";
	private String email = "";
	public String getPassword() {
	  return password;
	}
	public void setPassword(String password) {
	  this.password = password;
	}
	public String getPhone() {
		return phoneNum;
	}
	public void setPhone(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getUsername() {
	  return username;
	}
	public void setUsername(String username) {
	  this.username = username;
	}
	public String getEmail() {
		  return email;
		}
		public void setEmail(String email) {
		  this.email = email;
		}
}
