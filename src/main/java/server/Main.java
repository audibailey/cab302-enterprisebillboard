package server;

import common.models.Billboard;
import common.models.Response;
import common.models.User;
import server.database.DataService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
	    // write your code here

        DataService dataService = new DataService(true);

        dataService.billboards.getAll();
        //dataService.users.getAll();
        dataService.schedules.getAll();

        // Billboard.fromObject(dataService.billboards.get("Billboard 1"));

        new Response<User>(new ArrayList<User>(), null);

        while (true) {
            System.out.println("Server");
            Thread.sleep(1000);
        }
    }
}
