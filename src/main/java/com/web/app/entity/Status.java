package com.web.app.entity;

public enum Status {

    valid(true),
    deleted(false);

    private final boolean isValid;

    Status(boolean isValid) {
         this.isValid = isValid;
    }

    public boolean isValid() {
        return isValid;
    }
}
