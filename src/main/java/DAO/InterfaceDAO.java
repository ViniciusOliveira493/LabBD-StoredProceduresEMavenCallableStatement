package DAO;

import java.sql.SQLException;
import java.util.List;

public interface InterfaceDAO<T> {
	public String create(T obj) throws SQLException;
	public String update(T obj) throws SQLException;
	public String delete(T obj) throws SQLException;
	public T read(T obj) throws Exception;
	public List<T> list() throws Exception;
}
