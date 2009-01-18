-- for kim document tables.  May want to merge to kim_tables.sql
CREATE TABLE krim_person_document_t
(
    FDOC_NBR          VARCHAR2(14) CONSTRAINT krim_person_document_tn1 NOT NULL,
    entity_id         VARCHAR2(40) CONSTRAINT krim_person_document_tn2 NOT NULL,
    obj_id            VARCHAR2(36) DEFAULT SYS_GUID() CONSTRAINT krim_person_document_tn3 NOT NULL,
    ver_nbr           NUMBER(8,0) DEFAULT 1 CONSTRAINT krim_person_document_tn4 NOT NULL,
    prncpl_id 		  VARCHAR2(40)  CONSTRAINT krim_person_document_tn5 NOT NULL,
    prncpl_nm         VARCHAR2(100)  CONSTRAINT krim_person_document_tn6 NOT NULL,
    prncpl_pswd       VARCHAR2(400),
    tax_id 		  VARCHAR2(40),
    univ_id 		  VARCHAR2(40),
    actv_ind          VARCHAR2(1) DEFAULT 'Y',
    CONSTRAINT krim_person_document_tp1 PRIMARY KEY ( FDOC_NBR )
)
/

CREATE TABLE KRIM_PND_AFLTN_MT ( 
    FDOC_NBR          VARCHAR2(14)  NOT NULL,
    ENTITY_AFLTN_ID	VARCHAR2(40) NOT NULL,
    AFLTN_TYP_CD   	VARCHAR2(40) NULL,
    CAMPUS_CD      	VARCHAR2(2) NULL,
    EDIT_FLAG    	VARCHAR2(1) DEFAULT 'N' NULL,
    DFLT_IND    	VARCHAR2(1) DEFAULT 'N' NULL,
    ACTV_IND       	VARCHAR2(1) DEFAULT 'Y' NULL,
    obj_id            VARCHAR2(36) DEFAULT SYS_GUID() NOT NULL,
    ver_nbr           NUMBER(8,0) DEFAULT 1  NOT NULL,
    PRIMARY KEY(ENTITY_AFLTN_ID,FDOC_NBR)
)
/

ALTER TABLE KRIM_PND_AFLTN_MT 
ADD CONSTRAINT KRIM_PND_AFLTN_MT_FK1
FOREIGN KEY (FDOC_NBR) 
REFERENCES krim_person_document_t (FDOC_NBR) ENABLE

/

CREATE TABLE KRIM_PND_CTZNSHP_MT ( 
    FDOC_NBR          VARCHAR2(14)  NOT NULL,
    ENTITY_CTZNSHP_ID	VARCHAR2(40) NOT NULL,
    OBJ_ID           	VARCHAR2(36) NOT NULL,
    VER_NBR          	NUMBER(8,0) DEFAULT 1 NOT NULL,
    POSTAL_CNTRY_CD  	VARCHAR2(2) NULL,
    CTZNSHP_STAT_CD  	VARCHAR2(40) NULL,
    STRT_DT          	DATE NULL,
    END_DT           	DATE NULL,
    ACTV_IND         	VARCHAR2(1) DEFAULT 'Y' NULL,
    EDIT_FLAG    	VARCHAR2(1) DEFAULT 'N' NULL,
    PRIMARY KEY(ENTITY_CTZNSHP_ID,FDOC_NBR)
)
/

CREATE TABLE KRIM_PND_EMP_INFO_MT ( 
    FDOC_NBR          VARCHAR2(14)  NOT NULL,
    PRMRY_DEPT_CD  	VARCHAR2(40) NULL,
    ENTITY_EMP_ID  	VARCHAR2(40) NOT NULL,
    EMP_ID         	VARCHAR2(40) NULL,
    EMP_REC_ID     	VARCHAR2(40) NULL,
    OBJ_ID         	VARCHAR2(36) NOT NULL,
    VER_NBR        	NUMBER(8,0) DEFAULT 1 NOT NULL,
    ENTITY_AFLTN_ID	VARCHAR2(40) NULL,
    EMP_STAT_CD    	VARCHAR2(40) NULL,
    EMP_TYP_CD     	VARCHAR2(40) NULL,
    BASE_SLRY_AMT  	NUMBER(15,2) NULL,
    PRMRY_IND      	VARCHAR2(1) NULL,
    ACTV_IND       	VARCHAR2(1) DEFAULT 'Y' NULL,
    EDIT_FLAG    	VARCHAR2(1) DEFAULT 'N' NULL,
    PRIMARY KEY(ENTITY_EMP_ID,FDOC_NBR)
)
/

