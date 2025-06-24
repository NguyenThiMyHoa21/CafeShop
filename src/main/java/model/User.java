package model;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; // ID tự sinh

	@Column(nullable = false, unique = true)
	private String username; // Tên đăng nhập, duy nhất

	@Column(nullable = false)
	private String password; // Mật khẩu (đã mã hóa)

	@Column(nullable = false)
	private String fullname; // Họ tên đầy đủ

	@ManyToOne
	@JoinColumn(name = "role_id", nullable = false)
	private Role role; // Vai trò người dùng (admin, thu ngân, phục vụ)

	// Constructors
	public User() {
	}

	public User(int id, String username, String password, String fullname, Role role) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.role = role;
	}

	// Getters và Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	// Hàm trả về tên hiển thị (dùng chung nếu cần)
	public String getName() {
		return fullname;
	}
}
