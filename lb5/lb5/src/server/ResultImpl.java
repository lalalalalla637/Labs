package server;

import interfaces.Result;

import java.io.Serializable;

public class ResultImpl implements Result, Serializable {

    private Object output;
    private long scoreTime;

    public ResultImpl(Object output, long scoreTime) {
        this.output = output;
        this.scoreTime = scoreTime;
    }

    public Object output() {
        return output;
    }

    public long scoreTime() {
        return scoreTime;
    }

}
