package com.techelevator.tenmo.dao;

import java.math.BigDecimal;

public interface AccountDao {

   BigDecimal getBalance(String userName);
   BigDecimal getBalanceById(int userId);
   void balanceIncrease(int userId, BigDecimal transferAmount);
   void balanceDecrease(int userId, BigDecimal transferAmount);

}
