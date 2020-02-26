package models;

import java.io.Serializable;

/**
 * This class describes the Order object.
 *
 * @author Huborevich Dmitry.
 * Created by 21.11.2019
 */
public class Order implements Serializable {

    private Integer orderId;
    private Integer clientId;
    private String orderStatus;
    private Double totalCost;

    public Order() {
    }

    public Order(Integer clientId, String orderStatus, Double totalCost) {
        this.clientId = clientId;
        this.orderStatus = orderStatus;
        this.totalCost = totalCost;
    }

    public Order(Integer orderId, Integer clientId, String orderStatus, Double totalCost) {
        this.orderId = orderId;
        this.clientId = clientId;
        this.orderStatus = orderStatus;
        this.totalCost = totalCost;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return "Order: Id = " + orderId +
                ", client Id = " + clientId +
                ", status = " + orderStatus +
                ", total cost = " + totalCost;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 17;
        result += prime * orderId.hashCode();
        result += prime * clientId.hashCode();
        result += prime * orderStatus.hashCode();
        result += prime * totalCost.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Order))
            return false;
        Order other = (Order) obj;
        if (orderId == null) {
            if (other.orderId != null)
                return false;
        } else if (!orderId.equals(other.orderId))
            return false;
        if (clientId == null) {
            if (other.clientId != null)
                return false;
        } else if (!clientId.equals(other.clientId))
            return false;
        if (orderStatus == null) {
            if (other.orderStatus != null)
                return false;
        } else if (!orderStatus.equals(other.orderStatus))
            return false;
        if (totalCost == null) {
            return other.totalCost == null;
        } else return totalCost.equals(other.totalCost);
    }
}