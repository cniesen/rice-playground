CREATE INDEX EN_EDL_FIELD_DMP_TI1
      ON EN_EDL_FIELD_DMP_T (DOC_HDR_ID, FLD_NM, FLD_VAL)
/
CREATE INDEX EN_EDL_FIELD_DMP_TI2
      ON EN_EDL_FIELD_DMP_T (FLD_NM, FLD_VAL)
/