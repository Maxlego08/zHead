package fr.maxlego08.head.zcore.enums;

public enum Permission {
	
	EXAMPLE_PERMISSION,
	EXAMPLE_PERMISSION_RELOAD,

	;

	private String permission;

	private Permission() {
		this.permission = this.name().toLowerCase().replace("_", ".");
	}

	public String getPermission() {
		return permission;
	}

}
