package ps;

public interface IObservableUDP {
	public void addObserver(IObserver o);
	public void removeObserver(IObserver o);
	public void notifyObservers();
}
