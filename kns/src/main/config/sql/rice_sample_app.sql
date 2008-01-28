create table trv_doc_2 (
        FDOC_NBR                       VARCHAR2(14) CONSTRAINT FP_INT_BILL_DOC_TN1 NOT NULL,
        OBJ_ID                         VARCHAR2(36) DEFAULT SYS_GUID() CONSTRAINT FP_INT_BILL_DOC_TN2 NOT NULL,
        VER_NBR                        NUMBER(8) DEFAULT 1 CONSTRAINT FP_INT_BILL_DOC_TN3 NOT NULL,
        FDOC_EXPLAIN_TXT               VARCHAR2(400),
	    request_trav varchar2(30) not null,
	    traveler          varchar2(200),
        org          varchar2(60),
        dest         varchar2(60),
	    CONSTRAINT trv_doc_2P1 PRIMARY KEY (FDOC_NBR)
)
/

create table trv_acct (
    acct_num  varchar2(10) not null,
    acct_name varchar2(50),
    acct_type varchar2(100),
    acct_fo_id number(14),
    constraint trv_acct_pk primary key(acct_num)
)
/

create table trv_doc_acct (
    doc_hdr_id  number(14) not null,
    acct_num    varchar2(10) not null,
    constraint trv_doc_acct_pk primary key(doc_hdr_id, acct_num)
)
/

create table trv_acct_fo (
	acct_fo_id  number(14) not null,
	acct_fo_user_name varchar2(50) not null,
	constraint trv_acct_fo_id_pk primary key(acct_fo_id)
)
/

create table TRAV_DOC_2_ACCOUNTS (
    FDOC_NBR VARCHAR2(14),
    ACCT_NUM varchar2(10),
    CONSTRAINT TRAV_DOC_2_ACCOUNTS_P1 PRIMARY KEY (FDOC_NBR, ACCT_NUM)
)
/

create table TRV_ACCT_TYPE (
    ACCT_TYPE VARCHAR2(10),
    ACCT_TYPE_NAME varchar2(50),
    CONSTRAINT TRV_ACCT_TYPE_PK PRIMARY KEY (ACCT_TYPE)
)
/

create table TRV_ACCT_EXT (
    ACCT_NUM VARCHAR2(10),
    ACCT_TYPE varchar2(100),
    CONSTRAINT TRV_ACCT_TYPE_P1 PRIMARY KEY (ACCT_NUM, ACCT_TYPE)
)
/

CREATE SEQUENCE SEQ_TRAVEL_DOC_ID INCREMENT BY 1 START WITH 1000
/
CREATE SEQUENCE SEQ_TRAVEL_FO_ID INCREMENT BY 1 START WITH 1000
/

alter table trv_acct add constraint trv_acct_fk1 foreign key(acct_fo_id) references trv_acct_fo(acct_fo_id)
/

insert into trv_acct_fo (acct_fo_id, acct_fo_user_name) values (1, 'fred')
/
insert into trv_acct_fo (acct_fo_id, acct_fo_user_name) values (2, 'fran')
/
insert into trv_acct_fo (acct_fo_id, acct_fo_user_name) values (3, 'frank')
/

insert into TRV_ACCT values ('a1', 'a1', 'CAT', 1)
/
insert into TRV_ACCT values ('a2', 'a2', 'EAT', 2)
/
insert into TRV_ACCT values ('a3', 'a3', 'IAT', 3)
/

insert into TRV_DOC_ACCT (DOC_HDR_ID, ACCT_NUM) values (1, 'a1')
/
insert into TRV_DOC_ACCT (DOC_HDR_ID, ACCT_NUM) values (1, 'a2')
/
insert into TRV_DOC_ACCT (DOC_HDR_ID, ACCT_NUM) values (1, 'a3')
/

insert into en_usr_t values ('quickstart','quickstart','quickstart','quickstart','quickstart@school.edu','quickstart','quickstart','quickstart',to_date('01/01/2000', 'dd/mm/yyyy'),to_date('01/01/2100', 'dd/mm/yyyy'),0,0)
/
insert into en_wrkgrp_t values (1,1,'WorkflowAdmin',1,'W','Workflow Administrator Workgroup',1,null,0)
/
insert into EN_WRKGRP_MBR_T values ('quickstart',1,'U',1,0)
/

