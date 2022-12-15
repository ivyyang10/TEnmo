package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;


import java.security.Principal;

@Component
public class JdbcTransferDao implements TransferDao{


    private JdbcTemplate jdbcTemplate;
     private JdbcAccountDao accountDao;


    public JdbcTransferDao(JdbcTemplate jdbcTemplate, JdbcAccountDao accountDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.accountDao = accountDao;
    }

    @Override
    public Transfer addTransfer(Transfer transfer) {
       // boolean transferSuccess = false;
        String sql="INSERT INTO tenmo_transfer(sender_id, receiver_id,transfer_amount,transfer_status) "+
                "VALUES(?,?,?,'Approved') RETURNING transfer_id;";
        Integer transferID=jdbcTemplate.queryForObject(sql,Integer.class,transfer.getSenderID(),transfer.getReceiverID(),
                transfer.getTransferAmount());
        transfer.setTransferID(transferID);
        //if(accountDao.getBalance(transfer.getSenderID()).compareTo(transfer.getTransferAmount()) == 1) {
            accountDao.balanceDecrease(transfer.getSenderID(), transfer.getTransferAmount());
            accountDao.balanceIncrease(transfer.getReceiverID(), transfer.getTransferAmount());
            //transferSuccess = true;
            return getTransfer(transferID);
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
