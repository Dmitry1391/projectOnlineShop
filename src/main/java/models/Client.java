package models;

import java.io.Serializable;

/**
 * This class describes the Client object.
 *
 * @author Huborevich Dmitry.
 * Created by 21.11.2019
 */
public class Client implements Serializable {

    private Integer clientId;
    private String name;
    private String clientLogin;
    private String password;
    private Boolean blackList;

    public Client(String name, String clientLogin, String password, Boolean blackList) {
        this.name = name;
        this.clientLogin = clientLogin;
        this.password = password;
        this.blackList = blackList;
    }

    public Client(Integer clientId, String name, String clientLogin, String password, Boolean blackList) {
        this.clientId = clientId;
        this.name = name;
        this.clientLogin = clientLogin;
        this.password = password;
        this.blackList = blackList;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClientLogin() {
        return clientLogin;
    }

    public void setClientLogin(String clientLogin) {
        this.clientLogin = clientLogin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getBlackList() {
        return blackList;
    }

    public void setBlackList(Boolean blackList) {
        this.blackList = blackList;
    }

    @Override
    public String toString() {
        return "Client: id = " + clientId +
                ", name = " + name +
                ", login = " + clientLogin +
                ", password = " + password +
                ", black list = " + blackList;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 17;
        result += prime * blackList.hashCode();
        result += prime * name.hashCode();
        result += prime * clientLogin.hashCode();
        result += prime * password.hashCode();
        result += prime * clientId.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Client))
            return false;
        Client other = (Client) obj;
        if (blackList == null) {
            if (other.blackList != null)
                return false;
        } else if (!blackList.equals(other.blackList))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (clientLogin == null) {
            if (other.clientLogin != null)
                return false;
        } else if (!clientLogin.equals(other.clientLogin))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (clientId == null) {
            if (other.clientId != null)
                return false;
        } else if (!clientId.equals(other.clientId))
            return false;
        return true;
    }
}