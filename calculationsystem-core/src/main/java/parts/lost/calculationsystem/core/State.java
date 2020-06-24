/*
 * Copyright 2020 Jack Young
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package parts.lost.calculationsystem.core;

import parts.lost.calculationsystem.core.types.TreeType;

import java.util.*;

public class State {

	private String expression;
	private TreeType treeType;
	private final Set<CalculationManager> calculationManagerSet = new HashSet<>();
	private final Map<String, Object> storage = new HashMap<>();

	public State(String expression) {
		this.expression = expression;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public TreeType getTreeType() {
		return treeType;
	}

	public void setTreeType(TreeType treeType) {
		this.treeType = treeType;
	}

	public boolean addManager(CalculationManager calculationManager) {
		return calculationManagerSet.add(calculationManager);
	}

	public Set<CalculationManager> getCalculationManagers() {
		return calculationManagerSet;
	}

	public Map<String, Object> getStorage() {
		return storage;
	}

	@SuppressWarnings("unchecked")
	public <T> T getStorage(String key) {
		return (T) storage.get(key);
	}

	public void setStorage(String key, Object data) {
		if (data == null)
			storage.remove(key);
		else
			storage.put(key, data);
	}
}
