package com.techelevator.tenmo.dao;

import java.math.BigDecimal;

public interface AccountDao {

   BigDecimal getBalance(String userName);
   void balanceIncrease(String userName, BigDecimal transferAmount);
   void balanceDecrease(String userName, BigDecimal transferAmount);
}
