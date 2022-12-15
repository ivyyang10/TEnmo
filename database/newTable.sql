
BEGIN TRANSACTION;

DROP TABLE IF EXISTS tenmo_transfer CASCADE;


DROP SEQUENCE IF EXISTS seq_user_id, seq_account_id;

CREATE SEQUENCE seq_transfer_id
  INCREMENT BY 1
  START WITH 3001
  NO MAXVALUE;

  CREATE TABLE tenmo_transfer(
      transfer_id int NOT NULL DEFAULT nextval('seq_transfer_id'),
      sender_username varchar(50) NOT NULL,
      sender_id int NOT NULL,
      receiver_username varchar(50) NOT NULL,
      receiver_id int NOT NULL,
      transfer_amount numeric(13, 2) NOT NULL,
      transfer_status varchar(12), 
      CONSTRAINT PK_tenmo_transfer PRIMARY KEY (transfer_id),
      CONSTRAINT FK_tenmo_transfer_sender_username FOREIGN KEY (sender_username) REFERENCES tenmo_user(username),
      CONSTRAINT FK_tenmo_transfer_receiver_username FOREIGN KEY (receiver_username) REFERENCES tenmo_user(username),
      CONSTRAINT FK_tenmo_transfer_sender_id FOREIGN KEY (sender_id) REFERENCES account(user_id),
      CONSTRAINT FK_tenmo_transfer_receiver_id FOREIGN KEY (receiver_id) REFERENCES account(user_id),
      CONSTRAINT CK_transfer_status CHECK (transfer_status IN ('Approved', 'Pending', 'Rejected'))
);


COMMIT;