package data;

import java.security.MessageDigest;
import java.sql.Timestamp;
import java.util.Date;

import observer.Subject;

public class Transaction extends Subject<Transaction> {
    private final String id;
    private final ERC99 token;
    private final String sender, reciver;
    private final double amount, rate;
    private String prevId, nextId;
    private STATUS status;
    private Date timestamp, updateAt;

    public Transaction(ERC99 token, String sender, String reciver, double amount, double rate) {
        // create transaction and hashing data wiht SHA256
        this.token = token;
        this.sender = sender;
        this.reciver = reciver;
        if (token instanceof CSToken) {
            if (rate < 100) {
                rate = 100.00;
            } else if (rate > 150) {
                rate = 150.00;
            }
        }
        this.amount = amount;
        this.rate = rate;
        this.status = STATUS.Awaiting;
        Date dateNow = new Timestamp(System.currentTimeMillis());
        this.timestamp = dateNow;
        this.updateAt = dateNow;
        StringBuilder sb = new StringBuilder();
        sb.append(token.name)
                .append(token.symbol)
                .append(reciver)
                .append(sender)
                .append(amount)
                .append(rate)
                .append(this.timestamp.getTime());
        this.id = sha256(sb.toString());
    }

    public String getId() {
        return id;
    }

    public ERC99 getToken() {
        return token;
    }

    public String getSender() {
        return sender;
    }

    public String getReciver() {
        return reciver;
    }

    public double getAmount() {
        return amount;
    }

    public double getRate() {
        return rate;
    }

    public String getPrevId() {
        return prevId;
    }

    public void setPrevId(String prevId) {
        this.prevId = prevId;
    }

    public String getNextId() {
        return nextId;
    }

    public void setNextId(String nextId) {
        this.nextId = nextId;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        if (this.status == status)
            return;
        STATUS old = this.status;
        this.status = status;
        // notify observer
        propertyChanged(this, "status", status, old);
        setUpdateAt(new Timestamp(System.currentTimeMillis()));
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    private void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String sha256(final String base) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            final byte[] hash = digest.digest(base.getBytes("UTF-8"));
            final StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                final String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String toString() {
        // build string of transaction data
        StringBuilder sb = new StringBuilder();
        sb.append("ID: " + id + System.lineSeparator())
                .append("Satus: " + status + System.lineSeparator())
                .append("Sender:" + System.lineSeparator())
                .append(sender + System.lineSeparator())
                .append("Token:" + System.lineSeparator())
                .append("[" + token.symbol + "] " + token.name + " amount: " + amount + " rate: " + rate
                        + System.lineSeparator())
                .append("Reciver:" + System.lineSeparator())
                .append(reciver + System.lineSeparator())
                .append("previous ID: " + prevId + System.lineSeparator())
                .append("next ID: " + nextId + System.lineSeparator())
                .append("create at: " + timestamp + System.lineSeparator())
                .append("latest update at: " + updateAt + System.lineSeparator());
        return sb.toString();
    }
}