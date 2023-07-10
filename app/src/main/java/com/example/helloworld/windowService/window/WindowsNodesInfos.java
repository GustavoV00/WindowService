package com.example.helloworld.windowService.window;

import java.util.Objects;

public class WindowsNodesInfos {

    private WindowTitleInfo title;
    private String packageName;

    public WindowsNodesInfos(WindowTitleInfo title, String packageName) {
        this.title = title;
        this.packageName = packageName;
    }

    public WindowTitleInfo getTitle() {
        return title;
    }

    public void setTitle(WindowTitleInfo title) {
        this.title = title;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WindowsNodesInfos that = (WindowsNodesInfos) o;
        return Objects.equals(title, that.title) && Objects.equals(packageName, that.packageName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, packageName);
    }
}
