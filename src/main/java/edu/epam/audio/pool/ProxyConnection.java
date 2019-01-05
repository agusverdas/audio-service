package edu.epam.audio.pool;

import com.mysql.cj.jdbc.exceptions.OperationNotSupportedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

public class ProxyConnection implements Connection, Comparable<ProxyConnection> {
    private static Logger logger = LogManager.getLogger();

    private Connection connection;

    ProxyConnection(Connection connection){
        this.connection = connection;
    }

    public Statement createStatement() throws SQLException {
        return connection.createStatement();
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }

    public CallableStatement prepareCall(String sql) throws SQLException {
        throw new OperationNotSupportedException("Operation not supported.");
    }

    public String nativeSQL(String sql) throws SQLException {
        throw new OperationNotSupportedException("Operation not supported.");
    }

    public void setAutoCommit(boolean autoCommit) throws SQLException {
        connection.setAutoCommit(autoCommit);
    }

    public boolean getAutoCommit() throws SQLException {
        return connection.getAutoCommit();
    }

    public void commit() throws SQLException {
        connection.commit();
    }

    public void rollback() throws SQLException {
        connection.rollback();
    }

    public void close() throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        pool.releaseConnection(this);
    }

    void destroy()  {
        try {
            this.close();
        } catch (SQLException e) {
            logger.error("Error while destroying connection.", e);
        }
    }

    public boolean isClosed() throws SQLException {
        return connection.isClosed();
    }

    public DatabaseMetaData getMetaData() throws SQLException {
        throw new OperationNotSupportedException("Operation not supported.");
    }

    public void setReadOnly(boolean readOnly) throws SQLException {
        throw new OperationNotSupportedException("Operation not supported.");
    }

    public boolean isReadOnly() throws SQLException {
        throw new OperationNotSupportedException("Operation not supported.");
    }

    public void setCatalog(String catalog) throws SQLException {
        throw new OperationNotSupportedException("Operation not supported.");
    }

    public String getCatalog() throws SQLException {
        throw new OperationNotSupportedException("Operation not supported.");
    }

    public void setTransactionIsolation(int level) throws SQLException {
        throw new OperationNotSupportedException("Operation not supported.");
    }

    public int getTransactionIsolation() throws SQLException {
        throw new OperationNotSupportedException("Operation not supported.");
    }

    public SQLWarning getWarnings() throws SQLException {
        throw new OperationNotSupportedException("Operation not supported.");
    }

    public void clearWarnings() throws SQLException {
        throw new OperationNotSupportedException("Operation not supported.");
    }

    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        throw new OperationNotSupportedException("Operation not supported.");
    }

    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        throw new OperationNotSupportedException("Operation not supported.");
    }

    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        throw new OperationNotSupportedException("Operation not supported.");
    }

    public Map<String, Class<?>> getTypeMap() throws SQLException {
        throw new OperationNotSupportedException("Operation not supported.");
    }

    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
        throw new OperationNotSupportedException("Operation not supported.");
    }

    public void setHoldability(int holdability) throws SQLException {
        throw new OperationNotSupportedException("Operation not supported.");
    }

    public int getHoldability() throws SQLException {
        throw new OperationNotSupportedException("Operation not supported.");
    }

    public Savepoint setSavepoint() throws SQLException {
        throw new OperationNotSupportedException("Operation not supported.");
    }

    public Savepoint setSavepoint(String name) throws SQLException {
        throw new OperationNotSupportedException("Operation not supported.");
    }

    public void rollback(Savepoint savepoint) throws SQLException {
        throw new OperationNotSupportedException("Operation not supported.");
    }

    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        throw new OperationNotSupportedException("Operation not supported.");
    }

    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        throw new OperationNotSupportedException("Operation not supported.");
    }

    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        throw new OperationNotSupportedException("Operation not supported.");
    }

    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        throw new OperationNotSupportedException("Operation not supported.");
    }

    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        throw new OperationNotSupportedException("Operation not supported.");
    }

    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        throw new OperationNotSupportedException("Operation not supported.");
    }

    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        throw new OperationNotSupportedException("Operation not supported.");
    }

    public Clob createClob() throws SQLException {
        throw new OperationNotSupportedException("Operation not supported.");
    }

    public Blob createBlob() throws SQLException {
        throw new OperationNotSupportedException("Operation not supported.");
    }

    public NClob createNClob() throws SQLException {
        throw new OperationNotSupportedException("Operation not supported.");
    }

    public SQLXML createSQLXML() throws SQLException {
        throw new OperationNotSupportedException("Operation not supported.");
    }

    public boolean isValid(int timeout) throws SQLException {
        throw new OperationNotSupportedException("Operation not supported.");
    }

    public void setClientInfo(String name, String value) throws SQLClientInfoException {
        connection.setClientInfo(name, value);
    }

    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        connection.setClientInfo(properties);
    }

    public String getClientInfo(String name) throws SQLException {
        throw new OperationNotSupportedException("Operation not supported.");
    }

    public Properties getClientInfo() throws SQLException {
        throw new OperationNotSupportedException("Operation not supported.");
    }

    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        throw new OperationNotSupportedException("Operation not supported.");
    }

    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        throw new OperationNotSupportedException("Operation not supported.");
    }

    public void setSchema(String schema) throws SQLException {
        throw new OperationNotSupportedException("Operation not supported.");
    }

    public String getSchema() throws SQLException {
        throw new OperationNotSupportedException("Operation not supported.");
    }

    public void abort(Executor executor) throws SQLException {
        throw new OperationNotSupportedException("Operation not supported.");
    }

    public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
        throw new OperationNotSupportedException("Operation not supported.");
    }

    public int getNetworkTimeout() throws SQLException {
        throw new OperationNotSupportedException("Operation not supported.");
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new OperationNotSupportedException("Operation not supported.");
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new OperationNotSupportedException("Operation not supported.");
    }

    @Override
    public int compareTo(ProxyConnection connection) {
        return hashCode() - connection.hashCode();
    }
}
