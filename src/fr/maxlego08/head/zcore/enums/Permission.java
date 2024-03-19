package fr.maxlego08.head.zcore.enums;

public enum Permission {

	ZHEAD_USE,
    ZHEAD_RELOAD,
    ZHEAD_HELP,

	ZHEAD_UPDATE;

	private String permission;

	private Permission() {
		this.permission = this.name().toLowerCase().replace("_", ".");
	}

	public String getPermission() {
		return permission;
	}

}
