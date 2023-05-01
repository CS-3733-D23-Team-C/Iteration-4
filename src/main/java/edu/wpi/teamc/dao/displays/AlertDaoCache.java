package edu.wpi.teamc.dao.displays;

import edu.wpi.teamc.CApp;
import edu.wpi.teamc.dao.DBConnection;
import java.sql.Connection;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.postgresql.PGConnection;
import org.postgresql.util.PSQLException;

public class AlertDaoCache extends AlertDao {
  String table = "Alert";
  String tableDollarSign = "$Alert$";
  String notifyFunction = "notifyAlert()";
  String updateTrigger = "alertUpdate";
  String UpdateFK = "alertUpdateIDFK";
  private final @NonNull Map<Integer, Alert> alerts; // The cache
  private @NonNull Connection listenerConnection; // The connection to be used for listening
  DBConnection dbConnection = new DBConnection(CApp.getWpiDB());

  /**
   * Sets up a refresh on query DAO, creating the function and trigger necessary for notification.
   * Also begins listening
   */
  @SneakyThrows
  AlertDaoCache() {
    super();
    listenerConnection = dbConnection.getConnection();

    // In PSQL, triggers can only execute functions. So simply create a function that calls NOTIFY
    // on person
    // everytime something is changed
    listenerConnection
        .prepareStatement(
            "CREATE OR REPLACE FUNCTION "
                + notifyFunction
                + " RETURNS TRIGGER AS "
                + tableDollarSign
                + "BEGIN "
                + "NOTIFY"
                + table
                + ";"
                + "RETURN NULL;"
                + // Return value ignored
                "END;"
                + tableDollarSign
                + " language plpgsql")
        .execute();

    // Now create a trigger that references that function on any change
    listenerConnection
        .prepareStatement(
            "CREATE OR REPLACE TRIGGER "
                + updateTrigger
                + " AFTER UPDATE OR INSERT OR DELETE ON "
                + table
                + " FOR EACH STATEMENT EXECUTE FUNCTION "
                + notifyFunction)
        .execute();

    // We need to make sure we keep the Person objects we have up-to-date, so any time that occurs,
    // we need
    // to refresh what we have. We don't care about insert/delete, as delete will be handled by the
    // cascade,
    // insert means no login. Only update actually matters
    listenerConnection
        .prepareStatement(
            "CREATE OR REPLACE TRIGGER "
                + UpdateFK
                + " AFTER UPDATE "
                + "ON person FOR EACH STATEMENT EXECUTE FUNCTION "
                + notifyFunction)
        .execute();

    // Begin listening on the person table. This means our connection will get notified any time the
    // above
    // trigger is executed
    reListen();

    alerts = new HashMap<>();
  }

  /** Re-listens to the table, meant to be used every time a connection is re-created */
  @SneakyThrows
  private void reListen() {
    listenerConnection.prepareStatement("LISTEN login").execute();
  }

  /** Triggers a refresh of the ENTIRE table */
  private void invalidateTable() {
    // Get all of the people in the DB
    Collection<Alert> allRows = super.fetchAllObjects();

    alerts.clear(); // Clear all people in the cache

    // For each row, insert it into the cache
    for (Alert alert : allRows) {
      alerts.put(alert.getId(), alert);
    }
  }

  /** Checks if invalidation is necessary, and invalidates the table if necessary */
  @SneakyThrows
  private void checkAndInvalidate() {
    // Get the base driver to check for notifications
    PGConnection driver = listenerConnection.unwrap(PGConnection.class);

    try {
      // Try getting notifications
      if (driver.getNotifications().length > 0) {
        invalidateTable();
      }
      // If we get an error, we have timed out, and we MUST re-build the table
      // and connection, as we may have missed an update message
    } catch (PSQLException error) {
      listenerConnection = dbConnection.getConnection();
      reListen();
      invalidateTable();
    }
  }

  /**
   * Checks for any notifications, and if any are waiting, refreshes the entire table
   *
   * @return all of the logins in the table
   */
  public @NonNull Collection<Alert> getAll() {
    checkAndInvalidate();

    return new LinkedList<>(
        alerts.values()); // Return a copy of the logins, so that we don't reflect changes
  }

  /**
   * Checks for any notifications, and if any are waiting, refreshes the entire table
   *
   * @param key the key to fetch
   * @return the login associated with the given person, or null if none exists
   */
  public Alert get(@NonNull Integer key) {
    checkAndInvalidate();

    return alerts.get(key);
  }

  /** Closes the listening connection, allowing everything to be cleaned up */
  @SneakyThrows
  public void close() {
    super.close();
    listenerConnection.close();
  }
}
