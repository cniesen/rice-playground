CREATE TABLE EN_RTE_NODE_CFG_PARM_T (
    RTE_NODE_CFG_PARM_ID    NUMBER(19) NOT NULL,
    RTE_NODE_CFG_PARM_ND    NUMBER(19) NOT NULL,
    RTE_NODE_CFG_PARM_KEY   VARCHAR2(255) NOT NULL,
    RTE_NODE_CFG_PARM_VAL   VARCHAR2(4000),
    CONSTRAINT EN_RTE_NODE_CFG_PARM_T_PK PRIMARY KEY (RTE_NODE_CFG_PARM_ID) USING INDEX
)
/