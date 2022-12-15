package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import java.security.Principal;

public interface TransferDao {

    Transfer addTransfer(Transfer transfer);
    Transfer getTransfer(int id);
}
