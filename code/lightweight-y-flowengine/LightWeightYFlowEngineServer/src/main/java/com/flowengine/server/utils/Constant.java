package com.flowengine.server.utils;

/**
 * @Description:
 * @author yangzl 2023.1.8
 * @version 1.00.00
 * @history:
 */
public class Constant {

    public static String ROOT_ROLE_ID = "role_101";

    public static String USER_ADMIN_ID = "admin";

    public interface Column {

        String MAIN_ID = "main_id";

        String NODE_KEY = "node_key";

        public String COLUMN_NAME = "column_name";

        public String DATA_TYPE = "data_type";

        public String TABLE_OP_ID = "table_op_id";

        public String NODE_STATUS = "node_status";

        public String CFG_ID = "cfg_id";

        public String FLOW_ROLE_ID = "flow_role_id";
    }

    public interface Key {

        public String NEXT_NODE = "nextNode";

        public String NEXT_NODE_ID = "nextNodeId";

        public String NEXT_NODE_KEY = "nextNodeKey";

        public String KEY = "key";

        public String REF_TYPE = "refType";

        public String REF_ID = "refId";

        public String NEXT = "next";

        public String BACK = "back";

        public String END = "end";

        /**角色id**/
        public String ROLE_ID = "roleId";

        public String USER_NAME = "userName";

        public String USER_ID = "user_id";

        public String USERID = "userId";

        public String USER_PASSWORD = "user_password";

        public String LIMIT = "limit";

        public String PAGE = "page";

        public String NAME = "name";
        /**用户手机号**/
        public String USER_PHONE = "userPhone";
        /**密码**/
        public String PASSWORD = "password";
        /**旧密码**/
        String OLD_PWD = "oldPwd";
        /**机构ID**/
        public String ORG_ID = "orgId";
        /**性别**/
        public String SEX = "sex";
        /**性别**/
        public String OP_ID = "opId";

        String USER_CATEGORY = "userCategory";
        /**角色id**/
        public static final String MENU_ID = "menuId";

        public String PARENT_ID = "parentId";

        public String MENU_TEXT = "menuText";

        public String MODULE_NAME = "moduleName";

        public String TABLE_NAME = "tableName";

        public String TABLE_NAME_DESC = "tableNameDesc";

        public String SQL = "sql";

        public String DEPT_ID = "deptId";

        public String IS_STOP = "isStop";

        public String LIMIT_TIME = "limitTime";

        public String VALUE = "value";

        public String SUB_CFG_VALUE_TYPE = "subCfgValueType";

        public String CFG_NAME = "cfgName";

        public String KEY_CODE = "keyCode";

        public String LABEL = "label";

        public String COMBOBOX_DESC = "comboboxDesc";

        public String ROLE_NAME = "roleName";

        public String ROLE_TYPE = "roleType";

        public String CHILDREN = "children";

        public String PERSON = "person";

        public String SORT = "sort";

        public String CREATE_TIME = "createTime";

    }

    public interface Value {

        String START = "start";
    }

    public interface Dict {

        String FLOW_ROLE_ROLE_TYPE = "flow_role_role_type";
    }

    public interface Flow {
        String REFERENCE_TABLE_NAME = "referenceTableName";

        String REFERENCE_TABLE_ID = "referenceTableId";

        String FLOW_INSTANCE_TABLE_NAME = "flowInstanceTableName";

        String FLOW_INSTANCE_FLOW_TABLE_NAME = "flowInstanceFlowTableName";

        String FLOW_COMMENT_TABLE_NAME = "flowCommentTableName";

        String NODE_ID = "nodeId";

        public String NODE_NAME = "nodeName";

        public String FLOW_NODE_CHECK_TYPE = "flow_node_check_type";

        public String NODE_KEY = "nodeKey";

        public String NODE_TYPE = "nodeType";

        public String NODE_STATUS = "nodeStatus";

        public String TABLE_TYPE = "tableType";

        public String CHECK_TYPE = "checkType";

        public String INSTANCE_ID = "instanceId";

        public String TASK_OP_ID = "taskOpId";

        public String FLOW_STATUS = "flowStatus";

        public String FLOW_RESULT = "flowResult";

        public String FLOW_SORT = "flowSort";

        public String NEXT_NODE_ID = "nextNodeId";

        public String NEXT_NODE_KEY = "nextNodeKey";

        public String OPERATION_TIME = "operationTime";
    }
}
