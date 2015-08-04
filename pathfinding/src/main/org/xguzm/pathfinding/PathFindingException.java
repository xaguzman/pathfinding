package org.xguzm.pathfinding;

/**
 * Created by gdlxguzm on 8/4/2015.
 */
public class PathFindingException extends RuntimeException {

    public PathFindingException(String msg, Throwable cause){
        super(msg, cause);
    }

    public PathFindingException(String msg){
        this(msg, null);
    }
}
