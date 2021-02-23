package model;

public class Fruit {
	private final String variety; // Sorte
	private final String color;
	private int count;

	public Fruit(String variety, String color) {
		super();
		this.variety = variety;
		this.color = color;
		count = 1;
	}

	public void increment() {
		count++;
	}

	public void decrement() {
		count--;
	}
	
	public int getCount() {
		return count;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + count;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj instanceof Fruit) {
			Fruit other = (Fruit) obj;
			return ((this.variety.equals(other.variety)) && (this.color.equals(other.color)));
		}
		
		return false;

	}
}