CREATE TABLE KRIM_PND_NM_MT ( 
    FDOC_NBR          VARCHAR2(14)  NOT NULL,
    ENTITY_NM_ID	VARCHAR2(40) NOT NULL,
    OBJ_ID      	VARCHAR2(36) NOT NULL,
    VER_NBR     	NUMBER(8,0) DEFAULT 1 NOT NULL,
    NM_TYP_CD   	VARCHAR2(40) NULL,
    FIRST_NM    	VARCHAR2(40) NULL,
    MIDDLE_NM   	VARCHAR2(40) NULL,
    LAST_NM     	VARCHAR2(80) NULL,
    SUFFIX_NM   	VARCHAR2(20) NULL,
    TITLE_NM    	VARCHAR2(20) NULL,
    DFLT_IND    	VARCHAR2(1) DEFAULT 'N' NULL,
    ACTV_IND    	VARCHAR2(1) DEFAULT 'Y' NULL,
    EDIT_FLAG    	VARCHAR2(1) DEFAULT 'N' NULL,
    PRIMARY KEY(ENTITY_NM_ID,FDOC_NBR)
)
/

CREATE TABLE KRIM_PND_ADDR_MT ( 
    FDOC_NBR          VARCHAR2(14)  NOT NULL,
    ADDR_TYP_CD    	VARCHAR2(40) NULL,
    ADDR_LINE_1    	VARCHAR2(50) NULL,
    ADDR_LINE_2    	VARCHAR2(50) NULL,
    ADDR_LINE_3    	VARCHAR2(50) NULL,
    CITY_NM        	VARCHAR2(30) NULL,
    POSTAL_STATE_CD	VARCHAR2(2) NULL,
    POSTAL_CD      	VARCHAR2(20) NULL,
    POSTAL_CNTRY_CD	VARCHAR2(2) NULL,
    DISPLAY_SORT_CD	VARCHAR2(2) NULL,
    DFLT_IND       	VARCHAR2(1) DEFAULT 'N' NULL,
    ACTV_IND       	VARCHAR2(1) DEFAULT 'Y' NULL,
    ENTITY_ADDR_ID 	VARCHAR2(40) NOT NULL,
    OBJ_ID         	VARCHAR2(36) NOT NULL,
    VER_NBR        	NUMBER(8,0) DEFAULT 1 NOT NULL,
    EDIT_FLAG    	VARCHAR2(1) DEFAULT 'N' NULL,
    PRIMARY KEY(ENTITY_ADDR_ID,FDOC_NBR)
)
/

CREATE TABLE KRIM_PND_PHONE_MT ( 
    FDOC_NBR          VARCHAR2(14)  NOT NULL,
    ENTITY_PHONE_ID	VARCHAR2(40) NOT NULL,
    OBJ_ID         	VARCHAR2(36) NOT NULL,
    VER_NBR        	NUMBER(8,0) DEFAULT 1 NOT NULL,
    ENT_TYP_CD     	VARCHAR2(40) NULL,
    PHONE_TYP_CD   	VARCHAR2(40) NULL,
    PHONE_NBR      	VARCHAR2(20) NULL,
    PHONE_EXTN_NBR 	VARCHAR2(8) NULL,
    POSTAL_CNTRY_CD	VARCHAR2(2) NULL,
    DFLT_IND       	VARCHAR2(1) DEFAULT 'N' NULL,
    ACTV_IND       	VARCHAR2(1) DEFAULT 'Y' NULL,
    EDIT_FLAG    	VARCHAR2(1) DEFAULT 'N' NULL,
    PRIMARY KEY(ENTITY_PHONE_ID,FDOC_NBR)
)
/

