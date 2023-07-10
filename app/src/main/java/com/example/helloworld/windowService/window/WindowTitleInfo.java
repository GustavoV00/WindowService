package com.example.helloworld.windowService.window;

import java.util.Objects;

public class WindowTitleInfo {

    private String title;
    private boolean hasTitle;

    public WindowTitleInfo(String title, boolean hasTitle) {
        this.title = title;
        this.hasTitle = hasTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isHasTitle() {
        return hasTitle;
    }

    public void setHasTitle(boolean hasTitle) {
        this.hasTitle = hasTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WindowTitleInfo that = (WindowTitleInfo) o;
        return hasTitle == that.hasTitle && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, hasTitle);
    }
}
