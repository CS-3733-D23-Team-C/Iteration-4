package edu.wpi.teamc.dao.map;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EdgeDaoTest {

  @Test
  public void fetchAllObjects() {
    EdgeDao edgeDao = new EdgeDao();
    List<Edge> edges = edgeDao.fetchAllObjects();

    Assertions.assertNotNull(edges);
  }
  // make sure tests are bug free
  @Test
  public void updateRow() {
    EdgeDao edgeDao = new EdgeDao();
    Edge edge = new Edge(105, 110);
    Edge edgeRepl = new Edge(105, 190);
    Assertions.assertEquals(1, edgeDao.updateRow(edge, edgeRepl));
  }

  @Test
  public void addRow() {
    EdgeDao edgeDao = new EdgeDao();
    Edge edge = new Edge(105, 110);
    Assertions.assertEquals(1, edgeDao.addRow(edge));
  }

  @Test
  public void deleteRow() {
    EdgeDao edgeDao = new EdgeDao();
    Edge edge = new Edge(105, 190);
    Assertions.assertEquals(1, edgeDao.deleteRow(edge));
  }
}