CREATE TABLE KRIM_PND_EMAIL_MT ( 
    FDOC_NBR          VARCHAR2(14)  NOT NULL,
    ENTITY_EMAIL_ID	VARCHAR2(40) NOT NULL,
    OBJ_ID         	VARCHAR2(36) NOT NULL,
    VER_NBR        	NUMBER(8,0) DEFAULT 1 NOT NULL,
    ENT_TYP_CD     	VARCHAR2(40) NULL,
    EMAIL_TYP_CD   	VARCHAR2(40) NULL,
    EMAIL_ADDR     	VARCHAR2(200) NULL,
    DFLT_IND       	VARCHAR2(1) DEFAULT 'N' NULL,
    ACTV_IND       	VARCHAR2(1) DEFAULT 'Y' NULL,
    EDIT_FLAG    	VARCHAR2(1) DEFAULT 'N' NULL,
    PRIMARY KEY(ENTITY_EMAIL_ID,FDOC_NBR)
)
/

CREATE TABLE KRIM_PND_PRIV_PREF_MT ( 
    FDOC_NBR          VARCHAR2(14)  NOT NULL,
    OBJ_ID            	VARCHAR2(36) NOT NULL,
    VER_NBR           	NUMBER(8,0) DEFAULT 1 NOT NULL,
    SUPPRESS_NM_IND   	VARCHAR2(1) DEFAULT 'N' NULL,
    SUPPRESS_EMAIL_IND	VARCHAR2(1) DEFAULT 'Y' NULL,
    SUPPRESS_ADDR_IND 	VARCHAR2(1) DEFAULT 'Y' NULL,
    SUPPRESS_PHONE_IND	VARCHAR2(1) DEFAULT 'Y' NULL,
    SUPPRESS_PRSNL_IND	VARCHAR2(1) DEFAULT 'Y' NULL,
    EDIT_FLAG    	VARCHAR2(1) DEFAULT 'N' NULL,
    PRIMARY KEY(FDOC_NBR)
)
/

CREATE TABLE KRIM_PND_ROLE_MT ( 
    FDOC_NBR          VARCHAR2(14)  NOT NULL,
    ROLE_ID     	VARCHAR2(40) NOT NULL,
    OBJ_ID      	VARCHAR2(36) NOT NULL,
    VER_NBR     	NUMBER(8,0) DEFAULT 1 NOT NULL,
    ROLE_NM     	VARCHAR2(100) NOT NULL,
    KIM_TYP_ID  	VARCHAR2(40) NULL,
    ACTV_IND    	VARCHAR2(1) DEFAULT 'Y' NULL,
    NMSPC_CD  	VARCHAR2(40) NULL,
    EDIT_FLAG    	VARCHAR2(1) DEFAULT 'N' NULL,
    PRIMARY KEY(ROLE_ID,FDOC_NBR)
)
/
CREATE TABLE KRIM_PND_ROLE_MBR_MT ( 
    FDOC_NBR        VARCHAR2(14) NOT NULL,
    ROLE_MBR_ID  	VARCHAR2(40) NOT NULL,
    OBJ_ID      	VARCHAR2(36) NOT NULL,
    VER_NBR     	NUMBER(8,0)  DEFAULT 1 NOT NULL,
    ROLE_ID      	VARCHAR2(40) NOT NULL,
    MBR_ID   		VARCHAR2(40),
    MBR_NM			VARCHAR2(40),
    MBR_TYP_CD		VARCHAR2(40) NOT NULL,
    ACTV_IND    	VARCHAR2(1)  DEFAULT 'Y' NULL,
    ACTV_FRM_DT  	DATE NULL,
    ACTV_TO_DT  	DATE NULL,
    EDIT_FLAG    	VARCHAR2(1)  DEFAULT 'N' NULL,
    PRIMARY KEY(ROLE_MBR_ID,FDOC_NBR)
)
/
             
