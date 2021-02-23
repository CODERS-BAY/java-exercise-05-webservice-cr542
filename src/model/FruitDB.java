package model;

import java.util.ArrayList;
import java.util.List;

public class FruitDB {
	private static List<Fruit> fruits = new ArrayList<>();

	private FruitDB() {
	}

	public static List<Fruit> getFruits() {
		return fruits;
	}

	public static void insertFruit(Fruit fruit) {
		if (fruit == null) {
			throw new IllegalArgumentException("Cannot insert empty fruit");
		}

		Fruit newFruit = getFruit(fruit);

		if (newFruit == null) {
			fruits.add(fruit);
		} else {
			newFruit.increment();
		}
	}

	public static boolean deleteFruit(Fruit fruit) {
		if (fruit == null) {
			throw new IllegalArgumentException("Cannot delete empty fruit");
		}

		Fruit fruitToDelete = getFruit(fruit);
		if (fruitToDelete == null) {
			return false;
		}

		fruitToDelete.decrement();

		if (fruitToDelete.getCount() <= 0) {
			return fruits.remove(fruitToDelete);
		}
		
		return true;
	}

	private static Fruit getFruit(Fruit fruit) {
		int fruitIndex = fruits.indexOf(fruit);
		return fruitIndex == -1 ? null : fruits.get(fruitIndex);
	}
}
