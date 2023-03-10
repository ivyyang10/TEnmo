package com.techelevator.tenmo.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class Transfer {

    private int transferID;
    private int senderID;
    @NotNull
    private int receiverID;
    @Positive(message = "The transfer amount must be greater than 0.")
    private BigDecimal transferAmount;
    private String transferStatus;

    public Transfer() {
    }

    public Transfer(int transferID, int senderID, int receiverID, BigDecimal transferAmount, String transferStatus) {
        this.transferID = transferID;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.transferAmount = transferAmount;
        this.transferStatus = transferStatus;
    }

    public int getTransferID() {
        return transferID;
    }

    public void setTransferID(int transferID) {
        this.transferID = transferID;
    }

    public int getSenderID() {
        return senderID;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    public int getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(int receiverID) {
        this.receiverID = receiverID;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    public String getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(String transferStatus) {
        this.transferStatus = transferStatus;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "transferID=" + transferID +
                ", senderID=" + senderID +
                ", receiverID=" + receiverID +
                ", transferAmount=" + transferAmount +
                ", transferStatus='" + transferStatus + '\'' +
                '}';
    }
}
