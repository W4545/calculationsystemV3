package parts.lost.calcsystem.types.operations;

import parts.lost.calcsystem.types.TreeType;
import parts.lost.calcsystem.types.Value;

public interface GenOperation {

	/**
	 *
	 * @param array Value objects corresponding to those passed into generator by user
	 * @return A number
	 */
	Value operation(TreeType[] array);
}
