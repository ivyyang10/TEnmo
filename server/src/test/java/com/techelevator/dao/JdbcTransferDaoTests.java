package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.math.BigDecimal;

public class JdbcTransferDaoTests extends BaseDaoTests {

    private static final Transfer transfer_1 =new Transfer(3003,1001,1002,new BigDecimal(100),"Approved");

    private JdbcTransferDao dao;
    private Transfer testTransfer;
    private JdbcAccountDao accountDao;

    @Before
    public void setup(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        dao = new JdbcTransferDao(jdbcTemplate,accountDao);
        testTransfer = new Transfer(3003,1001,1002,new BigDecimal(150),"Approved");
    }

   // @Test
   // public void addTransfer_return_correct_transfer(){
    //  Transfer createdTransfer =dao.addTransfer(testTransfer);
    //  int newID =createdTransfer.getTransferID();
    //  Assert.assertTrue(newID>3000);

     // testTransfer.setTransferID(newID);
    //  assertTransferMatch(testTransfer,createdTransfer);
  //  }

  //  @Test
   // public void getTransfer_by_transferID_return_correct_transfer(){
     //   Transfer transfer =dao.getTransfer(3003);
      //  assertTransferMatch(transfer_1,transfer);
  //  }


    private void assertTransferMatch(Transfer expect, Transfer actual){
        Assert.assertEquals(expect.getTransferID(),actual.getTransferID());
        Assert.assertEquals(expect.getSenderID(),actual.getSenderID());
        Assert.assertEquals(expect.getReceiverID(),actual.getReceiverID());
        Assert.assertEquals(expect.getTransferAmount(),actual.getTransferAmount());
        Assert.assertEquals(expect.getTransferStatus(),actual.getTransferStatus());
    }
}
