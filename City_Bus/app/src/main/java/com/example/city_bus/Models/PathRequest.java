package com.example.city_bus.Models;

public class PathRequest {
    String src;
    String dest;

    public PathRequest(String src, String dest) {
        this.src = src;
        this.dest = dest;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }
}
