package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tables")
public class MyTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String name;  // Tên hoặc mã bàn, ví dụ "Bàn 1"

    @Column(nullable = false)
    private String status;  // Trạng thái bàn: "Trống", "Đang dùng", "Đã đặt"

    // Constructor không tham số (bắt buộc cho JPA)
    public MyTable() {
    }

    // Constructor có tham số (tuỳ chọn)
    public MyTable(String name, String status) {
        this.name = name;
        this.status = status;
    }

    // Getter và Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
