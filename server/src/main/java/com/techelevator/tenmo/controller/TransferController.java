package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;


@PreAuthorize("isAuthenticated()")
@RestController
public class TransferController {

    private TransferDao transferDao;
    private UserDao userDao;
    private AccountDao accountDao;

    public TransferController(TransferDao transferDao, UserDao userDao, AccountDao accountDao) {
        this.transferDao = transferDao;
        this.userDao = userDao;
        this.accountDao = accountDao;
    }
    //send transfer from logged in user
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/transfer", method = RequestMethod.POST)
    public Transfer sendNewTransfer(@Valid @RequestBody Transfer transfer, Principal principal) {
        String loggedInUser = principal.getName();
        int userId = userDao.findIdByUsername(loggedInUser);
        transfer.setSenderID(userId);
        if (accountDao.getBalanceById(transfer.getSenderID()).compareTo(transfer.getTransferAmount()) == -1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transfer Amount Exceeds Current Balance");
        }
        else if(transfer.getSenderID() == transfer.getReceiverID()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Logged in user can't transfer to own account");
        }
        else {

            accountDao.balanceDecrease(transfer.getSenderID(), transfer.getTransferAmount());
            accountDao.balanceIncrease(transfer.getReceiverID(), transfer.getTransferAmount());
            return transferDao.sendTransfer(transfer);
        }
        }

        @ResponseStatus(HttpStatus.CREATED)
        @RequestMapping(path = "/transfer/request", method = RequestMethod.POST)
        public Transfer requestTransfer(@Valid @RequestBody Transfer transfer, Principal principal){
            String loggedInUser = principal.getName();
            int userId = userDao.findIdByUsername(loggedInUser);
            transfer.setSenderID(userId);
            if(transfer.getSenderID() == transfer.getReceiverID()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Logged in user can't transfer to own account");
            }
            else {
                return transferDao.sendTransferRequest(transfer);
            }
    }

    @RequestMapping(path = "/transfer/{id}/reject", method = RequestMethod.PUT)
    public void rejectTransfer(@Valid Transfer transfer, @PathVariable int id){
    transferDao.rejectTransfer(transfer, id);
    }

    @RequestMapping(path = "/transfer/{id}/approve", method = RequestMethod.PUT)
    public void acceptTransfer(@Valid Transfer transfer, @PathVariable int id, Principal principal) {
        transferDao.approveTransfer(transfer, id);
        accountDao.balanceDecrease(transfer.getSenderID(), transfer.getTransferAmount());
        accountDao.balanceIncrease(transfer.getReceiverID(), transfer.getTransferAmount());
    }


   //list all transfers by logged in users
    @RequestMapping(path = "/transfer/transfers", method = RequestMethod.GET)
    public List<Transfer> listAllByUserName(Principal principal){
    String loggedInUser = principal.getName();
    int userId = userDao.findIdByUsername(loggedInUser);
    return transferDao.getAllByUserId(userId);
    }

    //get transfer by transfer id

    @RequestMapping (path = "/transfer/{id}", method = RequestMethod.GET)
    public Transfer getTransferById (@Valid @PathVariable int id, Principal principal){
            Transfer transfer = transferDao.getTransfer(id);
            String loggedInUser = principal.getName();
            int userId = userDao.findIdByUsername(loggedInUser);
            if (transfer == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transfer not found");
            } else if(userId != transfer.getSenderID() && userId != transfer.getReceiverID()){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only access transfers associated with your account");
            }
            else {
                return transfer;
            }
        }
    }


