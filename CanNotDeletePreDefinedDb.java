package com.jdbcproj;

public class CanNotDeletePreDefinedDb extends Exception{
	public CanNotDeletePreDefinedDb(String msg) {
		super(msg);
	}
}
