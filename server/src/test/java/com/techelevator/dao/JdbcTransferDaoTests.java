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
import java.util.List;

public class JdbcTransferDaoTests extends BaseDaoTests {

    private static final Transfer transfer_1 =new Transfer(3001,1001,1002,new BigDecimal(50),"Approved");
    private static final Transfer transfer_2 =new Transfer(3002,1002,1001,new BigDecimal(100),"Approved");

    private JdbcTransferDao dao;
    private Transfer testTransfer;


    @Before
    public void setup(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        dao = new JdbcTransferDao(jdbcTemplate);
        testTransfer = new Transfer(3003,1001,1002,new BigDecimal(150),"Approved");
    }

   @Test
   public void addTransfer_return_correct_transfer(){
    Transfer createdTransfer =dao.sendTransfer(testTransfer);
    int newID =createdTransfer.getTransferID();
    BigDecimal balance = createdTransfer.getTransferAmount().setScale(0);
    createdTransfer.setTransferAmount(balance);
    Assert.assertTrue(newID>3000);

     testTransfer.setTransferID(newID);
      assertTransferMatch(testTransfer,createdTransfer);
   }
///transfer is null?
    @Test
    public void getTransfer_by_transferID_return_correct_transfer(){
        Transfer transfer = dao.getTransfer(3001);
        transfer.setTransferAmount(transfer.getTransferAmount().setScale(0));
        assertTransferMatch(transfer_1, transfer);
   }

   @Test
   public void get_all_users_by_id_returns_list_of_all_transfers_by_id(){
        List<Transfer> transferList = dao.getAllByUserId(1001);
        Assert.assertEquals(2, transferList.size());
   }


    private void assertTransferMatch(Transfer expect, Transfer actual){
        Assert.assertEquals(expect.getTransferID(),actual.getTransferID());
        Assert.assertEquals(expect.getSenderID(),actual.getSenderID());
        Assert.assertEquals(expect.getReceiverID(),actual.getReceiverID());
        Assert.assertEquals(expect.getTransferAmount(),actual.getTransferAmount());
        Assert.assertEquals(expect.getTransferStatus(),actual.getTransferStatus());
    }
}
