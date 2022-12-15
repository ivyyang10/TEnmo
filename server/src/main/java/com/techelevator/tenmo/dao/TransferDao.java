package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import java.security.Principal;
import java.util.List;

public interface TransferDao {

    Transfer addTransfer(Transfer transfer);
    Transfer getTransfer(int id);
    List<Transfer> getAllByUserId(int id);
}
