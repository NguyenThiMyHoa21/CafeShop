package model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "menu_items")
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Liên kết với bảng sản phẩm
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    // Tên món hiển thị
    @Column(name = "name", nullable = false)
    private String name;

    // URL khi nhấn vào menu
    @Column(name = "url", nullable = false)
    private String url;

    // Danh sách vai trò được phép thấy menu này
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "menu_roles", joinColumns = @JoinColumn(name = "menu_item_id"))
    @Column(name = "role")
    private List<String> roles;

    // ===== Constructors =====
    public MenuItem() {
    }

    public MenuItem(Product product, String url, List<String> roles) {
        this.product = product;
        this.name = product != null ? product.getName() : "";
        this.url = url;
        this.roles = roles;
    }

    // ===== Getters and Setters =====
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
