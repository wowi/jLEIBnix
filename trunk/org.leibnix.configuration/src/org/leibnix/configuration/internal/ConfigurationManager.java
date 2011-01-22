package org.leibnix.configuration.internal;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;

import org.leibnix.configuration.IConfigSet;
import org.leibnix.configuration.IConfigurationManager;

public class ConfigurationManager implements IConfigurationManager {

	Connection mConnection;

	public ConfigurationManager() {
		try {
			mConnection = createConnection();
			Statement st = mConnection.createStatement();
			// st.execute("CREATE DATABASE c:\\MyDatabase");
			st.execute("USE c:\\MyDatabase");
			// st.execute("create table logging (ID UNIQUEIDENTIFIER, message varchar(2048), ts timestamp default now())");
			// st.execute("insert into logging (message) values ('Database created')");
			ResultSet rs = st.executeQuery("select count(*) from logging");
			newConfigSet("LEIBNIX_TARGETS",
					"KEY, ID String, Label String, Type int");
			if (rs.next()) {
				System.out.println("RS.intValue: " + rs.getInt(1));
			}
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public ConfigSet getConfigSet(String pConfigId, String pFilter) {
		ConfigSet configSet = null;
		try {
			if (tableExists(pConfigId)) {
				String sql = "select * from " + pConfigId;
				if (pFilter != null){
					sql = sql + " where " + pFilter;
				}
				Statement st = mConnection.createStatement();
				ResultSet rs = st.executeQuery(sql);
				configSet = new ConfigSet(pConfigId);
				HashMap values = null;
				while (rs.next()) {
					values = resultSetToHashMap(rs);
					configSet.add(values);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return configSet;
	}

	@Override
	public IConfigSet getConfigSet(String pConfigId) {
		return getConfigSet(pConfigId, null);
	}

	private HashMap resultSetToHashMap(ResultSet pResultSet)
			throws SQLException {
		HashMap ret = new HashMap();
		ResultSetMetaData md = pResultSet.getMetaData();
		int count = md.getColumnCount();
		String colName;
		Object value;
		for (int i = 0; i < count; i++) {
			colName = md.getColumnName(i + 1);
			value = pResultSet.getObject(i + 1);
			ret.put(colName, value);
		}
		return ret;
	}

	public void close() {
		try {
			mConnection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Connection createConnection() throws SQLException {
		// DriverManager.setLogStream( System.out );
		new smallsql.database.SSDriver();
//		new sun.jdbc.odbc.JdbcOdbcDriver();
		return DriverManager.getConnection("jdbc:smallsql:c:\\MyDatabase"
				+ "?locale=en;create=true");
		// return DriverManager.getConnection("jdbc:odbc:mssql","sa","");
	}

	@Override
	public void newConfigSet(String configId, String configDescr) {
		DatabaseMetaData md;
		boolean table_exists = false;
		try {
			table_exists = tableExists(configId);
			if (!table_exists) {
				createTable(configId, configDescr);
			} else {
				// FIXME: updateTable structure
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private boolean tableExists(String configId) throws SQLException {
		boolean table_exists = false;
		DatabaseMetaData md;
		md = mConnection.getMetaData();
		ResultSet rs = md.getTables(null, null, null, null);
		// ResultSetMetaData rm = rs.getMetaData();
		// int count = rm.getColumnCount();
		// for(int i=1; i<=count; i++){
		// System.out.println ("colname: " + rm.getColumnName(i));
		// }
		while (rs.next()) {
			if (rs.getString("TABLE_NAME").equals(configId)) {
				table_exists = true;
				break;
			}
		}
		return table_exists;
	}

	private void createTable(String configId, String configDescr)
			throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql = sql.append("create table ");
		sql = sql.append(configId);
		sql = sql.append(" (");
		StringTokenizer tok = new StringTokenizer(configDescr, ",");
		String col;
		String colType;
		String colDef;
		while (tok.hasMoreElements()) {
			colDef = ((String) tok.nextElement()).trim();
			if (colDef.indexOf(" ") != -1) {
				col = colDef.substring(0, colDef.indexOf(" ")).trim();
				colType = translateType(colDef.substring(
						colDef.indexOf(" ") + 1).trim());
			} else {
				col = colDef.trim();
				colType = "UNIQUEIDENTIFIER";
			}
			sql = sql.append(col);
			sql = sql.append(" ");
			sql = sql.append(colType);
			if (tok.hasMoreElements()) {
				sql = sql.append(",");
			}
		}
		sql = sql.append(")");
		Statement st = mConnection.createStatement();
		st.execute(sql.toString());
	}

	private String translateType(String pType) {
		if (pType.equalsIgnoreCase("STRING")) {
			return ("TEXT");
		} else if (pType.equalsIgnoreCase("INT")) {
			return ("INT");
		} else if (pType.equalsIgnoreCase("BOOLEAN")) {
			return ("BOOLEAN");
		}
		return null;
	}

	@Override
	public void destroyConfigSet(String pConfigId) {
		try {
			if (tableExists(pConfigId)) {
				Statement st = mConnection.createStatement();
				st.execute("drop table " + pConfigId);
				st.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void insertConfigSet(IConfigSet pConfigSet) {
		ConfigSet configSet = (ConfigSet) pConfigSet;
		try {
			if (tableExists(configSet.getId())) {
				List list = configSet.getList();

				for (int i = 0; i < list.size(); i++) {
					HashMap values = (HashMap) list.get(i);
					Iterator iter = values.keySet().iterator();
					StringBuffer sql = new StringBuffer();
					sql = sql.append("insert into ");
					sql = sql.append(configSet.getId());
					sql = sql.append(" (");
					StringBuffer val = new StringBuffer();
					while (iter.hasNext()) {
						String colName = (String) iter.next();
						sql = sql.append(colName);
						val = val.append(toSQL(values.get(colName)));
						if (iter.hasNext()) {
							sql = sql.append(",");
							val = val.append(",");
						}
					}
					sql.append(") values (");
					sql.append(val);
					sql.append(")");
					Statement st = mConnection.createStatement();
					st.execute(sql.toString());
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Object toSQL(Object pObject) {
		if (pObject instanceof String || pObject instanceof UUID) {
			return ("'" + String.valueOf((pObject) + "'"));
		} else if (pObject instanceof Integer) {
			return (String.valueOf((pObject)));
		}
		return null;
	}

	@Override
	public boolean deleteConfigSetItems(IConfigSet pConfigSet) {
		boolean ret = false;
		ConfigSet configSet = (ConfigSet) pConfigSet;
		try {
			if (tableExists(configSet.getId())) {
				List list = configSet.getList();

				for (int i = 0; i < list.size(); i++) {
					HashMap values = (HashMap) list.get(i);
					Iterator iter = values.keySet().iterator();
					StringBuffer sql = new StringBuffer();
					sql = sql.append("delete from ");
					sql = sql.append(configSet.getId());
					sql.append(" where KEY like ");
					sql.append(toSQL(values.get(iter.next())));
					System.out.println("count1: "
							+ getRowCount(configSet.getId()));
					Statement st = mConnection.createStatement();
					ret = st.execute(sql.toString());
					System.out.println("count2: "
							+ getRowCount(configSet.getId()));
				}
				ret=true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (ret);
	}

	private int getRowCount(String id) throws SQLException {
		Statement st = mConnection.createStatement();
		ResultSet result = st.executeQuery("select * from " + id);
		int count = 0;
		while (result.next()) {
			System.out.println(result.getString("KEY"));
			count++;
		}
		st.close();
		return count;
	}

	@Override
	public void rereadConigSet(IConfigSet pConfigSet) {
		try {
			ConfigSet configSet = (ConfigSet) pConfigSet;

			if (tableExists(configSet.getId())) {
				List list = configSet.getList();

				for (int i = 0; i < list.size(); i++) {
					HashMap values = (HashMap) list.get(i);
					Iterator iter = values.keySet().iterator();

					StringBuffer sql = new StringBuffer();
					sql = sql.append("select * from ");
					sql = sql.append(configSet.getId());
					sql.append(" where ID like ");
					sql.append(toSQL(values.get("ID")));
					Statement st = mConnection.createStatement();
					ResultSet result = st.executeQuery(sql.toString());
					if (result.next()) {
						String key = result.getString("KEY");
						values.put("KEY", key);
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
