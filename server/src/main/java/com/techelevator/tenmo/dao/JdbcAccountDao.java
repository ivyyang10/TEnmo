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
    public BigDecimal getBalance(String userName) {
        String sql ="SELECT * From account " +
                "JOIN tenmo_user ON account.user_id=tenmo_user.user_id " +
                "WHERE username ILIKE ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userName);
       if(rowSet.next()){
           return mapRowToAccount(rowSet).getBalance();
       }
        throw new UsernameNotFoundException("User " + userName + " was not found.");

    }

    @Override
    public BigDecimal getBalanceById(int id){
        String sql = "SELECT * FROM account " +
                "WHERE user_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, id);
        if(rowSet.next()){
            return mapRowToAccount(rowSet).getBalance();
        }  throw new UsernameNotFoundException("User Id " + id + " was not found.");
    }

    @Override
    public void balanceIncrease(int userId, BigDecimal transferAmount) {
        String sql ="UPDATE account SET balance = balance + ? " +
                    "WHERE account.user_id = ?" ;
        jdbcTemplate.update(sql,transferAmount, userId);
    }

    @Override
    public void balanceDecrease(int userId, BigDecimal transferAmount) {
        String sql ="UPDATE account SET balance = balance - ?" +
                "WHERE account.user_id = ?" ;
        jdbcTemplate.update(sql, transferAmount, userId);
    }

    private Account mapRowToAccount(SqlRowSet rs) {
        Account account = new Account();
        account.setAccountID(rs.getInt("account_id"));
        account.setUserID(rs.getInt("user_id"));
        account.setBalance(rs.getBigDecimal("balance"));

        return account;
    }

}