INSERT INTO FP_DOC_GROUP_T VALUES ('TR', '054EDFB3B260C8D2E043814FD881C8D2', 1,	'Travel Documents', null)
/
INSERT INTO FP_DOC_GROUP_T VALUES ('MO', '054EDFB3B260C8D2E043816FD881C8EE', 1,	'Obsolete Maintenance Table', null)
/
INSERT INTO FP_DOC_GROUP_T VALUES ('MR', '054EDFB3B260C8D2E043816FD881C8EA', 1,	'Reference Table Maintenance', null)
/
insert into FP_DOC_TYPE_T values ('TRAV', '1A6FEB2501C7607EE043814FD881607E', 1, 'TR', 'TRAV ACCNT', 'N', 'Y', 'N', 0, 'N', 'N')
/
insert into FP_DOC_TYPE_T values ('TRFO', '1A6FEB250342607EE043814FD881607E', 1, 'TR', 'TRAV FO', 'N', 'Y', 'N', 0, 'N', 'N')
/
insert into FP_DOC_TYPE_T values ('TRD2', '1A6FEB250342607EE043814FD889607E', 1, 'TR', 'TRAV D2', 'N', 'Y', 'N', 0, 'N', 'N')
/
insert into FP_DOC_TYPE_T values ('RUSR', '1A6FEB253342607EE043814FD889607E', 1, 'TR', 'RICE USR', 'N', 'Y', 'N', 0, 'N', 'N')
/
insert into FP_DOC_TYPE_T values ('PARM', '1A6FRB253342607EE043814FD889607E', 1, 'TR', 'System Parms', 'N', 'Y', 'N', 0, 'N', 'N')
/
insert into FP_DOC_TYPE_T values ('BR', '1A6FRB253343337EE043814FD889607E', 1, 'TR', 'Biz Rules', 'N', 'Y', 'N', 0, 'N', 'N')
/
insert into FP_DOC_TYPE_T values ('TRVA', '1A5FEB250342607EE043814FD889607E', 1, 'TR',  'TRAV MAINT', 'N', 'Y', 'N', 0, 'N', 'N')
/
INSERT INTO FP_DOC_TYPE_T VALUES ('PTYP', '1A6FEB2501C7607EE043814FD111607E', 1, 'MO', 'Parameter Type', 'N', 'Y', 'N', 0, 'N', 'N')
/
INSERT INTO FP_DOC_TYPE_T VALUES ('PDTP', '1A6FEB2501C7607EE043814FD112607E', 1, 'MR', 'Parameter Detailed Type', 'N', 'Y', 'N', 0, 'N', 'N')
/
INSERT INTO FP_DOC_TYPE_T VALUES ('PNMS', '1A6FEB2501C7607EE043814FD113607E', 1, 'MR', 'Parameter Namespace', 'N', 'Y', 'N', 0, 'N', 'N')
/
insert into TRV_ACCT_EXT values ('a1', 'IAT')
/
insert into TRV_ACCT_EXT values ('a2', 'EAT')
/
insert into TRV_ACCT_EXT values ('a3', 'IAT')
/
insert into TRV_ACCT_TYPE values ('CAT', 'Clearing Account Type')
/
insert into TRV_ACCT_TYPE values ('EAT', 'Expense Account Type')
/
insert into TRV_ACCT_TYPE values ('IAT', ' Income Account Type')
/
-- KEN sample data --

-- NOTIFICATION_PRODUCERS --
INSERT INTO NOTIFICATION_PRODUCERS
(ID, NAME, DESCRIPTION, CONTACT_INFO)
VALUES
(2, 'University Library System', 'This producer represents messages sent from the University Library system.', 'kuali-ken-testing@cornell.edu')
/

INSERT INTO NOTIFICATION_PRODUCERS
(ID, NAME, DESCRIPTION, CONTACT_INFO)
VALUES
(3, 'University Events Office', 'This producer represents messages sent from the University Events system.', 'kuali-ken-testing@cornell.edu')
/

-- NOTIFICATION_CHANNELS --
DELETE FROM NOTIFICATION_CHANNELS
/

