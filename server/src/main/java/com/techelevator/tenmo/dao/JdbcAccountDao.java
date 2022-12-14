package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BigDecimal getBalance(String username) {
        String sql ="SELECT balance From account " +
                "JOIN tenmo_user ON account.user_id=tenmo_user.user-id " +
                "WHERE username ILIKE ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, username);
       if(rowSet.next()){
           return mapRowToAccount(rowSet).getBalance();
       }
        throw new UsernameNotFoundException("User " + username + " was not found.");

    }

    @Override
    public void balanceIncrease(String userName, BigDecimal transferAmount) {
        String sql ="UPDATE account SET balance= balance + ? " +
                "JOIN tenmo_transfer ON tenmo_transfer.receiver_id = account.user_id " +
                "WHERE account.user_id =(SELECT user_id FROM tenmo_user WHERE username ILIKE ?;" ;
        jdbcTemplate.update(sql,transferAmount,userName);


    }

    @Override
    public void balanceDecrease(String userName, BigDecimal transferAmount) {
        String sql ="UPDATE account SET balance= balance - ? " +
                "JOIN tenmo_transfer ON tenmo_transfer.sender_id = account.user_id " +
                "WHERE account.user_id =(SELECT user_id FROM tenmo_user WHERE username ILIKE ?;" ;
        jdbcTemplate.update(sql,transferAmount,userName);
    }

    private Account mapRowToAccount(SqlRowSet rs) {
        Account account = new Account();
        account.setAccountID(rs.getInt("account_id"));
        account.setUserID(rs.getInt("user_id"));
        account.setBalance(rs.getBigDecimal("balance"));

        return account;
    }

}
