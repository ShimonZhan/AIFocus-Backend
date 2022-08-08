USE information_schema;

SELECT C.TABLE_NAME                         AS '表名',
       T.TABLE_COMMENT                      AS '表中文名',
       C.COLUMN_NAME                        AS '字段名',
       C.COLUMN_COMMENT                     AS '字段中文名',
       C.COLUMN_TYPE                        AS '数据类型',
       if(C.COLUMN_KEY = 'PRI', 'TRUE', '') AS '主键'

FROM COLUMNS C
         INNER JOIN TABLES T ON C.TABLE_SCHEMA = T.TABLE_SCHEMA
    AND C.TABLE_NAME = T.TABLE_NAME
WHERE T.TABLE_SCHEMA = 'aifocus'
-- 		and T.TABLE_NAME = 'answer_sheet'