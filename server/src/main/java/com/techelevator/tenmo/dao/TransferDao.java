package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import java.security.Principal;
import java.util.List;

public interface TransferDao {

    Transfer sendTransfer(Transfer transfer);
    Transfer sendTransferRequest(Transfer transfer);
    Transfer getTransfer(int id);
    void rejectTransfer(Transfer transfer, int id);
    void approveTransfer(Transfer transfer, int id);
    List<Transfer> getAllByUserId(int id);
}
