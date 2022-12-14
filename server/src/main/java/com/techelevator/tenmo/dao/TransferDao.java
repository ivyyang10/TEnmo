package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import java.security.Principal;

public interface TransferDao {

    Transfer addTransfer(Transfer transfer, Principal principal);
    Transfer getTransfer(int id);
}
