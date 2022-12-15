package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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

    public TransferController(TransferDao transferDao, UserDao userDao) {
        this.transferDao = transferDao;
        this.userDao = userDao;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/transfer", method = RequestMethod.POST)
    public Transfer createNewTransfer(@Valid @RequestBody Transfer transfer, Principal principal){
       String loggedInUser = principal.getName();
       int userId = userDao.findIdByUsername(loggedInUser);
       transfer.setSenderID(userId);
       return transferDao.addTransfer(transfer);
    }

    @RequestMapping(path = "/transfer/username", method = RequestMethod.GET)
    public List<Transfer> listAllByUserName(Principal principal){
        String loggedInUser = principal.getName();
        int userId = userDao.findIdByUsername(loggedInUser);
        return transferDao.getAllByUserId(userId);
    }

    @RequestMapping (path = "/transfer/{id}", method = RequestMethod.GET)
    public Transfer getTransferById (@Valid @PathVariable int id){
            Transfer transfer = transferDao.getTransfer(id);
            if (transfer == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found");
            } else {
                return transfer;
            }
        }
    }