INSERT INTO NOTIFICATION_CHANNELS
(ID, NAME, DESCRIPTION, SUBSCRIBABLE)
VALUES
(1, 'Kuali Rice Channel', 'This channel is used for sending out information about the Kuali Rice effort.', 'Y')
/

INSERT INTO NOTIFICATION_CHANNELS
(ID, NAME, DESCRIPTION, SUBSCRIBABLE)
VALUES
(2, 'Library Events Channel', 'This channel is used for sending out information about Library Events.', 'Y')
/

INSERT INTO NOTIFICATION_CHANNELS
(ID, NAME, DESCRIPTION, SUBSCRIBABLE)
VALUES
(3, 'Overdue Library Books', 'This channel is used for sending out information about your overdue books.', 'N')
/

INSERT INTO NOTIFICATION_CHANNELS
(ID, NAME, DESCRIPTION, SUBSCRIBABLE)
VALUES
(4, 'Concerts Coming to Campus', 'This channel broadcasts any concerts coming to campus.', 'Y')
/

INSERT INTO NOTIFICATION_CHANNELS
(ID, NAME, DESCRIPTION, SUBSCRIBABLE)
VALUES
(5, 'University Alerts', 'This channel broadcasts general announcements for the university.', 'N')
/

-- NOTIFICATION_CHANNEL_SUBSCRIPTIONS

INSERT INTO USER_CHANNEL_SUBSCRIPTIONS
(ID, CHANNEL_ID, USER_ID)
VALUES
(1, 1, 'TestUser4')
/

-- NOTIFICATION_RECIPIENTS_LISTS

INSERT INTO NOTIFICATION_RECIPIENTS_LISTS
(ID, CHANNEL_ID, RECIPIENT_TYPE, RECIPIENT_ID)
values
(1, 4, 'USER', 'TestUser1')
/

INSERT INTO NOTIFICATION_RECIPIENTS_LISTS
(ID, CHANNEL_ID, RECIPIENT_TYPE, RECIPIENT_ID)
values
(2, 4, 'USER', 'TestUser3')
/

-- NOTIFICATION_CHANNEL_REVIEWERS

INSERT INTO NOTIFICATION_REVIEWERS
(ID, CHANNEL_ID, REVIEWER_TYPE, REVIEWER_ID)
VALUES
(1, 1, 'GROUP', 'RiceTeam')
/

INSERT INTO NOTIFICATION_REVIEWERS
(ID, CHANNEL_ID, REVIEWER_TYPE, REVIEWER_ID)
VALUES
(2, 5, 'USER', 'TestUser3')
/

INSERT INTO NOTIFICATION_REVIEWERS
(ID, CHANNEL_ID, REVIEWER_TYPE, REVIEWER_ID)
VALUES
(3, 5, 'GROUP', 'TestGroup1')
/

-- NOTIFICATION_CHANNEL_PRODUCERS --
DELETE FROM NOTIFICATION_CHANNEL_PRODUCERS
/

INSERT INTO NOTIFICATION_CHANNEL_PRODUCERS
(CHANNEL_ID, PRODUCER_ID)
VALUES
(1, 1)
/

INSERT INTO NOTIFICATION_CHANNEL_PRODUCERS
(CHANNEL_ID, PRODUCER_ID)
VALUES
(2, 1)
/

INSERT INTO NOTIFICATION_CHANNEL_PRODUCERS
(CHANNEL_ID, PRODUCER_ID)
VALUES
(3, 1)
/

INSERT INTO NOTIFICATION_CHANNEL_PRODUCERS
(CHANNEL_ID, PRODUCER_ID)
VALUES
(4, 1)
/

INSERT INTO NOTIFICATION_CHANNEL_PRODUCERS
(CHANNEL_ID, PRODUCER_ID)
VALUES
(5, 1)
/

INSERT INTO NOTIFICATION_CHANNEL_PRODUCERS
(CHANNEL_ID, PRODUCER_ID)
VALUES
(2, 2)
/

INSERT INTO NOTIFICATION_CHANNEL_PRODUCERS
(CHANNEL_ID, PRODUCER_ID)
VALUES
(3, 2)
/

INSERT INTO NOTIFICATION_CHANNEL_PRODUCERS
(CHANNEL_ID, PRODUCER_ID)
VALUES
(4, 3)
/


commit
/
