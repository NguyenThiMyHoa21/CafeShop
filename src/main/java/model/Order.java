package model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "order_time")
    private LocalDateTime orderTime;

    @Column(name = "order_date")
    @Temporal(TemporalType.DATE)
    private Date orderDate;

    @Column(name = "note")
    private String note;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "table_id")
    private MyTable table;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderDetail> details;
    // Format thời gian hiển thị
    @Transient
    public String getFormattedOrderTime() {
        if (orderTime != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            return orderTime.format(formatter);
        }
        return "";
    }

    public Order() {
    }

    public Order(Integer id, LocalDateTime orderTime, Date orderDate, String note,
                 BigDecimal totalAmount, String status, User user, MyTable table,
                 List<OrderDetail> orderDetails) {
        this.id = id;
        this.orderTime = orderTime;
        this.orderDate = orderDate;
        this.note = note;
        this.totalAmount = totalAmount;
        this.status = status;
        this.user = user;
        this.table = table;
        this.orderDetails = orderDetails;
    }

    // ======= GETTERS & SETTERS =======

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MyTable getTable() {
        return table;
    }

    public void setTable(MyTable table) {
        this.table = table;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public List<OrderDetail> getDetails() {
        return details;
    }

    public void setDetails(List<OrderDetail> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderTime=" + orderTime +
                ", orderDate=" + orderDate +
                ", note='" + note + '\'' +
                ", totalAmount=" + totalAmount +
                ", status='" + status + '\'' +
                ", user=" + user +
                ", table=" + table +
                ", orderDetails=" + orderDetails +
                ", details=" + details +
                '}';
    }

    public void setBillDetails(List<BillDetail> billDetails) {
    }
}
