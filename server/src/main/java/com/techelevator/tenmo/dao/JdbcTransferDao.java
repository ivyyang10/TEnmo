package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcTransferDao implements TransferDao{
    public JdbcTransferDao(JdbcAccountDao dao) {
        this.dao = dao;
    }

    private JdbcTemplate jdbcTemplate;
     private JdbcAccountDao dao= new JdbcAccountDao(jdbcTemplate);


    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Transfer addTransfer(Transfer transfer) {
        String sql="INSERT INTO tenmo_transfer(sender_id, receiver_id,transfer_amount,transfer_status) "+
                "VALUES(?,?,?,'approved') RETURNING transfer_id;";
        Integer transferID=jdbcTemplate.queryForObject(sql,Integer.class,transfer.getSenderID(),transfer.getReceiverID(),
                transfer.getTransferAmount(),transfer.getTransferStatus());

        String sql1="SELECT username FROM tenmo_user " +
                "JOIN account ON tenmo_user.user_id = account.user_id " +
                "WHERE account.user_id =?;";
        SqlRowSet receiverName = jdbcTemplate.queryForRowSet(sql1,transfer.getReceiverID());





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
        transfer.setTransferStatus(rowSet.getNString("transfer_status"));

        return transfer;
    }
}
