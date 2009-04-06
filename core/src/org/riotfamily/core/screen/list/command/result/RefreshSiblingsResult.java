package org.riotfamily.core.screen.list.command.result;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteMethod;
import org.springframework.util.ObjectUtils;

@DataTransferObject
public class RefreshSiblingsResult implements CommandResult {

	private String objectId;
	
	public RefreshSiblingsResult() {
	}
		
	public RefreshSiblingsResult(String objectId) {
		this.objectId = objectId;
	}

	@RemoteMethod
	public String getAction() {
		return "refreshSiblings";
	}
	
	@RemoteMethod
	public String getObjectId() {
		return objectId;
	}
	
	public int hashCode() {
		return objectId != null ? objectId.hashCode() : 0;
	}
	
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof RefreshSiblingsResult) {
			RefreshSiblingsResult other = (RefreshSiblingsResult) obj;
			return ObjectUtils.nullSafeEquals(objectId, other.objectId);
		}
		return false;
	}

}
