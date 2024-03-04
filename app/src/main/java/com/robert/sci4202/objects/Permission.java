package com.robert.sci4202.objects;

public class Permission {
    private String  permissionString;
    private boolean permission;

    public Permission(boolean permission, String permissionString) {
        this.permission = permission;
        this.permissionString = permissionString;
    }

    public boolean getPermission() {
        return permission;
    }

    public void setPermission(boolean permission) {
        this.permission = permission;
    }

    public String getPermissionString() {
        return permissionString;
    }

    public void setPermissionString(String permissionString) {
        this.permissionString = permissionString;
    }
}
