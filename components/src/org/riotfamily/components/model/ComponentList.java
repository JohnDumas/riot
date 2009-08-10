/* ***** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is Riot.
 *
 * The Initial Developer of the Original Code is
 * Neteye GmbH.
 * Portions created by the Initial Developer are Copyright (C) 2007
 * the Initial Developer. All Rights Reserved.
 *
 * Contributor(s):
 *   flx
 *
 * ***** END LICENSE BLOCK ***** */
package org.riotfamily.components.model;

import java.util.ArrayList;

import org.riotfamily.common.collection.DirtyCheckList;
import org.springframework.util.Assert;

public class ComponentList extends DirtyCheckList<Component> 
		implements ContentPart {
	
	private String partId;
	
	private Content owner;

	public ComponentList(Content owner) {
		this(owner, owner.nextPartId());
	}
	
	public ComponentList(Content owner, String partId) {
		super(new ArrayList<Component>());
		Assert.notNull(owner, "owner must not be null");
		Assert.notNull(partId, "partId must not be null");
		this.owner = owner;
		this.partId = partId;
		owner.registerPart(this);
	}

	@Override
	protected void dirty() {
		owner.dirty();
	}
	
	public String getPartId() {
		return partId;
	}
	
	public String getId() {
		return owner.getPublicId(this);
	}

	public Content getOwner() {
		return owner;
	}

	@Override
	public int hashCode() {
		return partId.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		ComponentList other = (ComponentList) o;
		if (o instanceof ComponentList) {
			return getId().equals(other.getId());
		}
		return false;
	}
	
	public static ComponentList load(String listId) {
		return (ComponentList) Content.loadPart(listId);
	}
	
}