CREATE TABLE KRIM_PND_ROLE_MBR_ATTR_DATA_MT ( 
    FDOC_NBR          VARCHAR2(14)  NOT NULL,
    ATTR_DATA_ID      	VARCHAR2(40) NOT NULL,
    OBJ_ID            	VARCHAR2(36) NOT NULL,
    VER_NBR           	NUMBER(8,0) DEFAULT 1 NOT NULL,
    TARGET_PRIMARY_KEY	VARCHAR2(40) NULL,
    KIM_TYP_ID        	VARCHAR2(40) NULL,
    KIM_ATTR_DEFN_ID  	VARCHAR2(40) NULL,
    ATTR_VAL          	VARCHAR2(400) NULL,
    ACTV_IND    	VARCHAR2(1) DEFAULT 'Y' NULL,
    EDIT_FLAG    	VARCHAR2(1) DEFAULT 'N' NULL,
    PRIMARY KEY(ATTR_DATA_ID,FDOC_NBR)
)
/

CREATE TABLE KRIM_PND_GRP_PRNCPL_MT ( 
    GRP_MBR_ID  	VARCHAR2(40) NOT NULL,
    FDOC_NBR          VARCHAR2(14)  NOT NULL,
    OBJ_ID      	VARCHAR2(36) NOT NULL,
    VER_NBR     	NUMBER(8,0) DEFAULT 1 NOT NULL,
    GRP_ID      	VARCHAR2(40) NOT NULL,
    PRNCPL_ID   	VARCHAR2(40) ,
    ACTV_IND    	VARCHAR2(1) DEFAULT 'Y' NULL,
    GRP_NM      	VARCHAR2(80) NOT NULL,
    GRP_TYPE      	VARCHAR2(80) ,
    KIM_TYP_ID  	VARCHAR2(40) NULL,
    NMSPC_CD  	VARCHAR2(40) NULL,
    ACTV_FRM_DT  	DATE NULL,
    ACTV_TO_DT  	DATE NULL,
    EDIT_FLAG    	VARCHAR2(1) DEFAULT 'N' NULL,
    PRIMARY KEY(GRP_MBR_ID,FDOC_NBR)
)
/

CREATE TABLE KRIM_PND_DLGN_MT ( 
    FDOC_NBR        VARCHAR2(14)  NOT NULL,
    DLGN_ID     	VARCHAR2(40) NOT NULL,
    ROLE_ID     	VARCHAR2(40) NOT NULL,
    OBJ_ID      	VARCHAR2(36) NOT NULL,
    VER_NBR     	NUMBER(8,0) DEFAULT 1 NOT NULL,
    KIM_TYP_ID  	VARCHAR2(40) NULL,
    DLGN_TYP_CD    	VARCHAR2(100) NOT NULL,
    ACTV_IND    	VARCHAR2(1) DEFAULT 'Y' NULL,
    PRIMARY KEY(DLGN_ID,FDOC_NBR)
)
/

CREATE TABLE krim_role_document_t
(
    FDOC_NBR          VARCHAR2(14) 		CONSTRAINT krim_role_document_tn1 NOT NULL,
    role_id           VARCHAR2(40) 		CONSTRAINT krim_role_document_tn2 NOT NULL,
    obj_id            VARCHAR2(36) 		DEFAULT SYS_GUID() CONSTRAINT krim_role_document_tn3 NOT NULL,
    ver_nbr           NUMBER(8,0)  		DEFAULT 1 CONSTRAINT krim_role_document_tn4 NOT NULL,
    role_typ_id 	  VARCHAR2(40) 	 	CONSTRAINT krim_role_document_tn5 NOT NULL,
    role_typ_name     VARCHAR2(40) 		CONSTRAINT krim_role_document_tn6 NOT NULL,
    role_nmspc        VARCHAR2(100)  	CONSTRAINT krim_role_document_tn7 NOT NULL,
    role_nm       	  VARCHAR2(400),
    actv_ind          VARCHAR2(1) 		DEFAULT 'Y',
    CONSTRAINT krim_person_document_tp1 PRIMARY KEY ( FDOC_NBR )
)
/
