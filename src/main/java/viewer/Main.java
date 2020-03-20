package viewer;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        // write your code here
        while (true) {
            System.out.println("Viewer");
            Thread.sleep(1000);
        }
    }

    public static int ExampleFunction(int num) throws Exception {
        if (num > 0) {
            return num;
        } else {
            throw new Exception("Negative or 0 not valid");
        }
    }
}
