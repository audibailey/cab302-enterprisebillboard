package server;

import common.models.Billboard;
import server.database.DataService;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws Exception {
	    // write your code here

        DataService dataService = new DataService(true);

        dataService.billboards.getAll();
        dataService.users.getAll();
        dataService.schedules.getAll();

        // Billboard.fromObject(dataService.billboards.get("Billboard 1"));

        while (true) {
            System.out.println("Server");
            Thread.sleep(1000);
        }
    }
}
