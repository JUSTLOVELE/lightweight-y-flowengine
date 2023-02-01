package com.flowengine.server.utils;

/**
 * @Description:
 * @author yangzl 2023.1.8
 * @version 1.00.00
 * @history:
 */
public class Constant {

    public interface Column {

        String MAIN_ID = "main_id";

        String NODE_KEY = "node_key";
    }

    public interface Key {

        public String NEXT_NODE_ID = "nextNodeId";

        public String NEXT_NODE_KEY = "nextNodeKey";

        public String KEY = "key";

        public String REF_TYPE = "refType";

        public String REF_ID = "refId";

        public String NEXT = "next";

        public String BACK = "back";

        public String END = "end";
    }

    public interface Value {

        String START = "start";
    }
}
