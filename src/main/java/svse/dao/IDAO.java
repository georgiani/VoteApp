package svse.dao;
import java.util.List;

import ps.IObservable;

// aggregazione di famiglie di prodotti
public interface IDAO<T> {
	public T get(String id);
	public List<T> getAll();
	public void save(T t);
	public void update(T t, T u);
	public void delete(T t);
}
