package server.database.schedule;

import common.Schedule;
import server.database.ObjectHandler;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class ScheduleHandler extends ObjectHandler<Schedule> {

    public ScheduleHandler(Connection connection) { super(connection); }

    public Optional<Schedule> get(String name) throws Exception {
        return Optional.empty();
    }

    public List<Schedule> getAll() throws Exception {
        return null;
    }

    public void insert(Schedule schedule) throws Exception {

    }

    public void update(Schedule schedule) throws Exception {

    }

    public void delete(Schedule schedule) {

    }

    /**
     * Schedule a billboard's start time and duration, etc..
     *
     * @param user : a user object with contents in it
     * @throws Exception: this exception is a pass-through exception with a no results extended exception
     */
//    public String insert(String billboardName,
//                                    Date startTime,
//                                    Duration duration,
//                                    Duration interval) throws Exception {
//
//        // Query the database for the billboard
//        Statement sqlStatement = dbconn.createStatement();
//
//        //Try to select the billboard first to check if it's in the database or not
//        String query = "SELECT * FROM schedule WHERE schedule.billboardName = " + billboardName;
//
//        boolean fetchResult = sqlStatement.execute(query);
//        sqlStatement.close();
//
//        // Check if there was a result
//        if (fetchResult) {
//            ResultSet existedBillboard = sqlStatement.executeQuery(query);
//            if (existedBillboard == null) {
//                query = "INSERT INTO billboard " +
//                    "(userID, name, message, " +
//                    "messageColor, picture, backgroundColor," +
//                    " information, informationColor, locked)" +
//                    "VALUES( " + billboard.userID + "," + billboard.name + "," +
//                    billboard.message + "," + billboard.messageColor + "," + Arrays.toString(billboard.picture) + "," +
//                    billboard.backgroundColor + "," + billboard.information + "," + billboard.informationColor +
//                    "," + billboard.locked + ")";
//
//                boolean check = sqlStatement.execute(query);
//
//                if (check) {
//                    return "Insert successfully";
//                } else {
//                    throw new Exception("Error in insert");
//                }
//            } else {
//                query = "UPDATE billboard SET message = " + billboard.message + ", messageColor =" + billboard.messageColor +
//                    ", picture = " + Arrays.toString(billboard.picture) + ", backgroundColor = " + billboard.backgroundColor +
//                    ", information = " + billboard.information + ", informationColor = " + billboard.informationColor + ", locked =" + billboard.locked +
//                    "WHERE billboard.name = " + billboardName;
//                int checked = sqlStatement.executeUpdate(query);
//                if (checked > 0) {
//                    return "Update successfully";
//                } else {
//                    throw new Exception("Error in updating");
//                }
//            }
//        } else {
//            throw new Exception("No billboard with such name in database");
//        }
//    }
}
