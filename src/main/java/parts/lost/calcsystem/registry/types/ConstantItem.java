package parts.lost.calcsystem.registry.types;

// Name: Jack Young
// Date: 10/8/2019

import parts.lost.calcsystem.Priority;
import parts.lost.calcsystem.types.Value;

public class ConstantItem extends Item {

	private Value value;

	public ConstantItem(String identifier, Value value) {
		this.identifier = identifier;
		this.priority = Priority.ONE;
		this.value = value;
	}

	public Value getValue() {
		return value;
	}
}
