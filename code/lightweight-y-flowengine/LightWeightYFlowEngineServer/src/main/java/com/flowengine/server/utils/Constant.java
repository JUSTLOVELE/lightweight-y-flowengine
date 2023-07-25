package com.flowengine.server.utils;

/**
 * @Description:
 * @author yangzl 2023.1.8
 * @version 1.00.00
 * @history:
 */
public class Constant {

    public static String ROOT_ROLE_ID = "root";

    public static String USER_ADMIN_ID = "admin";

    public interface Column {

        String MAIN_ID = "main_id";

        String NODE_KEY = "node_key";

        public String COLUMN_NAME = "column_name";

        public String DATA_TYPE = "data_type";

        public String TABLE_OP_ID = "table_op_id";
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
    }

    public interface Value {

        String START = "start";
    }
}
