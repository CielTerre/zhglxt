package com.seezoon.framework.modules.system.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;

/**
 * mysql 类型与mybatis jdbcType 映射
 * 
 * @author  2018年4月27日
 */
public class GenTypeMapping {

	private static Map<String, String> dbMybatisTypeMapping = new HashMap<String, String>();
	private static Map<String, String> dbJavaTypeMapping = new HashMap<String, String>();

	/**
	 * db mybatis 常用映射
	 */
	static {
		dbMybatisTypeMapping.put("varchar", "VARCHAR");
		dbMybatisTypeMapping.put("varchar2", "VARCHAR");
		dbMybatisTypeMapping.put("char", "CHAR");
		dbMybatisTypeMapping.put("tinyint", "TINYINT");
		dbMybatisTypeMapping.put("int", "INTEGER");
		dbMybatisTypeMapping.put("integer", "INTEGER");
		dbMybatisTypeMapping.put("double", "DOUBLE");
		dbMybatisTypeMapping.put("float", "REAL");
		dbMybatisTypeMapping.put("datetime", "TIMESTAMP");
		dbMybatisTypeMapping.put("date", "DATE");
		dbMybatisTypeMapping.put("time", "TIME");
		dbMybatisTypeMapping.put("timestamp", "TIMESTAMP");
		dbMybatisTypeMapping.put("decimal", "DECIMAL");
		dbMybatisTypeMapping.put("numeric", "DECIMAL");
		dbMybatisTypeMapping.put("text", "LONGVARCHAR");
		dbMybatisTypeMapping.put("tinytext", "VARCHAR");
		dbMybatisTypeMapping.put("longtext", "LONGVARCHAR");
		dbMybatisTypeMapping.put("mediumtext", "LONGVARCHAR");
		dbMybatisTypeMapping.put("bigint", "BIGINT");
		dbMybatisTypeMapping.put("number", "DECIMAL");
		/**
		 * oracle 数据库对应MyBatis javaType
		 */
		dbMybatisTypeMapping.put("blob","BLOB");
		dbMybatisTypeMapping.put("char","CHAR");
		dbMybatisTypeMapping.put("clob","CLOB");
		dbMybatisTypeMapping.put("date","DATE");
		dbMybatisTypeMapping.put("decimal","DECIMAL");
		dbMybatisTypeMapping.put("number","DOUBLE");
		dbMybatisTypeMapping.put("float","FLOAT");
		dbMybatisTypeMapping.put("integer","INTEGER");
		dbMybatisTypeMapping.put("long varchar","LONGVARCHAR");
		dbMybatisTypeMapping.put("nchar","NCHAR");
		dbMybatisTypeMapping.put("nclob","NCLOB");
		dbMybatisTypeMapping.put("numeric/number","NUMERIC");
		dbMybatisTypeMapping.put("real","REAL");
		dbMybatisTypeMapping.put("smallint","SMALLINT");
		dbMybatisTypeMapping.put("timestamp","TIMESTAMP");
		dbMybatisTypeMapping.put("varchar","VARCHAR");
		dbMybatisTypeMapping.put("varchar2","VARCHAR");

	}
	/**
	 * db java 常用映射
	 */
	static {
		dbJavaTypeMapping.put("varchar", "String");
		dbJavaTypeMapping.put("varchar2", "String");
		dbJavaTypeMapping.put("char", "String");
		dbJavaTypeMapping.put("tinyint", "Short");
		dbJavaTypeMapping.put("int", "Integer");
		dbJavaTypeMapping.put("integer", "Integer");
		dbJavaTypeMapping.put("double", "Double");
		dbJavaTypeMapping.put("float", "Float");
		dbJavaTypeMapping.put("datetime", "Date");
//		import java.util.Date;
		dbJavaTypeMapping.put("date", "Date");
		dbJavaTypeMapping.put("time", "Date");
		dbJavaTypeMapping.put("timestamp", "Date");
//		import java.math.BigDecimal;
		dbJavaTypeMapping.put("decimal", "BigDecimal");
		dbJavaTypeMapping.put("numeric", "BigDecimal");
		dbJavaTypeMapping.put("number", "BigDecimal");
		dbJavaTypeMapping.put("text", "String");
		dbJavaTypeMapping.put("tinytext", "String");
		dbJavaTypeMapping.put("longtext", "String");
		dbJavaTypeMapping.put("mediumtext", "String");
		dbJavaTypeMapping.put("bigint", "Long");

		/**
		 * oracle 数据库 对应 java中的javaType
		 */
		dbJavaTypeMapping.put("char", "String");
		dbJavaTypeMapping.put("varchar2", "String");
		dbJavaTypeMapping.put("longvarchar", "String");
		dbJavaTypeMapping.put("numeric", "BigDecimal");
		dbJavaTypeMapping.put("decimal", "BigDecimal");
		dbJavaTypeMapping.put("bit", "boolean");
		dbJavaTypeMapping.put("tinyint", "byte");
		dbJavaTypeMapping.put("smallint", "short");
		dbJavaTypeMapping.put("integer", "int");
		dbJavaTypeMapping.put("bigint", "long");
		dbJavaTypeMapping.put("real", "float");
		dbJavaTypeMapping.put("float", "double");
		dbJavaTypeMapping.put("double", "double");
		dbJavaTypeMapping.put("binary", "byte[]");
		dbJavaTypeMapping.put("varbinary", "byte[]");
		dbJavaTypeMapping.put("longvarbinary", "byte[]");
		dbJavaTypeMapping.put("date", "Date");
		dbJavaTypeMapping.put("time", "Time");
		dbJavaTypeMapping.put("timestamp", "Timestamp");
	}

	/**
	 * 通过DB 类型获得 mybatis类型
	 * 
	 * @param dbType
	 * @return
	 */
	public static String getDbMybatisMapping(String dbType) {
		Assert.hasLength(dbType, "dbType 为空");
		String mapping = dbMybatisTypeMapping.get(dbType.toLowerCase());
		Assert.notNull(mapping, dbType + "无对应mybatis 映射，请补全");
		return mapping;
	}

	/**
	 * 通过DB 类型获得 java类型
	 * 
	 * @param dbType
	 * @return
	 */
	public static String getDbJavaMapping(String dbType) {
		Assert.hasLength(dbType, "dbType 为空");
		String mapping = dbJavaTypeMapping.get(dbType.toLowerCase());
		Assert.notNull(mapping, dbType + "无对应mybatis 映射，请补全");
		return mapping;
	}
}
