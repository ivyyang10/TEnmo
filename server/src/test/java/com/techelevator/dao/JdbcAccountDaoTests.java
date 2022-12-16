package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.model.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;

public class JdbcAccountDaoTests extends BaseDaoTests{

    private static final Account account_1= new Account(2001,1001,new BigDecimal(1000.00));
    private static final Account account_2= new Account(2002,1002,new BigDecimal(1000.00));

    private JdbcAccountDao dao;
    private Account testAccount;

    @Before
    public void setup(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        dao = new JdbcAccountDao(jdbcTemplate);
        testAccount=new Account(2000,1000,new BigDecimal(1000.00));
    }

    @Test
    public void get_balance_by_username_return_correct_amount(){
        BigDecimal actual = dao.getBalance("bob");
        BigDecimal expect= (new BigDecimal(1000)).setScale(2);

        Assert.assertEquals(expect,actual);

    }

    @Test
    public void get_balance_by_use_return_correct_amount(){
        BigDecimal actual = dao.getBalance("user");
        BigDecimal expect= (new BigDecimal(1000)).setScale(2);

        Assert.assertEquals(expect,actual);

    }

    @Test
    public void get_balance_by_userID_return_correct_amount(){
        BigDecimal actual = dao.getBalanceById(1001);
        BigDecimal expect= (new BigDecimal(1000)).setScale(2);

        Assert.assertEquals(expect,actual);
    }

    @Test
    public void get_balance_by_userID_1002_return_correct_amount(){
        BigDecimal actual = dao.getBalanceById(1002);
        BigDecimal expect= (new BigDecimal(1000)).setScale(2);

        Assert.assertEquals(expect,actual);
    }

    //balanceIncrease method is void, so not able to return value to test now.
    @Test
    public void balanceIncrease_after_transfer_for_receiver(){
         dao.balanceIncrease(1001,new BigDecimal(50).setScale(2));
         BigDecimal expected =(new BigDecimal(1050)).setScale(2);
         BigDecimal result=dao.getBalance("bob");
         Assert.assertEquals(expected,result);

    }

    @Test
    public void balanceDecrease_after_transfer_for_sender(){
        dao.balanceDecrease(1001,new BigDecimal(50).setScale(2));
        BigDecimal expected =(new BigDecimal(950)).setScale(2);
        BigDecimal result=dao.getBalance("bob");
        Assert.assertEquals(expected,result);

    }


    private void assertAccountsMatch(Account expect, Account actual){
        Assert.assertEquals(expect.getAccountID(),actual.getAccountID());
        Assert.assertEquals(expect.getUserID(),actual.getUserID());
        Assert.assertEquals(expect.getBalance(),actual.getBalance());

    }


}

