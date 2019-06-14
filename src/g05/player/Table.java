package g05.player;

public class Table {
	Road [] roadVector = new Road[19 * 19 * 4];
	int size;
	public Table() {size = 0;}
	
	void Add(Road road)
	{
		roadVector[size] = road;
		road.index = size++;
	}
	
	void Remove(Road road)
	{
		roadVector[road.index] = roadVector[--size];
		roadVector[road.index].index = road.index;
	}
}
