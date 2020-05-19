package server.ControllerTest;

import common.models.Billboard;
import common.models.User;
import common.router.IActionResult;
import common.router.Request;
import common.router.Status;
import org.junit.jupiter.api.Test;
import server.controllers.BillboardController;
import server.sql.CollectionFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class BillboardControllerTest {
    @Test
    public void testAnInsert() throws Exception {
        // Generate the billboard data to insert.
        Billboard bb = Billboard.Random(1);

        // Create new request.
        Request req = new Request(null, "blah", null, bb);

        // Insert the billboard to the database
        IActionResult test = new BillboardController.Insert().execute(req);
        assertEquals(Status.SUCCESS,test.status);
    }

    @Test
    public void testInsertExisted() throws Exception {
        // Generate the billboard data to insert
        Billboard bb = Billboard.Random(1);
        bb.name = "Existed";

        // Create new request.
        Request req = new Request(null, "blah", null, bb);

        // Insert the billboard to the database
        IActionResult test = new BillboardController.Insert().execute(req);

        // Generate another billboard data with existed name
        bb = Billboard.Random(1);
        bb.name = "Existed";

        // Create new request.
        req = new Request(null, "blah", null, bb);

        // Insert the billboard to the database
        test = new BillboardController.Insert().execute(req);
        assertEquals(Status.BAD_REQUEST,test.status);
    }

    @Test
    public void testJDelete() throws Exception {
        // Generate the billboard data to insert
        Billboard bb = Billboard.Random(1);
        bb.name = "Deleted";

        // Create new request.
        Request req = new Request(null, "blah", null, bb);

        // Insert the billboard to the database
        IActionResult test = new BillboardController.Insert().execute(req);

        // Get all billboard from database
        List<Billboard> billboardList = CollectionFactory.getInstance(Billboard.class).get(billboard -> true);
        // Find the billboard to delete
        Billboard deleted = null;
        for (Billboard temp: billboardList)
        {
            if (temp.name.equals("Deleted"))
            {
                deleted = temp;
                break;
            }
        }
        // Create new request.
        req = new Request(null, "blah", null, deleted);

        // Delete the billboard from the database
        test = new BillboardController.Delete().execute(req);
        assertEquals(Status.SUCCESS,test.status);
    }

    @Test
    public void testGetAll() throws Exception {

        // Create new request.
        Request req = new Request(null, "blah", null, null);

        // Get all the billboard from database
        IActionResult result = new BillboardController.Get().execute(req);

        assertEquals(Status.SUCCESS,result.status);
    }

    @Test
    public void testGetByName() throws Exception {
        // Insert a billboard with the name "BillboardTest"
        Billboard bb = Billboard.Random(1);
        bb.name = "BillboardTest";
        // Create new request.
        Request req = new Request(null, "blah", null, bb);

        // Insert the billboard to the database
        IActionResult test = new BillboardController.Insert().execute(req);

        HashMap<String, String> params = new HashMap<>();
        String billboardName = "BillboardTest";
        params.put("name", billboardName);
        // Create new request.
        req = new Request(null, "blah", params, null);

        // Get all the billboard from database
        IActionResult result = new BillboardController.GetByName().execute(req);
        Billboard resultBB = (Billboard) ((ArrayList) result.body).get(0);


        assertEquals(resultBB.name,bb.name);
    }

}

