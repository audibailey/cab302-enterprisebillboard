package client.services;

import common.models.Permissions;
import common.models.Session;
import common.utils.ClientSocketFactory;

import java.util.List;

public abstract class DataService<T> {
   public abstract List<T> update(T t);
}
