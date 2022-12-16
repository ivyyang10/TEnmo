package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;


import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao{


    private JdbcTemplate jdbcTemplate;



    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }



    ///trying to figure out how to not send more than we have
    @Override
    public Transfer addTransfer(Transfer transfer) {

        String sql = "INSERT INTO tenmo_transfer(sender_id, receiver_id,transfer_amount,transfer_status) " +
                "VALUES(?,?,?,'Approved') RETURNING transfer_id;";
        Integer transferID = jdbcTemplate.queryForObject(sql, Integer.class, transfer.getSenderID(), transfer.getReceiverID(),
                transfer.getTransferAmount());
        transfer.setTransferID(transferID);
            //MOVED BELOW TO THE CONTROLLER TO ALLOW FOR BETTER TESTING
            //accountDao.balanceDecrease(transfer.getSenderID(), transfer.getTransferAmount());
            //accountDao.balanceIncrease(transfer.getReceiverID(), transfer.getTransferAmount());
            return getTransfer(transferID);
        }

    @Override
    public List<Transfer> getAllByUserId(int id) {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT * FROM tenmo_transfer WHERE sender_id = ? OR receiver_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id, id);
        while(results.next()) {
            Transfer transfer = mapRowToTransfer(results);
            transfers.add(transfer);
        }
        return transfers;
    }

    @Override
    public Transfer getTransfer(int id) {
        Transfer transfer=null;
        String sql = "SELECT * FROM tenmo_transfer " +
                "WHERE transfer_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        if (results.next()) {
            transfer = mapRowToTransfer(results);
        }
        return transfer;
    }






    private Transfer mapRowToTransfer(SqlRowSet rowSet){
        Transfer transfer = new Transfer();
        transfer.setTransferID(rowSet.getInt("transfer_id"));
        transfer.setSenderID(rowSet.getInt("sender_id"));
        transfer.setReceiverID(rowSet.getInt("receiver_id"));
        transfer.setTransferAmount(rowSet.getBigDecimal("transfer_amount"));
        transfer.setTransferStatus(rowSet.getString("transfer_status"));

        return transfer;
    }
}
