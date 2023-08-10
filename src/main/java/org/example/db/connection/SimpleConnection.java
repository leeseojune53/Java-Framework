package db.connection;

public class SimpleConnection implements Connection {

    private boolean autoCommit = false;
    private boolean isClose = false; // If Connection commit, then Connection is close.

    @Override
    public boolean getAutoCommit() {
        return autoCommit;
    }

    @Override
    public void setAutoCommit(boolean autoCommit) {
        this.autoCommit = autoCommit;
    }

    @Override
    public void commit() {
        this.close();
    }

    @Override
    public void rollback() {
        // TODO Something Rollback
        this.close();
    }

    @Override
    public boolean isClose() {
        return isClose;
    }

    @Override
    public void close() {
        this.isClose = true;
    }
}
