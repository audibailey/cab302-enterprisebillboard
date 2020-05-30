package client.services;

/**
 * This class adds a specific type for table updating in java.
 *
 * @author Jamie Martin
 */
public abstract class DataService<T> {
   public abstract Boolean update(T t);
}
