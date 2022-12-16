package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;

public interface AccountDao {

   Account getAccount(int userID);
   BigDecimal getBalance(String userName);
   BigDecimal getBalanceById(int userId);
   void balanceIncrease(int userId, BigDecimal transferAmount);
   void balanceDecrease(int userId, BigDecimal transferAmount);

}
